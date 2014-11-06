package service;

import com.almworks.sqlite4java.SQLiteException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Server {

    public static void main(String[] args) throws SQLiteException {
        Logger.getLogger("com.almworks.sqlite4java").setLevel(Level.OFF);
        System.setProperty("sqlite4java.debug","false");
        /* SQLiteConnection session_files = new SQLiteConnection(new File("database/temporary.db"));
         SQLiteConnection local_files = new SQLiteConnection(new File("database/local_files.db"));
         session_files.open(true);
         local_files.open(true);

         // Limpa arquivos de sessão
         session_files.exec("DELETE FROM files");

         // Popula o banco com os arquivos locais, inicialmente
         SQLiteStatement select = local_files.prepare("SELECT * FROM files");
         SQLiteStatement insert;
         while (select.step()) {
         insert = session_files.prepare("INSERT INTO files (fname, path, is_dir) VALUES (?, ?, ?)");
         insert.bind(1, select.columnString(1));
         insert.bind(2, select.columnString(2));
         insert.bind(3, select.columnString(3));
         insert.step();
         }

         // session_files.dispose();*/
        Set<String> servers = new HashSet<>();
        String input;
        try {
            while ((input = JOptionPane.showInputDialog(null, "Insira IP:Porta do servidor", Inet4Address.getLocalHost().getHostAddress() + ":")) != null) {
                servers.add(input);
            }
            Random random = new Random();
            ServerService serverService = new ServerService(servers, random.nextInt(1000) + 20000);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
