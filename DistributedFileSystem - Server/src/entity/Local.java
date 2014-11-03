package entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Local implements Serializable {

    private Integer id;
    private String fname;
    private String path;
    private Boolean is_dir;
    private String body;
    private Integer fsize;
    private String ftype;
    private Timestamp created_at;
    private Timestamp read_at;
    private Timestamp updated_at;
    private String owner;

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

    public Boolean getIs_dir() {
        return is_dir;
    }

    public void setIs_dir(Boolean is_dir) {
        this.is_dir = is_dir;
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

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Timestamp getRead_at() {
        return read_at;
    }

    public void setRead_at(Timestamp read_at) {
        this.read_at = read_at;
    }
}
