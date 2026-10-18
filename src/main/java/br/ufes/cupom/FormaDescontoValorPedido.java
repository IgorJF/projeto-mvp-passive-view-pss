package br.ufes.cupom;

import br.ufes.model.Pedido;
import java.util.Optional;

public class FormaDescontoValorPedido implements IFormaDescontoTaxaEntrega {
    private double limiteValorPedido;
    private static double VALOR_DESCONTO = 5.00;

    public FormaDescontoValorPedido(double limiteValorPedido) {
        this.limiteValorPedido = limiteValorPedido;
    }

    @Override
    public Optional<CupomDescontoEntrega> calcularDesconto(Pedido pedido){
        Optional<CupomDescontoEntrega> optCupomDescontoValorPedido = Optional.empty();

        if(limiteValorPedido < pedido.getValorPedidoComDesconto()){
            optCupomDescontoValorPedido = Optional.of(new CupomDescontoEntrega("Desconto Valor Pedido", VALOR_DESCONTO));
        }
        return optCupomDescontoValorPedido;
    }
}
