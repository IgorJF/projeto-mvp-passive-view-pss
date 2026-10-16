package br.ufes.service;

import br.ufes.model.Usuario;
import br.ufes.repository.IUsuarioRepository;

public class UsuarioService {
    private IUsuarioRepository usuarioRepository;

    public UsuarioService(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void salvar(Usuario usuario){
         if (usuarioRepository.buscarPorEmailOuNomeUsuario(usuario.getNomeUsuario()) != null) {
            throw new IllegalArgumentException("Nome de usuario ja cadastrado.");
        }
        if (usuarioRepository.buscarPorEmailOuNomeUsuario(usuario.getEmail()) != null) {
            throw new IllegalArgumentException("E-mail ja cadastrado.");
        }
        usuarioRepository.salvar(usuario);
    }
}