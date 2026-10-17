package br.ufes.repository;

import br.ufes.model.Cliente;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ClienteRepository implements IClienteRepository {
    private List<Cliente> clientes;
    private int contadorId;

    public ClienteRepository(){
        clientes = new ArrayList<>();
        contadorId = 0;
    }

    @Override
    public void salvar(Cliente cliente) {
        if(cliente == null){
            throw new IllegalArgumentException("Cliente nao pode ser nulo");
        }
        cliente.setIdCliente(contadorId);
        clientes.add(cliente);
        contadorId++;
    }

    @Override
    public List<Cliente> listar() {
        return new ArrayList<>(clientes);
    }

    @Override
    public Cliente buscarPorId(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getIdCliente() == id) {
                return cliente;
            }
        }
        return null;
    }

    @Override
    public void editar(Cliente cliente) {
        if(cliente == null){
            throw new IllegalArgumentException("Cliente nao pode ser nulo");
        }
        for (int i = 0; i < clientes.size(); i++) {
            Cliente clienteSelecionado = clientes.get(i);
            if (clienteSelecionado.getIdCliente() == cliente.getIdCliente()) {
                clientes.set(i, cliente);
            }
        }
    }

    @Override
    public void excluir(Cliente cliente) {
        if(cliente == null){
            throw new IllegalArgumentException("Cliente nao pode ser nulo");
        }
        Iterator<Cliente> it = clientes.iterator();
        while(it.hasNext()) {
            Cliente clienteAtual = it.next();
            if (clienteAtual.getIdCliente() == cliente.getIdCliente()) {
                it.remove();
            }
        }
    }
}
