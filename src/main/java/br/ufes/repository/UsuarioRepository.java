package br.ufes.repository;

import br.ufes.model.Usuario;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UsuarioRepository implements IUsuarioRepository {
    private List<Usuario> usuarios;
    private int contadorId;

    public UsuarioRepository(){
        usuarios = new ArrayList<>();
        contadorId = 0;
    }

    @Override
    public void salvar(Usuario usuario) {
        if(usuario == null){
            throw new IllegalArgumentException("Usuario nao pode ser nulo");
        }
        usuario.setIdUsuario(contadorId);
        usuarios.add(usuario);
        contadorId++;
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

    @Override
    public Usuario buscarPorId(int id){
        for(Usuario usuario : usuarios){
            if(usuario.getIdUsuario() == id){
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void editar(Usuario usuario){
        if(usuario == null){
            throw new IllegalArgumentException("Usuario nao pode ser nulo");
        }
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuarioSelecionado = usuarios.get(i);
            if (usuarioSelecionado.getIdUsuario() == usuario.getIdUsuario()) {
                usuarios.set(i, usuario);
            }
        }
    }

    @Override
    public void excluir(Usuario usuario){
        if(usuario == null){
            throw new IllegalArgumentException("Usuario nao pode ser nulo");
        }
        Iterator<Usuario> it = usuarios.iterator();
        while(it.hasNext()) {
            Usuario usuarioAtual = it.next();
            if (usuarioAtual.getIdUsuario() == usuario.getIdUsuario()) {
                it.remove();
            }
        }
    }
}
