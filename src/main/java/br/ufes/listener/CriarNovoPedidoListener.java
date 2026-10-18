package br.ufes.listener;

import br.projeto.painelpedidos.listener.ICriarNovoPedidoListener;
import br.projeto.painelpedidos.model.PedidoAbstract;
import br.projeto.painelpedidos.repositorio.IPedidoRepository;
import br.ufes.facade.PainelPedidoFacade;
import br.ufes.model.Usuario;
import br.ufes.presenter.PedidoPresenter;
import br.ufes.repository.IClienteRepository;
import br.ufes.repository.IProdutoRepository;
import java.util.ArrayList;
import java.util.List;

//o componente espera a lista de novos pedidos no mesmo instante, mas a elaboracao do pedido eh interativa
//por isso a tela de pedido eh aberta e a lista volta vazia, o que o componente trata sem alterar o painel
//o pedido chega ao painel depois, quando Pagar pedido o salva e a fachada o entrega por receberNovoPedido

public class CriarNovoPedidoListener implements ICriarNovoPedidoListener {
    private Usuario usuarioLogado;
    private IPedidoRepository pedidoRepository;
    private IClienteRepository clienteRepository;
    private IProdutoRepository produtoRepository;
    private PainelPedidoFacade painelPedidoFacade;

    public CriarNovoPedidoListener(Usuario usuarioLogado, IPedidoRepository pedidoRepository, IClienteRepository clienteRepository, IProdutoRepository produtoRepository, PainelPedidoFacade painelPedidoFacade) {
        this.usuarioLogado = usuarioLogado;
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.painelPedidoFacade = painelPedidoFacade;
    }

    @Override
    public List<PedidoAbstract> criarNovoPedido() {
        new PedidoPresenter(usuarioLogado, pedidoRepository, clienteRepository, produtoRepository, painelPedidoFacade);
        return new ArrayList<>();
    }
}
