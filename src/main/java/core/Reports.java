package core;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
     * @param jogo the game
     */
    public Reports(Jogo jogo) {
        this.jogo = jogo;
    }

    /**
     * Exports the routes prepared in each manual simulation so they can be used in meeting briefings.
     */
    public void exportToJson() {
        JSONParser parser = new JSONParser();
        String filePath = "Reports\\paths.json";

        try {
            File file = new File(filePath);

            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            JSONArray existingMissionsArray;
            try (FileReader reader = new FileReader(filePath)) {
                existingMissionsArray = (JSONArray) parser.parse(reader);
            } catch (ParseException | IOException e) {
                existingMissionsArray = new JSONArray();
            }

            JSONObject missionObject = new JSONObject();
            missionObject.put("cod-missao", jogo.getMissao().getCodMissao());

            JSONArray pathsArray = new JSONArray();
            while (!this.jogo.getPaths().isEmpty()) {
                Divisao divisao = this.jogo.getPaths().dequeue();
                pathsArray.add(divisao.toJSONObject());
            }
            missionObject.put("paths", pathsArray);

            existingMissionsArray.add(missionObject);

            try (FileWriter fw = new FileWriter(filePath)) {
                fw.write(existingMissionsArray.toJSONString());
                fw.flush();
            }

        } catch (IOException ex) {
            System.err.println("Error while appending to JSON file: " + ex.getMessage());
        }
    }
}




