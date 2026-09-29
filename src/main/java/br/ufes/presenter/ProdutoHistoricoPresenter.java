/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufes.presenter;

import br.ufes.model.Produto;
import br.ufes.view.ProdutoHistoricoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

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
                
        view.setVisible(true);
    }
    
    private void carregarDados(){
        if (produto != null){
            view.getTxtNomeProduto().setText(produto.getNomeProduto());
            
            if(produto.getCategoria() != null){
                view.getTxtCategoria().setText(produto.getCategoria().getNomeCategoria());
            }
            String[] colunas = {"Data", "Percentual de lucro (%)", "Preço de venda"};
            DefaultTableModel tableModel = new DefaultTableModel(colunas, 0){
                @Override
                public boolean isCellEditable(int row, int column){
                    return false;
                }
            };
            view.getTblHistorico().setModel(tableModel);
        }
    }
    
    private void fechar(){
        view.dispose();
    }
}
