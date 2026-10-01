package br.ufes.presenter;

import br.ufes.model.Categoria;
import br.ufes.model.Produto;
import br.ufes.repository.CategoriaRepository;
import br.ufes.repository.ICategoriaRepository;
import br.ufes.repository.IHistoricoPrecoRepository;
import br.ufes.repository.IProdutoRepository;
import br.ufes.service.CategoriaService;
import br.ufes.service.ProdutoService;
import br.ufes.view.ProdutoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ProdutoPresenter {
    private ProdutoView view;
    private Produto produto;
    private IProdutoRepository repositoryProduto;
    private ICategoriaRepository repositoryCategoria;
    private IHistoricoPrecoRepository repositoryHistorico;
    private CategoriaService categoriaService;
    private ProdutoService produtoService;
    
    public ProdutoPresenter(IProdutoRepository repositoryProduto, ICategoriaRepository repositoryCategoria){
        this.repositoryProduto = repositoryProduto;
        this.repositoryCategoria = repositoryCategoria;
        produtoService = new ProdutoService(repositoryProduto, repositoryHistorico);
        view = new ProdutoView();
        configuraView(); 
    }

    public ProdutoView getView() {
        return view;
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
                } 
                catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        view.getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    cancelar();
                } 
                catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        view.setVisible(true);
    }
    
    private void salvar(){
        String nomeProduto = view.getTxtNomeProduto().getText(); 
        String textoPrecoCusto = view.getTxtPrecoCusto().getText();
        Categoria categoriaProduto = (Categoria)view.getCmbCategoriaProduto().getSelectedItem();
        
        if(nomeProduto.isBlank()){
             throw new IllegalArgumentException("Nome do produto nao pode ser vazio.");
        }
        if(textoPrecoCusto.isBlank()){
            throw new IllegalArgumentException("Preco de custo nao pode ser vazio.");
        }
        if(categoriaProduto == null){
            throw new IllegalArgumentException("Uma categoria deve ser selecionada.");
        }
        
        double precoCusto = Double.parseDouble(textoPrecoCusto.replace(",", "."));
        if(produto == null){
            produtoService.salvar(nomeProduto, precoCusto, categoriaProduto);
        }
        else{
            produtoService.editar(produto, nomeProduto, precoCusto, categoriaProduto);
        }
        
        JOptionPane.showMessageDialog(view, "Produto: " + nomeProduto + " salvo com sucesso");
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
        view.getTxtMargemLucro().setText("");
        view.getTxtPrecoVenda().setText("");
    }
}
