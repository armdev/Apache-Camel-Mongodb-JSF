package com.web.jsf.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.camel.CamelContext;
//import javax.enterprise.context.SessionScoped;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@ManagedBean(name = "mongoClientBean")
@ViewScoped
public class MongoClientBean implements Serializable {

    private UserDTO user;
  

    public MongoClientBean() {
        user = new UserDTO();
    }

    public String sendToRoute() {
        try {
            ApplicationContext appContext = new ClassPathXmlApplicationContext("META-INF/spring/context.xml");
            CamelContext camelContext = SpringCamelContext.springCamelContext(appContext, false);
            camelContext.start();
            ProducerTemplate producer = camelContext.createProducerTemplate();
            DBObject obj = new BasicDBObject();
            obj.put("firstname", user.getFirstname());
            obj.put("lastname", user.getLastname());
            obj.put("email", user.getEmail());

            DBObject result = producer.requestBody("direct:start", obj, com.mongodb.DBObject.class);

            camelContext.stop();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "userlist";
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }


}
