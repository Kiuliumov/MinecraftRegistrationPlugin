import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
    private static Connection connection;

    public static void connect(){
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:plugins/RegistrationPlugin/data.db");
            String query = "CREATE TABLE IF NOT EXISTS users(id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR(255) NOT NULL UNIQUE, password VARCHAR(255)) NOT NULL";
            connection.createStatement().execute(query);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static boolean registerUser(String username, String password){
        try{

            if (isUserRegistered(username)){
                throw new UserRegisteredException();
            }


            String query = "INSERT INTO users(username, password) VALUES(?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

        } catch(SQLException e) {
            e.printStackTrace();
        } catch (UserRegisteredException e){
            return false;
        }
        return true;
    }

    private static boolean isUserRegistered(String username) {
        try {
            var statement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            statement.setString(1, username);
            return statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

