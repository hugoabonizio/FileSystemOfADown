package entity;

import java.io.Serializable;

public class Local implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String fname;
    private String path;
    private String is_dir;
    private String body;
    private Integer fsize;
    private String ftype;
    private String created_at;
    private String read_at;
    private String updated_at;
    private String owner;
    private String new_fname;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getFsize() {
        return fsize;
    }

    public void setFsize(Integer fsize) {
        this.fsize = fsize;
    }

    public String getFtype() {
        return ftype;
    }

    public void setFtype(String ftype) {
        this.ftype = ftype;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRead_at() {
        return read_at;
    }

    public void setRead_at(String read_at) {
        this.read_at = read_at;
    }

    public String getNew_fname() {
        return new_fname;
    }

    public void setNew_fname(String new_fname) {
        this.new_fname = new_fname;
    }
}
