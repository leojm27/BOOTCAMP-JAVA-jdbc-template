package org.example;

import org.example.config.JdbcConfig;
import org.example.dao.UserDAO;
import org.example.models.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        System.out.println("JDBC TEMPLATE!");

        ApplicationContext context = new AnnotationConfigApplicationContext(JdbcConfig.class);

        UserDAO userDAO = context.getBean(UserDAO.class);

        // Crear un nuevo usuario
        // User newUser = new User("JohnDoe", "johndoe@example.com");
        // userDAO.createUser(newUser);

        // Obtener usuario por ID
        User user = userDAO.getUserById(9);
        System.out.println(user);

        // Actualizar usuario
        /*
        user.setUsername("JaneDoe");
        userDAO.updateUser(user);

        // Eliminar usuario
        userDAO.deleteUser(9);
        */

        // Listar todos los usuarios
        userDAO.getAllUsers().forEach(System.out::println);
    }
}