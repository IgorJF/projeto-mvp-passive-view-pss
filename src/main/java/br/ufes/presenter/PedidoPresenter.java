package br.ufes.presenter;

import br.projeto.painelpedidos.repositorio.IPedidoRepository;
import br.ufes.cupom.APITaxaDescontoMock;
import br.ufes.cupom.CupomDescontoEntrega;
import br.ufes.cupom.CupomDescontoPedido;
import br.ufes.facade.PainelPedidoFacade;
import br.ufes.model.Cliente;
import br.ufes.model.Pedido;
import br.ufes.model.Produto;
import br.ufes.model.ProdutoPedido;
import br.ufes.model.Usuario;
import br.ufes.repository.IClienteRepository;
import br.ufes.repository.IProdutoRepository;
import br.ufes.service.PedidoService;
import br.ufes.view.PedidoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

//pedidoPresenter abre a tela mestre-detalhe do pedido, o cabecalho identifica o pedido e a tabela controla os itens
//o atendente escolhe o cliente entre os cadastrados, o usuario de perfil cliente so enxerga o cliente associado a sua sessao
//o valor do pedido acompanha os itens automaticamente, os demais valores do resumo dependem de Calcular Valor Pedido
//a inclusao de um item ou a troca do cupom desatualiza os valores e exige um novo calculo antes do pagamento
//o segundo construtor abre a tela em modo somente leitura, usado pelo painel de pedidos e pela confirmacao do pagamento

public class PedidoPresenter {
    private PedidoView view;
    private Pedido pedido;
    private Usuario usuarioLogado;
    private PedidoService pedidoService;
    private IPedidoRepository pedidoRepository;
    private IClienteRepository clienteRepository;
    private IProdutoRepository produtoRepository;
    private PainelPedidoFacade painelPedidoFacade;
    private List<Cliente> clientes;
    private List<Produto> produtos;
    private String cupomAplicado;
    private boolean valoresCalculados;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public PedidoPresenter(Usuario usuarioLogado, IPedidoRepository pedidoRepository, IClienteRepository clienteRepository, IProdutoRepository produtoRepository, PainelPedidoFacade painelPedidoFacade){
        this.usuarioLogado = usuarioLogado;
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.painelPedidoFacade = painelPedidoFacade;
        this.pedidoService = new PedidoService(pedidoRepository, clienteRepository, produtoRepository, painelPedidoFacade);
        this.clientes = new ArrayList<>();
        this.produtos = new ArrayList<>();
        this.view = new PedidoView();
        configuraView();
        modoElaboracao();
    }

    public PedidoPresenter(Pedido pedido, IPedidoRepository pedidoRepository, IClienteRepository clienteRepository, IProdutoRepository produtoRepository, PainelPedidoFacade painelPedidoFacade){
        this.pedido = pedido;
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.painelPedidoFacade = painelPedidoFacade;
        this.pedidoService = new PedidoService(pedidoRepository, clienteRepository, produtoRepository, painelPedidoFacade);
        this.clientes = new ArrayList<>();
        this.produtos = new ArrayList<>();
        this.view = new PedidoView();
        configuraView();
        modoVisualizacao();
    }

