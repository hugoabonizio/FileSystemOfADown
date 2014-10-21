package util;

import java.io.Serializable;

public class Message implements Serializable {

    private String src;
    private Object data;
    private Action action;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public enum Action {
        CONNECT, READ, WRITE, CREATE, DELETE, GET_ATTRIBUTES, SET_ATTRIBUTES, RENAME, MKDIR, RMDIR, READDIR, ERROR, PING
    }
}