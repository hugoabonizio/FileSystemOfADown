package dao;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import java.sql.SQLException;
import jdbc.ConnectionFactory;

public class DAOFactory implements AutoCloseable {

    private SQLiteConnection connection = null;

    public DAOFactory() throws SQLiteException {
        connection = ConnectionFactory.getInstance().getConnection();
        connection.open(true);
    }

    @Override
    public void close() throws SQLException {
        connection.dispose();
    }

    public FileDAO getFileDAO() {
        return new FileDAO(connection);
    }
    
    public TemporaryDAO getTemporaryDAO() {
        return new TemporaryDAO(connection);
    }
}
