package br.ufes.presenter;

import br.ufes.repository.ICategoriaRepository;
import br.ufes.repository.IProdutoRepository;
import br.ufes.view.TelaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import br.ufes.repository.IHistoricoPrecoRepository;

public class TelaPrincipalPresenter {
    private TelaPrincipal view;
    private ICategoriaRepository categoriaRepository;
    private IProdutoRepository produtoRepository;
    private IHistoricoPrecoRepository historicoRepository;

    public TelaPrincipalPresenter(TelaPrincipal view, ICategoriaRepository categoriaRepository, IProdutoRepository produtoRepository, IHistoricoPrecoRepository historicoRepository){
        this.view = view;
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
        this.historicoRepository = historicoRepository;
        configuraView();
    }
    
    private void configuraView(){
        view.setVisible(true);
        
        view.getjMenuItemCategorias().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    new CategoriaPresenter(categoriaRepository, produtoRepository);
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        
        view.getjMenuItemIncluirProdutos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    new ProdutoPresenter(produtoRepository, categoriaRepository, historicoRepository);
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        
        view.getjMenuItemBuscarProdutos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    new BuscaProdutoPresenter(produtoRepository, categoriaRepository, historicoRepository);
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        
        view.getjMenuItemCalcularLucro().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    new CalculoMargemPrecoPresenter(produtoRepository, historicoRepository);
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
    }
}


