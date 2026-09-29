package br.ufes.presenter;

import br.ufes.model.Categoria;
import br.ufes.model.Produto;
import br.ufes.repository.CategoriaRepository;
import br.ufes.repository.ICategoriaRepository;
import br.ufes.repository.IProdutoRepository;
import br.ufes.view.ProdutoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ProdutoPresenter {
    private ProdutoView view;
    private Categoria categoria;
    private Produto produto;
    private IProdutoRepository repositoryProduto;
    private ICategoriaRepository repositoryCategoria;
    
     public ProdutoPresenter(IProdutoRepository repositoryProduto, ICategoriaRepository repositoryCategoria){
        this.repositoryProduto = repositoryProduto;
        this.repositoryCategoria = repositoryCategoria;
        view = new ProdutoView();
        configuraView(); 
    }

    private void configuraView() {
        view.setVisible(false);
        listarCategorias();
        limparConteudoView();
        view.getBtnSalvar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    salvar();
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        view.getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    cancelar();
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        view.setVisible(true);
    }
    
    private void salvar(){
        String nomeProduto = view.getTxtNomeProduto().getText(); 
        double precoCusto = Double.parseDouble(view.getTxtPrecoCusto().getText());
        Categoria categoriaProduto = (Categoria)view.getCmbCategoriaProduto().getSelectedItem();
        
        this.produto = new Produto(nomeProduto, precoCusto, categoriaProduto);
        repositoryProduto.salvar(produto);
        JOptionPane.showMessageDialog(view, "Produto: " + produto.getNomeProduto() + " salvo com sucesso");
        limparConteudoView();
    }
    
    private void listarCategorias(){
        for (Categoria categoria : repositoryCategoria.listar()) {
            view.getCmbCategoriaProduto().addItem(categoria);
        }
    }
    
    private void cancelar(){
        limparConteudoView();
    }
    
    private void limparConteudoView(){
        view.getTxtNomeProduto().setText("");
        view.getTxtPrecoCusto().setText("");
        view.getCmbCategoriaProduto().setSelectedIndex(-1);
    }
}
