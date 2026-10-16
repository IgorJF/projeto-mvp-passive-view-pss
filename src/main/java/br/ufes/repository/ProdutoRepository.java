package br.ufes.repository;

import br.ufes.model.Produto;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProdutoRepository implements IProdutoRepository{
    private List<Produto> produtos;
    private int contadorId;
    
    public ProdutoRepository(){
        produtos = new ArrayList<>();
        contadorId = 0;
    }
    
    @Override
    public Produto buscarPorId(int id){
        for (Produto produto : produtos) {
            if (produto.getIdProduto() == id) {
                return produto;
            }
        }
        return null;
    }

    @Override
    public void salvar(Produto produto){
        if(produto == null){
            throw new IllegalArgumentException("Produto nao pode ser nula");
        }
        produto.setIdProduto(contadorId);
        produtos.add(produto);
        contadorId++;  
    }

    @Override
    public void excluir(Produto produto){
        if(produto == null){
            throw new IllegalArgumentException("Produto nao pode ser nulo");
        }
        Iterator<Produto> it = produtos.iterator();
        while(it.hasNext()) {
            Produto produtoAtual = it.next();
            if (produtoAtual.getIdProduto() == produto.getIdProduto()) {
                it.remove();
            }
        }
    }

    @Override
    public List<Produto> listar(){
        return produtos;
    }

    @Override
    public void editar(Produto produto){
        if(produto == null){
            throw new IllegalArgumentException("produto nao pode ser nula");
        }
        for (int i = 0; i < produtos.size(); i++) {
            Produto produtoSelecionado = produtos.get(i);
            if (produtoSelecionado.getIdProduto() == produto.getIdProduto()) {
                produtos.set(i, produto);
            }
        }
    }
}