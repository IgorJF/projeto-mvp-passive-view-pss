package br.ufes.cupom;

import br.ufes.model.Pedido;
import br.ufes.model.ProdutoPedido;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FormaDescontoTipoProduto implements IFormaDescontoTaxaEntrega {
    private Map<String, Double> descontosPorTipoProduto;

    public FormaDescontoTipoProduto() {
        descontosPorTipoProduto = new HashMap<>();
        descontosPorTipoProduto.put("Alimentacao", 5.00);
        descontosPorTipoProduto.put("Educacao", 2.00);
        descontosPorTipoProduto.put("Lazer", 1.50);
    }

    @Override
    public Optional<CupomDescontoEntrega> calcularDesconto(Pedido pedido) {
        Optional<CupomDescontoEntrega> optCupomDescontoEntrega = Optional.empty();
        double descontoTotal = 0;
        
        for (ProdutoPedido produtoPedido : pedido.getProdutos()) {
            if (produtoPedido.getProduto().getCategoria() == null) {
                continue;
            }
            String tipo = produtoPedido.getProduto().getCategoria().getNomeCategoria();
            if (descontosPorTipoProduto.containsKey(tipo)) {
                descontoTotal += descontosPorTipoProduto.get(tipo);
            }
        }
        if (descontoTotal > 0) {
            optCupomDescontoEntrega = Optional.of(new CupomDescontoEntrega("Desconto Item", descontoTotal));
        }
        return optCupomDescontoEntrega;
    }
}
