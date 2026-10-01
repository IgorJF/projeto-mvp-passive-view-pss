package br.ufes.service;

import br.ufes.model.Categoria;
import br.ufes.model.HistoricoPreco;
import br.ufes.model.Produto;
import br.ufes.repository.IProdutoRepository;
import java.time.LocalDate;
import java.util.List;

public class CalculoPrecoService {

    private IProdutoRepository produtoRepository;

    public CalculoPrecoService(IProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public void calcularPrecos() {
        List<Produto> produtos = produtoRepository.listar();
        for (Produto produto : produtos) {
            Categoria categoria = produto.getCategoria();
            double percentualLucro = categoria.getPercentualLucro();
            double precoCusto = produto.getPrecoCusto();
            double precoVenda = precoCusto * (1 + percentualLucro / 100);
            
            produto.setPrecoVenda(precoVenda);
            produto.setMargemLucro(percentualLucro);
            HistoricoPreco historico = produto.getHistoricoPreco();

            historico.adicionarPreco(precoVenda,percentualLucro, LocalDate.now());
        }
    }
}