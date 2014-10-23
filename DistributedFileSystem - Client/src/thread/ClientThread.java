package thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import service.ClientService;
import util.Connection;
import util.Message;
import util.Message.Action;

public class ClientThread implements Runnable
{

    private final ClientService clientService;
    private final Socket socket;
    
    public ClientThread(ClientService clientService, Socket socket)
    {
        this.clientService = clientService;
        this.socket = socket;
    }

    @Override
    public void run()
    {
        Message message;
        try {
            message = (Message) new ObjectInputStream(socket.getInputStream()).readObject();
            Action action = message.getAction();

            if (action.equals(Action.CONNECT) || action.equals(Action.DISCONNECT)) {
                clientService.setServer((Connection) message.getData());
            } else if (action.equals(Action.READDIR)) {
                
            } else if (action.equals(Action.READ)) {
                
            } else if (action.equals(Action.GET_ATTRIBUTES)) {
                
            }
            
            socket.close();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
