package service;

import com.almworks.sqlite4java.SQLiteException;
import thread.WaitConnection;
import dao.DAOFactory;
import dao.FileDAO;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Connection;
import util.Message;

public class ServerService
{

    private FileDAO fileDAO;
    private String me;
    private ServerSocket meSS;

    public ServerService(String monitorAddress, int mePort)
    {
        DAOFactory daoFactory;
        try
        {
            daoFactory = new DAOFactory();
            fileDAO = daoFactory.getFileDAO();
        } catch (SQLiteException ex)
        {
            Logger.getLogger(ServerService.class.getName()).log(Level.SEVERE, null, ex);
        }

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
            message.setData("I'm a server");
            message.setAction(Message.Action.CONNECT);
            connection.send(message);
        } catch (IOException ex)
        {
            Logger.getLogger(ServerService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listen(int port)
    {
        try
        {
            me = Inet4Address.getLocalHost().getHostAddress() + ":" + port;
            System.out.println("Server: " + me);

            meSS = new ServerSocket(port);
            new Thread(new WaitConnection(this)).start();
        } catch (IOException ex)
        {
            Logger.getLogger(ServerService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ServerSocket getMeSS()
    {
        return meSS;
    }

    public void setMeSS(ServerSocket meSS)
    {
        this.meSS = meSS;
    }

    public FileDAO getFileDAO()
    {
        return fileDAO;
    }

    public void setFileDAO(FileDAO fileDAO)
    {
        this.fileDAO = fileDAO;
    }

    public String getMe()
    {
        return me;
    }

    public void setMe(String me)
    {
        this.me = me;
    }
}
