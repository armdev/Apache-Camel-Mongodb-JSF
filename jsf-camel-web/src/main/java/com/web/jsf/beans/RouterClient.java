package com.web.jsf.beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.camel.CamelContext;
//import javax.enterprise.context.SessionScoped;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;

@ManagedBean(name = "routerClient")
@SessionScoped
public class RouterClient implements Serializable {

  
    private CamelContext camelctx;

    public RouterClient() {
    }

    private String message;

    public void sendToRoute() {
        try {
            camelctx = new DefaultCamelContext();
            camelctx.start();
            try {
                camelctx.addRoutes(new MyRouteBuilder());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            ProducerTemplate producer = camelctx.createProducerTemplate();
            String result = producer.requestBody("direct:start", message, String.class);
             System.out.println("result " +result);
            camelctx.stop();
        } catch (Exception ex) {
            
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

   

}
