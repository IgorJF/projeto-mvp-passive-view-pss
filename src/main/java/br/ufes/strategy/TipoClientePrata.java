package br.ufes.strategy;

import br.ufes.model.Cliente;

public class TipoClientePrata extends TipoCliente{
    public TipoClientePrata(Cliente cliente) {
        super(cliente);
    }
    
    @Override
    public String tipoAtualCliente() {
        return "Prata";
    }

    @Override
    public void promoverTipoCliente() {
        if(cliente.getTipoCliente().tipoAtualCliente().equals("Prata") && cliente.getTotalCompradoHistorico() >= 100){
            cliente.setTipoCliente(new TipoClienteOuro(cliente));
        }
    }
}
