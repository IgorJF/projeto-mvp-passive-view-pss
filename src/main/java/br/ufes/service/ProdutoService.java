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

    public ProdutoService(IProdutoRepository produtoRepository, IHistoricoPrecoRepository historicoRepository) {
        this.produtoRepository = produtoRepository;
        this.historicoRepository = historicoRepository;
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

    public void editar(Produto produto, String nomeProduto, double precoCusto, Categoria categoria) {
        if (produto == null) {
            throw new IllegalArgumentException("Produto não selecionado para edição.");
        }
        produto.setNomeProduto(nomeProduto);
        produto.setPrecoCusto(precoCusto);
        produto.setCategoria(categoria);
        produtoRepository.salvar(produto);
    }

    public List<Produto> listar() {
        return produtoRepository.listar();
    }

    private double calcularPrecoVenda(double precoCusto, double margemLucro) {
        double precoVenda = precoCusto * (1 + (margemLucro/100));
        return Math.round(precoVenda * 100.0)/100.0;
    }

    private void registrarHistorico(Produto produto) {
        HistoricoPreco historico = new HistoricoPreco(produto, LocalDate.now(), produto.getMargemLucro(), produto.getPrecoVenda());
        historicoRepository.salvar(historico);
    }
}
