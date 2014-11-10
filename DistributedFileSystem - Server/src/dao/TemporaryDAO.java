package dao;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import entity.Local;
import entity.Temporary;
import java.util.ArrayList;
import java.util.List;

public class TemporaryDAO {

    private final SQLiteConnection connection;
    private static final String createQuery = "INSERT OR REPLACE INTO files (fname, path, is_dir, owner, ip) VALUES (?, ?, ?, ?, ?)"; // RETURNING id;";
    private static final String deleteQuery = "DELETE FROM files WHERE fname = ? AND path = ? AND owner = ?;";
    private static final String renameQuery = "UPDATE files SET fname = ? WHERE fname = ? AND path = ? AND owner = ?;";
    private static final String mkdirQuery = "INSERT INTO files (fname, path, is_dir, owner, ip) VALUES (?, ?, ?, ?, ?)"; // RETURNING id";
    private static final String readdirQuery = "SELECT * FROM files WHERE path = ? AND owner = ? GROUP BY fname, path, owner;";
    private static final String clearQuery = "DELETE FROM files";
    private static final String allQuery = "SELECT * FROM files";
    private static final String deleteFolderQuery = "DELETE FROM files WHERE path LIKE ? AND owner = ?";
    private static final String getIpQuery = "SELECT ip FROM files WHERE fname = ? AND path = ? AND owner = ?";
    private static final String serverDownQuery = "SELECT * FROM files WHERE ip = ?";
    private static final String serverDownQuery2 = "SELECT * FROM files WHERE fname = ? AND path = ? AND owner = ? AND ip != ? AND ip != ? GROU BY ip";

    public TemporaryDAO(SQLiteConnection connection) {
        this.connection = connection;
    }

    public String read(Local file) throws SQLiteException {
        return null;
    }

    public Integer create(Local file, String ip) throws SQLiteException {
        SQLiteStatement statement = connection.prepare(createQuery);
        statement.bind(1, file.getFname());
        statement.bind(2, file.getPath());
        statement.bind(3, file.getIs_dir());
        statement.bind(4, file.getOwner());
        statement.bind(5, ip);
        /*if (statement.step()) {
            return (Integer) statement.columnValue(0);
        }
        statement.dispose();*/
        statement.step();
        return null;
    }
    
    public Integer create(Temporary file) throws SQLiteException {
        SQLiteStatement statement = connection.prepare(createQuery);
        statement.bind(1, file.getFname());
        statement.bind(2, file.getPath());
        statement.bind(3, file.getIs_dir());
        statement.bind(4, file.getOwner());
        statement.bind(5, file.getIp());
        statement.step();
        return null;
    }

    public void delete(Local file) throws SQLiteException {
        SQLiteStatement statement = connection.prepare(deleteQuery);
        statement.bind(1, file.getFname());
        statement.bind(2, file.getPath());
        statement.bind(3, file.getOwner());
        statement.step();
    }

    public void rename(Local file) throws SQLiteException {
        SQLiteStatement statement = connection.prepare(renameQuery);
        statement.bind(1, file.getNew_fname());
        statement.bind(2, file.getFname());
        statement.bind(3, file.getPath());
        statement.bind(4, file.getOwner());
        statement.step();
    }

    public Integer mkdir(Local file, String ip) throws SQLiteException {
        SQLiteStatement statement = connection.prepare(mkdirQuery);
        statement.bind(1, file.getFname());
        statement.bind(2, file.getPath());
        statement.bind(3, file.getIs_dir());
        statement.bind(4, file.getOwner());
        statement.bind(5, ip);
        /*if (statement.step()) {
            return (Integer) statement.columnValue(0);
        }*/
        statement.step();
        return null;
    }

    public void rmdir(Local file) throws SQLiteException {
        SQLiteStatement statement = connection.prepare(deleteQuery);
        statement.bind(1, file.getFname());
        statement.bind(2, file.getPath());
        statement.bind(3, file.getOwner());
        statement.step();
    }

    public List<Temporary> readdir(Local f) throws SQLiteException {
        List<Temporary> fileList = new ArrayList<>();

        SQLiteStatement statement = connection.prepare(readdirQuery);
        statement.bind(1, f.getPath());
        statement.bind(2, f.getOwner());
        while (statement.step()) {
            Temporary file = new Temporary();
            file.setId((Integer) statement.columnValue(0));
            file.setFname((String) statement.columnValue(1));
            file.setPath(f.getPath());
            file.setIs_dir(Boolean.valueOf((String) statement.columnValue(3)));
            file.setIp((String) statement.columnValue(4));
            file.setOwner(f.getOwner());

            fileList.add(file);
        }

        return fileList;
    }
    
    public void clear() throws SQLiteException {
        SQLiteStatement statement = connection.prepare(clearQuery);
        statement.step();
    }
    
    public List<Temporary> all() throws SQLiteException {
        List<Temporary> fileList = new ArrayList<>();

        SQLiteStatement statement = connection.prepare(allQuery);
        while (statement.step()) {
            Temporary file = new Temporary();
            file.setId((Integer) statement.columnValue(0));
            file.setFname((String) statement.columnValue(1));
            file.setPath((String) statement.columnValue(2));
            file.setIs_dir(Boolean.valueOf((String) statement.columnValue(3)));
            file.setIp((String) statement.columnValue(4));
            file.setOwner((String) statement.columnValue(5));

            fileList.add(file);
        }

        return fileList;
    }
    
    public void deleteFolder(Local file) throws SQLiteException {
        SQLiteStatement statement = connection.prepare(deleteFolderQuery);
        statement.bind(1, file.getPath());
        statement.bind(2, file.getOwner());
        statement.step();
    }
    
    public List<String> getIp(Local f) throws SQLiteException {
        List<String> ipList = new ArrayList<>();

        SQLiteStatement statement = connection.prepare(getIpQuery);
        statement.bind(1, f.getFname());
        statement.bind(2, f.getPath());
        statement.bind(3, f.getOwner());
        while (statement.step()) {
            ipList.add((String) statement.columnValue(0));
        }

        return ipList;
    }
    
    public List<Temporary> serverDown(String ip, String me) throws SQLiteException {
        List<Temporary> temp_list = new ArrayList<>();
        List<Temporary> final_list = new ArrayList<>();
        
        SQLiteStatement statement = connection.prepare(serverDownQuery);
        statement.bind(1, ip);
        while (statement.step()) {
            Temporary file = new Temporary();
            file.setId((Integer) statement.columnValue(0));
            file.setFname((String) statement.columnValue(1));
            file.setPath((String) statement.columnValue(2));
            file.setIs_dir(Boolean.valueOf((String) statement.columnValue(3)));
            file.setIp((String) statement.columnValue(4));
            file.setOwner((String) statement.columnValue(5));

            temp_list.add(file);
        }
        
        for (Temporary t: temp_list) {
            statement = connection.prepare(serverDownQuery2);
            statement.bind(1, t.getFname());
            statement.bind(2, t.getPath());
            statement.bind(3, t.getOwner());
            statement.bind(4, ip);
            statement.bind(5, me);
            while (statement.step()) {
                Temporary file = new Temporary();
                file.setId((Integer) statement.columnValue(0));
                file.setFname((String) statement.columnValue(1));
                file.setPath((String) statement.columnValue(2));
                file.setIs_dir(Boolean.valueOf((String) statement.columnValue(3)));
                file.setIp((String) statement.columnValue(4));
                file.setOwner((String) statement.columnValue(5));

                System.out.println("fname: " + file.getFname());
                
                final_list.add(file);
            }
        }
        return final_list;
    }
}
