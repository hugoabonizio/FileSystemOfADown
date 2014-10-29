package dao;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import entity.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class FileDAO {

    private final SQLiteConnection connection;
    private static final String readQuery = "SELECT body FROM files WHERE fname = ? AND path = ?;";
    private static final String writeQuery = "UPDATE files SET body = ?, fsize = ?, updated_at = ? WHERE id = ?;";
    private static final String createQuery = "INSERT INTO files (fname, path, is_dir, body, fsize, ftype, created_at, read_at, updated_at, owner) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id;";
    private static final String deleteQuery = "DELETE FROM files WHERE fname = ? AND path = ?;";
    private static final String getAttributesQuery = "SELECT id, is_dir, fsize, ftype, created_at, read_at, updated_at, owner FROM files WHERE fname = ? AND path = ?;";
    //private static final String setAttributesQuery = "UPDATE file SET owner = ?, updated_at = ? WHERE id = ?;";
    private static final String renameQuery = "UPDATE files SET fname = ?, updated_at = ? WHERE id = ?;";
    private static final String mkdirQuery = "INSERT INTO files (fname, path, is_dir, fsize, created_at, read_at, updated_at, owner) VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
    private static final String readdirQuery = "SELECT * FROM files WHERE path = ?;";
    private static final String updateRead_atQuery = "UPDATE files SET read_at = ? WHERE fname = ? AND path = ?;";
    private static final String allQuery = "SELECT * FROM files;";

    public FileDAO(SQLiteConnection connection) {
        this.connection = connection;
    }

    public String read(File file) throws SQLiteException {
        String body = null;

        SQLiteStatement statement = connection.prepare(readQuery);
        statement.bind(1, file.getFname());
        statement.bind(2, file.getPath());
        if (statement.step()) {
            body = (String) statement.columnValue(4);
        }
        updateRead_at(file);

        return body;
    }

    public void write(File file) throws SQLiteException {
        SQLiteStatement statement = connection.prepare(writeQuery);
        statement.bind(1, file.getBody());
        statement.bind(2, file.getFsize());
        statement.bind(3, file.getUpdated_at().toString());
        statement.bind(4, file.getId());
        statement.step();
    }

    public Integer create(File file) throws SQLiteException {
        SQLiteStatement statement = connection.prepare(createQuery);
        statement.bind(1, file.getFname());
        statement.bind(2, file.getPath());
        statement.bind(3, file.getIs_dir().toString());
        statement.bind(4, file.getBody());
        statement.bind(5, file.getFsize());
        statement.bind(6, file.getFtype());
        statement.bind(7, file.getCreated_at().toString());
        statement.bind(8, file.getRead_at().toString());
        statement.bind(9, file.getUpdated_at().toString());
        statement.bind(10, file.getOwner());
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

    public File getAttributes(File arg) throws SQLiteException {
        File file = null;

        SQLiteStatement statement = connection.prepare(getAttributesQuery);
        statement.bind(1, arg.getFname());
        statement.bind(2, arg.getPath());
        if (statement.step()) {
            file = new File();
            file.setId((Integer) statement.columnValue(0));
            file.setIs_dir((Boolean) statement.columnValue(3));
            file.setFsize((Integer) statement.columnValue(5));
            file.setFtype((String) statement.columnValue(6));
            file.setCreated_at((Timestamp) statement.columnValue(7));
            file.setRead_at((Timestamp) statement.columnValue(8));
            file.setUpdated_at((Timestamp) statement.columnValue(9));
            file.setOwner((String) statement.columnValue(10));
        }
        updateRead_at(arg);

        return file;
    }

    /*public void setAttributes(File file) throws SQLiteException {
     SQLiteStatement statement = connection.prepare(setAttributesQuery);
     statement.bind(1, file.getOwner());
     statement.bind(2, file.getUpdated_at().toString());
     statement.bind(3, file.getId());
     statement.step();
     }*/
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
        statement.bind(4, file.getFsize());
        statement.bind(5, file.getCreated_at().toString());
        statement.bind(6, file.getRead_at().toString());
        statement.bind(7, file.getUpdated_at().toString());
        statement.bind(8, file.getOwner());
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

    public List<File> readdir(String path) throws SQLiteException {
        List<File> fileList = new ArrayList<>();

        SQLiteStatement statement = connection.prepare(readdirQuery);
        statement.bind(1, path);
        while (statement.step()) {
            File file = new File();
            file.setId((Integer) statement.columnValue(0));
            file.setFname((String) statement.columnValue(1));
            file.setIs_dir((Boolean) statement.columnValue(3));
            file.setBody((String) statement.columnValue(4));
            file.setFsize((Integer) statement.columnValue(5));
            file.setFtype((String) statement.columnValue(6));
            file.setCreated_at((Timestamp) statement.columnValue(7));
            file.setRead_at((Timestamp) statement.columnValue(8));
            file.setUpdated_at((Timestamp) statement.columnValue(9));
            file.setOwner((String) statement.columnValue(10));

            fileList.add(file);
        }

        return fileList;
    }

    private void updateRead_at(File file) throws SQLiteException {
        SQLiteStatement statement = connection.prepare(updateRead_atQuery);
        statement.bind(1, file.getRead_at().toString());
        statement.bind(2, file.getFname());
        statement.bind(3, file.getPath());
        statement.step();
    }
    
    public List<File> all() throws SQLiteException {
        List<File> fileList = new ArrayList<>();

        SQLiteStatement statement = connection.prepare(allQuery);
        while (statement.step()) {
            File file = new File();
            file.setId((Integer) statement.columnValue(0));
            file.setFname((String) statement.columnValue(1));
            file.setPath((String) statement.columnValue(2));
            file.setIs_dir((Boolean) statement.columnValue(3));
            file.setBody((String) statement.columnValue(4));
            file.setFsize((Integer) statement.columnValue(5));
            file.setFtype((String) statement.columnValue(6));
            file.setCreated_at((Timestamp) statement.columnValue(7));
            file.setRead_at((Timestamp) statement.columnValue(8));
            file.setUpdated_at((Timestamp) statement.columnValue(9));
            file.setOwner((String) statement.columnValue(10));

            fileList.add(file);
        }

        return fileList;
    }
}
