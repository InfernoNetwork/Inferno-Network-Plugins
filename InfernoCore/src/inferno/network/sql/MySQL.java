package inferno.network.sql;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQL {
	
	public static Connection connection;
	
    public MySQL(String ip, String userName, String password, String db) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + db + "?user=" + userName + "&password=" + password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
