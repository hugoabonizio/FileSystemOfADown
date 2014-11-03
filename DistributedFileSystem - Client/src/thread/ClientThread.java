package thread;

import entity.Local;
import entity.Temporary;
import frame.Frame;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import service.ClientService;
import util.Connection;
import util.Message;
import util.Message.Action;

public class ClientThread implements Runnable {

    private final ClientService clientService;
    private final Frame frame;
    private final Socket socket;

    public ClientThread(ClientService clientService, Socket socket) {
        this.clientService = clientService;
        this.frame = clientService.getFrame();
        this.socket = socket;
    }

    @Override
    public void run() {
        Message message;
        try {
            message = (Message) new ObjectInputStream(socket.getInputStream()).readObject();
            Action action = message.getAction();
            //System.out.println("Action: " + action);

            if (action.equals(Action.CONNECT_CLIENT)) {
                clientService.setOther_servers((Set<Connection>) message.getData());
            } else if (action.equals(Action.READ)) {
                frame.getTxtFile().setEnabled(true);
                frame.getBtnSave().setEnabled(true);
                frame.getTxtFile().setText((String) message.getData());
            } else if (action.equals(Action.GET_ATTRIBUTES)) {
                Local file = (Local) message.getData();
                frame.setOpenedFileId(file.getId());
                frame.getTxtName().setEnabled(true);
                frame.getTxtName().setText(file.getFname());
                frame.getLabelTipo().setText("Tipo: " + file.getFtype());
                frame.getLabelTamanho().setText("Tamanho: " + file.getFtype());
                frame.getLabelDataCriacao().setText("Data de criação: " + file.getCreated_at());
                frame.getLabelDataAcesso().setText("Data de acesso: " + file.getRead_at());
                frame.getLabelDataModificacao().setText("Data de modificação: " + file.getUpdated_at());
                frame.getLabelProprietario().setText("Proprietário: " + file.getOwner());
            } else if (action.equals(Action.READDIR)) {
                addRows(message);
            } else if (action.equals(Action.MKDIR)) {

            } else if (action.equals(Action.CREATE)) {

            } else if (action.equals(Action.PING)) {
                clientService.lastPING = System.currentTimeMillis();
            }

            socket.close();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addRows(Message message) {
        ((DefaultTableModel) frame.getTableDirectory().getModel()).getDataVector().removeAllElements();
        ((DefaultTableModel) frame.getTableDirectory().getModel()).fireTableDataChanged();
        for (Temporary t : (List<Temporary>) message.getData()) {
            DefaultTableModel m = (DefaultTableModel) frame.getTableDirectory().getModel();
            Vector v = new Vector();
            if (t.getIs_dir().equals(Frame.FOLDER)) {
                v.add("Pasta");
            } else {
                v.add("Arquivo");
            }
            v.add(t.getFname());
            m.addRow(v);
        }
    }

}
