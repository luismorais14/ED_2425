import Exceptions.JogoException;
import core.*;
import io.JSONHandler;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class TestJSONHandlerClass {

    @Test
    void testImportData() throws JogoException {
        // Setup
        Missao missao = new Missao();
        Edificio edificio = new Edificio();
        Player p1 = new Player();
        Jogo jogo = new Jogo(missao, edificio, p1);
        JSONHandler jsonHandler = new JSONHandler();

        // Import data
        jsonHandler.importData(jogo);

        // Expected values
        String expectedCod = "pata de coelho";
        int expectedVersao = 1;
        String[] expectedDivisoes = {"Heliporto", "Escada 6", "Camaratas", "Armazém", "Escada 5",
                "Laboratório", "Escritório 3", "Escada 4", "WC", "Corredor 2",
                "Segurança", "Hall", "Escada 3", "Escritório 1", "Escritório 2",
                "Escada de Emergência", "Corredor 1", "Escada 2", "Porteiro",
                "Escada 1", "Garagem"};

        // Test for cod-missao reader
        Iterator<Missao> missaoIterator = jogo.getMissaoIterator();
        Missao mission = missaoIterator.next();  // Assuming next() retrieves the current mission object
        assertEquals(expectedCod, mission.getCodMissao());

        // Test for versao reader
        assertEquals(expectedVersao, mission.getVersao());

        // Test for JogoException when passing null
        JogoException exception = assertThrows(JogoException.class, () -> {
            jsonHandler.importData(null);
        });
        assertEquals("Jogo object is null", exception.getMessage());
    }

}