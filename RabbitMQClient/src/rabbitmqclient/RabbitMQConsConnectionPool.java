/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rabbitmqclient;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Haris Tanwir
 */
public class RabbitMQConsConnectionPool {

    private String ip = null;
    private Integer port = null;
    private Integer poolSize = 0;
    private Long connectionWait = 50L;
    private String connectionName = null;
    private ConnectionFactory factory = null;
    private Connection connection = null;
    private List<Channel> channelPool = null;

    public RabbitMQConsConnectionPool(String ip, Integer port, String connectionName) {
        this.ip = ip;
        this.port = port;
        this.connectionName = connectionName + "(Consumer)";
        this.channelPool = new ArrayList<>();
        try {
            factory = new ConnectionFactory();
            factory.setHost(ip);
            factory.setPort(port);
            connection = factory.newConnection(this.connectionName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RabbitMQConsConnectionPool(String ip, Integer port, String connectionName, Integer poolSize) {
        this.ip = ip;
        this.port = port;
        if (poolSize > this.poolSize) {
            this.poolSize = poolSize;
        }
        this.connectionName = connectionName + "(Consumer)";
        this.channelPool = new ArrayList<>();
        try {
            factory = new ConnectionFactory();
            factory.setHost(ip);
            factory.setPort(port);
            connection = factory.newConnection(this.connectionName);
            for (int i = 0; i < poolSize; i++) {
                channelPool.add(connection.createChannel());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Channel getChannel() {
        Channel channel = null;
        if (!channelPool.isEmpty()) {
            synchronized (channelPool) {
                if (!channelPool.isEmpty()) {
                    channel = channelPool.remove(0);
                } else {
                    try {
                        channel = connection.createChannel();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } else {
            if (poolSize == 0) {
                try {
                    channel = connection.createChannel();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                while (channel == null) {
                    try {
                        Thread.sleep(connectionWait);
                    } catch (InterruptedException ex) {
                    }
                    if (!channelPool.isEmpty()) {
                        synchronized (channelPool) {
                            if (!channelPool.isEmpty()) {
                                channel = channelPool.remove(0);
                            } else {
                                try {
                                    channel = connection.createChannel();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
        return channel;
    }

    private boolean releaseChannel(Channel channel) {
        synchronized (channelPool) {
            return channelPool.add(channel);
        }
    }

    public void shutdown() {
        synchronized (channelPool) {
            for (Channel channel : channelPool) {
                try {
                    channel.close();
                } catch (Exception ex) {
                }
                channel = null;
            }
            try {
                channelPool.clear();
            } catch (Exception ex) {
            }
        }
        try {
            synchronized (connection) {
                if (connection.isOpen()) {
                    connection.close();
                }
            }
        } catch (Exception ex) {
        }
    }

    private void shutdown(Channel channel) {
        try {
            channel.close();
        } catch (Exception ex) {
        }
        channel = null;
    }

    private Boolean isConnected(Channel channel) {
        Boolean connected = true;
        if (connection == null) {
            connected = null;
        } else if (channel == null) {
            connected = false;
        } else if (!connection.isOpen()) {
            connected = null;
        } else if (!channel.isOpen()) {
            connected = false;
        }
        return connected;
    }

    public GetResponse dequeue(String queuename) throws Exception {
        Channel channel = getChannel();
        try {
            while (!isConnected(channel)) {
                channel = connection.createChannel();
            }
            if (channel.isOpen()) {
                channel.queueDeclare(queuename, true, false, false, null);
                GetResponse mqMessage = channel.basicGet(queuename, true);
                releaseChannel(channel);
                return mqMessage;
            } else {
                return null;
            }
        } catch (Exception ex) {
            if (poolSize > 0) {
                releaseChannel(channel);
            } else {
                shutdown(channel);
            }
            throw ex;
        }
    }
}
