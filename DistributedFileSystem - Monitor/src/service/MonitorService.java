package service;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import thread.AreYouStillAlive;
import thread.WaitConnection;
import util.Connection;

public class MonitorService
{

    private Set<Connection> ipServidores;
    private Set<Connection> ipClientes;
    private String me;
    private ServerSocket server;

    public MonitorService(int port)
    {
        ipServidores = new HashSet<>();
        ipClientes = new HashSet<>();
        
        listen(port);
        ping();
    }

    private void listen(int port)
    {
        try
        {
            me = Inet4Address.getLocalHost().getHostAddress() + ":" + port;
            System.out.println("Monitor de servidores: " + me);

            server = new ServerSocket(port);
            new Thread(new WaitConnection(this)).start();
        } catch (IOException ex)
        {
            Logger.getLogger(MonitorService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void ping() {
        new Thread(new AreYouStillAlive(this)).start();
    }

    public Set<Connection> getIpServidores()
    {
        return ipServidores;
    }

    public void setIpServidores(Set<Connection> ipServidores)
    {
        this.ipServidores = ipServidores;
    }

    public Set<Connection> getIpClientes()
    {
        return ipClientes;
    }

    public void setIpClientes(Set<Connection> ipClientes)
    {
        this.ipClientes = ipClientes;
    }

    public ServerSocket getServer()
    {
        return server;
    }

    public void setServer(ServerSocket server)
    {
        this.server = server;
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
