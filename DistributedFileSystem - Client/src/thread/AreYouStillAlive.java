package thread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import service.ClientService;
import util.Connection;
import util.Message;
import util.Message.Action;

public class AreYouStillAlive implements Runnable {

    private final ClientService clientService;
    private static final int TIMEOUT = 1500;

    public AreYouStillAlive(ClientService clientService) {
        this.clientService = clientService;
    }

    @SuppressWarnings("")
    @Override
    public void run() {
        try {
            while (true) {
                testConnection(clientService.getServer());
            }
        } catch (IOException ex) {
            System.out.println("CAIU!");
            while (!clientService.getOther_servers().isEmpty()) {
                String newServer = (new ArrayList<>(clientService.getOther_servers())).get(0);
                try {
                    clientService.getOther_servers().remove(newServer);
                    clientService.connectToServer(newServer);
                    break;
                } catch (IOException e) {
                    // proximo
                }
            }
            
            if (clientService.getOther_servers().isEmpty()) {
                JOptionPane.showMessageDialog(null, "O servidor que você estava conectado caiu e "
                        + "não foi possível conectar a outro servidor");
            }
        }
    }

    private void testConnection(Connection c) throws IOException {
        Message message = new Message();
        message.setSrc(clientService.getMe());
        message.setData("VOCE AINDA ESTA VIVO?");
        message.setAction(Action.PING);
        c.send(message);

        try {
            Thread.sleep(TIMEOUT);
        } catch (Exception ex) {
            Logger.getLogger(AreYouStillAlive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
