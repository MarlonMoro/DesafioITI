package model;

import java.util.ArrayList;
import java.util.List;

public class Extrato {

    private List<Transacao> movimentacoes;

    public Extrato() {
        this.movimentacoes = new ArrayList<>();
    }

    public List<Transacao> getMovimentacoes() {
        return movimentacoes;
    }

    public void setMovimentacoes(List<Transacao> movimentacoes) {
        this.movimentacoes = movimentacoes;
    }
}
