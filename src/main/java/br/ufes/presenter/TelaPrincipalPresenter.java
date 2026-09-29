package br.ufes.presenter;

import br.ufes.repository.ICategoriaRepository;
import br.ufes.repository.IHistoricoPrecoRepository;
import br.ufes.repository.IProdutoRepository;
import br.ufes.view.CategoriaView;
import br.ufes.view.ProdutoView;
import br.ufes.view.TelaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class TelaPrincipalPresenter {
    private TelaPrincipal telaPrincipal;
    private ICategoriaRepository categoriaRepository;
    private IProdutoRepository produtoRepository;
    private IHistoricoPrecoRepository repositoryHistorico;

    public TelaPrincipalPresenter(ICategoriaRepository categoriaRepository, IProdutoRepository produtoRepository, IHistoricoPrecoRepository repositoryHistorico) {
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
         this.repositoryHistorico = repositoryHistorico;
        telaPrincipal = new TelaPrincipal();
        configuraView();
        telaProduto();
        telaCategoria();
    }
    
    private void configuraView() {
        telaPrincipal.setVisible(true);
    }
    
    private void telaProduto(){
        telaPrincipal.getjMenuItemIncluirProdutos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try{
                    new ProdutoPresenter(produtoRepository, categoriaRepository);
                } catch(Exception e){
                    JOptionPane.showMessageDialog(telaPrincipal, "Erro: Não foi possivel abrir a tela de incluir produtos - " + e.getMessage());
                }
            }
        });          
    }
    
    private void telaCategoria(){
        telaPrincipal.getjMenuItemCategorias().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try{
                    new CategoriaPresenter(categoriaRepository);
                } catch(Exception e){
                    JOptionPane.showMessageDialog(telaPrincipal, "Erro: Não foi possivel abrir a tela de categorias - " + e.getMessage());
                }
            }
        });          
    }
    
    private void telaBuscaroProduto(){
        telaPrincipal.getjMenuItemBuscarProdutos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                try{
                    new BuscaProdutoPresenter(produtoRepository);
                }
                } catch(Exception e){
                    JOptionPane.showMessageDialog(telaPrincipal, "Erro: Não foi possivel abrir a tela de busca de produtos - " + e.getMessage());
                }
            }
        });
    }
}
