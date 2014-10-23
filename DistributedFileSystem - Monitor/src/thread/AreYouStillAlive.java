package thread;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import service.MonitorService;
import util.Connection;
import util.Message;
import util.Message.Action;

public class AreYouStillAlive implements Runnable
{

    private final MonitorService monitorService;
    private static final int TIMEOUT = 1500;

    public AreYouStillAlive(MonitorService monitorService)
    {
        this.monitorService = monitorService;
    }

    @SuppressWarnings("")
    @Override
    public void run()
    {
        Message message, answer;
        while (true)
        {
            for (Connection c : monitorService.getServerList())
            {
                message = new Message();
                message.setSrc(monitorService.getMe());
                message.setData("VOCE AINDA ESTA VIVO?");
                message.setAction(Action.PING);
                c.send(message);

                try
                {
                    answer = null;
                    Thread.sleep(TIMEOUT);
                    if ((answer = (Message) c.getInput().readObject()) == null)
                    {
                        monitorService.getServerList().remove(c);
                        Map<Connection, Connection> clientMap = monitorService.getClientMap();
                        Iterator it = clientMap.keySet().iterator();
                        while (it.hasNext()) {
                            Connection key = (Connection) it.next();
                            if (clientMap.get(key).equals(c)) {
                                Message answerToClient = new Message();
                                answerToClient.setSrc(monitorService.getMe());
                                answerToClient.setData(MonitorService.selectServer(key));
                                answerToClient.setAction(Action.DISCONNECT);
                                key.send(answerToClient);
                            }
                        }
                    }
                } catch (InterruptedException | IOException | ClassNotFoundException ex)
                {
                    Logger.getLogger(AreYouStillAlive.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
