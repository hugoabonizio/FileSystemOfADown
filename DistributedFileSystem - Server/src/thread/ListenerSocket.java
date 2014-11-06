package thread;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import dao.LocalDAO;
import dao.TemporaryDAO;
import entity.Local;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
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
    private TemporaryDAO tempDAO;
    private LocalDAO localDAO;

    public ListenerSocket(ServerService serverService, Connection connection) throws SQLiteException {
        this.serverService = serverService;
        this.connection = connection;
    }

    @Override
    public void run() {
        Message message = null, answer;

        try {
            tempConnection = new SQLiteConnection(new java.io.File("database/temporary.db"));
            tempConnection.open(true);
            tempConnection.setBusyTimeout(10000);
            tempDAO = new TemporaryDAO(tempConnection);

            localConnection = new SQLiteConnection(new java.io.File("database/local.db"));
            localConnection.open(true);
            localDAO = new LocalDAO(localConnection);
        } catch (SQLiteException ex) {
            Logger.getLogger(ListenerSocket.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            while ((message = (Message) connection.getInput().readObject()) != null) {
                Action action = message.getAction();
                if (action != Action.PING && action != Action.PING_ACK) {
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
                    serverService.getServerIPSet().add(message.getSrc());

                    for (Local f : (List<Local>) message.getData()) {
                        tempDAO.create(f, message.getSrc());
                    }

                    // enviar a temporary pro servidor que acabou de conectar
                    if (!message.getSrc().equals("SERVER")) {
                        answer = new Message();
                        answer.setAction(Action.CONNECT_SERVER);
                        answer.setData(tempDAO.all());
                        answer.setSrc("SERVER");
                    }

                    /*
                     Message sendServers = new Message();
                     sendServers.setAction(Action.SERVER_LIST);
                     sendServers.setSrc(serverService.getMe());
                     sendServers.setData(serverService.getServerIPSet());
                     c.send(sendServers);
                    
                     } else if (action.equals(Action.SERVER_LIST)) {
                     serverService.getServerSet().addAll((Set<Connection>) message.getData());
                     for (String ip: (Set<String>) message.getData()) {
                     System.out.println("conenctando à: " + ip);
                     serverService.connectToServer(ip);
                     }*/
                } else if (action.equals(Action.CONNECT_CLIENT)) {
                    answer = new Message();
                    answer.setAction(Action.CONNECT_CLIENT);
                    answer.setData(serverService.getServerIPSet());
                    answer.setSrc(serverService.getMe());
                    Connection.send(message.getSrc(), answer);
                } else if (action.equals(Action.DISCONNECT)) {
                    serverService.getServerSet().remove((Connection) message.getData());
                    //replicar os dados contidos nesse servidor que se desconectou para outro servidor
                } else if (action.equals(Action.READ)) {
                    Local file = (Local) message.getData();
                    if (!message.getSrc().equals("SERVER")) {
                        answer = new Message();
                        answer.setAction(Action.READ);
                        answer.setData(file);
                        answer.setSrc("SERVER");
                        answer.setMainSrc(message.getSrc());
                        
                        List<String> ipList = tempDAO.getIp(file);
                        Connection.send(ipList.get(0), answer);
                    } else {
                        answer = new Message();
                        answer.setAction(Action.READ);
                        answer.setData(localDAO.read(file));
                        answer.setSrc(serverService.getMe());
                        Connection.send(message.getMainSrc(), answer);
                    }
                } else if (action.equals(Action.WRITE)) {
                    Local file = (Local) message.getData();
                    localDAO.write(file);
                    answer = new Message();
                    answer.setAction(Action.WRITE);
                    answer.setSrc(serverService.getMe());
                    Connection.send(message.getSrc(), answer);
                } else if (action.equals(Action.CREATE)) {
                    Local file = (Local) message.getData();
                    localDAO.create(file);
                    tempDAO.create(file, serverService.getMe());

                    List<String> serverList = new ArrayList<>(serverService.getServerIPSet());
                    long seed = System.nanoTime();
                    Collections.shuffle(serverList, new Random(seed));

                    if (!message.getSrc().equals("SERVER") && !serverList.isEmpty()) {
                        answer = new Message();
                        answer.setSrc("SERVER");
                        answer.setData(file);
                        answer.setAction(Action.CREATE);
                        Connection.send(serverList.get(0), answer);
                        throwAction(file, Action.CREATE_TEMP);
                    }

                    /*answer = new Message();
                     answer.setAction(Action.CREATE);
                     answer.setData(localDAO.create(file));
                     answer.setSrc(serverService.getMe());
                     Connection.send(message.getSrc(), answer);*/
                } else if (action.equals(Action.DELETE)) {
                    Local file = (Local) message.getData();
                    if (message.getSrc().equals("SERVER")) {
                        if (!serverService.getServerSet().isEmpty()) {
                            localDAO.rmdir(file);
                            tempDAO.delete(file);

                            throwAction(file, Action.DELETE_TEMP);
                        }
                    } else {
                        throwFileOperation(file, message.getAction(), tempDAO.getIp(file));
                    }

                    /*answer = new Message();
                     answer.setAction(Action.DELETE);
                     answer.setData("Arquivo deletado com sucesso!");
                     answer.setSrc(serverService.getMe());
                     Connection.send(message.getSrc(), answer);*/
                } else if (action.equals(Action.GET_ATTRIBUTES)) {
                    Local file = (Local) message.getData();
                    if (!message.getSrc().equals("SERVER")) {
                        answer = new Message();
                        answer.setAction(Action.GET_ATTRIBUTES);
                        answer.setData(file);
                        answer.setSrc("SERVER");
                        answer.setMainSrc(message.getSrc());
                        
                        List<String> ipList = tempDAO.getIp(file);
                        Connection.send(ipList.get(0), answer);
                    } else {
                        answer = new Message();
                        answer.setAction(Action.GET_ATTRIBUTES);
                        answer.setData(localDAO.getAttributes(file));
                        answer.setSrc(serverService.getMe());
                        Connection.send(message.getMainSrc(), answer);
                    }
                }/* else if (action.equals(Action.SET_ATTRIBUTES)) {
                 Local file = (Local) message.getData();
                 LocalDAO.setAttributes(file);
                 answer = new Message();
                 answer.setAction(Action.SET_ATTRIBUTES);
                 answer.setData("Atributos do arquivo alterados com sucesso!");
                 answer.setSrc(serverService.getMe());
                 Connection.send(message.getSrc(), answer);
                 }*/ else if (action.equals(Action.RENAME)) {
                    Local file = (Local) message.getData();
                    localDAO.rename(file);
                    answer = new Message();
                    answer.setAction(Action.RENAME);
                    answer.setData("Arquivo renomeado com sucesso!");
                    answer.setSrc(serverService.getMe());
                    Connection.send(message.getSrc(), answer);
                } else if (action.equals(Action.MKDIR)) {
                    Local file = (Local) message.getData();
                    localDAO.mkdir(file);
                    tempDAO.create(file, serverService.getMe());

                    List<String> serverList = new ArrayList<>(serverService.getServerIPSet());
                    long seed = System.nanoTime();
                    Collections.shuffle(serverList, new Random(seed));

                    if (!message.getSrc().equals("SERVER") && !serverList.isEmpty()) {
                        answer = new Message();
                        answer.setSrc("SERVER");
                        answer.setData(file);
                        answer.setAction(Action.MKDIR);
                        Connection.send(serverList.get(0), answer);
                        throwAction(file, Action.CREATE_TEMP);
                    }

                    /*answer = new Message();
                     answer.setAction(Action.MKDIR);
                     answer.setData(localDAO.mkdir(file));
                     answer.setSrc(serverService.getMe());
                     Connection.send(message.getSrc(), answer);*/
                } else if (action.equals(Action.RMDIR)) {
                    Local file = (Local) message.getData();
                    if (message.getSrc().equals("SERVER")) {
                        if (!serverService.getServerSet().isEmpty()) {
                            localDAO.rmdir(file);
                            tempDAO.delete(file);

                            file.setPath(file.getPath() + file.getFname() + "/%");
                            localDAO.deleteFolder(file);
                            tempDAO.deleteFolder(file);

                            throwAction(file, Action.DELETE_TEMP);
                        }
                    } else {
                        throwFileOperation(file, message.getAction(), tempDAO.getIp(file));
                    }

                    /*answer = new Message();
                     answer.setAction(Action.RMDIR);
                     answer.setData("Diretório deletado com sucesso!");
                     answer.setSrc(serverService.getMe());
                     Connection.send(message.getSrc(), answer);*/
                } else if (action.equals(Action.READDIR)) {
                    Local file = (Local) message.getData();
                    answer = new Message();
                    answer.setAction(Action.READDIR);
                    answer.setData(tempDAO.readdir(file));
                    answer.setSrc(serverService.getMe());

                    Connection.send(message.getSrc(), answer);
                } else if (action.equals(Action.PING)) {
                    answer = new Message();
                    answer.setSrc(serverService.getMe());
                    answer.setData("ESTOU VIVO POR ENQUANTO...");
                    answer.setAction(Action.PING_ACK);
                    Connection.send(message.getSrc(), answer);
                } else if (action.equals(Action.CREATE_TEMP)) {
                    tempDAO.create((Local) message.getData(), message.getSrc());
                } else if (action.equals(Action.DELETE_TEMP)) {
                    Local file = (Local) message.getData();
                    tempDAO.delete(file);
                    if (file.getIs_dir().equals("true")) {
                        file.setPath(file.getPath() + file.getFname() + "/%");
                        tempDAO.deleteFolder(file);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ListenerSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException | SQLiteException ex) {
            System.out.println(ex.getMessage());
            sendErrorAnswer(message, ex.getMessage());
        } finally {
            localConnection.dispose();
            tempConnection.dispose();
            try {
                connection.getSocket().close();
            } catch (IOException ex) {
                Logger.getLogger(ListenerSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void sendErrorAnswer(Message message, String error) {
        Message answer = new Message();
        answer.setAction(Action.ERROR);
        answer.setData(error);
        answer.setSrc(serverService.getMe());
        Connection.send(message.getSrc(), answer);
    }

    private void throwAction(Local file, Action action) {
        Message answer = new Message();
        answer.setAction(action);
        answer.setData(file);
        answer.setSrc(serverService.getMe());
        for (Connection c : serverService.getServerSet()) {
            try {
                c.send(answer);
            } catch (IOException ex) {
                Logger.getLogger(ListenerSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void throwFileOperation(Local file, Action action, List<String> ipList) {
        Message answer = new Message();
        answer.setAction(action);
        answer.setData(file);
        answer.setSrc("SERVER");
        for (String s : ipList) {
            Connection.send(s, answer);
        }
    }
}
