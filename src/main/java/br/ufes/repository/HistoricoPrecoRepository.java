package br.ufes.repository;

import br.ufes.model.HistoricoPreco;
import br.ufes.model.Produto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

//guarda todos os registros de calculo de preco de todos os produtos, na ordem em que foram salvos
//listarPorProduto devolve uma copia filtrada, do calculo mais recente para o mais antigo
//buscarDataUltimoCalculo olha todos os registros, pois o intervalo de 10 dias vale para o calculo global

public class HistoricoPrecoRepository implements IHistoricoPrecoRepository {
    private List<HistoricoPreco> historicos;

    public HistoricoPrecoRepository(){
        this.historicos = new ArrayList<>();
    }

    @Override
    public void salvar(HistoricoPreco historico){
        if(historico == null){
            throw new IllegalArgumentException("Historico nao pode ser nulo");
        }
        historicos.add(historico);
    }

    @Override
    public List<HistoricoPreco> listarPorProduto(Produto produto){
        if(produto == null){
            throw new IllegalArgumentException("Produto nao pode ser nulo");
        }
        List<HistoricoPreco> doProduto = new ArrayList<>();
        for (int i = historicos.size() - 1; i >= 0; i--) {
            HistoricoPreco historico = historicos.get(i);
            if (historico.getIdProduto() == produto.getIdProduto()) {
                doProduto.add(historico);
            }
        }
        doProduto.sort(Comparator.comparing(HistoricoPreco::getData).reversed());
        return doProduto;
    }

    @Override
    public LocalDate buscarDataUltimoCalculo(){
        LocalDate ultimaData = null;
        for (HistoricoPreco historico : historicos) {
            if (ultimaData == null || historico.getData().isAfter(ultimaData)) {
                ultimaData = historico.getData();
            }
        }
        return ultimaData;
    }
}
