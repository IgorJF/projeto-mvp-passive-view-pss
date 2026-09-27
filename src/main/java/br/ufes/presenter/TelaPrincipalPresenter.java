package br.ufes.presenter;

import br.ufes.view.ProdutoView;
import br.ufes.view.TelaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class TelaPrincipalPresenter {
    private TelaPrincipal telaPrincipal;
    
    public TelaPrincipalPresenter(){
        telaPrincipal = new TelaPrincipal();
        configuraView();
        telaProduto();
    }
    
    private void configuraView() {
        telaPrincipal.setVisible(true);
    }
    
    private void telaProduto(){
        telaPrincipal.getjMenuItemIncluirProdutos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try{
                    new ProdutoView().setVisible(true);
                } catch(Exception e){
                    JOptionPane.showMessageDialog(telaPrincipal, "Erro: Não foi possivel abrir a tela de incluir produtos - " + e.getMessage());
                }
            }
        });          
    }
}
