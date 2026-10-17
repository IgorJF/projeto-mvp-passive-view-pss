package br.ufes.service;

import br.ufes.model.Cliente;
import br.ufes.model.Usuario;
import br.ufes.repository.IClienteRepository;
import br.ufes.repository.IUsuarioRepository;
import java.util.List;

//todo cliente novo nasce com o tipo PRATA, definido no construtor de Cliente
//o tipo de cliente e o total de compras nao sao informados pela tela, por isso nao aparecem como parametro
//o total de compras eh um dado calculado e a tela apenas apresenta o valor devolvido por calcularTotalCompras

public class ClienteService {
    private IClienteRepository clienteRepository;
    private IUsuarioRepository usuarioRepository;

    public ClienteService(IClienteRepository clienteRepository, IUsuarioRepository usuarioRepository) {
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Cliente salvar(String nomeCliente, String logradouro, String bairro, String cidade, String uf) {
        if (nomeCliente == null || nomeCliente.isBlank()){
            throw new IllegalArgumentException("O nome do cliente eh obrigatorio");
        }
        if (logradouro == null || logradouro.isBlank()){
            throw new IllegalArgumentException("O logradouro eh obrigatorio");
        }
        if (bairro == null || bairro.isBlank()){
            throw new IllegalArgumentException("O bairro eh obrigatorio");
        }
        if (cidade == null || cidade.isBlank()){
            throw new IllegalArgumentException("A cidade eh obrigatoria");
        }
        if (uf == null || uf.isBlank()){
            throw new IllegalArgumentException("A UF eh obrigatoria");
        }
        Cliente cliente = new Cliente(nomeCliente.trim(), logradouro.trim(), bairro.trim(), cidade.trim(), uf.trim().toUpperCase());
        clienteRepository.salvar(cliente);
        return cliente;
    }

    public void editar(Cliente cliente, String nomeCliente, String logradouro, String bairro, String cidade, String uf) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente nao selecionado para edicao.");
        }
        if (nomeCliente == null || nomeCliente.isBlank()){
            throw new IllegalArgumentException("O nome do cliente eh obrigatorio");
        }
        if (logradouro == null || logradouro.isBlank()){
            throw new IllegalArgumentException("O logradouro eh obrigatorio");
        }
        if (bairro == null || bairro.isBlank()){
            throw new IllegalArgumentException("O bairro eh obrigatorio");
        }
        if (cidade == null || cidade.isBlank()){
            throw new IllegalArgumentException("A cidade eh obrigatoria");
        }
        if (uf == null || uf.isBlank()){
            throw new IllegalArgumentException("A UF eh obrigatoria");
        }
        cliente.setNomeCliente(nomeCliente.trim());
        cliente.setLogradouro(logradouro.trim());
        cliente.setBairro(bairro.trim());
        cliente.setCidade(cidade.trim());
        cliente.setUf(uf.trim().toUpperCase());
        clienteRepository.editar(cliente);
    }

    public void excluir(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente nao selecionado para exclusao.");
        }
        validarExclusao(cliente);
        clienteRepository.excluir(cliente);
    }

    //um cliente que possui usuario associado nao pode ser excluido
    public void validarExclusao(Cliente cliente) {
        for (Usuario usuario : usuarioRepository.listar()) {
            if (usuario.getClienteAssociado() == cliente) {
                throw new IllegalArgumentException("Existe um usuario associado a este cliente. Ele nao pode ser excluido.");
            }
        }
    }

    public double calcularTotalCompras(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente nao pode ser nulo");
        }
        return cliente.getTotalCompradoHistorico();
    }

    public List<Cliente> listar() {
        return clienteRepository.listar();
    }
}
