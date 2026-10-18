package br.ufes.cupom;
/**
 *
 * @author igorj
 */

import br.ufes.model.Pedido;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FormaDescontoTaxaPorTipoCliente implements IFormaDescontoTaxaEntrega {
    private Map<String, Double> descontosPorTipoCliente;

    public FormaDescontoTaxaPorTipoCliente() {
        descontosPorTipoCliente = new HashMap<>();
        descontosPorTipoCliente.put("Ouro", 3.00);
        descontosPorTipoCliente.put("Prata", 2.00);
        descontosPorTipoCliente.put("Bronze", 1.00);
    }

    @Override
    public Optional<CupomDescontoEntrega> calcularDesconto(Pedido pedido){
        Optional<CupomDescontoEntrega> optCupomDescontoEntrega = Optional.empty();
        if(pedido.getCliente() == null || pedido.getCliente().getTipoCliente() == null){
            return optCupomDescontoEntrega;
        }
        //no projeto atual o tipo do cliente e fornecido pela strategy TipoCliente
        String tipoCliente = pedido.getCliente().getTipoCliente().tipoAtualCliente();

        if(descontosPorTipoCliente.containsKey(tipoCliente)){
            double valor = descontosPorTipoCliente.get(tipoCliente);
            optCupomDescontoEntrega = Optional.of(new CupomDescontoEntrega("Desconto Tipo Cliente " + tipoCliente, valor));
        }
        return optCupomDescontoEntrega;
    }
}
