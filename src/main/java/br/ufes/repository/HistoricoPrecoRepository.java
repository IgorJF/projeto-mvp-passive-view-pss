package br.ufes.repository;

import br.ufes.model.HistoricoPreco;
import br.ufes.model.Produto;
import java.util.ArrayList;
import java.util.List;

public class HistoricoPrecoRepository implements IHistoricoPrecoRepository {
    private List<HistoricoPreco> historicos;
    
    public HistoricoPrecoRepository(){
        historicos = new ArrayList<>();
    }

    @Override
    public void salvar(HistoricoPreco historico) {
        historicos.add(historico);
    }
    
    @Override
    public List<HistoricoPreco> listarPorProduto(Produto produto){
        List<HistoricoPreco> historicosDoProduto = new ArrayList<>();
        for (HistoricoPreco historico : historicos) {
            if (historico.getProduto().getId() == produto.getId()) {
                historicosDoProduto.add(historico);
            }
        }
        return historicosDoProduto;
    }
}