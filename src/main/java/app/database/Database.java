package app.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
        private final static String DBURL = System.getenv("DB_URL");
    private final static String DBUSER = System.getenv("DB_USER");
    private final static String DBPASS = System.getenv("DB_PASSWORD");
    public static Connection GetConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
    public static void StopConnection(Connection connection){
        try{
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
