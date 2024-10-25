package com.tbond.yumdash.test;

public class BeansTester {
    private final ShowBean firstBean;
    private final ShowBean secondBean;


    public BeansTester(ShowBean firstBean, ShowBean secondBean) {
        this.firstBean = firstBean;
        this.secondBean = secondBean;
    }

    public void compareBeans() {
        System.out.println("Compared beans is:" + (firstBean == secondBean));
    }
}
