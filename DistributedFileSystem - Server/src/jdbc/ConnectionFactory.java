package jdbc;

import com.almworks.sqlite4java.SQLiteConnection;
import java.io.File;

public class ConnectionFactory {

    private static ConnectionFactory instance = null;

    private ConnectionFactory() {
    }

    public static ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory();
        }

        return instance;
    }

    public SQLiteConnection getConnection(String database) {
        return new SQLiteConnection(new File("database/" + database + ".db"));
    }
}
