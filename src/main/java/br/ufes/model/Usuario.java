package br.ufes.model;

import br.ufes.strategy.IPerfilUsuarioStrategy;

public class Usuario {
    private int idUsuario;
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

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public IPerfilUsuarioStrategy getPerfil() {
        return perfil;
    }

    public void setPerfil(IPerfilUsuarioStrategy perfil) {
        this.perfil = perfil;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

    public boolean isAdministrador() {
        return perfil.perfilUsuario().equals("Administrador");
    }
}
