package br.ufes.repository;

import br.ufes.model.Categoria;
import java.util.List;

public interface ICategoriaRepository {
    void salvar(Categoria categoria);
    List<Categoria> listar();
    //void novo(); - vai ser apenas mudanca de estado de elementos
    //void editar(Categoria categoria); - uso salvar
}
