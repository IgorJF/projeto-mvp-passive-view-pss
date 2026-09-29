/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufes.service;

import br.ufes.model.Categoria;
import br.ufes.model.HistoricoPreco;
import br.ufes.model.Produto;
import br.ufes.repository.ICategoriaRepository;
import br.ufes.repository.IHistoricoPrecoRepository;
import br.ufes.repository.IProdutoRepository;
import java.util.List;
import java.time.LocalDate;

/**
 *
 * @author Daniel
 */
public class CalculoPrecoService {
    private IProdutoRepository produtoRepository;
    private ICategoriaRepository categoriaRepository;
    private IHistoricoPrecoRepository historicoPrecoRepository;

    public CalculoPrecoService(IProdutoRepository produtoRepository, ICategoriaRepository categoriaRepository, IHistoricoPrecoRepository historicoPrecoRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.historicoPrecoRepository = historicoPrecoRepository;
    }

    public void calcularPrecos() {
        List<Produto> produtos = produtoRepository.listar();
        for(Produto p : produtos){
            Categoria categoria = p.getCategoria();
            double percentualLucro = categoria.getPercentualLucro();
            double precoCustoProduto = p.getPrecoCusto();
            double precoVenda = precoCustoProduto * (1 + percentualLucro/100);
            p.setPrecoVenda(precoVenda);
            p.setMargemLucro(percentualLucro);
            HistoricoPreco historico = new HistoricoPreco(p, LocalDate.now(), percentualLucro, precoVenda);
            historicoPrecoRepository.salvar(historico);
        }
    }
}
