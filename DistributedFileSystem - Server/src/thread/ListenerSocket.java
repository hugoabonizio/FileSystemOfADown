package thread;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import dao.FileDAO;
import dao.TemporaryDAO;
import entity.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import service.ServerService;
import util.Connection;
import util.Message;
import util.Message.Action;

public class ListenerSocket implements Runnable {

    private final ServerService serverService;
    private final Connection connection;
    private SQLiteConnection tempConnection, localConnection;
    private TemporaryDAO TempDAO;
    private FileDAO LocalDAO;

    public ListenerSocket(ServerService serverService, Connection connection) throws SQLiteException {
        this.serverService = serverService;
        this.connection = connection;
    }

    @Override
    public void run() {
        Message message, answer;
        
        try {
            tempConnection = new SQLiteConnection(new java.io.File("database/temporary.db"));
            tempConnection.open(true);
            TempDAO = new TemporaryDAO(tempConnection);
        
            localConnection = new SQLiteConnection(new java.io.File("database/local.db"));
            localConnection.open(true);
            LocalDAO = new FileDAO(localConnection);
        } catch (SQLiteException ex) {
            Logger.getLogger(ListenerSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            while ((message = (Message) connection.getInput().readObject()) != null) {
                Action action = message.getAction();
                if (action != Action.PING) {
                    System.out.println("Action: " + action);
                }

                if (action.equals(Action.CONNECT_SERVER)) {
                    String ip = message.getSrc().split(":")[0];
                    int port = Integer.parseInt(message.getSrc().split(":")[1]);

                    Socket socket = new Socket(ip, port);
                    Connection c = new Connection();
                    c.setOutput(new ObjectOutputStream(socket.getOutputStream()));
                    c.setInput(new ObjectInputStream(socket.getInputStream()));
                    c.setIp(ip);
                    c.setPort(port);
                    c.setSocket(socket);

                    serverService.getServerSet().add(c);
                    for (File f : (List<File>) message.getData()) {
                        TempDAO.create(f);
                    }
                } else if (action.equals(Action.CONNECT_CLIENT)) {
                    answer = new Message();
                    answer.setAction(Action.CONNECT_CLIENT);
                    answer.setData(serverService.getServerSet());
                    answer.setSrc(serverService.getMe());
                    Connection.send(message.getSrc(), answer);
                } else if (action.equals(Action.DISCONNECT)) {
                    serverService.getServerSet().remove((Connection) message.getData());
                    //replicar os dados contidos nesse servidor que se desconectou para outro servidor
                } else if (action.equals(Action.READ)) {
                    File file = (File) message.getData();
                    answer = new Message();
                    answer.setAction(Action.READ);
                    answer.setData(LocalDAO.read(file));
                    answer.setSrc(serverService.getMe());
                    connection.send(answer);
                } else if (action.equals(Action.WRITE)) {
                    File file = (File) message.getData();
                    LocalDAO.write(file);
                    answer = new Message();
                    answer.setAction(Action.WRITE);
                    answer.setSrc(serverService.getMe());
                    connection.send(answer);
                } else if (action.equals(Action.CREATE)) {
                    File file = (File) message.getData();
                    answer = new Message();
                    answer.setAction(Action.CREATE);
                    answer.setData(LocalDAO.create(file));
                    answer.setSrc(serverService.getMe());
                    connection.send(answer);
                } else if (action.equals(Action.DELETE)) {
                    File file = (File) message.getData();
                    LocalDAO.delete(file);
                    answer = new Message();
                    answer.setAction(Action.DELETE);
                    answer.setData("Arquivo deletado com sucesso!");
                    answer.setSrc(serverService.getMe());
                    connection.send(answer);
                } else if (action.equals(Action.GET_ATTRIBUTES)) {
                    File file = (File) message.getData();
                    answer = new Message();
                    answer.setAction(Action.GET_ATTRIBUTES);
                    answer.setData(LocalDAO.getAttributes(file));
                    answer.setSrc(serverService.getMe());
                    connection.send(answer);
                }/* else if (action.equals(Action.SET_ATTRIBUTES)) {
                 File file = (File) message.getData();
                 LocalDAO.setAttributes(file);
                 answer = new Message();
                 answer.setAction(Action.SET_ATTRIBUTES);
                 answer.setData("Atributos do arquivo alterados com sucesso!");
                 answer.setSrc(serverService.getMe());
                 connection.send(answer);
                 }*/ else if (action.equals(Action.RENAME)) {
                    File file = (File) message.getData();
                    LocalDAO.rename(file);
                    answer = new Message();
                    answer.setAction(Action.RENAME);
                    answer.setData("Arquivo renomeado com sucesso!");
                    answer.setSrc(serverService.getMe());
                    connection.send(answer);
                } else if (action.equals(Action.MKDIR)) {
                    File file = (File) message.getData();
                    answer = new Message();
                    answer.setAction(Action.MKDIR);
                    answer.setData(LocalDAO.mkdir(file));
                    answer.setSrc(serverService.getMe());
                    connection.send(answer);
                } else if (action.equals(Action.RMDIR)) {
                    File file = (File) message.getData();
                    LocalDAO.rmdir(file);
                    answer = new Message();
                    answer.setAction(Action.RMDIR);
                    answer.setData("Diretório deletado com sucesso!");
                    answer.setSrc(serverService.getMe());
                    connection.send(answer);
                } else if (action.equals(Action.READDIR)) {
                    String path = (String) message.getData();
                    System.out.println("source: " + message.getSrc());
                    answer = new Message();
                    answer.setAction(Action.READDIR);
                    answer.setData(TempDAO.readdir(path));
                    System.out.println("depois do DAO");
                    answer.setSrc(serverService.getMe());
                    
                    Connection.send(message.getSrc(), answer);
                } else if (action.equals(Action.PING)) {
                    answer = new Message();
                    answer.setSrc(serverService.getMe());
                    answer.setData("ESTOU VIVO POR ENQUANTO...");
                    answer.setAction(Action.PING);
                    //System.out.println("PINGA " + message.getSrc());
                    Connection.send(message.getSrc(), answer);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ListenerSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException | SQLiteException ex) {
            ex.printStackTrace();
            sendErrorAnswer(connection, ex.getMessage());
        }
    }

    private void sendErrorAnswer(Connection connection, String error) {
        Message answer = new Message();
        answer.setAction(Action.ERROR);
        answer.setData(error);
        answer.setSrc(serverService.getMe());
        connection.send(answer);
    }
}
