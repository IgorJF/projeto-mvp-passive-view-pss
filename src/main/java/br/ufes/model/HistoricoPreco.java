package br.ufes.model;

import java.time.LocalDate;

//um HistoricoPreco eh o registro de UM calculo de preco de UM produto: data, percentual de lucro usado e preco de venda resultante
//os campos sao final e nao existem setters, entao um registro nunca muda depois de criado (mesmo que a categoria mude depois)
//o produto eh identificado pelo id, e o repositorio de historicos eh quem guarda todos os registros

public class HistoricoPreco {
    private final int idProduto;
    private final LocalDate data;
    private final double percentualLucro;
    private final double precoVenda;

    public HistoricoPreco(int idProduto, LocalDate data, double percentualLucro, double precoVenda) {
        if(data == null){
            throw new IllegalArgumentException("Data do calculo nao pode ser nula");
        }
        if(percentualLucro < 0){
            throw new IllegalArgumentException("Percentual de lucro nao pode ser menor que zero");
        }
        if(precoVenda < 0){
            throw new IllegalArgumentException("Preco de venda nao pode ser menor que zero");
        }
        this.idProduto = idProduto;
        this.data = data;
        this.percentualLucro = percentualLucro;
        this.precoVenda = precoVenda;
    }

    public int getIdProduto() {
        return idProduto;
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
