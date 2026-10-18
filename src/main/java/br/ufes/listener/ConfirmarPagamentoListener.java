package br.ufes.listener;

import br.projeto.painelpedidos.listener.IConfirmarPagamentoListener;
import br.projeto.painelpedidos.model.PedidoAbstract;
import br.projeto.painelpedidos.repositorio.IPedidoRepository;
import br.ufes.facade.PainelPedidoFacade;
import br.ufes.model.Pedido;
import br.ufes.presenter.PagamentoPresenter;
import br.ufes.repository.IClienteRepository;
import br.ufes.repository.IProdutoRepository;

//o painel pede o pagamento do pedido selecionado, quem conduz o fluxo eh a tela de pagamento da aplicacao
//o registro do pagamento e a mudanca para Em preparo acontecem quando o usuario finaliza o pagamento naquela tela

public class ConfirmarPagamentoListener implements IConfirmarPagamentoListener{
    private IPedidoRepository pedidoRepository;
    private IClienteRepository clienteRepository;
    private IProdutoRepository produtoRepository;
    private PainelPedidoFacade painelPedidoFacade;

    public ConfirmarPagamentoListener(IPedidoRepository pedidoRepository, IClienteRepository clienteRepository, IProdutoRepository produtoRepository, PainelPedidoFacade painelPedidoFacade) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.painelPedidoFacade = painelPedidoFacade;
    }

    @Override
    public void confirmarPagamento(PedidoAbstract pedido) {
        new PagamentoPresenter((Pedido) pedido, pedidoRepository, clienteRepository, produtoRepository, painelPedidoFacade);
    }
}
