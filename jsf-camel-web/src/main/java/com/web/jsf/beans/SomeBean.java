package com.web.jsf.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

//@ManagedBean(name = "helloBean")
//@SessionScoped
public class SomeBean {

    public String someMethod(String message) {
        System.out.println("Return " +" Hello " + message + " SomeBean");
        return "Hello " + message + " SomeBean";
    }
}
