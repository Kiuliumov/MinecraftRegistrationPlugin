package cantina.plugin.registrationPlugin.src.Database;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class Database {
    public static Connection connection;
    private static final Set<String> loggedInUsers = new HashSet<>();

    public static void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:players.db");
            Statement stmt = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS players (" +
                    "username TEXT PRIMARY KEY, " +
                    "password TEXT, " +
                    "ip TEXT, " +
                    "lastLogin INTEGER)";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean registerUser(String username, String password, String ip) {
        try {
            String sql = "INSERT INTO players (username, password, ip, lastLogin) VALUES (?, ?, ?, ?)";
            loggedInUsers.add(username);
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, ip);
            stmt.setLong(4, System.currentTimeMillis());
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isUserRegistered(String username) {
        try {
            String sql = "SELECT * FROM players WHERE username = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            boolean exists = rs.next();
            rs.close();
            stmt.close();
            return exists;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean verifyUser(String username, String password) {
        try {
            String sql = "SELECT password FROM players WHERE username = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            boolean isValid = rs.next() && rs.getString("password").equals(password);
            rs.close();
            stmt.close();
            return isValid;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isUserLoggedIn(String username) {
        return loggedInUsers.contains(username);
    }

    public static void setUserLoggedIn(String username, boolean status) {
        if (status) {
            loggedInUsers.add(username);
        } else {
            loggedInUsers.remove(username);
        }
    }

    public static boolean isIpExpired(String username, String currentIp) {
        try {
            String sql = "SELECT ip FROM players WHERE username = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            boolean isExpired = rs.next() && !rs.getString("ip").equals(currentIp);
            rs.close();
            stmt.close();
            return isExpired;
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static void updateUserIp(String username, String newIp) {
        try {
            String sql = "UPDATE players SET ip = ?, lastLogin = ? WHERE username = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, newIp);
            stmt.setLong(2, System.currentTimeMillis());
            stmt.setString(3, username);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
