package traffic.Runners;

import traffic.Constants.Prompt;
import traffic.DataHandler.RoadDataHandler;

public class SystemInfoRunner extends Thread {
    private volatile int currentSeconds;
    private int previousSeconds;
    private Thread infoDisplayer;
    private static volatile SystemInfoRunner instance;

    public SystemInfoRunner() {
        this.currentSeconds = 0;
        this.previousSeconds = 0;
    }

    @Override
    public void run() {
        this.setName("QueueThread");

        while (!Thread.interrupted()) {
            try {
                Thread.sleep(1000);
                this.currentSeconds++;
                RoadDataHandler.getInstance().updateRoadData();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static SystemInfoRunner getInstance() {
        if (instance == null) {
            synchronized (SystemInfoRunner.class) {
                if (instance == null) {
                    instance = new SystemInfoRunner();
                }
            }
        }

        return instance;
    }

    public Thread infoDisplayer() {
        infoDisplayer = new Thread(() -> {
            while (!infoDisplayer.isInterrupted()) {
                if (previousSeconds != currentSeconds) {
                    System.out.printf(Prompt.SYSTEM_INFO,
                            currentSeconds,
                            RoadDataHandler.getInstance().getMaxRoads(),
                            RoadDataHandler.getInstance().getInterval());

                    if (!RoadDataHandler.getInstance().getRoads().isEmpty()) {
                        System.out.println(RoadDataHandler.getInstance());
                    }

                    System.out.println(Prompt.PRESS_ENTER_TO_CONTINUE);

                    previousSeconds = currentSeconds;
                }
            }
        });

        return infoDisplayer;
    }
}