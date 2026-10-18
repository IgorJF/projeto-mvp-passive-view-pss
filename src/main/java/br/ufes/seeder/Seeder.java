package br.ufes.seeder;

import br.projeto.painelpedidos.repositorio.IPedidoRepository;
import br.ufes.cupom.APITaxaDescontoMock;
import br.ufes.model.Categoria;
import br.ufes.model.Cliente;
import br.ufes.model.HistoricoPreco;
import br.ufes.model.Pedido;
import br.ufes.model.Produto;
import br.ufes.model.ProdutoPedido;
import br.ufes.model.Usuario;
import br.ufes.repository.ICategoriaRepository;
import br.ufes.repository.IClienteRepository;
import br.ufes.repository.IHistoricoPrecoRepository;
import br.ufes.repository.IProdutoRepository;
import br.ufes.repository.IUsuarioRepository;
import br.ufes.strategy.IPerfilUsuarioStrategy;
import br.ufes.strategy.UsuarioAdministradorStrategy;
import br.ufes.strategy.UsuarioAtendenteStrategy;
import br.ufes.strategy.UsuarioClienteStrategy;
import br.ufes.service.CalculoPrecoService;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Seeder {
    private final ICategoriaRepository categoriaRepository;
    private final IProdutoRepository produtoRepository;
    private final IHistoricoPrecoRepository historicoRepository;
    private final IClienteRepository clienteRepository;
    private final IUsuarioRepository usuarioRepository;
    private final IPedidoRepository pedidoRepository;
    
    public Seeder(ICategoriaRepository categoriaRepository, IProdutoRepository produtoRepository, IHistoricoPrecoRepository historicoRepository, IClienteRepository clienteRepository, IUsuarioRepository usuarioRepository, IPedidoRepository pedidoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
        this.historicoRepository = historicoRepository;
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
        this.pedidoRepository = pedidoRepository;
        executar();
    }

    public void executar() {
        criarCategorias();
        criarProdutos();
        criarClientes();
        criarUsuarios();
        criarPedidos();
    }

    private void criarCategorias() {
        categoriaRepository.salvar(new Categoria("Educação", 25.0));
        categoriaRepository.salvar(new Categoria("Papelaria", 30.0));
        categoriaRepository.salvar(new Categoria("Alimentação", 22.0));
        categoriaRepository.salvar(new Categoria("Lazer", 35.0));
        categoriaRepository.salvar(new Categoria("Entretenimento", 40.0));
        categoriaRepository.salvar(new Categoria("Higiene", 28.0));
        categoriaRepository.salvar(new Categoria("Limpeza", 25.0));
    }

    private void criarProdutos() {
        Categoria educacao = buscarCategoria("Educação");
        Categoria papelaria = buscarCategoria("Papelaria");
        Categoria alimentacao = buscarCategoria("Alimentação");
        Categoria lazer = buscarCategoria("Lazer");
        Categoria entretenimento = buscarCategoria("Entretenimento");
        Categoria higiene = buscarCategoria("Higiene");
        Categoria limpeza = buscarCategoria("Limpeza");
        
        produto("Livro didático", 45.00, educacao);
        produto("Livro paradidático", 30.00, educacao);
        produto("Mochila escolar", 70.00, educacao);
        produto("Caderno universitário", 16.00, papelaria);
        produto("Lápis grafite HB", 1.20, papelaria);
        produto("Caneta esferográfica azul", 2.20, papelaria);
        produto("Borracha branca", 1.00, papelaria);
        produto("Apontador com depósito", 3.50, papelaria);
        produto("Jogo de tabuleiro", 55.00, lazer);
        produto("Bola recreativa", 40.00, lazer);
        produto("Quebra-cabeça 500 peças", 35.00, lazer);
        produto("Fone de ouvido", 48.00, entretenimento);
        produto("Caixa de som portátil", 80.00, entretenimento);
        produto("Revista de passatempos", 12.00, entretenimento);
        produto("Biscoito integral", 5.50, alimentacao);
        produto("Suco de uva 1 L", 9.00, alimentacao);
        produto("Barra de cereal", 3.20, alimentacao);
        produto("Sabonete", 2.80, higiene);
        produto("Creme dental", 5.50, higiene);
        produto("Detergente líquido", 2.60, limpeza);
        produto("Esponja multiuso", 1.70, limpeza);
    }

    private Categoria buscarCategoria(String nome) {
        for (Categoria categoria : categoriaRepository.listar()) {
            if (categoria.getNomeCategoria().equalsIgnoreCase(nome)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Categoria não encontrada: " + nome);
    }

    private void produto(String nome, double precoCusto, Categoria categoria) {
        Produto produto = new Produto(nome, precoCusto, categoria);
        produtoRepository.salvar(produto);
        double percentual = categoria.getPercentualLucro();
        double precoVenda = CalculoPrecoService.calcularPrecoVenda(precoCusto, percentual);
        produto.setMargemLucro(percentual);
        produto.setPrecoVenda(precoVenda);
        historicoRepository.salvar(new HistoricoPreco(produto.getIdProduto(), LocalDate.now().minusDays(10), percentual, precoVenda));
    }

    private void criarClientes() {
        cliente("Ana Souza", "Rua das Flores, 123", "Centro", "Alegre", "ES", 245.80);
        cliente("Bruno Lima", "Rua do Comércio, 45", "Vila do Sul", "Alegre", "ES", 120.00);
        cliente("Carlos Mendes", "Avenida Brasil, 210", "Centro", "Guaçuí", "ES", 450.30);
        cliente("Daniela Rocha", "Rua das Acácias, 88", "Nova Alegre", "Alegre", "ES", 89.90);
        cliente("Eduardo Silva", "Rua São José, 15", "São Miguel", "Guaçuí", "ES", 310.20);
        cliente("Fernanda Costa", "Praça da Matriz, 5", "Centro", "Alegre", "ES", 560.75);
        cliente("Gabriel Oliveira", "Rua do Café, 302", "Vila do Café", "Alegre", "ES", 75.40);
        cliente("Juliana Santos", "Rua Direita, 77", "Centro", "Guaçuí", "ES", 220.00);
        cliente("Marcos Ribeiro", "Estrada da Serra, 9", "Cachoeirinha", "Alegre", "ES", 180.50);
        cliente("Patrícia Almeida", "Rua Bela Vista, 64", "Bela Vista", "Guaçuí", "ES", 99.00);
    }

    private Cliente cliente(String nome, String logradouro, String bairro, String cidade, String uf, double totalCompras) {
        Cliente cliente = new Cliente(nome, logradouro, bairro, cidade, uf);
        cliente.setTotalCompradoHistorico(totalCompras);
        clienteRepository.salvar(cliente);
        return cliente;
    }

    private void criarUsuarios() {
        usuario("Administrador do Sistema", "admin", "admin@pocdelivery.com", "admin123", new UsuarioAdministradorStrategy(), true, null);
        usuario("Fernanda Alves", "fernanda", "fernanda@pocdelivery.com", "123456", new UsuarioAtendenteStrategy(), true, null);
        usuario("Ricardo Nascimento", "ricardo", "ricardo@pocdelivery.com", "123456", new UsuarioAtendenteStrategy(), true, null);
        usuario("Diego Rocha", "diego", "diego@pocdelivery.com", "123456", new UsuarioAtendenteStrategy(), false, null);
        
        Cliente clienteAna = buscarCliente("Ana Souza");
        usuario("Ana Souza", "ana", "ana@pocdelivery.com", "123456", new UsuarioClienteStrategy(), true, clienteAna);
        Cliente clienteBruno = buscarCliente("Bruno Lima");
        usuario("Bruno Lima", "bruno", "bruno@pocdelivery.com", "123456", new UsuarioClienteStrategy(), false, clienteBruno);
    }

    private Usuario usuario(String nome, String nomeUsuario, String email, String senha, IPerfilUsuarioStrategy perfil, boolean status, Cliente clienteAssociado) {
        Usuario usuario = new Usuario(nome, nomeUsuario, email, senha, perfil, status);
        usuario.setClienteAssociado(clienteAssociado);
        usuarioRepository.salvar(usuario);
        return usuario;
    }

    private void criarPedidos() {
        Pedido pedidoNovo = pedido("Ana Souza", LocalDateTime.now().minusMinutes(3), "Livro didático", 1, "Suco de uva 1 L", 2);

        Pedido pedidoEmPreparo = pedido("Bruno Lima", LocalDateTime.now().minusMinutes(18), "Caderno universitário", 2, "Caneta esferográfica azul", 3);
        pedidoEmPreparo.registrarPagamento("Pix - chave CPF", LocalDateTime.now().minusMinutes(16));

        Pedido pedidoEmRota = pedido("Carlos Mendes", LocalDateTime.now().minusMinutes(45), "Jogo de tabuleiro", 1, "Barra de cereal", 4);
        pedidoEmRota.registrarPagamento("Cartao de credito a vista", LocalDateTime.now().minusMinutes(43));
        pedidoEmRota.marcarProntoParaEntrega(LocalDateTime.now().minusMinutes(25));
        pedidoEmRota.sairParaEntrega(LocalDateTime.now().minusMinutes(10));

        Pedido pedidoCancelado = pedido("Fernanda Costa", LocalDateTime.now().minusMinutes(60), "Fone de ouvido", 1, "Sabonete", 2);
        pedidoCancelado.registrarPagamento("Pix - chave e-mail", LocalDateTime.now().minusMinutes(58));
        pedidoCancelado.cancelar(LocalDateTime.now().minusMinutes(30));
    }

    private Pedido pedido(String nomeCliente, LocalDateTime dataHora, String nomeProduto, int quantidade, String nomeOutroProduto, int outraQuantidade) {
        Cliente cliente = buscarCliente(nomeCliente);
        ProdutoPedido item = new ProdutoPedido(buscarProduto(nomeProduto), quantidade);
        ProdutoPedido outroItem = new ProdutoPedido(buscarProduto(nomeOutroProduto), outraQuantidade);
        double valorTotal = item.getSubtotal() + outroItem.getSubtotal() + APITaxaDescontoMock.getTaxaEntregaPadrao();
        Pedido pedido = new Pedido(pedidoRepository.gerarProximoIdentificador(), dataHora, cliente.getNomeCliente(), cliente.getEnderecoCompleto(), valorTotal);
        pedido.setCliente(cliente);
        pedido.adicionarProdutoPedido(item);
        pedido.adicionarProdutoPedido(outroItem);
        pedidoRepository.salvar(pedido);
        return pedido;
    }

    private Produto buscarProduto(String nome) {
        for (Produto produto : produtoRepository.listar()) {
            if (produto.getNomeProduto().equalsIgnoreCase(nome)) {
                return produto;
            }
        }
        throw new IllegalArgumentException("Produto não encontrado: " + nome);
    }

    private Cliente buscarCliente(String nome) {
        for (Cliente cliente : clienteRepository.listar()) {
            if (cliente.getNomeCliente().equalsIgnoreCase(nome)) {
                return cliente;
            }
        }
        return null; 
    }
}