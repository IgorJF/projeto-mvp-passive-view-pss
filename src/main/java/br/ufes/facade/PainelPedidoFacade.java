package br.ufes.facade;

import br.projeto.painelpedidos.configuracao.ConfiguracaoPainelPedido;
import br.projeto.painelpedidos.configuracao.LeitorConfiguracaoPainelPedido;
import br.projeto.painelpedidos.model.PedidoAbstract;
import br.projeto.painelpedidos.repositorio.IPedidoRepository;
import br.projeto.painelpedidos.som.SomTomStrategy;
import br.projeto.painelpedidos.tempo.RelogioServiceSistema;
import br.ufes.listener.ConfirmarPagamentoListener;
import br.ufes.listener.CriarNovoPedidoListener;
import br.ufes.listener.VisualizarPedidoListener;
import br.ufes.model.Usuario;
import br.ufes.repository.IClienteRepository;
import br.ufes.repository.IProdutoRepository;
import java.util.ArrayList;
import java.util.List;

public class PainelPedidoFacade {
    private final br.projeto.painelpedidos.aplicacao.PainelPedidoFacade painelPedido;

    public PainelPedidoFacade(Usuario usuarioLogado, IPedidoRepository pedidoRepository, IClienteRepository clienteRepository, IProdutoRepository produtoRepository) {
        ConfiguracaoPainelPedido configuracao = new LeitorConfiguracaoPainelPedido(".", "painel_pedidos.env").ler();
        this.painelPedido = new br.projeto.painelpedidos.aplicacao.PainelPedidoFacade(
                pedidoRepository, 
                configuracao, 
                new SomTomStrategy(880, 200), 
                new RelogioServiceSistema()
        );
        this.setVisualizarPedidoListener(new VisualizarPedidoListener(pedidoRepository, clienteRepository, produtoRepository, this));
        this.setConfirmarPagamentoListener(new ConfirmarPagamentoListener(pedidoRepository, clienteRepository, produtoRepository, this));
        this.setCriarNovoPedidoListener(new CriarNovoPedidoListener(usuarioLogado, pedidoRepository, clienteRepository, produtoRepository, this));
    }

    public void setVisualizarPedidoListener(VisualizarPedidoListener listener) {
        this.painelPedido.setVisualizarPedidoListener(listener);
    }

    public void setConfirmarPagamentoListener(ConfirmarPagamentoListener listener) {
        this.painelPedido.setConfirmarPagamentoListener(listener);
    }

    public void setCriarNovoPedidoListener(CriarNovoPedidoListener listener) {
        this.painelPedido.setCriarNovoPedidoListener(listener);
    }

    public boolean iniciar() {
        return this.painelPedido.iniciar();
    }

    public void receberNovosPedidos(List<PedidoAbstract> novosPedidos) {
        this.painelPedido.receberNovosPedidos(novosPedidos);
    }
    
    public void receberNovoPedido(PedidoAbstract pedido) {
        List<PedidoAbstract> novosPedidos = new ArrayList<>();
        novosPedidos.add(pedido);
        painelPedido.receberNovosPedidos(novosPedidos);
    }

    public void encerrar() {
        this.painelPedido.encerrar();
    }
}