package br.ufes.service;

import br.ufes.model.Pagamento;
import br.ufes.model.Pedido;
import br.ufes.strategy.IFormaPagamentoStrategy;
import java.time.LocalDateTime;
import java.util.UUID;

//esta etapa nao integra com adquirente, banco ou gateway, a finalizacao apenas registra o pagamento no pedido
//o simulador de falhas eh chamado antes do registro, se ele lancar a excecao nada eh gravado e o pedido continua como estava
//somente a forma de pagamento e a data e hora sao guardadas, o codigo da transacao serve apenas para a confirmacao na tela

public class PagamentoService {
    private SimuladorPagamentoService simuladorPagamento;

    public PagamentoService() {
        this.simuladorPagamento = SimuladorPagamentoService.getInstancia();
    }

    public Pagamento finalizarPagamento(Pedido pedido, IFormaPagamentoStrategy formaPagamento) {
        if (pedido == null) {
            throw new IllegalArgumentException("Nao existe pedido para pagamento.");
        }
        if (formaPagamento == null) {
            throw new IllegalArgumentException("Selecione uma forma de pagamento.");
        }
        if (!pedido.estaNovo()) {
            throw new IllegalArgumentException("Somente um pedido novo pode receber o registro do pagamento.");
        }
        formaPagamento.validarDadosPagamento();
        simuladorPagamento.simularFalhaPagamento();
        LocalDateTime dataHoraPagamento = LocalDateTime.now();
        pedido.registrarPagamento(formaPagamento.formaPagamento(), dataHoraPagamento);
        Pagamento pagamento = new Pagamento(formaPagamento.formaPagamento(), dataHoraPagamento);
        pedido.setPagamento(pagamento);
        return pagamento;
    }

    public String gerarCodigoTransacao() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase();
    }
}
