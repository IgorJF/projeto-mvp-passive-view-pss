package br.ufes.presenter;

import br.ufes.model.Produto;
import br.ufes.repository.ICategoriaRepository;
import br.ufes.repository.IHistoricoPrecoRepository;
import br.ufes.repository.IProdutoRepository;
import br.ufes.service.ProdutoService;
import br.ufes.view.BuscaProdutoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

//CRIAR STATE DA TELA DE BUSCA PRODUTO

public class BuscaProdutoPresenter {
    private BuscaProdutoView view;
    private IProdutoRepository repositoryProduto;
    private ICategoriaRepository repositoryCategoria;
    private IHistoricoPrecoRepository repositoryHistorico;
    private ProdutoService produtoService;
    private ProdutoPresenter produtoPresenter;
    private List<Produto> produtosListados = new ArrayList<>();

    public BuscaProdutoPresenter(IProdutoRepository repositoryProduto, ICategoriaRepository repositoryCategoria, IHistoricoPrecoRepository repositoryHistorico){
        view = new BuscaProdutoView();
        this.repositoryProduto = repositoryProduto;
        this.repositoryCategoria = repositoryCategoria;
        this.repositoryHistorico = repositoryHistorico;
        this.produtoService = new ProdutoService(repositoryProduto, repositoryCategoria, repositoryHistorico);
        configuraView();
    }

    private void configuraView(){
        view.setVisible(false);
        popularFiltro();
        listarTodosProdutos();
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
        view.getBtnExcluir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    excluir();
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        view.getTblProdutosPesquisados().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                boolean selecionado = view.getTblProdutosPesquisados().getSelectedRow() != -1;
                view.getBtnVisualizar().setEnabled(selecionado);
                view.getBtnExcluir().setEnabled(selecionado);
            }
        });
        view.setDefaultCloseOperation(BuscaProdutoView.DISPOSE_ON_CLOSE);
        view.setVisible(true);
    }

    //adicionando as categorias de filtragem
    private void popularFiltro(){
        view.getCmbFiltro().addItem("Nome do Produto");
        view.getCmbFiltro().addItem("Categoria");
    }

    //pega o que esta na categoria de filtragem (nome ou categoria, pega o que esta na caixa de busca
    //se for nome, procura por algo que esteja no nome do produto (contains) e lista a partir de do metodo listarProdutoPesquisado
    //os achados, que guarda e exibe a partir de um vetor de produtos, mesmo coisa com categoria, e muda o estado da variavel de 
    //encontrado para true, pois se nao encontrar, entrara no if que mostrara o JOptionPane
    private void buscar(){
        String filtro = view.getCmbFiltro().getSelectedItem().toString();
        String caixaDePesquisa = view.getTxtPesquisa().getText().toLowerCase();
        boolean encontrou = false;
        limparTabela();

        if(caixaDePesquisa.isBlank()){
            JOptionPane.showMessageDialog(view, "Informe um valor para pesquisa");
            listarTodosProdutos();
            return;
        }

        if(filtro.equals("Nome do Produto")){
            for(Produto produto : repositoryProduto.listar()){
                if(produto.getNomeProduto().toLowerCase().contains(caixaDePesquisa)){
                    listarProdutoPesquisado(produto);
                    encontrou = true;
                }
            }
        }
        else if(filtro.equals("Categoria")){
            for(Produto produto : repositoryProduto.listar()){
                if(produto.getCategoria().getNomeCategoria().toLowerCase().contains(caixaDePesquisa)){
                    listarProdutoPesquisado(produto);
                    encontrou = true;
                }
            }
        }

        if(!encontrou){
            JOptionPane.showMessageDialog(view, "Produto nao encontrado");
        }
    }
    
    //possui um vetor que armazenara os produtos pesquisados e depois lista esses produtos na tabela, eh utilizado no metodo de busca
    //para chamar os pesquisados
    private void listarProdutoPesquisado(Produto produto){
        produtosListados.add(produto);
        DefaultTableModel modelo = (DefaultTableModel) view.getTblProdutosPesquisados().getModel();
        modelo.addRow(new Object[]{
            produto.getNomeProduto(),
            produto.getPrecoCusto(),
            produto.getCategoria().getNomeCategoria(),
            produto.getMargemLucro(),
            produto.getPrecoVenda()
        });
    }

    //lista todos os produtos
    private void listarTodosProdutos(){
        limparTabela();
        for (Produto produto : repositoryProduto.listar()){
            listarProdutoPesquisado(produto);
        }
    }

    //limpa a tabela de produtos e zera o vetor de pesquisados
    private void limparTabela(){
        produtosListados.clear();
        DefaultTableModel modelo = (DefaultTableModel) view.getTblProdutosPesquisados().getModel();
        modelo.setRowCount(0);
    }

    private void fechar(){
        view.dispose();
    }

    //abre a tela de cadastro de produto para cadastrar novo produto
    private void novo(){
        produtoPresenter = new ProdutoPresenter(repositoryProduto, repositoryCategoria, repositoryHistorico);
    }

    //pega a linha selecionada, passa com indice no get do array e o array busca naquele indice o produto
    //como o array sempre muda com uma pesquisa, a gente pega o produto da linha naquele momento que foi pesquisado
    //e abre diretamente a janela de produto no modo de visualizacao
    private void visualizar(){
        int linha = view.getTblProdutosPesquisados().getSelectedRow();
        if (linha == -1) {
            throw new IllegalArgumentException("Selecione um produto na tabela para visualizar.");
        }
        Produto produto = produtosListados.get(linha);
        produtoPresenter = new ProdutoPresenter(repositoryProduto, repositoryCategoria, repositoryHistorico);
        produtoPresenter.visualizar(produto);
    }

    //pega a linha selecionada, faz o array buscar com o get, e passa o id daquele produto para o service que vai entrar no repositorio
    //e excluir o produto
    private void excluir(){
        int linha = view.getTblProdutosPesquisados().getSelectedRow();
        if (linha == -1) {
            throw new IllegalArgumentException("Selecione um produto na tabela para excluir.");
        }
        Produto produto = produtosListados.get(linha);
        int confirmacao = JOptionPane.showConfirmDialog(view, "Deseja realmente excluir o produto " + produto.getNomeProduto(), "Confirmacao de Exclusao", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION){
            produtoService.excluir(produto.getIdProduto());
            JOptionPane.showMessageDialog(view, "Produto: " + produto.getNomeProduto() + " excluido com sucesso");
            listarTodosProdutos();
        }
        else{
            JOptionPane.showMessageDialog(view, "Exclusao cancelada");
        }
    }
}
