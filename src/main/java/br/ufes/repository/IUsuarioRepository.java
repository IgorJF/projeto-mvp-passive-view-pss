package br.ufes.repository;

import br.ufes.model.Usuario;
import java.util.List;

public interface IUsuarioRepository {
    void salvar(Usuario usuario);
    List<Usuario> listar();
    Usuario buscarPorEmailOuNomeUsuario(String login);
    Usuario buscarPorId(int id);
    void editar(Usuario usuario);
    void excluir(Usuario usuario);
}
