package util;

import frame.Frame;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connection implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private ObjectOutputStream output;
    private ObjectInputStream input;
    private int port;
    private Socket socket;
    private String ip;

    public ObjectOutputStream getOutput() {
        return output;
    }

    public void setOutput(ObjectOutputStream output) {
        this.output = output;
    }

    public ObjectInputStream getInput() {
        return input;
    }

    public void setInput(ObjectInputStream input) {
        this.input = input;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.output);
        hash = 59 * hash + Objects.hashCode(this.input);
        hash = 59 * hash + this.port;
        hash = 59 * hash + Objects.hashCode(this.socket);
        hash = 59 * hash + Objects.hashCode(this.ip);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Connection other = (Connection) obj;
        if (this.port != other.port) {
            return false;
        }
        return Objects.equals(this.ip, other.ip);
    }

    public static void send(String dest, Object obj) throws IOException {
        String ip = dest.split(":")[0];
        int port = new Integer(dest.split(":")[1]);
        try {
            Socket socket = new Socket(ip, port);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(obj);
            Thread.sleep(200);
            socket.close();
        } catch (IOException ex) {
            throw ex;
        } catch (InterruptedException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void send(Message obj) throws IOException {
        output.writeObject(obj);
    }

    public static void trySendRequest(Object objects, Message m, boolean isConnection) {
        if (isConnection) {
            for (Connection c : (List<Connection>) objects) {
                try {
                    Connection.send(c.getIp() + ":" + c.getPort(), m);
                    break;
                } catch (IOException ex) {
                    Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            for (String s : (List<String>) objects) {
                try {
                    Connection.send(s, m);
                    break;
                } catch (IOException ex) {
                    Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
