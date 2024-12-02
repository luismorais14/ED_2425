import Exceptions.JogoException;
import core.Divisao;
import core.Edificio;
import core.Jogo;
import core.Missao;
import io.JSONHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestJSONHandlerClass {

    @Test
    void testImportData() throws JogoException {
        Missao missao = new Missao();
        Edificio edificio = new Edificio();
        Jogo jogo = new Jogo(missao, null, edificio);
        JSONHandler jsonHandler = new JSONHandler();
        jsonHandler.importData(jogo);
        String expectedCod = "pata de coelho";
        int expectedVersao = 1;
        String[] expectedDivisoes = {"Heliporto", "Escada 6", "Camaratas", "Armazém", "Escada 5",
                "Laboratório", "Escritório 3", "Escada 4", "WC", "Corredor 2",
                "Segurança", "Hall", "Escada 3", "Escritório 1", "Escritório 2",
                "Escada de Emergência", "Corredor 1", "Escada 2", "Porteiro",
                "Escada 1", "Garagem"};

        //Test for cod-missao reader
        assertEquals(expectedCod, jogo.getMissao().getCodMissao());

        //Test for versao reader
        assertEquals(expectedVersao, jogo.getMissao().getVersao());

        JogoException exception = assertThrows(JogoException.class, () -> {
            jsonHandler.importData(null);
        });
        assertEquals("Jogo object is null", exception.getMessage());
    }
}