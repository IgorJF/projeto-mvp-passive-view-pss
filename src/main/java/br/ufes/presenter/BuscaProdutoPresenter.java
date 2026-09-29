package br.ufes.presenter;

import br.ufes.model.Produto;
import br.ufes.repository.ICategoriaRepository;
import br.ufes.repository.IProdutoRepository;
import br.ufes.view.BuscaProdutoView;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author igorj
 */
public class BuscaProdutoPresenter {
    private BuscaProdutoView view;
    private IProdutoRepository repositoryProduto;
    private ICategoriaRepository repositoryCategoria;
    private Produto produto;
    
    public BuscaProdutoPresenter(IProdutoRepository repositoryProduto, ICategoriaRepository repositoryCategoria){
        view = new BuscaProdutoView();
        this.repositoryProduto = repositoryProduto;
        this.repositoryCategoria = repositoryCategoria;
        configuraView();
    }
    
    private void configuraView(){
        view.setVisible(false);
        popularFiltro();
        listarProdutos();
        view.setVisible(true);
    }
    
    private void popularFiltro(){
        view.getCmbFiltro().addItem("Nome do Produto");
        view.getCmbFiltro().addItem("Categoria");
    }
    
    private void listarProdutos(){
        DefaultTableModel modelo = (DefaultTableModel) view.getTblProdutosPesquisados().getModel();
        modelo.setRowCount(0);
        for (Produto produto : repositoryProduto.listar()) {
            modelo.addRow(new Object[]{
                produto.getNomeProduto(),
                produto.getPrecoCusto(),
                produto.getCategoria().getNomeCategoria(),
                produto.getMargemLucro(),
                produto.getPrecoVenda()
            });
        }
    }
}
