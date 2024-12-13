package io;

import core.Divisao;
import core.Jogo;
import core.MissionManager;
import core.MissionPath;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Iterator;

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

        Iterator<MissionPath> missionIterator = MissionManager.getAllMissionPaths().iterator();
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

        String folderPath = "Reports";
        File folder = new File(folderPath);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        try (FileWriter file = new FileWriter(folderPath + "\\Paths.json")) {
            file.write(missionsArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            System.err.println("Error while saving JSON: " + e.getMessage());
        }
    }
}








