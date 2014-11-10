package util;

import java.io.Serializable;

public class Message implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String src;
    private String mainSrc;
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

    public String getMainSrc() {
        return mainSrc;
    }

    public void setMainSrc(String mainSrc) {
        this.mainSrc = mainSrc;
    }

    public enum Action {

        CONNECT_SERVER, CONNECT_CLIENT, READ,
        WRITE, CREATE, DELETE, GET_ATTRIBUTES, SET_ATTRIBUTES,
        RENAME, MKDIR, RMDIR, READDIR, ERROR, PING, PING_ACK,
        CREATE_TEMP, RENAME_TEMP, DELETE_TEMP, DISCONNECT, SERVER_DOWN
    }
}
