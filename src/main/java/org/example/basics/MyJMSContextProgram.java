package org.example.basics;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MyJMSContextProgram {

  public static void main(String[] args) throws NamingException {
    InitialContext initialContext = new InitialContext();
    Queue queue = (Queue) initialContext.lookup("queue/myQueue");

    try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        JMSContext jmsContext = connectionFactory.createContext()) {

      jmsContext.createProducer().send(queue, "Through context");
      String receiveBody = jmsContext.createConsumer(queue).receiveBody(String.class);

      System.out.println(receiveBody);
    }
  }
}
