package service;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import thread.WaitConnection;
import dao.LocalDAO;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Connection;
import util.Message;

public class ServerService {

    private String me;
    private ServerSocket meSS;
    private Set<Connection> serverSet;
    private SQLiteConnection localConnection;
    private LocalDAO localDAO;

    public ServerService(List<String> servers, int mePort) {
        try {
            localConnection = new SQLiteConnection(new java.io.File("database/local.db"));
            localConnection.open(true);
            localDAO = new LocalDAO(localConnection);
        } catch (SQLiteException ex) {
            Logger.getLogger(ServerService.class.getName()).log(Level.SEVERE, null, ex);
        }
        serverSet = new HashSet<>();

        for (String s : servers) {
            connectToServer(s);
        }
        listen(mePort);
    }

    private void connectToServer(String server) throws NumberFormatException {
        Socket socket;
        try {
            String ip = server.split(":")[0];
            int port = Integer.parseInt(server.split(":")[1]);

            socket = new Socket(ip, port);
            Connection connection = new Connection();
            connection.setOutput(new ObjectOutputStream(socket.getOutputStream()));
            connection.setInput(new ObjectInputStream(socket.getInputStream()));
            connection.setIp(ip);
            connection.setPort(port);
            connection.setSocket(socket);

            Message message = new Message();
            try {
                message.setSrc(me);
                message.setData(localDAO.all());
                message.setAction(Message.Action.CONNECT_SERVER);
                connection.send(message);
            } catch (SQLiteException ex) {
                Logger.getLogger(ServerService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listen(int port) {
        try {
            me = Inet4Address.getLocalHost().getHostAddress() + ":" + port;
            System.out.println("Server: " + me);

            meSS = new ServerSocket(port);
            new Thread(new WaitConnection(this)).start();
        } catch (IOException ex) {
            Logger.getLogger(ServerService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ServerSocket getMeSS() {
        return meSS;
    }

    public void setMeSS(ServerSocket meSS) {
        this.meSS = meSS;
    }

    public String getMe() {
        return me;
    }

    public void setMe(String me) {
        this.me = me;
    }

    public Set<Connection> getServerSet() {
        return serverSet;
    }

    public void setServerSet(Set<Connection> serverSet) {
        this.serverSet = serverSet;
    }
}
