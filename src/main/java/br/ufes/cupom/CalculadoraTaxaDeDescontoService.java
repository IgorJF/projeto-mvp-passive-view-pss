package br.ufes.cupom;

/**
 *
 * @author igorj
 */

import br.ufes.model.Pedido;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CalculadoraTaxaDeDescontoService {
    private List<IFormaDescontoTaxaEntrega> metodosDesconto;

    public CalculadoraTaxaDeDescontoService(){ //construtor necessario no diagrama
        metodosDesconto = new ArrayList<>();
        metodosDesconto.add(new FormaDescontoTipoProduto());
        metodosDesconto.add(new FormaDescontoTaxaPorBairro());
        metodosDesconto.add(new FormaDescontoValorPedido(200));
        metodosDesconto.add(new FormaDescontoTaxaPorTipoCliente());
    }

    public void calcularDesconto(Pedido pedido){
        if(pedido == null){
            throw new IllegalArgumentException("Informe um pedido valido");
        }
        for (IFormaDescontoTaxaEntrega metodo : metodosDesconto){
            Optional<CupomDescontoEntrega> optCupomDescontoEntrega = metodo.calcularDesconto(pedido);
            //O optional permite que verifique o conteudo da variavel antes de realizar alguma operacao
            if (optCupomDescontoEntrega.isPresent()) {
                CupomDescontoEntrega cupom = optCupomDescontoEntrega.get();
                double taxaAntes = pedido.getTaxaEntrega();

                pedido.aplicarDescontoTaxaEntrega(cupom.getValorDesconto());

                double taxaDepois = pedido.getTaxaEntrega();
                double descontoReal = taxaAntes - taxaDepois;

                if (descontoReal > 0) {
                    cupom.setValorDesconto(descontoReal);
                    pedido.getCuponsDescontoEntrega().add(cupom);
                }
            }
        }
    }
}
