package br.ufes.service;

import br.ufes.model.Cliente;
import br.ufes.model.Usuario;
import br.ufes.repository.IUsuarioRepository;
import br.ufes.strategy.IPerfilUsuarioStrategy;
import java.util.List;

//a confirmacao de senha eh validada apenas na entrada de dados e nao eh armazenada
//na edicao, uma senha vazia significa manter a senha ja cadastrada
//o status eh alterado somente por habilitar e desabilitar, nunca pela edicao dos dados
//o usuario administrador nao pode ser desabilitado nem excluido

public class UsuarioService {
    private IUsuarioRepository usuarioRepository;

    public UsuarioService(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void salvar(String nome, String nomeUsuario, String email, String senha, IPerfilUsuarioStrategy perfil, Cliente clienteAssociado){
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome completo eh obrigatorio.");
        }
        if (nomeUsuario == null || nomeUsuario.isBlank()) {
            throw new IllegalArgumentException("O nome de usuario eh obrigatorio.");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("O e-mail eh obrigatorio.");
        }
        if (perfil == null) {
            throw new IllegalArgumentException("O perfil do usuario eh obrigatorio.");
        }
        if (perfil.perfilUsuario().equals("Administrador")) {
            throw new IllegalArgumentException("A manutencao vale apenas para usuarios nao administradores.");
        }
        if (perfil.perfilUsuario().equals("Cliente") && clienteAssociado == null) {
            throw new IllegalArgumentException("Usuario com perfil Cliente deve possuir um cliente associado.");
        }
        if (senha == null || senha.isBlank()) {
            throw new IllegalArgumentException("A senha eh obrigatoria.");
        }
        validarDuplicidade(nomeUsuario, email, null);
        Usuario usuario = new Usuario(nome, nomeUsuario, email, senha, perfil, true);
        usuario.setClienteAssociado(clienteAssociado);
        usuarioRepository.salvar(usuario);
    }

    public void editar(Usuario usuario, String nome, String nomeUsuario, String email, String senha, IPerfilUsuarioStrategy perfil, Cliente clienteAssociado){
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario nao selecionado para edicao.");
        }
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome completo eh obrigatorio.");
        }
        if (nomeUsuario == null || nomeUsuario.isBlank()) {
            throw new IllegalArgumentException("O nome de usuario eh obrigatorio.");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("O e-mail eh obrigatorio.");
        }
        if (perfil == null) {
            throw new IllegalArgumentException("O perfil do usuario eh obrigatorio.");
        }
        if (perfil.perfilUsuario().equals("Administrador")) {
            throw new IllegalArgumentException("A manutencao vale apenas para usuarios nao administradores.");
        }
        if (perfil.perfilUsuario().equals("Cliente") && clienteAssociado == null) {
            throw new IllegalArgumentException("Usuario com perfil Cliente deve possuir um cliente associado.");
        }
        validarDuplicidade(nomeUsuario, email, usuario);
        usuario.setNome(nome);
        usuario.setNomeUsuario(nomeUsuario);
        usuario.setEmail(email);
        usuario.setPerfil(perfil);
        usuario.setClienteAssociado(clienteAssociado);
        if (senha != null && !senha.isBlank()) {
            usuario.setSenha(senha);
        }
        usuarioRepository.editar(usuario);
    }

    public void excluir(Usuario usuario){
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario nao selecionado para exclusao.");
        }
        if (usuario.isAdministrador()) {
            throw new IllegalArgumentException("O registro do Administrador nao pode ser excluido.");
        }
        usuarioRepository.excluir(usuario);
    }

    public void habilitar(Usuario usuario){
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario nao selecionado.");
        }
        if (usuario.isAdministrador()) {
            throw new IllegalArgumentException("O Administrador nao pode ter o status alterado.");
        }
        usuario.setStatus(true);
        usuarioRepository.editar(usuario);
    }

    public void desabilitar(Usuario usuario){
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario nao selecionado.");
        }
        if (usuario.isAdministrador()) {
            throw new IllegalArgumentException("O Administrador nao pode ser desabilitado.");
        }
        usuario.setStatus(false);
        usuarioRepository.editar(usuario);
    }

    public List<Usuario> listar(){
        return usuarioRepository.listar();
    }

    private void validarDuplicidade(String nomeUsuario, String email, Usuario usuarioAtual){
        for (Usuario existente : usuarioRepository.listar()) {
            if (existente == usuarioAtual) {
                continue;
            }
            if (existente.getNomeUsuario().equalsIgnoreCase(nomeUsuario.trim())) {
                throw new IllegalArgumentException("Nome de usuario ja cadastrado.");
            }
            if (existente.getEmail().equalsIgnoreCase(email.trim())) {
                throw new IllegalArgumentException("E-mail ja cadastrado.");
            }
        }
    }
}
