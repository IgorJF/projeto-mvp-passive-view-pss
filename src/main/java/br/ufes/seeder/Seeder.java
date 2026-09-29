package br.ufes.seeder;

import br.ufes.model.Categoria;
import br.ufes.repository.ICategoriaRepository;

public class Seeder {
    public Seeder(ICategoriaRepository categorias){
        criarCategoriasIniciais(categorias);
    }
    
    private void criarCategoriasIniciais(ICategoriaRepository categorias){
        Categoria bebidas = new Categoria("Bebidas", 5.0);
        Categoria alimentos = new Categoria("Alimentos", 10.0);

        categorias.salvar(bebidas);
        categorias.salvar(alimentos);
    }
}
