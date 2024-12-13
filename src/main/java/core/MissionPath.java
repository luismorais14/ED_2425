package core;

import ADT.UnorderedListADT;
import implementations.ArrayUnorderedList;

public class MissionPath {
    private String codMissao;
    private UnorderedListADT<Divisao> paths;

    public MissionPath() {
        this.codMissao = "";
        this.paths = new ArrayUnorderedList<Divisao>();
    }

    public MissionPath(String codMissao, UnorderedListADT<Divisao> paths) {
        this.codMissao = codMissao;
        this.paths = paths;
    }

    public String getCodMissao() {
        return codMissao;
    }

    public void setCodMissao(String codMissao) {
        this.codMissao = codMissao;
    }

    public UnorderedListADT<Divisao> getPaths() {
        return paths;
    }

    public void setPaths(UnorderedListADT<Divisao> paths) {
        this.paths = paths;
    }
}
