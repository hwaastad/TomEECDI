/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.tomeecdiinject.jms;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.waastad.tomeecdiinject.controller.ApplicationController;
import org.waastad.tomeecdiinject.qualifier.Current;
import org.waastad.tomeecdiinject.qualifier.UserCache;
import org.waastad.tomeecdiinject.qualifier.UserCacheType;

/**
 *
 * @author Helge Waastad <helge.waastad@waastad.org>
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "destination",
            propertyValue = "jms/myTopic"),
    @ActivationConfigProperty(propertyName = "subscriptionDurability",
            propertyValue = "NonDurable"),
    @ActivationConfigProperty(propertyName = "subscriptionName",
            propertyValue = "TrueUpdate Manager")})
@Slf4j
public class TopicMDB implements MessageListener {
    
    @Inject
    @UserCache(UserCacheType.WEB_USERAGENT_CACHE_NAME)
    Cache<String, String> myCache;
    
    @Override
    public void onMessage(Message msg) {
        System.out.println("Got a topic message....");
        TextMessage textMessage = (TextMessage) msg;
        try {
            myCache.containsKey(textMessage.getText());
        } catch (JMSException ex) {
            log.error("Baaaaad", ex);
        }
    }
    
}
