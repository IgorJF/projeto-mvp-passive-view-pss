package br.ufes.model;

import br.ufes.repository.IHistoricoPrecoRepository;

public class Produto {
    private int id;
    private String nomeProduto;
    private double precoCusto;
    private Categoria categoria;
    private double margemLucro;
    private double precoVenda;
    private HistoricoPreco historicoPreco;
    
    public Produto(String nomeProduto, double precoCusto, Categoria categoria){
        this.nomeProduto = nomeProduto;
        this.precoCusto = precoCusto;
        this.categoria = categoria;
        this.historicoPreco = new HistoricoPreco();
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public double getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(double precoCusto) {
        this.precoCusto = precoCusto;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
    public double getMargemLucro() {
        return margemLucro;
    }

    public void setMargemLucro(double margemLucro) {
        this.margemLucro = margemLucro;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }
    
     public HistoricoPreco getHistoricoPreco() {
        return historicoPreco;
    }
}




