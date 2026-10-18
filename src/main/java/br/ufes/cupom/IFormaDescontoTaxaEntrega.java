package br.ufes.cupom;

/**
 *
 * @author igorj
 */

import br.ufes.model.Pedido;
import java.util.Optional;

public interface IFormaDescontoTaxaEntrega {
    Optional<CupomDescontoEntrega> calcularDesconto(Pedido pedido);
    //boolean seAplica(Pedido pedido);;
}
