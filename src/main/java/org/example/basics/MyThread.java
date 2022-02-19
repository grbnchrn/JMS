package org.example.basics;

import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class MyThread extends Thread{
  public Thread getNewThread(final MessageProducer messageProducer, final TextMessage textMessage) {
    Thread th =
        new Thread(
            new Runnable() {
              @Override
              public void run() {
                try {
                  Thread.sleep(1000);
                  messageProducer.send(textMessage);
                } catch (Exception e) {
                  e.printStackTrace();
                }
              }
            });
    return th;
    }
}
