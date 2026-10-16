package br.ufes.service;

import br.ufes.model.Categoria;
import br.ufes.model.Produto;
import br.ufes.repository.ICategoriaRepository;
import br.ufes.repository.IHistoricoPrecoRepository;
import br.ufes.repository.IProdutoRepository;
import java.util.List;

public class ProdutoService{
    private IProdutoRepository produtoRepository;
    private ICategoriaRepository categoriaRepository;
    private IHistoricoPrecoRepository historicoRepository;

    public ProdutoService(IProdutoRepository produtoRepository, ICategoriaRepository categoriaRepository, IHistoricoPrecoRepository historicoRepository){
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.historicoRepository = historicoRepository;
    }

    public void salvar(String nomeProduto, double precoCusto, Categoria categoria){
        validar(nomeProduto, precoCusto, categoria);
        Produto produto = new Produto(nomeProduto, precoCusto, categoria);
        produtoRepository.salvar(produto);
    }

    public void editar(Produto produto, String nomeProduto, double precoCusto, Categoria categoria){
        if (produto == null) {
            throw new IllegalArgumentException("Produto nao selecionado para edicao.");
        }
        validar(nomeProduto, precoCusto, categoria);
        produto.setNomeProduto(nomeProduto);
        produto.setPrecoCusto(precoCusto);
        produto.setCategoria(categoria);
        produtoRepository.editar(produto);
    }

    //ao excluir um produto, os registros de historico dele tambem sao removidos
    public void excluir(int id){
        if(id < 0){
            throw new IllegalArgumentException("Nao tem produto com id menor que zero");
        }
        Produto produtoBuscado = produtoRepository.buscarPorId(id);
        produtoRepository.excluir(produtoBuscado);
    }

    public List<Produto> listar() {
        return produtoRepository.listar();
    }

    private void validar(String nomeProduto, double precoCusto, Categoria categoria){
        if (nomeProduto == null || nomeProduto.isBlank()) {
            throw new IllegalArgumentException("Produto deve ter um nome.");
        }
        if (Double.isNaN(precoCusto) || Double.isInfinite(precoCusto) || precoCusto <= 0) {
            throw new IllegalArgumentException("O preco de custo deve ser maior que zero.");
        }
        if (categoria == null) {
            throw new IllegalArgumentException("O produto deve pertencer a uma categoria.");
        }
        if (categoriaRepository.buscarPorId(categoria.getIdCategoria()) == null) {
            throw new IllegalArgumentException("A categoria selecionada nao existe.");
        }
    }
}
