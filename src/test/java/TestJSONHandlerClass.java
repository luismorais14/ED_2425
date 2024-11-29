import Exceptions.JogoException;
import core.Jogo;
import core.Missao;
import io.JSONHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestJSONHandlerClass {

    @Test
    void testImportData() throws JogoException {
        Missao missao = new Missao();
        Jogo jogo = new Jogo(missao, null);
        JSONHandler jsonHandler = new JSONHandler();
        jsonHandler.importData(jogo);
        String expectedCod = "pata de coelho";
        int expectedVersao = 1;

        assertEquals(expectedCod, jogo.getMissao().getCodMissao());
        assertEquals(expectedVersao, jogo.getMissao().getVersao());

        JogoException exception = assertThrows(JogoException.class, () -> {
            jsonHandler.importData(null);
        });
        assertEquals("Jogo object is null", exception.getMessage());
    }
}