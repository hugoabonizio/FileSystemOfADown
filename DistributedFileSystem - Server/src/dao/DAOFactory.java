package dao;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import java.sql.SQLException;
import jdbc.ConnectionFactory;

public class DAOFactory implements AutoCloseable {

    private SQLiteConnection connection = null;

    @Override
    public void close() throws SQLException {
        //connection.dispose();
    }

    public FileDAO getFileDAO() throws SQLiteException {
        connection = ConnectionFactory.getInstance().getConnection("local");
        connection.open(true);
        return new FileDAO(connection);
    }
    
    public TemporaryDAO getTemporaryDAO() throws SQLiteException {
        connection = ConnectionFactory.getInstance().getConnection("temporary");
        connection.open(true);
        return new TemporaryDAO(connection);
    }
}
