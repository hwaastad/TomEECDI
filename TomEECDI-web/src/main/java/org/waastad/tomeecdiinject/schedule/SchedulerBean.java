/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.tomeecdiinject.schedule;

import java.util.UUID;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import org.waastad.tomeecdiinject.jms.JmsService;

/**
 *
 * @author Helge Waastad <helge.waastad@waastad.org>
 */
@Singleton
public class SchedulerBean {
    
    @Inject
    private JmsService jmsService;
    
    @Schedule(hour = "*", minute = "*", second = "*/10")
    private void doStuff() {
        jmsService.sendTopicMessage(UUID.randomUUID().toString());
    }
}
