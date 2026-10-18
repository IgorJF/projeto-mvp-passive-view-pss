package br.ufes.service;

import br.projeto.painelpedidos.repositorio.IPedidoRepository;
import br.ufes.cupom.CalculadoraDescontoPedidoService;
import br.ufes.cupom.CalculadoraTaxaDeDescontoService;
import br.ufes.cupom.CupomDescontoPedido;
import br.ufes.facade.PainelPedidoFacade;
import br.ufes.model.Cliente;
import br.ufes.model.Pedido;
import br.ufes.model.Produto;
import br.ufes.model.ProdutoPedido;
import br.ufes.model.Usuario;
import br.ufes.repository.IClienteRepository;
import br.ufes.repository.IProdutoRepository;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoService {
    private IPedidoRepository pedidoRepository;
    private IClienteRepository clienteRepository;
    private IProdutoRepository produtoRepository;
    private PainelPedidoFacade painelPedidoFacade;
    private CalculadoraTaxaDeDescontoService calculadoraTaxaEntrega;
    private CalculadoraDescontoPedidoService calculadoraDescontoPedido;

    public PedidoService(IPedidoRepository pedidoRepository, IClienteRepository clienteRepository, IProdutoRepository produtoRepository, PainelPedidoFacade painelPedidoFacade) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.painelPedidoFacade = painelPedidoFacade;
        this.calculadoraTaxaEntrega = new CalculadoraTaxaDeDescontoService();
        this.calculadoraDescontoPedido = new CalculadoraDescontoPedidoService();
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.listar();
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.listar();
    }

    //o usuario com perfil de cliente monta o pedido somente para o cliente associado a sua sessao
    public Cliente obterClienteDoUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Nao existe usuario autenticado.");
        }
        Cliente cliente = usuario.getClienteAssociado();
        if (cliente == null) {
            throw new IllegalArgumentException("O usuario autenticado nao possui um cliente associado.");
        }
        return cliente;
    }

    public Pedido iniciarPedido(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Selecione um cliente para iniciar o pedido.");
        }
        Pedido pedido = new Pedido(pedidoRepository.gerarProximoIdentificador(), LocalDateTime.now(), cliente.getNomeCliente(), cliente.getEnderecoCompleto(), 0);
        pedido.setCliente(cliente);
        return pedido;
    }

    //o endereco do pedido eh uma copia do cadastro do cliente e a partir daqui pertence somente ao pedido
    public Pedido alterarCliente(Pedido pedido, Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Selecione um cliente para o pedido.");
        }
        if (pedido == null) {
            return iniciarPedido(cliente);
        }
        return copiar(pedido, cliente, cliente.getEnderecoCompleto(), 0);
    }

    public void adicionarProduto(Pedido pedido, Produto produto) {
        validarPedidoEmElaboracao(pedido);
        if (produto == null) {
            throw new IllegalArgumentException("Selecione um produto para adicionar ao pedido.");
        }
        pedido.adicionarProdutoPedido(new ProdutoPedido(produto, 1));
    }

    //quantidade zero remove o item do pedido
    public void alterarQuantidade(Pedido pedido, ProdutoPedido produtoPedido, int quantidade) {
        validarPedidoEmElaboracao(pedido);
        if (produtoPedido == null) {
            throw new IllegalArgumentException("Selecione um item do pedido.");
        }
        if (quantidade < 0) {
            throw new IllegalArgumentException("A quantidade do item nao pode ser negativa.");
        }
        if (quantidade == 0) {
            pedido.removerProdutoPedido(produtoPedido);
            return;
        }
        produtoPedido.setQuantidade(quantidade);
    }

    //o cupom eh submetido ao mecanismo ja implementado sobre uma copia, assim o pedido em elaboracao nao fica com um desconto ainda nao calculado
    public CupomDescontoPedido validarCupom(Pedido pedido, String codigoCupom) {
        validarPedidoEmElaboracao(pedido);
        if (codigoCupom == null || codigoCupom.isBlank()) {
            throw new IllegalArgumentException("Informe o codigo do cupom de desconto.");
        }
        Pedido simulacao = copiar(pedido, pedido.getCliente(), pedido.getEnderecoEntrega(), 0);
        calculadoraDescontoPedido.aplicarCupomNoPedido(simulacao, codigoCupom.trim());
        return simulacao.getCupomDescontoPedido();
    }

    public Pedido calcularValores(Pedido pedido, String enderecoEntrega, String codigoCupom) {
        validarPedidoEmElaboracao(pedido);
        if (pedido.getProdutos().isEmpty()) {
            throw new IllegalArgumentException("Inclua ao menos um produto no pedido para calcular os valores.");
        }
        return calcular(pedido, enderecoEntrega, codigoCupom, 0);
    }

    public Pedido salvarPedido(Pedido pedido, String enderecoEntrega, String codigoCupom) {
        Pedido calculado = calcularValores(pedido, enderecoEntrega, codigoCupom);
        Pedido pedidoSalvo = calcular(calculado, enderecoEntrega, codigoCupom, calculado.getValorFinalPedido());
        if (painelPedidoFacade != null) {
            painelPedidoFacade.receberNovoPedido(pedidoSalvo);
        }
        else {
            pedidoRepository.salvar(pedidoSalvo);
        }
        return pedidoSalvo;
    }

    private Pedido calcular(Pedido pedido, String enderecoEntrega, String codigoCupom, double valorTotal) {
        Pedido calculado = copiar(pedido, pedido.getCliente(), enderecoEntrega, valorTotal);
        if (codigoCupom != null && !codigoCupom.isBlank()) {
            calculadoraDescontoPedido.aplicarCupomNoPedido(calculado, codigoCupom.trim());
        }
        calculadoraTaxaEntrega.calcularDesconto(calculado);
        return calculado;
    }

    private Pedido copiar(Pedido pedido, Cliente cliente, String enderecoEntrega, double valorTotal) {
        if (cliente == null) {
            throw new IllegalArgumentException("Selecione um cliente para o pedido.");
        }
        if (enderecoEntrega == null || enderecoEntrega.isBlank()) {
            throw new IllegalArgumentException("Informe o endereco de entrega do pedido.");
        }
        Pedido copia = new Pedido(pedido.getIdentificador(), pedido.getDataHora(), cliente.getNomeCliente(), enderecoEntrega, valorTotal);
        copia.setCliente(cliente);
        for (ProdutoPedido produtoPedido : pedido.getProdutos()) {
            copia.adicionarProdutoPedido(produtoPedido);
        }
        return copia;
    }

    private void validarPedidoEmElaboracao(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("Selecione um cliente antes de montar o pedido.");
        }
    }
}