    private void configuraView(){
        view.setVisible(false);
        listarClientes();
        listarProdutos();

        view.getCmbCliente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    selecionarCliente();
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });

        view.getBtnAdicionar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    adicionarProduto();
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });

        view.getBtnCalcularValor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    calcularValor();
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });

        view.getBtnDetalhes().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    detalharDescontosTaxaEntrega();
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });

        view.getBtnAplicarCupom().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    aplicarCupom();
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });

        view.getBtnRemoverCupom().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    removerCupom();
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });

        view.getBtnPagarPedido().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    pagarPedido();
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });

        view.getBtnFechar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    fechar();
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });

        view.setDefaultCloseOperation(PedidoView.DISPOSE_ON_CLOSE);
        view.setVisible(true);
    }

    private void listarClientes(){
        clientes.clear();
        view.getCmbCliente().removeAllItems();
        if (usuarioLogado == null) {
            view.getCmbCliente().addItem(pedido.getNomeCliente());
            return;
        }
        if (usuarioLogado.getPerfil().podeGerenciarPedidoCliente()) {
            clientes.addAll(pedidoService.listarClientes());
        }
        else {
            clientes.add(pedidoService.obterClienteDoUsuario(usuarioLogado));
        }
        for (Cliente cliente : clientes) {
            view.getCmbCliente().addItem(cliente.getNomeCliente());
        }
    }

    private void listarProdutos(){
        produtos.clear();
        view.getCmbProdutos().removeAllItems();
        produtos.addAll(pedidoService.listarProdutos());
        for (Produto produto : produtos) {
            view.getCmbProdutos().addItem(produto.getNomeProduto() + " (R$ " + String.format("%.2f", produto.getPrecoVenda()) + ")");
        }
    }

    //ao definir o cliente o endereco cadastrado eh copiado para o pedido, o cadastro do cliente nunca eh alterado por esta tela
    private void selecionarCliente(){
        int linha = view.getCmbCliente().getSelectedIndex();
        if (linha == -1 || clientes.isEmpty()) {
            return;
        }
        pedido = pedidoService.alterarCliente(pedido, clientes.get(linha));
        carregarDadosPedido();
        listarItens();
        desatualizarValores();
    }

    private void adicionarProduto(){
        int linha = view.getCmbProdutos().getSelectedIndex();
        if (linha == -1) {
            throw new IllegalArgumentException("Selecione um produto para adicionar ao pedido.");
        }
        pedidoService.adicionarProduto(pedido, produtos.get(linha));
        listarItens();
        desatualizarValores();
    }

    private void calcularValor(){
        pedido = pedidoService.calcularValores(pedido, view.getTxtEndereco().getText(), cupomAplicado);
        valoresCalculados = true;
        apresentarValores();
    }

    private void detalharDescontosTaxaEntrega(){
        if (!valoresCalculados) {
            throw new IllegalArgumentException("Calcule o valor do pedido para consultar os descontos da taxa de entrega.");
        }
        List<CupomDescontoEntrega> cuponsEntrega = pedido.getCuponsDescontoEntrega();
        if (cuponsEntrega.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nenhum desconto foi aplicado a taxa de entrega deste pedido.");
            return;
        }
        StringBuilder descontos = new StringBuilder();
        for (CupomDescontoEntrega cupomEntrega : cuponsEntrega) {
            descontos.append(cupomEntrega.getNomeMetodo()).append(": - R$ ").append(String.format("%.2f", cupomEntrega.getValorDesconto())).append("\n");
        }
        JOptionPane.showMessageDialog(view, descontos.toString(), "Descontos da taxa de entrega", JOptionPane.INFORMATION_MESSAGE);
    }

    private void aplicarCupom(){
        CupomDescontoPedido cupom = pedidoService.validarCupom(pedido, view.getTxtCupomDesconto().getText());
        cupomAplicado = cupom.getCodigo();
        desatualizarValores();
        apresentarCupomAplicado(cupom);
        JOptionPane.showMessageDialog(view, "Cupom: " + cupom.getCodigo() + " aplicado ao pedido. Calcule o valor do pedido novamente.");
    }

    private void removerCupom(){
        if (cupomAplicado == null) {
            throw new IllegalArgumentException("Nao existe cupom aplicado ao pedido.");
        }
        cupomAplicado = null;
        view.getTxtCupomDesconto().setText("");
        view.getTxtCupomAplicado().setText("");
        desatualizarValores();
        JOptionPane.showMessageDialog(view, "Cupom removido do pedido. Calcule o valor do pedido novamente.");
    }

    //pagar pedido salva o pedido com a situacao Novo e abre a tela de pagamento, nao existe acao separada de salvar
    private void pagarPedido(){
        if (!valoresCalculados) {
            throw new IllegalArgumentException("Calcule o valor do pedido antes de prosseguir para o pagamento.");
        }
        pedido = pedidoService.salvarPedido(pedido, view.getTxtEndereco().getText(), cupomAplicado);
        new PagamentoPresenter(pedido, pedidoRepository, clienteRepository, produtoRepository, painelPedidoFacade);
        fechar();
    }

    private void fechar(){
        view.dispose();
    }

    private void listarItens(){
        DefaultTableModel modelo = (DefaultTableModel) view.getTblProdutos().getModel();
        modelo.setRowCount(0);
        int item = 1;
        for (ProdutoPedido produtoPedido : pedido.getProdutos()) {
            modelo.addRow(new Object[]{item, produtoPedido.getProduto().getNomeProduto(), produtoPedido.getProduto().getPrecoVenda(), produtoPedido.getQuantidade(), produtoPedido.getSubtotal()});
            item++;
        }
    }

    private void carregarDadosPedido(){
        view.getTxtNumeroPedido().setText("#" + pedido.getIdentificador());
        view.getTxtData().setText(pedido.getDataHora().format(formatter));
        view.getTxtEndereco().setText(pedido.getEnderecoEntrega());
    }

    //o valor do pedido eh a soma dos itens e acompanha a tabela, os demais valores so aparecem depois de Calcular Valor Pedido
    private void apresentarValores(){
        view.getTxtValorPedido().setText(String.format("%.2f", pedido.getValorTotalProdutos()));
        view.getTxtTaxaEntrega().setText(String.format("%.2f", APITaxaDescontoMock.getTaxaEntregaPadrao()));
        view.getTxtDescontoTaxaEntrega().setText(String.format("%.2f", pedido.getDescontoTaxaEntrega()));
        view.getTxtTaxaEntregaFinal().setText(String.format("%.2f", pedido.getTaxaEntrega()));
        view.getTxtTotalPedido().setText(String.format("%.2f", pedido.getValorFinalPedido()));
        apresentarCupomAplicado(pedido.getCupomDescontoPedido());
    }

    private void apresentarCupomAplicado(CupomDescontoPedido cupom){
        if (cupom == null) {
            view.getTxtCupomAplicado().setText("");
            return;
        }
        view.getTxtCupomAplicado().setText(cupom.getCodigo() + String.format(" (%.0f%% no pedido)", cupom.getPercentualDesconto() * 100));
    }

    private void desatualizarValores(){
        valoresCalculados = false;
        if (pedido == null) {
            return;
        }
        view.getTxtValorPedido().setText(String.format("%.2f", pedido.getValorTotalProdutos()));
        view.getTxtTaxaEntrega().setText("");
        view.getTxtDescontoTaxaEntrega().setText("");
        view.getTxtTaxaEntregaFinal().setText("");
        view.getTxtTotalPedido().setText("");
    }

    public void modoElaboracao(){
        habilitarCampos(true);
        view.getCmbCliente().setEnabled(usuarioLogado.getPerfil().podeGerenciarPedidoCliente());
        selecionarCliente();
    }

    public void modoVisualizacao(){
        carregarDadosPedido();
        listarItens();
        apresentarValores();
        habilitarCampos(false);
    }

    private void habilitarCampos(boolean habilitado){
        view.getCmbCliente().setEnabled(habilitado);
        view.getCmbProdutos().setEnabled(habilitado);
        view.getTxtEndereco().setEnabled(habilitado);
        view.getTxtCupomDesconto().setEnabled(habilitado);
        view.getTblProdutos().setEnabled(habilitado);
        view.getBtnAdicionar().setEnabled(habilitado);
        view.getBtnAplicarCupom().setEnabled(habilitado);
        view.getBtnRemoverCupom().setEnabled(habilitado);
        view.getBtnCalcularValor().setEnabled(habilitado);
        view.getBtnDetalhes().setEnabled(habilitado);
        view.getBtnPagarPedido().setEnabled(habilitado);
    }
}
