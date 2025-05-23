package io;

import ADT.UnorderedListADT;
import Exceptions.JogoException;
import core.*;
import core.Character;
import implementations.ArrayUnorderedList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;

public class JSONHandler {
    private UnorderedListADT<Divisao> divisionLists = new ArrayUnorderedList<Divisao>();

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

        readEdificio(jogo);
        readLigacoes(jogo);
        readCodMissao(jogo);
        readVersao(jogo);
        readInimigos(jogo);
        readEntradasSaidas(jogo);
        readAlvo(jogo);
        readItems(jogo);
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

            Missao missao = new Missao();
            missao.setCodMissao(codMissao);

            jogo.addMissao(missao);
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

            Iterator<Missao> missaoIterator = jogo.getMissaoIterator();

            while (missaoIterator.hasNext()) {
                Missao missao = missaoIterator.next();
                missao.setVersao(versao);
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

                UnorderedListADT<Character> inimigos = new ArrayUnorderedList<>();
                Alvo alvo = new Alvo();
                Item item = new Item();

                Divisao div = new Divisao(divisao, inimigos, alvo, item);

                if (jogo.getEdificio().getStartVertex() == null) {
                    jogo.getEdificio().setStartVertex(div);
                }

                jogo.getEdificio().addDivison(div);
                this.divisionLists.addToFront(div);
            }
        } catch (ParseException e) {
            System.out.println("Parse Exception");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
    }

    private int randomHP() {
        Random rand = new Random();
        int min = 30;
        int max = 70;
        int result = rand.nextInt(max-min) + min;

        return result;
    }

    /**
     * Search a division by its name.
     *
     * @param divisaoNome the name of the division
     * @return the index of the found division, null if not.
     */

    private Divisao searchDivisao(String divisaoNome) {
        if (this.divisionLists.isEmpty()) {
            return null;
        }

        Iterator<Divisao> iterator = this.divisionLists.iterator();

        while (iterator.hasNext()) {
            Divisao divisao = iterator.next();
            if (divisao.getNome().equals(divisaoNome)) {
                return divisao;
            }
        }

        return null;
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
        int hp = 0;

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(readJsonFile());
            ja = (JSONArray) jsonObject.get("inimigos");

            for (int i = 0; i < ja.size(); i++) {
                JSONObject obj = (JSONObject) ja.get(i);
                nomeInimigo = (String) obj.get("nome");
                divisaoInimigo = (String) obj.get("divisao");
                poder = ((Number) obj.get("poder")).intValue();
                hp = randomHP();

                Divisao div = searchDivisao(divisaoInimigo);

                Inimigo inimigo = new Inimigo(nomeInimigo, poder , hp);

                if (div != null)
                    div.addCharacter(inimigo);
            }

        } catch (IOException e) {
            System.out.println("IO Exception");
        } catch (ParseException e) {
            System.out.println("Parse Exception");
        }
    }

    /**
     * Reads the connections between divisions from the .json file
     *
     * @param jogo the game where the data will be saved
     */

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

                Divisao d1 = searchDivisao(divisao1);
                Divisao d2 = searchDivisao(divisao2);

                if (d1 == null || d2 == null) {
                    System.err.println("Division does not exist");
                } else {
                    jogo.getEdificio().addConnection(d1, d2);
                }

            }

        } catch (IOException e) {
            System.out.println("IO Exception");
        } catch (ParseException e) {
            System.out.println("Parse Exception");
        }
    }


    /**
     * Reads where the wanted target is from the .json file
     *
     * @param jogo the game where the data will be saved
     */

    private void readAlvo(Jogo jogo) {
        JSONParser parser = new JSONParser();
        String tipo = "";
        String divisao = "";

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(readJsonFile());

            JSONObject obj = (JSONObject) jsonObject.get("alvo");

            tipo = (String) obj.get("tipo");
            divisao = (String) obj.get("divisao");

            Divisao d1 = searchDivisao(divisao);

            Alvo alvo = new Alvo(tipo);

            d1.setAlvo(alvo);

            Iterator<Missao> missaoIterator = jogo.getMissaoIterator();

            while (missaoIterator.hasNext()) {
                Missao missao = missaoIterator.next();
                missao.setAlvo(alvo);
            }

        } catch (IOException e) {
            System.out.println("IO Exception");
        } catch (ParseException e) {
            System.out.println("Parse Exception");
        }
    }

    /**
     * Reads the items from the .json file
     *
     * @param jogo the game where the data will be saved
     */

    private void readItems(Jogo jogo) {
        JSONParser parser = new JSONParser();
        JSONArray ja;
        String divisao = "";
        int pontos = 0;
        String tipo = "";

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(readJsonFile());
            ja = (JSONArray) jsonObject.get("itens");

            for (int i = 0; i < ja.size(); i++) {
                JSONObject obj = (JSONObject) ja.get(i);
                tipo = (String) obj.get("tipo");
                if (tipo.equals("colete")) {
                    pontos = ((Number) obj.get("pontos-extra")).intValue();
                } else {
                    pontos = ((Number) obj.get("pontos-recuperados")).intValue();
                }
                divisao = (String) obj.get("divisao");

                Divisao d1 = searchDivisao(divisao);

                if (d1 != null)
                    d1.setItem(new Item(tipo, pontos));
            }

        } catch (IOException e) {
            System.out.println("IO Exception");
        } catch (ParseException e) {
            System.out.println("Parse Exception");
        }

    }

    /**
     * Reads the entrances/exits from the .json file
     *
     * @param jogo the game where the data will be saved
     */

    private void readEntradasSaidas(Jogo jogo) {
        JSONParser parser = new JSONParser();
        JSONArray ja;

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(readJsonFile());
            ja = (JSONArray) jsonObject.get("entradas-saidas");

            for (int i = 0; i < ja.size(); i++) {
                String nomeDivisao = (String) ja.get(i);

                Divisao divisao = searchDivisao(nomeDivisao);


                if (divisao != null) {
                    divisao.setEntradaSaida(true);
                } else {
                    System.err.println("Division not found: " + nomeDivisao);
                }
            }
        } catch (IOException e) {
            System.out.println("IO Exception");
        } catch (ParseException e) {
            System.out.println("Parse Exception");
        }
    }

}
