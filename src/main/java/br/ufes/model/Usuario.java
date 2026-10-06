package br.ufes.model;

/**
 *
 * @author igorj
 */
public class Usuario {
    private int id;
    private String nomeCompleto;
    private String email;
    private String nomeUsuario;
    private String senha;

    public Usuario(String nomeCompleto, String email, String nomeUsuario, String senha){
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public String getEmail() {
        return email;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getSenha() {
        return senha;
    }
}