/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esb.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.artemis.api.jms.ActiveMQJMSConstants;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

/**
 *
 * @author Haris Tanwir
 */
public class ActiveMQProdConnectionPool {

    private String ip = null;
    private Integer port = null;
    private Integer poolSize = 0;
    private Long connectionWait = 50L;
    private String connectionUrl = null;
    private ActiveMQConnectionFactory factory = null;
    private HashMap<String, ArrayList<ActiveMQConnection>> activeMQConnectionMap = null;

    public ActiveMQProdConnectionPool(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
        this.connectionUrl = "tcp://" + ip + ":" + port + "?consumerWindowSize=0";
        this.activeMQConnectionMap = new HashMap<String, ArrayList<ActiveMQConnection>>();
        try {
            factory = new ActiveMQConnectionFactory(connectionUrl);
//            factory.setUserName("admin");
//            factory.setPassword("admin");
            factory.setUser(Utility.getProperty("AMQ_USER"));
            factory.setPassword(Utility.getProperty("AMQ_PASSWORD"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ActiveMQConnection getConnection(String queueName) {
        ActiveMQConnection mqConnection = null;
        ArrayList<ActiveMQConnection> mqConnectionList = activeMQConnectionMap.get(queueName);
        if (mqConnectionList == null) {
            mqConnectionList = new ArrayList<ActiveMQConnection>();
        }
        if (!mqConnectionList.isEmpty()) {
            synchronized (mqConnectionList) {
                if (!mqConnectionList.isEmpty()) {
                    mqConnection = mqConnectionList.remove(0);
                } else {
                    try {
                        Connection connection = factory.createConnection();
                        Session session = connection.createSession(false, ActiveMQJMSConstants.INDIVIDUAL_ACKNOWLEDGE);
                        Destination destination = session.createQueue(queueName);
                        MessageProducer producer = session.createProducer(destination);
                        mqConnection = new ActiveMQConnection(connection, session, destination, null, producer);
                        activeMQConnectionMap.put(queueName, mqConnectionList);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } else {
            if (poolSize == 0) {
                synchronized (mqConnectionList) {
                    try {
                        Connection connection = factory.createConnection();
                        Session session = connection.createSession(false, ActiveMQJMSConstants.INDIVIDUAL_ACKNOWLEDGE);
                        Destination destination = session.createQueue(queueName);
                        MessageProducer producer = session.createProducer(destination);
                        mqConnection = new ActiveMQConnection(connection, session, destination, null, producer);
                        activeMQConnectionMap.put(queueName, mqConnectionList);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                while (mqConnection == null) {
                    try {
                        Thread.sleep(connectionWait);
                    } catch (InterruptedException ex) {
                    }
                    if (!mqConnectionList.isEmpty()) {
                        synchronized (mqConnectionList) {
                            if (!mqConnectionList.isEmpty()) {
                                mqConnection = mqConnectionList.remove(0);
                            } else {
                                try {
                                    Connection connection = factory.createConnection();
                                    Session session = connection.createSession(false, ActiveMQJMSConstants.INDIVIDUAL_ACKNOWLEDGE);
                                    Destination destination = session.createQueue(queueName);
                                    MessageProducer producer = session.createProducer(destination);
                                    mqConnection = new ActiveMQConnection(connection, session, destination, null, producer);
                                    activeMQConnectionMap.put(queueName, mqConnectionList);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
        return mqConnection;
    }

    private boolean releaseConnection(String queueName, ActiveMQConnection connection) {
        ArrayList<ActiveMQConnection> mqConnectionList = activeMQConnectionMap.get(queueName);
        if (mqConnectionList != null) {
            synchronized (mqConnectionList) {
                return mqConnectionList.add(connection);
            }
        } else {
            return false;
        }
    }

    public void shutdown() {
        Set<String> queueNames = activeMQConnectionMap.keySet();
        for (String queueName : queueNames) {
            ArrayList<ActiveMQConnection> mqConnectionList = activeMQConnectionMap.remove(queueName);
            synchronized (mqConnectionList) {
                for (ActiveMQConnection mqConnection : mqConnectionList) {
                    try {
                        MessageProducer producer = mqConnection.getProducer();
                        if (producer != null) {
                            try {
                                producer.close();
                            } catch (Exception ex) {
                            }
                            producer = null;
                        }
                    } catch (Exception ex) {
                    }
                    try {
                        MessageConsumer consumer = mqConnection.getConsumer();
                        if (consumer != null) {
                            try {
                                consumer.close();
                            } catch (Exception ex) {
                            }
                            consumer = null;
                        }
                    } catch (Exception ex) {
                    }
                    try {
                        Session session = mqConnection.getSession();
                        if (session != null) {
                            try {
                                session.close();
                            } catch (Exception ex) {
                            }
                            session = null;
                        }
                    } catch (Exception ex) {
                    }
                    try {
                        Connection connection = mqConnection.getConnection();
                        if (connection != null) {
                            try {
                                connection.close();
                            } catch (Exception ex) {
                            }
                            connection = null;
                        }
                    } catch (Exception ex) {
                    }
                    try {
                        Destination destination = mqConnection.getDestination();
                        destination = null;
                    } catch (Exception ex) {
                    }
                }
            }
        }
    }

    private void shutdown(ActiveMQConnection mqConnection) {
        try {
            MessageProducer producer = mqConnection.getProducer();
            if (producer != null) {
                try {
                    producer.close();
                } catch (Exception ex) {
                }
                producer = null;
            }
        } catch (Exception ex) {
        }
        try {
            MessageConsumer consumer = mqConnection.getConsumer();
            if (consumer != null) {
                try {
                    consumer.close();
                } catch (Exception ex) {
                }
                consumer = null;
            }
        } catch (Exception ex) {
        }
        try {
            Session session = mqConnection.getSession();
            if (session != null) {
                try {
                    session.close();
                } catch (Exception ex) {
                }
                session = null;
            }
        } catch (Exception ex) {
        }
        try {
            Connection connection = mqConnection.getConnection();
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ex) {
                }
                connection = null;
            }
        } catch (Exception ex) {
        }
        try {
            Destination destination = mqConnection.getDestination();
            destination = null;
        } catch (Exception ex) {
        }
    }

    private boolean isConnected(ActiveMQConnection mqConnection) {
        boolean connected = true;
        if (mqConnection.getProducer() == null
                || mqConnection.getSession() == null
                || mqConnection.getConnection() == null) {
            connected = false;
        }
        return connected;
    }

    public Boolean enqueue(String message, String queueName, Integer priority) throws Exception {
        ActiveMQConnection mqConnection = getConnection(queueName);
        try {
            while (!isConnected(mqConnection)) {
                Connection connection = factory.createConnection();
                Session session = connection.createSession(false, ActiveMQJMSConstants.INDIVIDUAL_ACKNOWLEDGE);
                Destination destination = session.createQueue(queueName);
                MessageProducer producer = session.createProducer(destination);
                mqConnection = new ActiveMQConnection(connection, session, destination, null, producer);
            }
            TextMessage textMessage = mqConnection.getSession().createTextMessage();
            textMessage.setText(message);
            MessageProducer producer = mqConnection.getProducer();
            producer.send(textMessage, DeliveryMode.PERSISTENT, priority, 0);
            releaseConnection(queueName, mqConnection);
            return true;
        } catch (Exception ex) {
            shutdown(mqConnection);
            throw ex;
        }
    }

}
