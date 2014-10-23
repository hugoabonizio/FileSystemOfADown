package thread;

import util.Connection;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import service.MonitorService;

public class WaitConnection implements Runnable {

    private Socket socket;
    private final MonitorService monitorService;

    public WaitConnection(MonitorService monitorService) {
        this.monitorService = monitorService;
    }

    @Override
    public void run() {
        Connection connection;
        while (true) {
            try {
                socket = monitorService.getMeSS().accept();

                connection = new Connection();
                connection.setInput(new ObjectInputStream(socket.getInputStream()));
                connection.setOutput(new ObjectOutputStream(socket.getOutputStream()));
                connection.setPort(socket.getPort());
                connection.setSocket(socket);

                new Thread(new ListenerSocket(monitorService, connection)).start();
            } catch (IOException ex) {
                Logger.getLogger(WaitConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
