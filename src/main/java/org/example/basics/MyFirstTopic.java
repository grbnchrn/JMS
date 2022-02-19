package org.example.basics;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MyFirstTopic {
  public static void main(String[] args) {
    {
      InitialContext initialContext = null;
      Connection connection = null;
      Session session = null;

      try {
        initialContext = new InitialContext();

        ConnectionFactory connectionFactory =
            (ConnectionFactory) initialContext.lookup("ConnectionFactory");

        Topic topic = (Topic) initialContext.lookup("topic/myTopic");

        connection = connectionFactory.createConnection();
        session = connection.createSession();

        MessageProducer messageProducer = session.createProducer(topic);
        connection.start();
        MessageConsumer consumer1 = session.createConsumer(topic);
        MessageConsumer consumer2 = session.createConsumer(topic);

        TextMessage textMessage =
                session.createTextMessage("My msg is now through topic1");
        messageProducer.send(textMessage);


        TextMessage receivedTextMessage1 = (TextMessage) consumer1.receive();
        System.out.println("Message 1 received : " + receivedTextMessage1.getText());

        TextMessage receivedTextMessage2 = (TextMessage) consumer2.receive();
        System.out.println("Message  2 received : " + receivedTextMessage2.getText());



      } catch (NamingException | JMSException e) {
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
}
