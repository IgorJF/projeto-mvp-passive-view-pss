package br.ufes.service;

/**
 *
 * @author igorj
 */
import br.ufes.model.Categoria;
import br.ufes.model.HistoricoPreco;
import br.ufes.model.Produto;
import br.ufes.repository.IHistoricoPrecoRepository;
import br.ufes.repository.IProdutoRepository;
import java.time.LocalDate;
import java.util.List;

public class ProdutoService {
    private IProdutoRepository produtoRepository;
    private IHistoricoPrecoRepository historicoRepository;

    public ProdutoService(IProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public void salvar(String nomeProduto, double precoCusto, Categoria categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("O produto deve pertencer a uma categoria.");
        }
        if (precoCusto < 0) {
            throw new IllegalArgumentException("O preco de custo deve ser maior que zero.");
        }
        Produto produto = new Produto(nomeProduto, precoCusto, categoria);
        produtoRepository.salvar(produto);
    }

    public void editar(Produto produto, String nomeProduto, double precoCusto, Categoria categoria){
        if (produto == null) {
            throw new IllegalArgumentException("Produto nao selecionado para edicao.");
        }
        produto.setNomeProduto(nomeProduto);
        produto.setPrecoCusto(precoCusto);
        produto.setCategoria(categoria);
        produtoRepository.salvar(produto);
    }

    public List<Produto> listar() {
        return produtoRepository.listar();
    }
}
