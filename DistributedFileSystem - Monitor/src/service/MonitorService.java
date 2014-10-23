package service;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import thread.AreYouStillAlive;
import thread.WaitConnection;
import util.Connection;

public class MonitorService
{

    private static List<Connection> serverList;
    private static Map<Connection, Connection> clientMap;
    private String me;
    private ServerSocket meSS;

    public MonitorService(int port)
    {
        serverList = new LinkedList<>();
        clientMap = new HashMap<>();

        listen(port);
        ping();
    }

    private void listen(int port)
    {
        try
        {
            me = Inet4Address.getLocalHost().getHostAddress() + ":" + port;
            System.out.println("Monitor de servidores: " + me);

            meSS = new ServerSocket(port);
            new Thread(new WaitConnection(this)).start();
        } catch (IOException ex)
        {
            Logger.getLogger(MonitorService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void ping()
    {
        new Thread(new AreYouStillAlive(this)).start();
    }

    public ServerSocket getMeSS()
    {
        return meSS;
    }

    public void setMeSS(ServerSocket meSS)
    {
        this.meSS = meSS;
    }

    public String getMe()
    {
        return me;
    }

    public void setMe(String me)
    {
        this.me = me;
    }

    public Map<Connection, Connection> getClientMap()
    {
        return clientMap;
    }

    public void setClientMap(Map<Connection, Connection> clientMap)
    {
        MonitorService.clientMap = clientMap;
    }

    public List<Connection> getServerList()
    {
        return serverList;
    }

    public void setServerList(List<Connection> serverList)
    {
        MonitorService.serverList = serverList;
    }

    public static Connection selectServer(Connection client)
    {
        Random random = new Random();
        int position = random.nextInt(serverList.size());
        Connection server = serverList.get(position);
        clientMap.put(client, server);
        return server;
    }
}
