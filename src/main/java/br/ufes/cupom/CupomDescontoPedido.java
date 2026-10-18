package br.ufes.cupom;

import java.time.LocalDateTime;

/**
 *
 * @author igorj
 */

public class CupomDescontoPedido {
    private String codigo;
    private double percentualDesconto;
    private LocalDateTime inicioValidade;
    private LocalDateTime finalValidade;

    public CupomDescontoPedido(String codigo, double percentualDesconto, LocalDateTime inicioValidade, LocalDateTime finalValidade) {
        if (codigo == null) {
            throw new IllegalArgumentException("O codigo do cupom nao pode ser vazio.");
        }
        if (percentualDesconto <= 0) {
            throw new IllegalArgumentException("O percentual de desconto deve ser maior que zero.");
        }
        if (inicioValidade == null) {
            throw new IllegalArgumentException("A data de inicio da validade nao pode ser nula.");
        }
        if (finalValidade == null) {
            throw new IllegalArgumentException("A data final da validade nao pode ser nula.");
        }
        if (finalValidade.isBefore(inicioValidade)) {
            throw new IllegalArgumentException("A data final da validade nao pode ser anterior a data de inicio.");
        }
        this.codigo = codigo;
        this.percentualDesconto = percentualDesconto;
        this.inicioValidade = inicioValidade;
        this.finalValidade = finalValidade;
    }
    
    /*public boolean getCupomValidoPorDataAtual(){;;
       LocalDateTime horaAtual = LocalDateTime.now();
       if(horaAtual.isAfter(inicioValidade) && horaAtual.isBefore(finalValidade)){
            return true;
            //nao pode ser operador <> porque LocalDateTime eh objeto
        }
        return false;
    }*/
    
    public boolean getCupomValidoPorData(LocalDateTime dataDoPedido) {
        if(!dataDoPedido.isBefore(inicioValidade) && !dataDoPedido.isAfter(finalValidade)){
            return true;
        }
        return false;
    }
    
    public String getCodigo(){
        return codigo;
    }
    
    public double getPercentualDesconto(){
        return percentualDesconto;
    }
    
    public LocalDateTime getInicioValidade(){
        return inicioValidade;
    }
    
    public LocalDateTime getFinalValidade(){
        return finalValidade;
    }
    
    public void setCodigo(String codigo){
        this.codigo = codigo;
    }
    
    public void setPercentualDesconto(double percentualDesconto) {
        this.percentualDesconto = percentualDesconto;
    }

    public void setInicioValidade(LocalDateTime inicioValidade) {
        this.inicioValidade = inicioValidade;
    }
    
    public void setFimValidade(LocalDateTime finalValidade) {
        this.finalValidade = finalValidade;
    }
    
    @Override
    public String toString() {
        return  "Codigo: " + codigo + 
                ", Percentual: " + percentualDesconto * 100 +
                ", Inicio Validade: " + inicioValidade +
                ", Final Validade: " + finalValidade;
    }
}
