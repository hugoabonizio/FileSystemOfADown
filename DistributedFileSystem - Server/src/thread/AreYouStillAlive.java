package thread;

import java.io.IOException;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import service.ServerService;
import util.Connection;
import util.Message;
import util.Message.Action;

public class AreYouStillAlive implements Runnable {

    private final ServerService serverService;
    private static final int TIMEOUT = 1500;
    private Connection c;

    public AreYouStillAlive(ServerService serverService) {
        this.serverService = serverService;
    }

    @SuppressWarnings("")
    @Override
    public void run() {
        try {
            while (true) {
                Iterator<Connection> ite = new HashSet<>(serverService.getServerSet()).iterator();
                while (ite.hasNext()) {
                    c = ite.next();
                    testConnection(c);
                }
                //Thread.sleep(TIMEOUT);
            }
        } catch (IOException ex) {
            System.out.println("CAIU!");
        }
    }

    private void testConnection(Connection c) throws IOException {
        Message message = new Message();
        message.setSrc(serverService.getMe());
        message.setData("VOCE AINDA ESTA VIVO?");
        message.setAction(Action.PING);
        c.send(message, true);

        try {
            Thread.sleep(TIMEOUT);
        } catch (InterruptedException ex) {
            Logger.getLogger(AreYouStillAlive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
