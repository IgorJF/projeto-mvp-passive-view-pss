package br.ufes.model;

import br.ufes.strategy.TipoCliente;
import br.ufes.strategy.TipoClientePrata;

public class Cliente {
    private int idCliente;
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

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
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

    public String getEnderecoCompleto() {
        return logradouro + "\nBairro " + bairro + "\n" + cidade + " - " + uf;
    }

    public String toString(){
        return nomeCliente;
    }
}
