import core.Jogo;
import core.Missao;
import io.JSONHandler;

public class Main {
    public static void main(String[] args) {
        Missao missao = new Missao();
        Jogo jogo = new Jogo(missao, null);
        JSONHandler jsonHandler = new JSONHandler();
        try {
            jsonHandler.importData(jogo);
        } catch (Exception e) {
            e.printStackTrace();
    }

        System.out.println(jogo.getMissao().getCodMissao());
    }
}
