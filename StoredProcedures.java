import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class StoredProcedures extends Database{
    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:plugins/RegistrationPlugin/data.db");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    ;

    public static boolean udp_delete_user_by_username(String username) {
        query = 
    }
}
