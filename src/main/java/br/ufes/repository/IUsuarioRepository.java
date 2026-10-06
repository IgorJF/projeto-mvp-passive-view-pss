package br.ufes.repository;

import br.ufes.model.Usuario;
import java.util.List;

/**
 *
 * @author igorj
 */
public interface IUsuarioRepository {
    void salvar(Usuario usuario);
    List<Usuario> listar();
}