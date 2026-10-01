package br.ufes.repository;

import br.ufes.model.Categoria;
import java.util.ArrayList;
import java.util.List;

public class CategoriaRepository implements ICategoriaRepository {
    private List<Categoria> categorias;
    private int idContador;
    
    public CategoriaRepository(){
        categorias = new ArrayList<>();
        idContador = 1;
    }
    
    @Override
    public void salvar(Categoria categoria){
        if(categoria == null){
            throw new IllegalArgumentException("Categoria não é valida");
        }
        if (categoria.getId() == 0) {
            existe(categoria.getNomeCategoria());
            categoria.setId(idContador++);
            categorias.add(categoria);
        }
        for (int i = 0; i < categorias.size(); i++) {
            if (categorias.get(i).getId() == categoria.getId()) {
                categorias.set(i, categoria);
            }
        }
    }
    
    @Override
    public void excluir(Categoria categoria){
        categorias.remove(categoria);
    }
    
    @Override
    public List<Categoria> listar(){
        return categorias;
    }
    
    private void existe(String nomeCategoria){
         for(Categoria categoria : categorias){
            if(categoria.getNomeCategoria().equalsIgnoreCase(nomeCategoria)){
                throw new RuntimeException("A categoria com nome de " + nomeCategoria + " ja existe");
            }
        }
    }
}