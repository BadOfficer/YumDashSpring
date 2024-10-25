package com.tbond.yumdash.test;


public class ShowBean {

    public ShowBean() {
        System.out.println("Bean of class - " + this.getClass().getName());
    }

    public void show() {
        System.out.println("Showing bean");
    }
}
