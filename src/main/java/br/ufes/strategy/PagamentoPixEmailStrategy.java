package br.ufes.strategy;

public class PagamentoPixEmailStrategy implements IFormaPagamentoStrategy {
    @Override
    public String formaPagamento() {
        return "Pix - chave e-mail";
    }

    @Override
    public void validarDadosPagamento() {
    }
}
