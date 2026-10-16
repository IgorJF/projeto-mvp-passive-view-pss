package br.ufes.service;

import br.ufes.model.Cliente;
import br.ufes.repository.IClienteRepository;
import java.util.List;

public class ClienteService {
    private IClienteRepository clienteRepository;

    public ClienteService(IClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public void cadastrar(Cliente cliente) {
        if (cliente.getNomeCliente() == null){
            throw new IllegalArgumentException("O nome do cliente eh obrigatorio");
        }

        if (cliente.getLogradouro() == null){
            throw new IllegalArgumentException("O logradouro eh obrigatorio");
        }

        if (cliente.getBairro() == null){
            throw new IllegalArgumentException("O bairro eh obrigatorio");
        }

        if (cliente.getCidade() == null){
            throw new IllegalArgumentException("A cidade eh obrigatoria");
        }

        if (cliente.getUf() == null){ 
            throw new IllegalArgumentException("A UF eh obrigatoria");
        }
        clienteRepository.salvar(cliente);
    }

    public List<Cliente> listar() {
        return clienteRepository.listar();
    }

    public void excluir(Cliente cliente) {
        clienteRepository.excluir(cliente);
    }
}