package entity;

import java.io.Serializable;

public class Temporary implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private int id;
    private String fname;
    private String path;
    private String is_dir;
    private String ip;
    private String owner;

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

    public void setIs_dir(Boolean is_dir) {
        this.is_dir = is_dir.toString();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
