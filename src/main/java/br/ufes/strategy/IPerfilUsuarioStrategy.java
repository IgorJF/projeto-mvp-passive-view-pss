package br.ufes.strategy;

public interface IPerfilUsuarioStrategy {
    boolean podeGerenciarUsuarios();
    boolean podeGerenciarProdutos();
    String perfilUsuario();
}
