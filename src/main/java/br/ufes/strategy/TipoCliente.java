package br.ufes.strategy;

import br.ufes.model.Cliente;

public abstract class TipoCliente {
    protected Cliente cliente;

    public TipoCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public abstract String tipoAtualCliente();
    
    //mudar as regras de promover e desmoprover quando implementar pedido
    public void promoverTipoCliente(){
        throw  new IllegalArgumentException("Nao a como promover clientes nesse nivel");
    }
    public void rebaixarTipoCliente(){
        throw  new IllegalArgumentException("Nao a como rebaixar clientes nesse nivel");
    }
}
