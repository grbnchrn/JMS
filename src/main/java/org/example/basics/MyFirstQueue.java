package org.example.basics;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MyFirstQueue {

  public static void main(String[] args) {
    InitialContext initialContext = null;
    Connection connection = null;
    Session session = null;


    try {
      initialContext = new InitialContext();

      ConnectionFactory connectionFactory =
          (ConnectionFactory) initialContext.lookup("ConnectionFactory");
      Queue queue = (Queue) initialContext.lookup("queue/myQueue");

      connection = connectionFactory.createConnection();
      session = connection.createSession();

      MessageProducer messageProducer = session.createProducer(queue);


        for (int i = 0; i < 5; i++) {
            TextMessage textMessage = (TextMessage)session.createTextMessage("My msg " +i);
            messageProducer.send(textMessage);

        }


      Thread.sleep(20000);
      MessageConsumer consumer = session.createConsumer(queue);
      connection.start();

      for (int i = 0; i < 5; i++) {
        Thread.sleep(100);
        TextMessage receivedTextMessage = (TextMessage) consumer.receive();
        System.out.println("Message received : " + receivedTextMessage.getText());

      }

      Thread.sleep(20000);
    } catch (NamingException | JMSException | InterruptedException e) {
      e.printStackTrace();
    } finally {
      try {
        session.close();
        connection.close();
        initialContext.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
