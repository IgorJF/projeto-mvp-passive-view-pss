package br.ufes.repository;

import br.ufes.model.HistoricoPreco;
import br.ufes.model.Produto;
import java.util.ArrayList;
import java.util.List;

public class HistoricoPrecoRepository implements IHistoricoPrecoRepository {
    private List<HistoricoPreco> historicos;

    public HistoricoPrecoRepository(){
        this.historicos = new ArrayList<>();
    }

    @Override
    public void salvar(HistoricoPreco historico){
        historicos.add(historico);
    }
}