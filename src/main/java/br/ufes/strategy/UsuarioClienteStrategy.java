package br.ufes.strategy;

public class UsuarioClienteStrategy implements IPerfilUsuarioStrategy{
    @Override
    public boolean podeGerenciarUsuarios() {
        return false;
    }

    @Override
    public boolean podeGerenciarProdutos() {
        return false;
    }
    
    @Override
    public boolean podeGerenciarPedidoCliente() {
        return false;
    }

    @Override
    public String perfilUsuario() {
        return "Cliente";
    }
}
