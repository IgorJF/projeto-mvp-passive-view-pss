package br.ufes.repository;

import br.ufes.model.Cliente;
import java.util.List;

public interface IClienteRepository {
    void salvar(Cliente cliente);
    List<Cliente> listar();
    Cliente buscarPorId(int id);
    void editar(Cliente cliente);
    void excluir(Cliente cliente);
}
