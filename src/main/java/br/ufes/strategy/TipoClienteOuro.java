package br.ufes.strategy;

import br.ufes.model.Cliente;

public class TipoClienteOuro extends TipoCliente{
    public TipoClienteOuro(Cliente cliente) {
        super(cliente);
    }
    
    @Override
    public String tipoAtualCliente() {
        return "Ouro";
    }

    @Override
    public void promoverTipoCliente() {
        if(cliente.getTipoCliente().tipoAtualCliente().equals("Ouro") && cliente.getTotalCompradoHistorico() >= 200){
            cliente.setTipoCliente(new TipoClienteDiamante(cliente));
        }
    }
}
