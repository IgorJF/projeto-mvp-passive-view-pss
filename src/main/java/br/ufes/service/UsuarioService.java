package br.ufes.service;

import br.ufes.model.Usuario;
import br.ufes.repository.IUsuarioRepository;

/**
 *
 * @author igorj
 */
public class UsuarioService {
    private IUsuarioRepository usuarioRepository;
    
    public UsuarioService(IUsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }
    
    public Usuario autenticar(String identificacao, String senha){
        for (Usuario usuario : usuarioRepository.listar()){
            boolean identificacaoValida = usuario.getNomeUsuario().equalsIgnoreCase(identificacao)|| usuario.getEmail().equalsIgnoreCase(identificacao);
            if (identificacaoValida && usuario.getSenha().equals(senha)){
                return usuario;
            }
        }
        return null;
    }
}
