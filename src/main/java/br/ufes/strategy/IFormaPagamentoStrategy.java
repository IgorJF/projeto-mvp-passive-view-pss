package br.ufes.strategy;

public interface IFormaPagamentoStrategy {
    String formaPagamento();
    void validarDadosPagamento();
}
