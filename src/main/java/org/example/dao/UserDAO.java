package org.example.dao;

import org.example.models.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDAO {

    private final JdbcTemplate jdbcTemplate;

    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<User> userRowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(rs.getInt("id"), rs.getString("username"), rs.getString("email"));
        }
    };

    // Create (Insertar un nuevo usuario)
    public void createUser(User user) {
        String sql = "INSERT INTO users (username, email) VALUES (?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getEmail());
        System.out.println("Usuario creado exitosamente.");
    }

    // Read (Obtener un usuario por ID)
    public User getUserById(int id) {
        String sql = "SELECT * from users WHERE id = ?";
        // return jdbcTemplate.queryForObject(sql, userRowMapper, id); // FALLA cuando no encuentra el registro

        try {
            return jdbcTemplate.queryForObject(sql, userRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            // Manejo del caso en que no se encuentra el usuario
            System.out.println(STR."No se encontró ningún usuario con el ID: \{id}");
            return null;  // O lanza una excepción personalizada si prefieres
        }
    }

    // Update (Actualizar un usuario)
    public void updateUser(User user) {
        String sql = "UPDATE users SET username = ?, email = ? WHERE id = ?";
        int affectedRows = jdbcTemplate.update(sql, user.getUsername(), user.getEmail(), user.getId());
        if (affectedRows > 0) {
            System.out.println("Usuario actualizado exitosamente.");
        } else {
            System.out.println(STR."No se encontró el usuario con el id: \{user.getId()}");
        }
    }

    // Delete (Eliminar un usuario por ID)
    public void deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        int affectedRows = jdbcTemplate.update(sql, id);
        if (affectedRows > 0) {
            System.out.println("Usuario eliminado exitosamente.");
        } else {
            System.out.println(STR."No se encontró el usuario con el id: \{id}");
        }
    }

    // Listar todos los usuarios
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, userRowMapper);
    }
}
