package cantina.plugin.registrationPlugin.src.Database;

import java.sql.*;

public class StoredProcedures extends Database{

    public static boolean udp_delete_user_by_username(String username) {
        String query = "DELETE FROM users WHERE username = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean udp_update_user_password(String username, String newPassword) {
        String query = "UPDATE users SET password = ? WHERE username = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newPassword);
            statement.setString(2, username);
            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String udp_get_user_details(String username) {
        String query = "SELECT id, username, password FROM users WHERE username = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("username");
                String password = resultSet.getString("password");

                return "ID: " + id + ", Username: " + name + ", Password: " + password;
            } else {
                return "User not found!";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error retrieving user data.";
        }
    }
    public static int udp_get_user_count(){

        String query = "SELECT COUNT(*) AS total FROM users";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

