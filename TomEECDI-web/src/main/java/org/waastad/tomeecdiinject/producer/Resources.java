/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.tomeecdiinject.producer;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;
import lombok.extern.slf4j.Slf4j;
import org.waastad.tomeecdiinject.qualifier.Current;
import org.waastad.tomeecdiinject.qualifier.UserCache;
import org.waastad.tomeecdiinject.qualifier.UserCacheType;

/**
 *
 * @author Helge Waastad <helge.waastad@waastad.org>
 */
@ApplicationScoped
@Slf4j
public class Resources {

    @Resource(mappedName = "MyConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Produces
    @ApplicationScoped
    @Resource(mappedName = "jms/myTopic")
    private Topic myTopic;

    @Produces
    @RequestScoped
    public Connection createJmsConnection() throws JMSException {
        System.out.println("Producing JMS Connection...");
        return connectionFactory.createConnection();
    }

    public void closeJmsConnection(@Disposes Connection connection) throws JMSException {
        System.out.println("Disposing JMS Connection...");
        connection.close();
    }

    @Produces
    @RequestScoped
    public Session createJMSSession(Connection connection) throws JMSException {
        System.out.println("Producing JMS Session...");
        return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    public void closeJMSSession(@Disposes Session session) throws JMSException {
        System.out.println("Disposing JMS Session...");
        session.close();
    }

    @Produces
    @RequestScoped
    public MessageProducer createWebCacheTopicProducer(Session session) throws JMSException {
        System.out.println("Producing JMS WebCache MessageProducer...");
        return session.createProducer(myTopic);
    }

    public void closeWebCacheTopicProducer(@Disposes MessageProducer producer) throws JMSException {
        System.out.println("Disposing JMS WebCache MessageProducer...");
        producer.close();
    }

    @Produces
    @ApplicationScoped
    @Current
    public CacheManager getCacheManager() {
        log.info("Producing Web cache manager....");
//        try {
            //URI uri = this.getClass().getClassLoader().getResource("cache.ccf").toURI();
            CachingProvider cachingProvider = Caching.getCachingProvider();
            return cachingProvider.getCacheManager();
//            return cachingProvider.getCacheManager(uri, Thread.currentThread().getContextClassLoader(), cachingProvider.getDefaultProperties());
//        } catch (URISyntaxException e) {
//            log.warn("Failed initializing customer ccf, using default");
//            return Caching.getCachingProvider().getCacheManager();
//        }
    }

    public void disposeCacheManager(@Disposes @Current CacheManager manager) {
        log.info("Disposing Web cache manager...");
        manager.close();
    }

    @Produces
    @UserCache(UserCacheType.WEB_USERAGENT_CACHE_NAME)
    @ApplicationScoped
    public Cache<String, String> createUserAgentCache(@Current CacheManager manager) {
        log.info("Producing Web cache...");
        return manager.createCache(
                "myCache",
                new MutableConfiguration()
                .setStoreByValue(false)
                .setStatisticsEnabled(true)
                .setManagementEnabled(true)
                .setTypes(String.class, String.class)
                .setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(new Duration(TimeUnit.HOURS, 12L))));
    }

    public void disposeUserAgentCache(@Disposes @UserCache(UserCacheType.WEB_USERAGENT_CACHE_NAME) Cache<String, String> cache) {
        log.info("Disposing Web cache...");
        cache.close();
    }

}
