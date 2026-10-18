package br.ufes.presenter;

import br.projeto.painelpedidos.repositorio.IPedidoRepository;
import br.ufes.facade.PainelPedidoFacade;
import br.ufes.model.Pagamento;
import br.ufes.model.Pedido;
import br.ufes.model.ProdutoPedido;
import br.ufes.repository.IClienteRepository;
import br.ufes.repository.IProdutoRepository;
import br.ufes.service.PagamentoService;
import br.ufes.strategy.IFormaPagamentoStrategy;
import br.ufes.strategy.PagamentoCartaoCreditoStrategy;
import br.ufes.strategy.PagamentoPixCpfStrategy;
import br.ufes.strategy.PagamentoPixEmailStrategy;
import br.ufes.view.PagamentoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class PagamentoPresenter {
    private PagamentoView view;
    private Pedido pedido;
    private PagamentoService pagamentoService;
    private IFormaPagamentoStrategy formaPagamento;
    private IPedidoRepository pedidoRepository;
    private IClienteRepository clienteRepository;
    private IProdutoRepository produtoRepository;
    private PainelPedidoFacade painelPedidoFacade;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public PagamentoPresenter(Pedido pedido, IPedidoRepository pedidoRepository, IClienteRepository clienteRepository, IProdutoRepository produtoRepository, PainelPedidoFacade painelPedidoFacade){
        this.pedido = pedido;
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.painelPedidoFacade = painelPedidoFacade;
        this.pagamentoService = new PagamentoService();
        this.view = new PagamentoView();
        configuraView();
    }

    private void configuraView(){
        view.setVisible(false);
        carregarDadosPedido();
        listarProdutos();
        apresentarValores();
        modoSelecaoFormaPagamento();
        view.getBtnCopiar1().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    copiarCodigo();
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });

        view.getBtnCopiar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    copiarCodigo();
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });

        view.getBtnFinalizarPagamento().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    finalizarPagamento();
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });

        view.getBtnVisualizarPedido().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    visualizarPedido();
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

        view.setDefaultCloseOperation(PagamentoView.DISPOSE_ON_CLOSE);
        view.setVisible(true);
    }
    
    private void selecaoFormaPagamento(){
        if (view.getRbCartaoCredito().isSelected()) {
            formaPagamento = new PagamentoCartaoCreditoStrategy(view.getTxtNumeroCartao().getText(), view.getTxtNumeroCartao1().getText(), view.getTxtValidade().getText(), view.getTxtCVV().getText());
        } else if (view.getRbPixCPF().isSelected()) {
            formaPagamento = new PagamentoPixCpfStrategy();
            
        } else if (view.getRbPixEmail().isSelected()) {
            formaPagamento = new PagamentoPixEmailStrategy();
            
        } else {
            JOptionPane.showMessageDialog(view, "Por favor, selecione uma forma de pagamento.");
            return;
        }
    }

    private void finalizarPagamento(){
        selecaoFormaPagamento();
        pagamentoService.finalizarPagamento(pedido, formaPagamento);
        JOptionPane.showMessageDialog(view, "Pagamento do pedido #" + pedido.getIdentificador() + " registrado com sucesso");
        modoPagamentoRegistrado();
    }

    private void visualizarPedido(){
        new PedidoPresenter(pedido, pedidoRepository, clienteRepository, produtoRepository, painelPedidoFacade);
    }

    private void copiarCodigo(){
        JOptionPane.showMessageDialog(view, "Codigo Pix copiado.");
    }

    private void fechar(){
        view.dispose();
    }

    private void carregarDadosPedido(){
        view.getTxtNumeroPedido().setText("#" + pedido.getIdentificador());
        view.getCmbCliente().removeAllItems();
        view.getCmbCliente().addItem(pedido.getNomeCliente());
        view.getCmbCliente().setEnabled(false);
        view.getTxtDataPedido().setText(pedido.getDataHora().format(formatter));
    }

    private void listarProdutos(){
        DefaultTableModel modelo = (DefaultTableModel) view.getTblProdutos().getModel();
        modelo.setRowCount(0);
        int cont = 1;
        for (ProdutoPedido produtoPedido : pedido.getProdutos()) {
            modelo.addRow(new Object[]{cont, produtoPedido.getProduto().getNomeProduto(), produtoPedido.getQuantidade(), produtoPedido.getProduto().getPrecoVenda(), produtoPedido.getSubtotal()});
            cont++;
        }
        view.getTblProdutos().setEnabled(false);
    }

    private void apresentarValores(){
        view.getTxtValorItens().setText(String.format("%.2f", pedido.getValorTotalProdutos()));
        view.getTxtTaxaEntrega().setText(String.format("%.2f", pedido.getTaxaEntrega()));
        view.getTxtDescontoPedido().setText(String.format("%.2f", pedido.getDescontoPedido()));
        view.getTxtTotalPedido().setText(String.format("%.2f", pedido.getValorFinalPedido()));
    }

    private void apresentarFormaPagamento(){
        view.getTxtChavePixCPF().setText("123.456.789-00");
        view.getTxtValorPagar1().setText(String.format("%.2f", pedido.getValorFinalPedido()));
        view.getTxtCodigoCopiaCola1().setText("CHAVE PIX");
        view.getTxtChavePixEmail().setText("admin@pocdelivery.com");
        view.getTxtValorPagar().setText(String.format("%.2f", pedido.getValorFinalPedido()));
        view.getTxtCodigoCopiaCola().setText("CHAVE PIX");
        view.getBtnFinalizarPagamento().setEnabled(true);
    }

    private void apresentarConfirmacao(Pagamento pagamento){
        if (pagamento == null) {
            return;
        }
        view.getLblSituacaoPagamento().setText("Pagamento aceito com sucesso");
        view.getTxtNumeroPedidoFinal().setText("#" + pedido.getIdentificador());
        view.getTxtStatus().setText(pedido.getSituacao());
        view.getTxtFormaPagamento().setText(pagamento.getFormaPagamento());
        view.getTxtCodigoTransacao().setText(pagamentoService.gerarCodigoTransacao());
        view.getTxtDataHoraPedido().setText(pagamento.getDataHoraPagamento().format(formatter));
    }

    public void modoSelecaoFormaPagamento(){
        formaPagamento = null;
        view.getLblSituacaoPagamento().setText("Pagamento pendente");
        view.getTxtNumeroPedidoFinal().setText("");
        view.getTxtStatus().setText(pedido.getSituacao());
        view.getTxtFormaPagamento().setText("");
        view.getTxtCodigoTransacao().setText("");
        view.getTxtDataHoraPedido().setText("");
        apresentarFormaPagamento();
        view.getBtnFinalizarPagamento().setEnabled(false);
    }

    public void modoPagamentoRegistrado(){
        apresentarConfirmacao(pedido.getPagamento());
        view.getBtnFinalizarPagamento().setEnabled(false);
    }
}
