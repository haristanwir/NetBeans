/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rabbitmqclient;

import com.rabbitmq.client.AMQP;
import java.util.Date;

/**
 *
 * @author Haris Tanwir
 */
public class RabbitMQClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new RabbitMQClient().init();
    }

    public void init() {
        String QUEUE_NAME = "EMPLOYEE";
        RabbitMQConsConnectionPool mqConsumerPool = new RabbitMQConsConnectionPool("localhost", 5672, RabbitMQClient.class.getName(), 10);
        RabbitMQProdConnectionPool mqProducerPool = new RabbitMQProdConnectionPool("localhost", 5672, RabbitMQClient.class.getName(), 10);

        try {
            for (int i = 0; i < 50; i++) {
                AMQP.BasicProperties prop = new AMQP.BasicProperties.Builder()
                        .appId(this.getClass().getName())
                        .clusterId(null)
                        .contentEncoding("UTF-8")
                        .contentType(null)
                        .correlationId(null)
                        .deliveryMode(null)
                        .expiration(null)
                        .headers(null)
                        .messageId(null)
                        .priority(0)
                        .replyTo(null)
                        .timestamp(new Date())
                        .type(null)
                        .userId(null)
                        .build();
                
                mqProducerPool.enqueue("hello world - " + i, QUEUE_NAME, prop);
            }
            mqConsumerPool.shutdown();
            mqProducerPool.shutdown();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
