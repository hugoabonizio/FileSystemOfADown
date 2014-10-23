package thread;

import service.ServerService;
import util.Connection;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WaitConnection implements Runnable {

    private Socket socket;
    private final ServerService serverService;

    public WaitConnection(ServerService serverService) {
        this.serverService = serverService;
    }

    @Override
    public void run() {
        Connection connection;
        while (true) {
            try {
                socket = serverService.getMeSS().accept();

                connection = new Connection();
                connection.setInput(new ObjectInputStream(socket.getInputStream()));
                connection.setOutput(new ObjectOutputStream(socket.getOutputStream()));
                connection.setPort(socket.getPort());
                connection.setSocket(socket);

                new Thread(new ListenerSocket(serverService, connection)).start();
            } catch (IOException ex) {
                Logger.getLogger(WaitConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
