/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.tomeecdiinject.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author Helge Waastad <helge.waastad@waastad.org>
 */
@Named
@ApplicationScoped
public class ApplicationController {

    private String name = "dsfsdf";

    @PostConstruct
    public void init() {
        System.out.println("INIT App Controller");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
