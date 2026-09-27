package br.ufes.model;

public class Categoria {
    private int id;
    private String nomeCategoria;
    private String percentualLucro;
    
    public Categoria(String nomeCategoria, String percentualLucro){
        this.nomeCategoria = nomeCategoria;
        this.percentualLucro = percentualLucro;
    }
}
