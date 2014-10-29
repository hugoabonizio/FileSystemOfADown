package thread;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        while (true) {
            testConnection(clientService.getServer());
        }
    }

    private void testConnection(Connection c) {
        Message message = new Message();
        message.setSrc(clientService.getMe());
        message.setData("VOCE AINDA ESTA VIVO?");
        message.setAction(Action.PING);
        c.send(message);
        
        try {
            Message message_aux = null;
            Thread.sleep(TIMEOUT);
            if ((message_aux = (Message) c.getInput().readObject()) == null) {
                Message answer = new Message();
                answer.setSrc(clientService.getMe());
                answer.setData(c);
                answer.setAction(Action.DISCONNECT);
                Connection.trySendRequest(clientService.getOther_servers(), message, true);
                //cliente necessita se conectar em outro servidor
            }
        } catch (InterruptedException | IOException | ClassNotFoundException ex) {
            Logger.getLogger(AreYouStillAlive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
