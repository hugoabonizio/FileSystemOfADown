package service;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Server
{

    public static void main(String[] args)
    {
        String monitorAddress;
        try
        {
            monitorAddress = JOptionPane.showInputDialog(null, "Insira IP:Porta do monitor de servidores", Inet4Address.getLocalHost().getHostAddress() + ":");
            Random random = new Random();
            ServerService serverService = new ServerService(monitorAddress, random.nextInt(1000) + 20000);
        } catch (UnknownHostException ex)
        {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
