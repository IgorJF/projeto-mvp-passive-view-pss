package br.ufes.repository;

import br.ufes.model.Produto;
import java.util.List;

public interface IProdutoRepository {
    void salvar(Produto produto);
    List<Produto> listar();
}
