package com.web.jsf.mongo;

import com.mongodb.DBObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.camel.CamelContext;
//import javax.enterprise.context.SessionScoped;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.spring.SpringCamelContext;
import org.bson.types.ObjectId;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@ManagedBean(name = "userListBean")
@ViewScoped
public class UserListBean implements Serializable {

    private List<UserDTO> userList = new ArrayList<>();

    public UserListBean() {

    }

    public void init() {
        try {
            ApplicationContext appContext = new ClassPathXmlApplicationContext("META-INF/spring/context.xml");
            CamelContext camelContext = SpringCamelContext.springCamelContext(appContext, false);
            camelContext.start();
            ProducerTemplate producer = camelContext.createProducerTemplate();

            Object findAll = producer.requestBody("direct:findAll", (Object) null);
            // System.out.println("Object " + findAll.toString());

            List<DBObject> resultList = (List<DBObject>) findAll;

            UserDTO userDTO = null;
            for (DBObject dbObject : resultList) {
                userDTO = new UserDTO();
                String email = (String) dbObject.get("email");
                userDTO.setEmail(email);
                String firstname = (String) dbObject.get("firstname");
                userDTO.setFirstname(firstname);

                String lastname = (String) dbObject.get("lastname");
                userDTO.setLastname(lastname);

                ObjectId id = (ObjectId) dbObject.get("_id");
                userDTO.setId(id);
                userList.add(userDTO);

            }

            camelContext.stop();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<UserDTO> getUserList() {

        return userList;
    }

    public void setUserList(List<UserDTO> userList) {
        this.userList = userList;
    }

}
