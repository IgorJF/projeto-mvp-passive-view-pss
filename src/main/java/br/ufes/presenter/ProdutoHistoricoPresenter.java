/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufes.presenter;

import br.ufes.model.Produto;
import br.ufes.view.ProdutoHistoricoView;

/**
 *
 * @author ana-luiza
 */
public class ProdutoHistoricoPresenter {
    private ProdutoHistoricoView view;
    private Produto produto;
    
    public ProdutoHistoricoPresenter(Produto produto){
        this.produto = produto;
        this.view = new ProdutoHistoricoView();
        configuraView();
    }
    
    private void configuraView(){
        view.setVisible(false);
    
        view.setVisible(true);
    }
}
