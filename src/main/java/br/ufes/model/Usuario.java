package br.ufes.model;

import br.ufes.strategy.IPerfilUsuarioStrategy;

public class Usuario {
    private String nome;
    private String nomeUsuario;
    private String email;
    private String senha;
    private IPerfilUsuarioStrategy perfil;
    private boolean status;
    private Cliente clienteAssociado = null;

    public Usuario(String nome, String nomeUsuario, String email, String senha, IPerfilUsuarioStrategy perfil, boolean status) {
        this.nome = nome;
        this.nomeUsuario = nomeUsuario;
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;
        this.status = status;
    }

    public String getNome() {
        return nome;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getEmail() {
        return email;
    }

    public IPerfilUsuarioStrategy getPerfil() {
        return perfil;
    }

    public boolean isStatus() {
        return status;
    }

    public Cliente getClienteAssociado() {
        return clienteAssociado;
    }
    
    public void setClienteAssociado(Cliente cliente) {
        this.clienteAssociado = cliente;
    }
    
    public boolean autenticarSenha(String senha) {
        return this.senha.equals(senha);
    } 
}
