package service;

import frame.Frame;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Client {

    public static void main(String args[]) {
        generateGUI();

        try {
            final String owner = JOptionPane.showInputDialog(null, "Quem é você?");
            final String serverAddress = JOptionPane.showInputDialog(null, "Insira IP:Porta do servidor ao qual deseja se conectar", Inet4Address.getLocalHost().getHostAddress() + ":");
            final Random random = new Random();

            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new Frame(owner, serverAddress, random.nextInt(1000) + 11000).setVisible(true);
                }
            });
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void generateGUI() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
