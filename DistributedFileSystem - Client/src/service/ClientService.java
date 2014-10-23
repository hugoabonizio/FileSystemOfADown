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
    private ServerSocket meSS;
    private Connection server;

    public ClientService(String monitorAddress, int mePort)
    {
        connectToMonitor(monitorAddress);
        listen(mePort);
    }

    private void connectToMonitor(String monitorAddress) throws NumberFormatException
    {
        Socket socket;
        try
        {
            String ip = monitorAddress.split(":")[0];
            int port = Integer.parseInt(monitorAddress.split(":")[1]);
            
            socket = new Socket(ip, port);
            Connection connection = new Connection();
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
            meSS = new ServerSocket(mePort);
            
            Socket socket;
            while (true)
            {
                socket = meSS.accept();
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

    public ServerSocket getMeSS()
    {
        return meSS;
    }

    public void setMeSS(ServerSocket meSS)
    {
        this.meSS = meSS;
    }

    public Connection getServer()
    {
        return server;
    }

    public void setServer(Connection server)
    {
        this.server = server;
    }
}
