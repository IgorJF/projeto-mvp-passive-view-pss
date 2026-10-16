package br.ufes.model;

public class Categoria{
    private int idCategoria;
    private String nomeCategoria;
    private double percentualLucro;
    
    public Categoria(String nomeCategoria, double percentualLucro){
        if(nomeCategoria.isBlank()){
            throw new IllegalArgumentException("Nome de categoria nao pode ser vazio");
        }
        if(percentualLucro < 0){
            throw new IllegalArgumentException("Percentual de lucro nao pode ser menor que zero");
        }
        this.nomeCategoria = nomeCategoria;
        this.percentualLucro = percentualLucro;
    }
    
    public int getIdCategoria(){
        return idCategoria;
    }
    
    public String getNomeCategoria(){
        return nomeCategoria;
    }

    public double getPercentualLucro() {
        return percentualLucro;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public void setPercentualLucro(double percentualLucro) {
        this.percentualLucro = percentualLucro;
    } 
    
    public String toString(){
        return nomeCategoria;
    }
}