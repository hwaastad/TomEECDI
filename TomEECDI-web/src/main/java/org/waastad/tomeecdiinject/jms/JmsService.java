/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.tomeecdiinject.jms;

import java.io.Serializable;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 *
 * @author Helge Waastad <helge.waastad@waastad.org>
 */
public class JmsService implements Serializable {

    private static final long serialVersionUID = -8751042612059402727L;
    @Inject
    private Session session;

    @Inject
    private MessageProducer producer;

    public void sendTopicMessage(String message) {
        try {
            TextMessage textMessage = session.createTextMessage(message);
            producer.send(textMessage);
        } catch (JMSException e) {
            e.printStackTrace();
            System.out.println("Error sending JMS");
        }
    }
}
