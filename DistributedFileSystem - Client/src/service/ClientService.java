package service;

import frame.Frame;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Level;
import java.util.logging.Logger;
import thread.AreYouStillAlive;
import thread.WaitConnection;
import util.Connection;
import util.Message;
import util.Message.Action;

public class ClientService {

    private Frame frame;
    private String owner;
    private String me;
    private ServerSocket meSS;
    private Connection server;
    private Set<String> other_servers;
    //public long lastPING = System.currentTimeMillis();
    
    public ClientService(Frame frame, String owner, String serverAddress, int mePort) {
        this.frame = frame;
        this.owner = owner;
        this.other_servers = new CopyOnWriteArraySet<>();

        listen(mePort);
        try {
            connectToServer(serverAddress);
        } catch (IOException ex) {
            System.exit(0);
        }
        ping();
    }

    public void connectToServer(String serverAddress) throws IOException {
        Socket socket;
        try {
            String ip = serverAddress.split(":")[0];
            int port = Integer.parseInt(serverAddress.split(":")[1]);

            socket = new Socket(ip, port);
            server = new Connection();
            server.setOutput(new ObjectOutputStream(socket.getOutputStream()));
            server.setInput(new ObjectInputStream(socket.getInputStream()));
            server.setIp(ip);
            server.setPort(port);
            server.setSocket(socket);

            Message message = new Message();
            message.setSrc(me);
            message.setAction(Action.CONNECT_CLIENT);
            server.send(message);
        } catch (ArrayIndexOutOfBoundsException | NullPointerException | NumberFormatException ex) {
            //Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        } catch (IOException ex) {
            throw ex;
        }
    }

    private void listen(int mePort) {
        try {
            me = Inet4Address.getLocalHost().getHostAddress() + ":" + mePort;
            System.out.println("Escutando em: " + me);
            
            meSS = new ServerSocket(mePort);
            new Thread(new WaitConnection(this, meSS)).start();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void ping() {
        new Thread(new AreYouStillAlive(this)).start();
    }

    public String getMe() {
        return me;
    }

    public void setMe(String me) {
        this.me = me;
    }

    public ServerSocket getMeSS() {
        return meSS;
    }

    public void setMeSS(ServerSocket meSS) {
        this.meSS = meSS;
    }

    public Connection getServer() {
        return server;
    }

    public void setServer(Connection server) {
        this.server = server;
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public Set<String> getOther_servers() {
        return other_servers;
    }

    public void setOther_servers(Set<String> other_servers) {
        this.other_servers = other_servers;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
