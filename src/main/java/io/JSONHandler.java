package io;

import ADT.ListADT;
import ADT.UnorderedListADT;
import Exceptions.JogoException;
import core.Jogo;
import implementations.ArrayUnorderedList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JSONHandler {
    public void importData(Jogo jogo) throws JogoException {
        if (jogo == null) {
            throw new JogoException("Jogo object is null");
        }

        readCodMissao(jogo);
        readVersao(jogo);
        readEdificio(jogo);
        //TODO ler array de ligacoes
        //TODO ler array de inimigos
        //TODO ler array de entradas e saidas
        //TODO ler alvo
        //TODO ler array de items

    }

    private FileReader readJsonFile() throws FileNotFoundException {
        return new FileReader("resources\\anexo.json");
    }

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

    private void readEdificio(Jogo jogo) {
        JSONParser parser = new JSONParser();

        try {
            FileReader fr = readJsonFile();
            Object obj = parser.parse(fr);
            JSONObject jsonObject = (JSONObject) obj;

            JSONArray ja = (JSONArray) jsonObject.get("edificio");

            for (int i = 0; i < ja.size(); i++) {
                String divisao = (String) ja.get(i);

                jogo.getEdificio().setDivisao(divisao);
            }
        } catch (ParseException e) {
            System.out.println("Parse Exception");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
    }
}
