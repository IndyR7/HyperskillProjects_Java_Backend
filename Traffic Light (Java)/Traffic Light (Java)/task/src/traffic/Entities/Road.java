package traffic.Entities;

import traffic.Constants.Prompt;
import traffic.Constants.RoadStatus;

public class Road {
    private String name;
    private String status;
    private int secondsToStatusChange;

    public Road(String name, String status) {
        this.name = name;
        this.status = status;
        this.secondsToStatusChange = 0;
    }

    public String getName() {
        return this.name;
    }

    public void setSecondsToStatusChange(int secondsToStatusChange) {
        this.secondsToStatusChange = secondsToStatusChange;
    }

    public void changeRoadStatus() {
        this.status = this.status.equals(RoadStatus.OPEN) ? RoadStatus.CLOSED : RoadStatus.OPEN;
    }

    public boolean isOpen() {
        return this.status.equals(RoadStatus.OPEN);
    }

    public String toString() {
        String colorCode = this.status.equals(RoadStatus.CLOSED) ? "\u001B[31m"
                : this.status.equals(RoadStatus.OPEN) && this.secondsToStatusChange <= 2 ? "\u001B[33m"
                : "\u001B[32m";
        String resetCode = "\u001B[0m";

        return String.format(colorCode + Prompt.ROAD_STATUS + resetCode
                , this.name, this.status, secondsToStatusChange);
    }
}
