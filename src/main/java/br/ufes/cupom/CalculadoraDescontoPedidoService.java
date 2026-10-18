package br.ufes.cupom;

import br.ufes.model.Pedido;

/**
 *
 * @author igorj
 */
public class CalculadoraDescontoPedidoService {
    private CupomDescontoPedidoRepository cuponsCadastrados;

    public CalculadoraDescontoPedidoService() {
        this.cuponsCadastrados = new CupomDescontoPedidoRepository();
    }

    public void aplicarCupomNoPedido(Pedido pedido, String codigoCupom) {
        CupomDescontoPedido cupom = cuponsCadastrados.buscarCupom(codigoCupom);

        if (cupom == null) {
            throw new IllegalArgumentException("Cupom nao existe");
        }

        if (!cupom.getCupomValidoPorData(pedido.getDataHora())) {
            throw new IllegalArgumentException("Cupom fora da validade.");
        }

        double valorDesconto = pedido.getValorTotalPedido() * cupom.getPercentualDesconto();

        if (pedido.getCupomDescontoPedido() == null) {
            pedido.adicionarCupomDescontoPedido(cupom);
            pedido.aplicarDescontoCupomPedido(valorDesconto);
        }
        else{
            CupomDescontoPedido cupomPedido = pedido.getCupomDescontoPedido();
            if (cupom.getPercentualDesconto() >= cupomPedido.getPercentualDesconto()) {
                pedido.adicionarCupomDescontoPedido(cupom);
                pedido.aplicarDescontoCupomPedido(valorDesconto);
            }
        }
    }
}
