package br.ufes.presenter;

import br.ufes.model.Produto;
import br.ufes.repository.ICategoriaRepository;
import br.ufes.repository.IHistoricoPrecoRepository;
import br.ufes.repository.IProdutoRepository;
import br.ufes.view.BuscaProdutoView;
import br.ufes.view.ProdutoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author igorj
 */
public class BuscaProdutoPresenter {
    private BuscaProdutoView view;
    private IProdutoRepository repositoryProduto;
    private ICategoriaRepository repositoryCategoria;
    private ProdutoPresenter produtoPresenter;
    private IHistoricoPrecoRepository repositoryHistorico;
    private ProdutoVisualizacaoPresenter produtoVisualizacao;
    
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
        view.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    buscar();
                } 
                catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        view.getBtnFechar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    fechar();
                } 
                catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        view.getBtnNovo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    novo();
                } 
                catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        view.getBtnVisualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    visualizar();
                } 
                catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        view.setVisible(true);
    }
    
    private void popularFiltro(){
        view.getCmbFiltro().addItem("Nome do Produto");
        view.getCmbFiltro().addItem("Categoria");
    }
    
    private void buscar(){
        String filtro = view.getCmbFiltro().getSelectedItem().toString();
        String caixaDePesquisa = view.getTxtPesquisa().getText();
        boolean encontrou = false;
        limparTabela();
        
        if(caixaDePesquisa.isBlank()){
            JOptionPane.showMessageDialog(view, "Informe um valor para pesquisa");
            listarProdutos();
            return;
        }

        if(filtro.equals("Nome do Produto")){
            for(Produto produto : repositoryProduto.listar()){
                if(produto.getNomeProduto().equalsIgnoreCase(caixaDePesquisa)){
                    listarProdutoPesquisado(produto);
                    encontrou = true;
                }
            }
        }
        else if(filtro.equals("Categoria")){
            for(Produto produto : repositoryProduto.listar()){
                if(produto.getCategoria().getNomeCategoria().equalsIgnoreCase(caixaDePesquisa)){
                    listarProdutoPesquisado(produto);
                    encontrou = true;
                }
            }
        }
        
        if(!encontrou){
            JOptionPane.showMessageDialog(view, "Produto nao encontrado");
        }
    }
    
    private void listarProdutoPesquisado(Produto produto){
        DefaultTableModel modelo = (DefaultTableModel) view.getTblProdutosPesquisados().getModel();
        modelo.addRow(new Object[]{
            produto.getNomeProduto(),
            produto.getPrecoCusto(),
            produto.getCategoria().getNomeCategoria(),
            produto.getMargemLucro(),
            produto.getPrecoVenda()
        });
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
    
    private void limparTabela(){
        DefaultTableModel modelo = (DefaultTableModel) view.getTblProdutosPesquisados().getModel();
        modelo.setRowCount(0);
    }
    
    private void fechar(){
        view.dispose();
    }
    
    private void novo(){
        produtoPresenter = new ProdutoPresenter(repositoryProduto, repositoryCategoria);
    }
    
    private void visualizar(){
        int linha = view.getTblProdutosPesquisados().getSelectedRow();
        if(linha == -1){
            JOptionPane.showMessageDialog(view, "Selecione uma linha");
        }
        else{
            Produto produto = repositoryProduto.listar().get(linha);
            produtoVisualizacao = new ProdutoVisualizacaoPresenter(produto, repositoryHistorico, repositoryCategoria, repositoryProduto);
        }
    }
}
