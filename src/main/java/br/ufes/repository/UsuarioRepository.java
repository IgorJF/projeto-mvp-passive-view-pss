package br.ufes.repository;

import br.ufes.model.Usuario;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository implements IUsuarioRepository {
    private List<Usuario> usuarios = new ArrayList<>();

    @Override
    public void salvar(Usuario usuario) {
        usuarios.add(usuario);
    }

    @Override
    public List<Usuario> listar() {
        return new ArrayList<>(usuarios);
    }
    
    @Override
    public Usuario buscarPorEmailOuNomeUsuario(String login){
        for(Usuario usuario : usuarios){
            if(usuario.getEmail().equals(login) || usuario.getNomeUsuario().equals(login)){
                return usuario;
            }
        }
        return null;
    }
}