package thread;

import com.almworks.sqlite4java.SQLiteException;
import entity.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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

                if (action.equals(Action.CONNECT)) {
                    //Quando o supervisor manda a tabela temporaria do momento...
                } else if (action.equals(Action.READ)) {
                    try {
                        File file = (File) message.getData();
                        answer = new Message();
                        answer.setAction(Action.READ);
                        answer.setData(serverService.getFileDAO().read(file));
                        answer.setSrc(serverService.getMe());
                        connection.send(answer);
                    } catch (SQLiteException ex) {
                        sendErrorAnswer(connection, ex.getMessage());
                    }
                } else if (action.equals(Action.WRITE)) {
                    try {
                        File file = (File) message.getData();
                        serverService.getFileDAO().write(file);
                        answer = new Message();
                        answer.setAction(Action.WRITE);
                        answer.setSrc(serverService.getMe());
                        connection.send(answer);
                    } catch (SQLiteException ex) {
                        sendErrorAnswer(connection, ex.getMessage());
                    }
                } else if (action.equals(Action.CREATE)) {
                    try {
                        File file = (File) message.getData();
                        answer = new Message();
                        answer.setAction(Action.CREATE);
                        answer.setData(serverService.getFileDAO().create(file));
                        answer.setSrc(serverService.getMe());
                        connection.send(answer);
                    } catch (SQLiteException ex) {
                        sendErrorAnswer(connection, ex.getMessage());
                    }
                } else if (action.equals(Action.DELETE)) {
                    try {
                        File file = (File) message.getData();
                        serverService.getFileDAO().delete(file);
                        answer = new Message();
                        answer.setAction(Action.DELETE);
                        answer.setData("Arquivo deletado com sucesso!");
                        answer.setSrc(serverService.getMe());
                        connection.send(answer);
                    } catch (SQLiteException ex) {
                        sendErrorAnswer(connection, ex.getMessage());
                    }
                } else if (action.equals(Action.GET_ATTRIBUTES)) {
                    try {
                        File file = (File) message.getData();
                        answer = new Message();
                        answer.setAction(Action.GET_ATTRIBUTES);
                        answer.setData(serverService.getFileDAO().getAttributes(file));
                        answer.setSrc(serverService.getMe());
                        connection.send(answer);
                    } catch (SQLiteException ex) {
                        sendErrorAnswer(connection, ex.getMessage());
                    }
                }/* else if (action.equals(Action.SET_ATTRIBUTES)) {
                 try {
                 File file = (File) message.getData();
                 serverService.getFileDAO().setAttributes(file);
                 answer = new Message();
                 answer.setAction(Action.SET_ATTRIBUTES);
                 answer.setData("Atributos do arquivo alterados com sucesso!");
                 answer.setSrc(serverService.getMe());
                 connection.send(answer);
                 } catch (SQLiteException ex) {
                 sendErrorAnswer(connection, ex.getMessage());
                 }
                 }*/ else if (action.equals(Action.RENAME)) {
                    try {
                        File file = (File) message.getData();
                        serverService.getFileDAO().rename(file);
                        answer = new Message();
                        answer.setAction(Action.RENAME);
                        answer.setData("Arquivo renomeado com sucesso!");
                        answer.setSrc(serverService.getMe());
                        connection.send(answer);
                    } catch (SQLiteException ex) {
                        sendErrorAnswer(connection, ex.getMessage());
                    }
                } else if (action.equals(Action.MKDIR)) {
                    try {
                        File file = (File) message.getData();
                        answer = new Message();
                        answer.setAction(Action.MKDIR);
                        answer.setData(serverService.getFileDAO().mkdir(file));
                        answer.setSrc(serverService.getMe());
                        connection.send(answer);
                    } catch (SQLiteException ex) {
                        sendErrorAnswer(connection, ex.getMessage());
                    }
                } else if (action.equals(Action.RMDIR)) {
                    try {
                        File file = (File) message.getData();
                        serverService.getFileDAO().rmdir(file);
                        answer = new Message();
                        answer.setAction(Action.RMDIR);
                        answer.setData("Diretório deletado com sucesso!");
                        answer.setSrc(serverService.getMe());
                        connection.send(answer);
                    } catch (SQLiteException ex) {
                        sendErrorAnswer(connection, ex.getMessage());
                    }
                } else if (action.equals(Action.READDIR)) {
                    try {
                        String path = (String) message.getData();
                        answer = new Message();
                        answer.setAction(Action.READDIR);
                        answer.setData(serverService.getFileDAO().readdir(path));
                        answer.setSrc(serverService.getMe());
                        connection.send(answer);
                    } catch (SQLiteException ex) {
                        sendErrorAnswer(connection, ex.getMessage());
                    }
                } else if (action.equals(Action.PING)) {
                    answer = message;
                    answer.setSrc(serverService.getMe());
                    answer.setData("ESTOU VIVO POR ENQUANTO...");
                    connection.send(answer);
                }
            }
        } catch (IOException ex) {
            //Logger.getLogger(ListenerSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ListenerSocket.class.getName()).log(Level.SEVERE, null, ex);
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
