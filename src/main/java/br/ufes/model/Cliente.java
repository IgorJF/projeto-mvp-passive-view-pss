package br.ufes.model;

import br.ufes.strategy.TipoCliente;
import br.ufes.strategy.TipoClienteOuro;
import br.ufes.strategy.TipoClientePrata;

public class Cliente {
    private String nomeCliente;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;
    private TipoCliente tipoCliente;
    private double totalCompradoHistorico;

    public Cliente(String nomeCliente, String logradouro, String bairro, String cidade, String uf) {
        this.nomeCliente = nomeCliente;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.tipoCliente = new TipoClientePrata(this);
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getUf() {
        return uf;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public double getTotalCompradoHistorico() {
        return totalCompradoHistorico;
    }

    public void setTipoCliente(TipoCliente tipo) {
        this.tipoCliente = tipo;
    }

    public void setTotalCompradoHistorico(double valorNovaCompra) {
        this.totalCompradoHistorico += valorNovaCompra;
    }
}
