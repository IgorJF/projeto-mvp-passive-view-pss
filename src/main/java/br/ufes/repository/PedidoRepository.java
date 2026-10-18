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

    @Override
    public void salvar(PedidoAbstract pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido nao pode ser nulo");
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