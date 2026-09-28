package br.ufes.model;

public class Categoria {
    private int id;
    private String nomeCategoria;
    private double percentualLucro;
    
    public Categoria(String nomeCategoria, double percentualLucro){
        if(nomeCategoria == null || nomeCategoria.isBlank() || nomeCategoria.isEmpty()){
            throw new IllegalArgumentException("Informa um nome valido para a categoria");
        }
        if(percentualLucro <= 0){
            throw new IllegalArgumentException("O preco deve ser maior que zero");
        }
        this.nomeCategoria = nomeCategoria;
        this.percentualLucro = percentualLucro;
    }

    public int getId() {
        return id;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public double getPercentualLucro() {
        return percentualLucro;
    }
}
