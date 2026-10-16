package br.ufes.service;

import br.ufes.model.Usuario;
import br.ufes.repository.IUsuarioRepository;

public class AutenticacaoSenhaUsuarioService {
    private IUsuarioRepository usuarioRepository;

    public AutenticacaoSenhaUsuarioService(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario autenticarSenhaUsuario(String login, String senhaInformada) {
        Usuario usuario = usuarioRepository.buscarPorEmailOuNomeUsuario(login);
        if (usuario == null) {
            throw new IllegalArgumentException("Login invalido.");
        }
        if (!usuario.autenticarSenha(senhaInformada)) {
            throw new IllegalArgumentException("Senha invalida");
        }
        if (!usuario.isStatus()) {
            throw new IllegalArgumentException("Usuario desabilitado.");
        }
        return usuario;
    }
}