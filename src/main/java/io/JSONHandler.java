package io;

import Exceptions.JogoException;
import core.Divisao;
import core.Jogo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JSONHandler {
    //TODO estruturas para guardar temporariamente os objetos

    /**
     * Reads the entire data from the .json file
     *
     * @param jogo the game where the data will be saved
     * @throws JogoException exception to be thrown if the game object is null
     */
    public void importData(Jogo jogo) throws JogoException {
        if (jogo == null) {
            throw new JogoException("Jogo object is null");
        }

        //TODO fix na ordem de ler
        readEdificio(jogo);
        //TODO ler array de ligacoes
        readCodMissao(jogo);
        readVersao(jogo);
        //TODO ler array de inimigos
        //TODO ler array de entradas e saidas
        //TODO ler alvo
        //TODO ler array de items


    }

    /**
     * Reads the .json file
     *
     * @return a FileReader object
     * @throws FileNotFoundException exception to be thrown if the file was not found
     */
    private FileReader readJsonFile() throws FileNotFoundException {
        return new FileReader("resources\\anexo.json");
    }

    /**
     * Reads the mission code from the .json file
     *
     * @param jogo the game where the data will be saved
     */
    private void readCodMissao(Jogo jogo) {
        JSONParser parser = new JSONParser();
        String codMissao = "";

        try {
            FileReader fr = readJsonFile();
            Object obj = parser.parse(fr);
            JSONObject jsonObject = (JSONObject) obj;

            codMissao = (String) jsonObject.get("cod-missao");

            jogo.getMissao().setCodMissao(codMissao);
        } catch (ParseException e) {
            System.out.println("Parse Exception");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
    }

    /**
     * Reads the mission version from the .json file
     *
     * @param jogo the game where the data will be saved
     */
    private void readVersao(Jogo jogo) {
        JSONParser parser = new JSONParser();
        int versao = 0;

        try {
            FileReader fr = readJsonFile();
            Object obj = parser.parse(fr);
            JSONObject jsonObject = (JSONObject) obj;

            versao = ((Number) jsonObject.get("versao")).intValue();

            jogo.getMissao().setVersao(versao);
        } catch (ParseException e) {
            System.out.println("Parse Exception");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
    }

    /**
     * Reads the game building from the .json file
     *
     * @param jogo the game where the data will be saved
     */
    private void readEdificio(Jogo jogo) {
        JSONParser parser = new JSONParser();

        try {
            FileReader fr = readJsonFile();
            Object obj = parser.parse(fr);
            JSONObject jsonObject = (JSONObject) obj;

            JSONArray ja = (JSONArray) jsonObject.get("edificio");

            for (int i = 0; i < ja.size(); i++) {
                String divisao = (String) ja.get(i);

                Divisao div = new Divisao(divisao, null, null, null);

                jogo.getEdificio().addDivison(div);
            }
        } catch (ParseException e) {
            System.out.println("Parse Exception");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
    }

    /**
     * Reads the enemies from the .json file
     *
     * @param jogo the game where the data will be saved
     */
    private void readInimigos(Jogo jogo) {
        JSONParser parser = new JSONParser();
        JSONArray ja;
        String nomeInimigo = "";
        String divisaoInimigo = "";
        int poder = 0;

        try {
            ja = (JSONArray) parser.parse(readJsonFile());

            for (int i = 0; i < ja.size(); i++) {
                JSONObject obj = (JSONObject) ja.get(i);
                nomeInimigo = (String) obj.get("nome-inimigo");
                divisaoInimigo = (String) obj.get("divisao-inimigo");
                poder = ((Number) obj.get("poder")).intValue();

                //jogo.getEdificio().;
            }

        } catch (IOException e) {
            System.out.println("IO Exception");
        } catch (ParseException e) {
            System.out.println("Parse Exception");
        }
    }

    private void readLigacoes(Jogo jogo) {
        JSONParser parser = new JSONParser();
        JSONArray ja;
        String divisao1 = "";
        String divisao2 = "";

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(readJsonFile());
            ja = (JSONArray) jsonObject.get("ligacoes");

            for (int i = 0; i < ja.size(); i++) {
                JSONArray ligacaoArray = (JSONArray) ja.get(i);

                divisao1 = (String) ligacaoArray.get(0);
                divisao2 = (String) ligacaoArray.get(1);

                //procurar divisao já criada no edificio
                //instanciar divisao/chamar divisao
                //adicionar ligacao
            }

        } catch (IOException e) {
            System.out.println("IO Exception");
        } catch (ParseException e) {
            System.out.println("Parse Exception");
        }
    }
}
