package br.ufes.model;

import java.time.LocalDate;

    public class HistoricoPreco {

    private Produto produto;
    private LocalDate data;
    private double percentualLucro;
    private double precoVenda;

    public HistoricoPreco(Produto produto, LocalDate data, double percentualLucro, double precoVenda) {
        this.produto = produto;
        this.data = data;
        this.percentualLucro = percentualLucro;
        this.precoVenda = precoVenda;
    }

    public Produto getProduto() {
        return produto;
    }

    public LocalDate getData() {
        return data;
    }

    public double getPercentualLucro() {
        return percentualLucro;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }
}



