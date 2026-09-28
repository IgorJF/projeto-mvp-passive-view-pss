package br.ufes.model;

public class Categoria {
    private int id;
    private String nomeCategoria;
    private double percentualLucro;
    
    public Categoria(String nomeCategoria, double percentualLucro){
        this.nomeCategoria = nomeCategoria;
        this.percentualLucro = percentualLucro;
    }
}
