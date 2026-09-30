package br.ufes.service;

/**
 *
 * @author igorj
 */
import br.ufes.model.Categoria;
import br.ufes.repository.ICategoriaRepository;
import java.util.List;

public class CategoriaService {
    private ICategoriaRepository categoriaRepository;

    public CategoriaService(ICategoriaRepository categoriaRepository){
        this.categoriaRepository = categoriaRepository;
    }

    public void salvar(String nomeCategoria, double percentualLucro){
        if (nomeCategoria == null || nomeCategoria.isBlank()){
            throw new IllegalArgumentException("Informe um nome valido para a categoria.");
        }
        if (percentualLucro <= 0){
            throw new IllegalArgumentException("O percentual de lucro deve ser maior que zero.");
        }
        Categoria categoria = new Categoria(nomeCategoria, percentualLucro);
        categoriaRepository.salvar(categoria);
    }

    public void atualizar(Categoria categoria, String nomeCategoria, double percentualLucro) {
        if (categoria == null) {
            throw new IllegalArgumentException("Categoria não selecionada para edição.");
        }
        
        categoria.setNomeCategoria(nomeCategoria);
        categoria.setPercentualLucro(percentualLucro);
        
        categoriaRepository.salvar(categoria);
    }

    public void excluir(Categoria categoria) {
        categoriaRepository.excluir(categoria);
    }

    public List<Categoria> listar() {
        return categoriaRepository.listar();
    }
}
