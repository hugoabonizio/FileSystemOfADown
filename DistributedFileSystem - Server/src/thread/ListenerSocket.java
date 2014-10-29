package thread;

import com.almworks.sqlite4java.SQLiteException;
import entity.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import service.ServerService;
import util.Connection;
import util.Message;
import util.Message.Action;

public class ListenerSocket implements Runnable {

    private final ServerService serverService;
    private final Connection connection;

    public ListenerSocket(ServerService serverService, Connection connection) {
        this.serverService = serverService;
        this.connection = connection;
    }

    @Override
    public void run() {
        Message message, answer;
        try {
            while ((message = (Message) connection.getInput().readObject()) != null) {
                Action action = message.getAction();

                if (action.equals(Action.CONNECT_SERVER)) {
                    //Quando o servidor manda a tabela temporaria do momento...
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
                        serverService.getTempDAO().create(f);
                    }
                } else if (action.equals(Action.READ)) {
                    File file = (File) message.getData();
                    answer = new Message();
                    answer.setAction(Action.READ);
                    answer.setData(serverService.getFileDAO().read(file));
                    answer.setSrc(serverService.getMe());
                    connection.send(answer);
                } else if (action.equals(Action.WRITE)) {
                    File file = (File) message.getData();
                    serverService.getFileDAO().write(file);
                    answer = new Message();
                    answer.setAction(Action.WRITE);
                    answer.setSrc(serverService.getMe());
                    connection.send(answer);
                } else if (action.equals(Action.CREATE)) {
                    File file = (File) message.getData();
                    answer = new Message();
                    answer.setAction(Action.CREATE);
                    answer.setData(serverService.getFileDAO().create(file));
                    answer.setSrc(serverService.getMe());
                    connection.send(answer);
                } else if (action.equals(Action.DELETE)) {
                    File file = (File) message.getData();
                    serverService.getFileDAO().delete(file);
                    answer = new Message();
                    answer.setAction(Action.DELETE);
                    answer.setData("Arquivo deletado com sucesso!");
                    answer.setSrc(serverService.getMe());
                    connection.send(answer);
                } else if (action.equals(Action.GET_ATTRIBUTES)) {
                    File file = (File) message.getData();
                    answer = new Message();
                    answer.setAction(Action.GET_ATTRIBUTES);
                    answer.setData(serverService.getFileDAO().getAttributes(file));
                    answer.setSrc(serverService.getMe());
                    connection.send(answer);
                }/* else if (action.equals(Action.SET_ATTRIBUTES)) {
                 File file = (File) message.getData();
                 serverService.getFileDAO().setAttributes(file);
                 answer = new Message();
                 answer.setAction(Action.SET_ATTRIBUTES);
                 answer.setData("Atributos do arquivo alterados com sucesso!");
                 answer.setSrc(serverService.getMe());
                 connection.send(answer);
                 }*/ else if (action.equals(Action.RENAME)) {
                    File file = (File) message.getData();
                    serverService.getFileDAO().rename(file);
                    answer = new Message();
                    answer.setAction(Action.RENAME);
                    answer.setData("Arquivo renomeado com sucesso!");
                    answer.setSrc(serverService.getMe());
                    connection.send(answer);
                } else if (action.equals(Action.MKDIR)) {
                    File file = (File) message.getData();
                    answer = new Message();
                    answer.setAction(Action.MKDIR);
                    answer.setData(serverService.getFileDAO().mkdir(file));
                    answer.setSrc(serverService.getMe());
                    connection.send(answer);
                } else if (action.equals(Action.RMDIR)) {
                    File file = (File) message.getData();
                    serverService.getFileDAO().rmdir(file);
                    answer = new Message();
                    answer.setAction(Action.RMDIR);
                    answer.setData("Diretório deletado com sucesso!");
                    answer.setSrc(serverService.getMe());
                    connection.send(answer);
                } else if (action.equals(Action.READDIR)) {
                    String path = (String) message.getData();
                    answer = new Message();
                    answer.setAction(Action.READDIR);
                    answer.setData(serverService.getFileDAO().readdir(path));
                    answer.setSrc(serverService.getMe());
                    connection.send(answer);
                } else if (action.equals(Action.PING)) {
                    answer = message;
                    answer.setSrc(serverService.getMe());
                    answer.setData("ESTOU VIVO POR ENQUANTO...");
                    connection.send(answer);
                }
            }
        } catch (IOException ex) {
            //Logger.getLogger(ListenerSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException | SQLiteException ex) {
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
