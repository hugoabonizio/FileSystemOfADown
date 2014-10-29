package jdbc;

import com.almworks.sqlite4java.SQLiteConnection;
import java.io.File;

public class ConnectionFactory {

    private static ConnectionFactory instance = null;
    private static SQLiteConnection connTempInstance = null;
    private static SQLiteConnection connLocalInstance = null;

    private ConnectionFactory() {
    }

    public static ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory();
        }

        return instance;
    }

    public SQLiteConnection getConnection(String database) {
        if (database == "local") {
            if (connLocalInstance == null) {
                connLocalInstance = new SQLiteConnection(new File("database/" + database + ".db"));
            }
            return connLocalInstance;
        } else {
            if (connTempInstance == null) {
                connTempInstance = new SQLiteConnection(new File("database/" + database + ".db"));
            }
            return connTempInstance;
        }
        
    }
}
