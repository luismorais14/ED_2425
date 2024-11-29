import Exceptions.JogoException;
import core.Jogo;
import core.Missao;
import io.JSONHandler;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestJSONHandlerClass {
    @Test
    public void testImportData() throws JogoException {
        Missao missao = new Missao();
        Jogo jogo = new Jogo(missao, null);
        JSONHandler jsonHandler = new JSONHandler();
        jsonHandler.importData(jogo);
        String expected = "pata de coelho";

        assertEquals("CodMissao should be 'pata de coelho'", expected, jogo.getMissao().getCodMissao());

        JogoException exception = assertThrows(JogoException.class, () -> {
            jsonHandler.importData(null);
        });
        assertEquals("Jogo object is null", exception.getMessage());
    }
}
