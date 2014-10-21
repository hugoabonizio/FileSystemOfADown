package thread;

import java.io.IOException;
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
            for (Connection c : monitorService.getIpServidores())
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
                        monitorService.getIpServidores().remove(c);
                    }
                } catch (InterruptedException | IOException | ClassNotFoundException ex)
                {
                    Logger.getLogger(AreYouStillAlive.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
