package ru.romansayapin.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.romansayapin.spring.config.MyConfig;
import ru.romansayapin.spring.model.User;

import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);

        Communication communication = context.getBean("communication", Communication.class);
        communication.getAllUser();
        String saveUser = communication.saveUser();
        System.out.println(saveUser);
        User user = new User(3, "Thomas", "Shelby", (byte)30);
        String upUser = communication.updateUser(user);
        System.out.println(upUser);
        String delUser = communication.deleteUser(user);
        System.out.println(delUser);
        System.out.println(saveUser+upUser+delUser);


    }

}
