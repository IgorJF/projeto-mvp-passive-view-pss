package br.ufes.service;

import java.util.Random;

public class SimuladorPagamentoService {
    private static SimuladorPagamentoService instancia;
    private Random sorteio;

    private SimuladorPagamentoService() {
        sorteio = new Random();
    }

    public static SimuladorPagamentoService getInstancia() {
        if (instancia == null) {
            instancia = new SimuladorPagamentoService();
        }
        return instancia;
    }

    public void simularFalhaPagamento() {
        double percentual = sorteio.nextDouble() * 100;
        if (percentual < 10) {
            throw new RuntimeException("Servidor indisponivel");
        }
        if (percentual < 20) {
            throw new RuntimeException("Falha ao processar pagamento");
        }
        if (percentual < 25) {
            throw new RuntimeException("Tempo limite excedido");
        }
        if (percentual < 30) {
            throw new RuntimeException("Falha de comunicacao com o servico de pagamento");
        }
    }
}
