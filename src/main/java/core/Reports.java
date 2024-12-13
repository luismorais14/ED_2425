package core;

import ADT.OrderedListADT;
import ADT.QueueADT;
import implementations.ArrayOrderedList;
import implementations.LinkedQueue;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

public class Reports {
    private Jogo jogo;

    /**
     * Creates an empty report
     */
    public Reports() {
        this.jogo = new Jogo();
    }

    /**
     * Creates a report specifying the game
     *
     * @param jogo the game
     */
    public Reports(Jogo jogo) {
        this.jogo = jogo;
    }

    /**
     * Exports the routes prepared in each manual simulation so they can be used in meeting briefings.
     */
    public void exportToJson() {
        JSONArray missionsArray = new JSONArray();

        Iterator<MissionPath> missionIterator = this.jogo.getMissionPaths().iterator();
        while (missionIterator.hasNext()) {
            MissionPath missaoData = missionIterator.next();
            JSONObject missionObject = new JSONObject();
            missionObject.put("cod-missao", missaoData.getCodMissao());

            JSONArray pathsArray = new JSONArray();
            Iterator<Divisao> pathIterator = missaoData.getPaths().iterator();
            while (pathIterator.hasNext()) {
                Divisao path = pathIterator.next();
                JSONObject pathObject = new JSONObject();
                pathObject.put("Divisao", path.getNome());
                pathsArray.add(pathObject);
            }

            missionObject.put("paths", pathsArray);
            missionsArray.add(missionObject);
        }

        try (FileWriter file = new FileWriter("Reports\\Paths.json", true)) {
            file.write(missionsArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            System.err.println("Error while saving JSON: " + e.getMessage());
        }
    }

}






