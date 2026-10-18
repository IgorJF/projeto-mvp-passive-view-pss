package br.ufes.model;

import java.time.LocalDateTime;

public class Pagamento {
    private String formaPagamento;
    private LocalDateTime dataHoraPagamento;

    public Pagamento(String formaPagamento, LocalDateTime dataHoraPagamento) {
        if (formaPagamento == null || formaPagamento.isBlank()) {
            throw new IllegalArgumentException("Informe a forma de pagamento utilizada");
        }
        if (dataHoraPagamento == null) {
            throw new IllegalArgumentException("Informe a data e hora do pagamento");
        }
        this.formaPagamento = formaPagamento;
        this.dataHoraPagamento = dataHoraPagamento;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public LocalDateTime getDataHoraPagamento() {
        return dataHoraPagamento;
    }

    @Override
    public String toString() {
        return formaPagamento + " em " + dataHoraPagamento;
    }
}
