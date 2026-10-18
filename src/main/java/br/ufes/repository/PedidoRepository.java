package br.ufes.repository;

import br.projeto.painelpedidos.model.PedidoAbstract;
import br.projeto.painelpedidos.repositorio.IPedidoRepository;
import java.util.ArrayList;
import java.util.List;

public class PedidoRepository implements IPedidoRepository {
    private List<PedidoAbstract> pedidos;
    private int contadorId;

    public PedidoRepository() {
        pedidos = new ArrayList<>();
        contadorId = 0;
    }

    //o painel chama salvar tanto para incluir um pedido novo quanto para gravar uma mudanca de situacao
    //por isso um pedido que ja esta na lista eh atualizado no lugar e nao duplicado
    @Override
    public void salvar(PedidoAbstract pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido nao pode ser nulo");
        }
        for (int posicao = 0; posicao < pedidos.size(); posicao++) {
            if (pedidos.get(posicao).getIdentificador().equals(pedido.getIdentificador())) {
                pedidos.set(posicao, pedido);
                return;
            }
        }
        pedidos.add(pedido);
    }

    @Override
    public List<PedidoAbstract> buscarTodos() {
        return pedidos;
    }

    @Override
    public String gerarProximoIdentificador() {
        contadorId++;
        return String.valueOf(contadorId);
    }
}