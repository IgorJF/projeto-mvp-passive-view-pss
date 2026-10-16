package br.ufes.repository;

import br.ufes.model.Cliente;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepository implements IClienteRepository {
    private List<Cliente> clientes = new ArrayList<>();

    @Override
    public void salvar(Cliente cliente) {
        clientes.add(cliente);
    }

    @Override
    public List<Cliente> listar() {
        return new ArrayList<>(clientes);
    }

    @Override
    public Cliente buscarPorNome(String nomeCliente) {
        for (Cliente cliente : clientes) {
            if (cliente.getNomeCliente().equalsIgnoreCase(nomeCliente)){
                return cliente;
            }
        }

        return null;
    }

    @Override
    public void editar(Cliente cliente) {

    }

    @Override
    public void excluir(Cliente cliente) {
        clientes.remove(cliente);
    }
}