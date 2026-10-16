package br.ufes.model;

public class Produto {
    private int idProduto;
    private String nomeProduto;
    private double precoCusto;
    private Categoria categoria;
    private double margemLucro;
    private double precoVenda;

    public Produto(String nomeProduto, double precoCusto, Categoria categoria){
        if(nomeProduto.isBlank()){
            throw new IllegalArgumentException("Nome de de produto nao pode ser vazio");
        }
        if(precoCusto < 0){
            throw new IllegalArgumentException("Preco de custo deve ser maior que zero");
        }
        if(categoria == null){
            throw new IllegalArgumentException("Categoria nao pode ser nulo");
        }
        this.nomeProduto = nomeProduto;
        this.precoCusto = precoCusto;
        this.categoria = categoria;
    }

    public int getIdProduto() {
        return idProduto;
    }
    
    public void setIdProduto(int id){
        this.idProduto = id;
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
}




