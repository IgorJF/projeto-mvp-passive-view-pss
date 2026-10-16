package br.ufes.repository;

import br.ufes.model.HistoricoPreco;
import br.ufes.model.Produto;
import java.time.LocalDate;
import java.util.List;

public interface IHistoricoPrecoRepository {
    void salvar(HistoricoPreco historico);
    List<HistoricoPreco> listarPorProduto(Produto produto);
    LocalDate buscarDataUltimoCalculo();
}
