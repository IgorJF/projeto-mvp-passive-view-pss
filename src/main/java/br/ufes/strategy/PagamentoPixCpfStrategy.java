package br.ufes.strategy;

public class PagamentoPixCpfStrategy implements IFormaPagamentoStrategy {
    @Override
    public String formaPagamento() {
        return "Pix - chave CPF";
    }

    @Override
    public void validarDadosPagamento() {
    }
}
