package traffic.DataHandler;

import traffic.Constants.Prompt;
import traffic.Constants.RoadStatus;
import traffic.Entities.Road;

import java.util.ArrayList;
import java.util.List;

public class RoadDataHandler {
    private int maxRoads;
    private int interval;
    private List<Road> roads;
    private int secondsToNextRoadStatusChange;
    private int indexOfCurrentOpenRoad;
    private boolean isRoadDeleted = false;
    private static RoadDataHandler roadDataHandler;

    private RoadDataHandler() {
    }

    public static RoadDataHandler getInstance() {
        if (roadDataHandler == null) {
            roadDataHandler = new RoadDataHandler();
        }

        return roadDataHandler;
    }

    public void updateRoadData() {
        decrementSecondsToStatusChange();

        int i = indexOfCurrentOpenRoad == roads.size() ? indexOfCurrentOpenRoad - 1 : indexOfCurrentOpenRoad;
        int counter = 0;

        while (counter < roads.size()) {
            Road road = roads.get(i);

            if (counter < 2) {
                road.setSecondsToStatusChange(secondsToNextRoadStatusChange);
            } else {
                road.setSecondsToStatusChange(secondsToNextRoadStatusChange + interval * (counter - 1));
            }

            counter++;
            i = i == roads.size() - 1 ? 0 : i + 1;
        }
    }

    private void decrementSecondsToStatusChange() {
        secondsToNextRoadStatusChange--;

        if (secondsToNextRoadStatusChange == 0) {
            if (roads.size() > 1) {
                changeRoadStatus(indexOfCurrentOpenRoad);
            }

            if (roads.size() == 1 && !roads.get(0).isOpen()) {
                roads.get(0).changeRoadStatus();
            }

            secondsToNextRoadStatusChange = interval;
            indexOfCurrentOpenRoad = indexOfCurrentOpenRoad >= roads.size() - 1 ? 0
                    : indexOfCurrentOpenRoad + 1;
        }
    }

    private void changeRoadStatus(int currentIndex) {
        Road currentRoad = roads.get(currentIndex);
        currentRoad.changeRoadStatus();

        if (isRoadDeleted) {
            this.isRoadDeleted = false;
        } else {
            Road nextRoad = currentIndex == roads.size() - 1 ? roads.get(0) : roads.get(currentIndex + 1);
            nextRoad.changeRoadStatus();
        }
    }

    public void addRoad(String roadName) {
        if (roads.size() >= maxRoads) {
            System.out.println(Prompt.QUEUE_IS_FULL);
        } else {
            Road road = roads.isEmpty() ? new Road(roadName, RoadStatus.OPEN)
                    : new Road(roadName, RoadStatus.CLOSED);

            roads.add(road);

            System.out.printf(Prompt.ROAD_ADDED + "\n", roadName);
        }
    }

    public void deleteRoad() {
        if (roads.size() >= 1) {
            System.out.printf(Prompt.ROAD_DELETED + "\n", roads.remove(0).getName());
        } else {
            System.out.println(Prompt.QUEUE_IS_EMPTY);
        }

        if (indexOfCurrentOpenRoad == 0) {
            isRoadDeleted = true;
        }

        indexOfCurrentOpenRoad = Math.max(indexOfCurrentOpenRoad - 1, 0);
    }

    public String toString() {
        List<String> roadsInfo = roads.stream()
                .map(Road::toString)
                .toList();

        return String.join("\n", roadsInfo);
    }

    public int getMaxRoads() {
        return this.maxRoads;
    }

    public void setMaxRoads(int maxRoads) {
        this.maxRoads = maxRoads;
        this.roads = new ArrayList<>();
    }

    public void setInterval(int interval) {
        this.secondsToNextRoadStatusChange = interval + 1;
        this.interval = interval;
    }

    public int getInterval() {
        return this.interval;
    }

    public List<Road> getRoads() {
        return this.roads;
    }
}
