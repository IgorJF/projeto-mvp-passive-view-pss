package br.ufes.listener;

import br.projeto.painelpedidos.listener.IVisualizarPedidoListener;
import br.projeto.painelpedidos.model.PedidoAbstract;
import br.projeto.painelpedidos.repositorio.IPedidoRepository;
import br.ufes.facade.PainelPedidoFacade;
import br.ufes.model.Pedido;
import br.ufes.presenter.PedidoPresenter;
import br.ufes.repository.IClienteRepository;
import br.ufes.repository.IProdutoRepository;

public class VisualizarPedidoListener implements IVisualizarPedidoListener{
    private IPedidoRepository pedidoRepository;
    private IClienteRepository clienteRepository;
    private IProdutoRepository produtoRepository;
    private PainelPedidoFacade painelPedidoFacade;

    public VisualizarPedidoListener(IPedidoRepository pedidoRepository, IClienteRepository clienteRepository, IProdutoRepository produtoRepository, PainelPedidoFacade painelPedidoFacade) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.painelPedidoFacade = painelPedidoFacade;
    }

    @Override
    public void visualizarPedido(PedidoAbstract pedido) {
        new PedidoPresenter((Pedido) pedido, pedidoRepository, clienteRepository, produtoRepository, painelPedidoFacade);
    }
}
