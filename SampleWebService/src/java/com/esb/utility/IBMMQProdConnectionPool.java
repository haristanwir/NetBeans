/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esb.utility;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
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

/**
 *
 * @author Haris Tanwir
 */
public class IBMMQProdConnectionPool {

    private String ip = null;
    private Integer port = null;
    private String mqChannelName = null;
    private String mqManagerName = null;
    private Integer poolSize = 0;
    private Long connectionWait = 50L;
    private MQQueueConnectionFactory factory = null;
    private HashMap<String, ArrayList<IBMMQConnection>> ibmMQConnectionMap = null;

    public IBMMQProdConnectionPool(String ip, Integer port, String mqManagerName, String mqChannelName) {
        this.ip = ip;
        this.port = port;
        this.mqManagerName = mqManagerName;
        this.mqChannelName = mqChannelName;
        this.ibmMQConnectionMap = new HashMap<String, ArrayList<IBMMQConnection>>();
        try {
            factory = new MQQueueConnectionFactory();
            factory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
            factory.setHostName(this.ip);
            factory.setPort(this.port);
            factory.setQueueManager(this.mqManagerName);
            factory.setChannel(this.mqChannelName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private IBMMQConnection getConnection(String queueName) {
        IBMMQConnection mqConnection = null;
        ArrayList<IBMMQConnection> mqConnectionList = ibmMQConnectionMap.get(queueName);
        if (mqConnectionList == null) {
            mqConnectionList = new ArrayList<IBMMQConnection>();
        }
        if (!mqConnectionList.isEmpty()) {
            synchronized (mqConnectionList) {
                if (!mqConnectionList.isEmpty()) {
                    mqConnection = mqConnectionList.remove(0);
                } else {
                    try {
                        Connection connection = factory.createConnection();
                        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                        Destination destination = session.createQueue(queueName);
                        MessageProducer producer = session.createProducer(destination);
                        mqConnection = new IBMMQConnection(connection, session, destination, null, producer);
                        ibmMQConnectionMap.put(queueName, mqConnectionList);
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
                        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                        Destination destination = session.createQueue(queueName);
                        MessageProducer producer = session.createProducer(destination);
                        mqConnection = new IBMMQConnection(connection, session, destination, null, producer);
                        ibmMQConnectionMap.put(queueName, mqConnectionList);
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
                                    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                                    Destination destination = session.createQueue(queueName);
                                    MessageProducer producer = session.createProducer(destination);
                                    mqConnection = new IBMMQConnection(connection, session, destination, null, producer);
                                    ibmMQConnectionMap.put(queueName, mqConnectionList);
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

    private boolean releaseConnection(String queueName, IBMMQConnection connection) {
        ArrayList<IBMMQConnection> mqConnectionList = ibmMQConnectionMap.get(queueName);
        if (mqConnectionList != null) {
            synchronized (mqConnectionList) {
                return mqConnectionList.add(connection);
            }
        } else {
            return false;
        }
    }

    public void shutdown() {
        Set<String> queueNames = ibmMQConnectionMap.keySet();
        for (String queueName : queueNames) {
            ArrayList<IBMMQConnection> mqConnectionList = ibmMQConnectionMap.remove(queueName);
            synchronized (mqConnectionList) {
                for (IBMMQConnection mqConnection : mqConnectionList) {
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

    private void shutdown(IBMMQConnection mqConnection) {
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

    private boolean isConnected(IBMMQConnection mqConnection) {
        boolean connected = true;
        if (mqConnection.getProducer() == null
                || mqConnection.getSession() == null
                || mqConnection.getConnection() == null) {
            connected = false;
        }
        return connected;
    }

    public Boolean enqueue(String message, String queueName, Integer priority) throws Exception {
        IBMMQConnection mqConnection = getConnection(queueName);
        try {
            while (!isConnected(mqConnection)) {
                Connection connection = factory.createConnection();
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                Destination destination = session.createQueue(queueName);
                MessageProducer producer = session.createProducer(destination);
                mqConnection = new IBMMQConnection(connection, session, destination, null, producer);
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
