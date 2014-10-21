package service;

import frame.Frame;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import thread.ClientThread;
import util.Connection;
import util.Message;
import util.Message.Action;

public class ClientService
{

    private String me;
    private ServerSocket server;
    private Connection connection;

    public ClientService(String monitorAddress, int mePort)
    {
        connect(monitorAddress);
        listen(mePort);
    }

    public final void connect(String address) throws NumberFormatException
    {
        Socket socket;
        try
        {
            String ip = address.split(":")[0];
            int port = Integer.parseInt(address.split(":")[1]);
            
            socket = new Socket(ip, port);
            connection = new Connection();
            connection.setOutput(new ObjectOutputStream(socket.getOutputStream()));
            connection.setInput(new ObjectInputStream(socket.getInputStream()));
            connection.setIp(ip);
            connection.setPort(port);
            connection.setSocket(socket);
            
            Message message = new Message();
            message.setSrc(me);
            message.setData("I'm client");
            message.setAction(Action.CONNECT);
            connection.send(message);
        }catch (IOException ex)
        {
            Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listen(int mePort)
    {
        try
        {
            me = Inet4Address.getLocalHost().getHostAddress() + ":" + mePort;
            server = new ServerSocket(mePort);
            
            Socket socket;
            while (true || !false)
            {
                socket = server.accept();
                new Thread(new ClientThread(this, socket)).start();
            }
        } catch (UnknownHostException ex)
        {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getMe()
    {
        return me;
    }

    public void setMe(String me)
    {
        this.me = me;
    }

    public ServerSocket getServer()
    {
        return server;
    }

    public void setServer(ServerSocket server)
    {
        this.server = server;
    }

    public Connection getConnection()
    {
        return connection;
    }

    public void setConnection(Connection connection)
    {
        this.connection = connection;
    }
}
