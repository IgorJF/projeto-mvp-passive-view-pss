package br.ufes.seeder;

import br.ufes.model.Categoria;
import br.ufes.model.Cliente;
import br.ufes.model.HistoricoPreco;
import br.ufes.model.Produto;
import br.ufes.model.Usuario;
import br.ufes.repository.CategoriaRepository;
import br.ufes.repository.ClienteRepository;
import br.ufes.repository.HistoricoPrecoRepository;
import br.ufes.repository.ProdutoRepository;
import br.ufes.repository.UsuarioRepository;
import br.ufes.service.CalculoPrecoService;
import br.ufes.strategy.IPerfilUsuarioStrategy;
import br.ufes.strategy.Perfis;
import java.time.LocalDate;

public class Seeder {
    private Cliente ana;
    private Cliente bruno;

    public void executar() {
        criarCategorias();
        criarProdutos();
        criarClientes();
        criarUsuarios();
    }

    // ---------- categorias e produtos (dados da especificacao inicial) ----------

    private void criarCategorias() {
        CategoriaRepository categorias = CategoriaRepository.getInstance();
        categorias.salvar(new Categoria("Educação", 25.0));
        categorias.salvar(new Categoria("Papelaria", 30.0));
        categorias.salvar(new Categoria("Alimentação", 22.0));
        categorias.salvar(new Categoria("Lazer", 35.0));
        categorias.salvar(new Categoria("Entretenimento", 40.0));
        categorias.salvar(new Categoria("Higiene", 28.0));
        categorias.salvar(new Categoria("Limpeza", 25.0));
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
        for (Categoria categoria : CategoriaRepository.getInstance().listar()) {
            if (categoria.getNomeCategoria().equalsIgnoreCase(nome)) {
                return categoria;
            }
        }
        return null;
    }

    // salva o produto e cria o primeiro registro de historico (hoje - 10 dias), calculado com a mesma regra da tela de calculo
    private void produto(String nome, double precoCusto, Categoria categoria) {
        Produto produto = new Produto(nome, precoCusto, categoria);
        ProdutoRepository.getInstance().salvar(produto);

        double percentual = categoria.getPercentualLucro();
        double precoVenda = CalculoPrecoService.calcularPrecoVenda(precoCusto, percentual);
        produto.setMargemLucro(percentual);
        produto.setPrecoVenda(precoVenda);
        HistoricoPrecoRepository.getInstance().salvar(
                new HistoricoPreco(produto.getIdProduto(), LocalDate.now().minusDays(10), percentual, precoVenda));
    }

    // ---------- clientes ----------

    private void criarClientes() {
        ana = cliente("Ana Souza", "Rua das Flores, 123", "Centro", "Alegre", "ES", 245.80);
        bruno = cliente("Bruno Lima", "Rua do Comércio, 45", "Vila do Sul", "Alegre", "ES", 120.00);
        cliente("Carlos Mendes", "Avenida Brasil, 210", "Centro", "Guaçuí", "ES", 450.30);
        cliente("Daniela Rocha", "Rua das Acácias, 88", "Nova Alegre", "Alegre", "ES", 89.90);
        cliente("Eduardo Silva", "Rua São José, 15", "São Miguel", "Guaçuí", "ES", 310.20);
        cliente("Fernanda Costa", "Praça da Matriz, 5", "Centro", "Alegre", "ES", 560.75);
        cliente("Gabriel Oliveira", "Rua do Café, 302", "Vila do Café", "Alegre", "ES", 75.40);
        cliente("Juliana Santos", "Rua Direita, 77", "Centro", "Guaçuí", "ES", 220.00);
        cliente("Marcos Ribeiro", "Estrada da Serra, 9", "Cachoeirinha", "Alegre", "ES", 180.50);
        cliente("Patrícia Almeida", "Rua Bela Vista, 64", "Bela Vista", "Guaçuí", "ES", 99.00);
    }

    // todo cliente nasce PRATA (o proprio construtor de Cliente cuida disso)
    private Cliente cliente(String nome, String logradouro, String bairro, String cidade, String uf, double totalCompras) {
        Cliente cliente = new Cliente(nome, logradouro, bairro, cidade, uf);
        cliente.registrarCompra(totalCompras);
        ClienteRepository.getInstance().salvar(cliente);
        return cliente;
    }

    // ---------- usuarios ----------

    private void criarUsuarios() {
        // o primeiro usuario do sistema e o Administrador, habilitado e sem cliente associado
        usuario("Administrador do Sistema", "admin", "admin@pocdelivery.com", "admin123", Perfis.ADMINISTRADOR);

        usuario("Fernanda Alves", "fernanda", "fernanda@pocdelivery.com", "123456", Perfis.ATENDENTE);
        usuario("Ricardo Nascimento", "ricardo", "ricardo@pocdelivery.com", "123456", Perfis.ATENDENTE);

        // atendente desabilitado, para testar o login de usuario desabilitado
        Usuario diego = usuario("Diego Rocha", "diego", "diego@pocdelivery.com", "123456", Perfis.ATENDENTE);
        diego.desabilitar();

        Usuario usuarioAna = usuario("Ana Souza", "anasouza", "ana@cliente.com", "123456", Perfis.CLIENTE);
        usuarioAna.setClienteAssociado(ana);
        Usuario usuarioBruno = usuario("Bruno Lima", "brunolima", "bruno@cliente.com", "123456", Perfis.CLIENTE);
        usuarioBruno.setClienteAssociado(bruno);
    }

    private Usuario usuario(String nome, String nomeUsuario, String email, String senha, IPerfilUsuarioStrategy perfil) {
        Usuario usuario = new Usuario(nome, nomeUsuario, email, senha, perfil);
        UsuarioRepository.getInstance().salvar(usuario);
        return usuario;
    }
}
