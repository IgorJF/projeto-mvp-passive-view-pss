package br.ufes.cupom;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author igorj
 */
public class CupomDescontoPedidoRepository {
    private List<CupomDescontoPedido> listaCuponsPedido;
           
    private void criarCupons() {
        listaCuponsPedido.add(new CupomDescontoPedido("DESC10", 0.10, LocalDateTime.of(2026, 9, 25, 0, 0), LocalDateTime.of(2026, 9, 27, 23, 59)));
        listaCuponsPedido.add(new CupomDescontoPedido("DESC20", 0.20, LocalDateTime.of(2026, 10, 1, 0, 0), LocalDateTime.of(2026, 10, 5, 23, 59)));
        listaCuponsPedido.add(new CupomDescontoPedido("DESC30", 0.30, LocalDateTime.of(2026, 9, 24, 0, 0), LocalDateTime.of(2026, 9, 24, 23, 59)));
        listaCuponsPedido.add(new CupomDescontoPedido("DIAPAI12", 0.12, LocalDateTime.of(2026, 10, 9, 0, 0), LocalDateTime.of(2026, 10, 10, 23, 59)));
        listaCuponsPedido.add(new CupomDescontoPedido("DIAMAE12", 0.20, LocalDateTime.of(2026, 10, 10, 0, 0), LocalDateTime.of(2026, 10, 12, 23, 59)));
        listaCuponsPedido.add(new CupomDescontoPedido("NATAL10", 0.10, LocalDateTime.of(2026, 9, 20, 0, 0), LocalDateTime.of(2026, 9, 26, 23, 59)));
        listaCuponsPedido.add(new CupomDescontoPedido("FESTA15", 0.15, LocalDateTime.of(2026, 9, 30, 18, 0), LocalDateTime.of(2026, 10, 1, 6, 0)));
        listaCuponsPedido.add(new CupomDescontoPedido("BLACK50", 0.50, LocalDateTime.of(2026, 9, 28, 0, 0), LocalDateTime.of(2026, 9, 28, 23, 59)));
    }
    
    public CupomDescontoPedidoRepository(){
         listaCuponsPedido = new ArrayList<>();
         criarCupons();
    }
    
    public CupomDescontoPedido buscarCupom(String codigo) {
        for (CupomDescontoPedido cupom : listaCuponsPedido) {
            if (cupom.getCodigo().equals(codigo)) {
                return cupom;
            }
        }
        return null;
    }

    public void adicionarCupom(CupomDescontoPedido novoCupom) {
        listaCuponsPedido.add(novoCupom);
    }
    
    public void removerCupom(CupomDescontoPedido cupom){
        listaCuponsPedido.remove(cupom);
    }
}
