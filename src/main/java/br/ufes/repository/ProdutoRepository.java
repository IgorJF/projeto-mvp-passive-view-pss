/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
    
    public ProdutoRepository(){
        produtos = new ArrayList<>();
    }
    
    @Override
    public void salvar(Produto produto){
        if(produto == null){
            throw new IllegalArgumentException("Produto não é valido");
        }
        produtos.add(produto);
    }
    
    @Override
    public List<Produto> listar() {
        return produtos;
    }
    @Override
    public Produto buscarProdutoId(int idProduto) {
        for(Produto p : produtos){
            if(p.getId() == idProduto){
                return p;
            }
        }
        return null;
    }
    /*
    @Override
    public void atualizar(Produto produto) {
        for(Produto p : produtos){
            if (p.getId()== p.getId()) {
                p.setNomeProduto(produto.getNomeProduto());
                p.setPrecoCusto(produto.getPrecoCusto());
                p.setCategoria(produto.getCategoria());
            }
            throw new IllegalArgumentException("Produto não encontrado");
        }
    }
    */
}
