/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufes.presenter;

import br.ufes.model.Produto;
import br.ufes.view.ProdutoVisualizacaoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author ana-luiza
 */
public class ProdutoVisualizacaoPresenter {
    private ProdutoVisualizacaoView view;
    private Produto produto;
    
    public ProdutoVisualizacaoPresenter(Produto produto){
        this.produto = produto;
        this.view = new ProdutoVisualizacaoView();
        configuraView();
    }
    
    public void configuraView(){
        view.setVisible(false);
        carregarDados();
        
        view.getBtnFechar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    fechar();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        
        view.getBtnEditar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    editar();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "FAlha: " + ex.getMessage());
                }
            }
        });
        
        view.getBtnHistorico().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    visualizarHistorico();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        view.setVisible(true);
    }
    
    private void carregarDados(){
        if (produto != null){
            view.getTxtNomeProduto().setText(produto.getNomeProduto());
            view.getTxtPrecoCusto().setText(String.format("%.2f", produto.getPrecoCusto()));
            
            if(produto.getCategoria() != null){
                view.getCbCategoria().setSelectedItem(produto.getCategoria().getNome());
            }
            
            view.getTxtMargemLucro().setText(String.format("%.2f%%", produto.getMargemLucroAtual()));
            view.getTxtPrecoVenda().setText(String.format("%.2f", produto.getPrecovendaAtual()));
        }
    }
    
    private void fechar(){
        view.dispose();
    }
    
    private void editar(){
        //conecta com tela edicao
    }
    
    private void visualizarHistorico(){
        //conecta com tela historico de precos
    }
}
