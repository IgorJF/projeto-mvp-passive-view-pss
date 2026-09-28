package br.ufes.repository;

import br.ufes.model.Categoria;
import java.util.ArrayList;
import java.util.List;

public class CategoriaRepository implements ICategoriaRepository {
    private List<Categoria> categorias;
    
    public CategoriaRepository(){
        categorias = new ArrayList<>();
    }
    
    @Override
    public void salvar(Categoria categoria){
        if(categoria == null){
            throw new IllegalArgumentException("Informa uma categoria valida");
        }
        categorias.add(categoria);
    }
    
    @Override
    public List<Categoria> listar(){
        return categorias;
    }
}
