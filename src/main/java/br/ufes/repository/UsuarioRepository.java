package br.ufes.repository;

import br.ufes.model.Usuario;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository implements IUsuarioRepository{
    private List<Usuario> usuarios;
    private int idContador;

    public UsuarioRepository(){
        usuarios = new ArrayList<>();
        idContador = 1;
    }

    @Override
    public void salvar(Usuario usuario){
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario nao e valido");
        }

        if (usuario.getId() == 0){
            usuario.setId(idContador++);
            usuarios.add(usuario);
        }

        for (int i = 0; i < usuarios.size(); i++){
            if (usuarios.get(i).getId() == usuario.getId()) {
                usuarios.set(i, usuario);
            }
        }
    }

    @Override
    public List<Usuario> listar() {
        return usuarios;
    }
}