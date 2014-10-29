package entity;

public class Temporary {

    private int id;
    private String fname;
    private String path;
    private String is_dir;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIs_dir() {
        return is_dir;
    }

    public void setIs_dir(String is_dir) {
        this.is_dir = is_dir;
    }
}
