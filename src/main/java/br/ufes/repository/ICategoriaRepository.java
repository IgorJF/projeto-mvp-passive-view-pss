package br.ufes.repository;

import br.ufes.model.Categoria;
import java.util.List;

public interface ICategoriaRepository {
    void salvar(Categoria categoria);
    List<Categoria> listar();
}
