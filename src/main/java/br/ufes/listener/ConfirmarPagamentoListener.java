package br.ufes.listener;

import br.projeto.painelpedidos.listener.IConfirmarPagamentoListener;
import br.projeto.painelpedidos.model.PedidoAbstract;
import java.time.LocalDateTime;

public class ConfirmarPagamentoListener implements IConfirmarPagamentoListener{
    @Override
    public void confirmarPagamento(PedidoAbstract pedido) {
        // abrir o SEU fluxo de pagamento e, ao confirmar:
        pedido.registrarPagamento("Pix - chave CPF", LocalDateTime.now());
    }
}
