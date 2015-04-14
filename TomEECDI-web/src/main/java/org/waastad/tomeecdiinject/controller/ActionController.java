/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.tomeecdiinject.controller;

import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.waastad.tomeecdiinject.jms.JmsService;

/**
 *
 * @author Helge Waastad <helge.waastad@waastad.org>
 */
@Named
@RequestScoped
public class ActionController {

    @Inject
    private JmsService jmsService;

    public void action(ActionEvent event) {
        jmsService.sendTopicMessage("asdSADGSDFGBD");
    }

}
