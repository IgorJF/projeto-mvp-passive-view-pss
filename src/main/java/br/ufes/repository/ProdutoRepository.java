package br.ufes.repository;

import br.ufes.model.Produto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author igor
 */
public class ProdutoRepository implements IProdutoRepository {
    private List<Produto> produtos;
    private int idContador;
    
    public ProdutoRepository(){
        produtos = new ArrayList<>();
        idContador = 1;
    }
    
    @Override
    public void salvar(Produto produto){
        if(produto == null){
            throw new IllegalArgumentException("Produto não é valido");
        }
        if (produto.getId() == 0) {
            existe(produto.getNomeProduto());
            produto.setId(idContador++);
            produtos.add(produto);
        }
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getId() == produto.getId()) {
                produtos.set(i, produto);
            }
        }
    }
    
    @Override
    public List<Produto> listar() {
        return produtos;
    }
    
    private void existe(String nomeProduto){
         for(Produto produto : produtos){
            if(produto.getNomeProduto().equalsIgnoreCase(nomeProduto)){
                throw new RuntimeException("O produto com nome de " + nomeProduto + " ja existe");
            }
        }
    }
}
