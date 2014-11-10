package thread;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import service.ServerService;
import util.Connection;
import util.Message;
import util.Message.Action;

public class AreYouStillAlive implements Runnable {

    private final ServerService serverService;
    private static final int TIMEOUT = 1500;

    public AreYouStillAlive(ServerService serverService) {
        this.serverService = serverService;
    }

    @SuppressWarnings("")
    @Override
    public void run() {
        Connection c = null;
        try {
            while (true) {
                Iterator<Connection> it = new HashSet<>(serverService.getServerSet()).iterator();
                while (it.hasNext()) {
                    c = it.next();
                    testConnection(c);
                }
            }
        } catch (IOException ex) {
            System.out.println("CAIU!");
            //replicate();
            serverService.getServerSet().remove(c);
            serverService.getServerIPSet().remove(c.getIp() + ":" + c.getPort());
        }
    }

    private void testConnection(Connection c) throws IOException {
        Message message = new Message();
        message.setSrc(serverService.getMe());
        message.setData("VOCE AINDA ESTA VIVO?");
        message.setAction(Action.PING);
        c.send(message);

        try {
            Thread.sleep(TIMEOUT);
        } catch (InterruptedException ex) {
            Logger.getLogger(AreYouStillAlive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void replicate(String serverIp) {
        Message answer = new Message();
        answer.setAction(Action.SERVER_DOWN);
        answer.setData(serverIp);
        answer.setSrc(serverService.getMe());
        for (Connection c : serverService.getServerSet()) {
            try {
                c.send(answer);
            } catch (IOException ex) {
                Logger.getLogger(ListenerSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}