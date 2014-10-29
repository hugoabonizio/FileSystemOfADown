package dao;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import entity.File;
import entity.Temporary;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TemporaryDAO {

    private final SQLiteConnection connection;
    private static final String createQuery = "INSERT INTO files (fname, path, is_dir) VALUES (?, ?, ?) RETURNING id;";
    private static final String deleteQuery = "DELETE FROM files WHERE fname = ? AND path = ?;";
    private static final String renameQuery = "UPDATE files SET fname = ? WHERE id = ?;";
    private static final String mkdirQuery = "INSERT INTO files (fname, path, is_dir) VALUES (?, ?, ?) RETURNING id";
    private static final String readdirQuery = "SELECT * FROM files WHERE path = ?;";

    public TemporaryDAO(SQLiteConnection connection) {
        this.connection = connection;
    }

    public String read(File file) throws SQLiteException {
        return null;
    }

    public Integer create(File file) throws SQLiteException {
        SQLiteStatement statement = connection.prepare(createQuery);
        statement.bind(1, file.getFname());
        statement.bind(2, file.getPath());
        statement.bind(3, file.getIs_dir().toString());
        if (statement.step()) {
            return (Integer) statement.columnValue(0);
        }
        return null;
    }

    public void delete(File file) throws SQLiteException {
        SQLiteStatement statement = connection.prepare(deleteQuery);
        statement.bind(1, file.getFname());
        statement.bind(2, file.getPath());
        statement.step();
    }

    public void rename(File file) throws SQLiteException {
        SQLiteStatement statement = connection.prepare(renameQuery);
        statement.bind(1, file.getFname());
        statement.bind(2, file.getUpdated_at().toString());
        statement.bind(3, file.getId());
        statement.step();
    }

    public Integer mkdir(File file) throws SQLiteException {
        SQLiteStatement statement = connection.prepare(mkdirQuery);
        statement.bind(1, file.getFname());
        statement.bind(2, file.getPath());
        statement.bind(3, file.getIs_dir().toString());
        if (statement.step()) {
            return (Integer) statement.columnValue(0);
        }
        return null;
    }

    public void rmdir(File file) throws SQLiteException {
        SQLiteStatement statement = connection.prepare(deleteQuery);
        statement.bind(1, file.getFname());
        statement.bind(2, file.getPath());
        statement.step();
    }

    public List<Temporary> readdir(String path) throws SQLiteException {
        List<Temporary> fileList = new ArrayList<>();

        SQLiteStatement statement = connection.prepare(readdirQuery);
        statement.bind(1, path);
        while (statement.step()) {
            Temporary file = new Temporary();
            file.setId((Integer) statement.columnValue(0));
            file.setFname((String) statement.columnValue(1));
            file.setIs_dir(new Boolean((String) statement.columnValue(3)));

            fileList.add(file);
        }

        return fileList;
    }
}
