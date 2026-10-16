
package br.ufes.strategy;

import br.ufes.model.Cliente;

public class TipoClienteDiamante extends TipoCliente {

    public TipoClienteDiamante(Cliente cliente){
        super(cliente);
    }

    @Override
    public String tipoAtualCliente() {
        return "Diamanete";
    }

}
