package com.web.jsf.beans;

import org.apache.camel.builder.RouteBuilder;

public class MyRouteBuilder extends RouteBuilder {

   
    @Override
    public void configure() throws Exception {
        from("direct:start").bean(SomeBean.class, "someMethod(${body})");

        //from("direct:start").bean("helloBean");
    }
}
