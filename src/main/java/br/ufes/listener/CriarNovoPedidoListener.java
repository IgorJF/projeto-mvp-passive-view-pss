package br.ufes.listener;

import br.projeto.painelpedidos.listener.ICriarNovoPedidoListener;
import br.projeto.painelpedidos.model.PedidoAbstract;
import java.util.ArrayList;
import java.util.List;

public class CriarNovoPedidoListener implements ICriarNovoPedidoListener {
    private List<PedidoAbstract> listaDeNovosPedidos;

    public CriarNovoPedidoListener() {
        listaDeNovosPedidos = new ArrayList<>();
    }
    
    @Override
    public List<PedidoAbstract> criarNovoPedido() {
        // você decide quais pedidos e quantos; devolva a lista
        return listaDeNovosPedidos;
    }
}
