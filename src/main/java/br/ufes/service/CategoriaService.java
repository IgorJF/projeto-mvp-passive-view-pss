package br.ufes.service;

import br.ufes.model.Categoria;
import br.ufes.repository.ICategoriaRepository;
import java.util.List;

public class CategoriaService {
    private ICategoriaRepository categoriaRepository;

    public CategoriaService(ICategoriaRepository categoriaRepository){
        this.categoriaRepository = categoriaRepository;
    }

    public void salvar(String nomeCategoria, double percentualLucro){
        if (nomeCategoria == null || nomeCategoria.isBlank()) {
            throw new IllegalArgumentException("O nome da categoria nao pode ser vazio.");
        }
        if (percentualLucro < 0) {
            throw new IllegalArgumentException("O percentual de lucro nao pode ser menor que zero.");
        }
        Categoria categoria = new Categoria(nomeCategoria, percentualLucro);
        categoriaRepository.salvar(categoria);
    }

    public void editar(Categoria categoria, String nomeCategoria, double percentualLucro){
        if (categoria == null) {
            throw new IllegalArgumentException("Categoria nao selecionada para edicao.");
        }
        if (nomeCategoria == null || nomeCategoria.isBlank()) {
            throw new IllegalArgumentException("O nome da categoria nao pode ser vazio.");
        }
        if (percentualLucro < 0) {
            throw new IllegalArgumentException("O percentual de lucro nao pode ser menor que zero.");
        }
        categoria.setNomeCategoria(nomeCategoria);
        categoria.setPercentualLucro(percentualLucro);
        categoriaRepository.salvar(categoria);
    }
    
    public void excluir(Categoria categoria){
        if (categoria == null) {
            throw new IllegalArgumentException("Categoria nao selecionada para exclusao.");
        }
        categoriaRepository.excluir(categoria);
    }

    public List<Categoria> listar(){
        return categoriaRepository.listar();
    }
}