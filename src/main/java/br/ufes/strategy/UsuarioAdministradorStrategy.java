package br.ufes.strategy;

public class UsuarioAdministradorStrategy implements IPerfilUsuarioStrategy{
    @Override
    public boolean podeGerenciarUsuarios() {
        return true;
    }

    @Override
    public boolean podeGerenciarProdutos() {
        return true;
    }

    @Override
    public boolean podeGerenciarPedidoCliente() {
        return true;
    }
    
    @Override
    public String perfilUsuario() {
        return "Administrador";
    }
}
