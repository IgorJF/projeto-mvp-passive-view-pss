package br.ufes.model;

public class Produto {
    private int id;
    private String nomeProduto;
    private double precoCusto;
    private Categoria categoria;
    
    public Produto(String nomeProduto, double precoCusto, Categoria categoria){
        this.nomeProduto = nomeProduto;
        this.precoCusto = precoCusto;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
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
}
