package br.ufes.repository;

/**
 *
 * @author Daniel
 */
import br.ufes.model.HistoricoPreco;
import br.ufes.model.Produto;
import java.util.List;

public interface IHistoricoPrecoRepository {
    void salvar(HistoricoPreco historico);
}
