package service;

import java.util.Random;

public class Monitor {

    public void main(String[] args) {
        Random random = new Random();
        MonitorService monitorService = new MonitorService(random.nextInt(1000) + 15000);
    }
}
