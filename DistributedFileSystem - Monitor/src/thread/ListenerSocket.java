package thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import service.MonitorService;
import util.Connection;
import util.Message;
import util.Message.Action;

public class ListenerSocket implements Runnable {

    private final MonitorService monitorService;
    private final Connection connection;

    public ListenerSocket(MonitorService monitorService, Connection connection) {
        this.monitorService = monitorService;
        this.connection = connection;
    }

    @Override
    public void run() {
        Message message, answer;
        try {
            while ((message = (Message) connection.getInput().readObject()) != null) {
                Action action = message.getAction();

                if (action.equals(Action.CONNECT)) {
                    String ip = message.getSrc().split(":")[0];
                    int port = Integer.parseInt(message.getSrc().split(":")[1]);

                    Socket socket = new Socket(ip, port);
                    Connection c = new Connection();
                    c.setOutput(new ObjectOutputStream(socket.getOutputStream()));
                    c.setInput(new ObjectInputStream(socket.getInputStream()));
                    c.setIp(ip);
                    c.setPort(port);
                    c.setSocket(socket);

                    if (message.getData().equals("I'm server"))
                        monitorService.getIpServidores().add(c);
                    else if (message.getData().equals("I'm client"))
                        monitorService.getIpClientes().add(c);
                    
                    //retornar alguma coisa para o emissor...
                }
            }
        } catch (IOException ex) {
            //Logger.getLogger(ListenerSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ListenerSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
