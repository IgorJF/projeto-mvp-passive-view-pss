/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.ufes.repository;

/**
 *
 * @author Daniel
 */
import br.ufes.model.HistoricoPreco;
import br.ufes.model.Produto;
import java.util.List;

public interface IHistoricoPrecoRepository {

    void salvar(HistoricoPreco historico);

    List<HistoricoPreco> listarPorProduto(Produto produto);
}
