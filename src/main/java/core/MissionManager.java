package core;

import ADT.UnorderedListADT;
import implementations.ArrayUnorderedList;

public class MissionManager {
    private static UnorderedListADT<MissionPath> allMissionPaths = new ArrayUnorderedList<MissionPath>();

    public static void addMissionPath(MissionPath missionPath) {
        allMissionPaths.addToFront(missionPath);
    }

    public static UnorderedListADT<MissionPath> getAllMissionPaths() {
        return allMissionPaths;
    }
}
