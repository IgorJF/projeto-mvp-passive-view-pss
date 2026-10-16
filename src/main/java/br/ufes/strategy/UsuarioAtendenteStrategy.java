
package br.ufes.strategy;

public class UsuarioAtendenteStrategy implements IPerfilUsuarioStrategy{
    @Override
    public boolean podeGerenciarUsuarios() {
        return false;
    }

    @Override
    public boolean podeGerenciarProdutos() {
        return true;
    }
    
    @Override
    public String perfilUsuario() {
        return "Atendente";
    }
}
