
package br.ufes.strategy;

import br.ufes.model.Cliente;

public class TipoClienteBronze extends TipoCliente{
    public TipoClienteBronze(Cliente cliente) {
        super(cliente);
    }
    
    @Override
    public String tipoAtualCliente() {
        return "Bronze";
    }

    @Override
    public void promoverTipoCliente() {
        if(cliente.getTipoCliente().tipoAtualCliente().equals("Bronze") && cliente.getTotalCompradoHistorico() >= 50){
            cliente.setTipoCliente(new TipoClientePrata(cliente));
        }
    }
}
