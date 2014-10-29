package thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import service.ClientService;

public class WaitConnection implements Runnable {

    private final ClientService clientService;
    private final ServerSocket server;

    public WaitConnection(ClientService clientService, ServerSocket server) {
        this.clientService = clientService;
        this.server = server;
    }

    @Override
    public void run() {
        Socket socket;
        while (true) {
            try {
                socket = server.accept();
                new Thread(new ClientThread(clientService, socket)).start();
            } catch (IOException ex) {
                Logger.getLogger(WaitConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
