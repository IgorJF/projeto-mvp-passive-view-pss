package br.ufes.presenter;

import br.ufes.model.Categoria;
import br.ufes.model.Produto;
import br.ufes.repository.ICategoriaRepository;
import br.ufes.repository.IHistoricoPrecoRepository;
import br.ufes.repository.IProdutoRepository;
import br.ufes.service.ProdutoService;
import br.ufes.state.InclusaoProdutoState;
import br.ufes.state.ProdutoState;
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
    private ProdutoService produtoService;
    private ProdutoState modo;

    public ProdutoPresenter(IProdutoRepository repositoryProduto, ICategoriaRepository repositoryCategoria, IHistoricoPrecoRepository repositoryHistorico){
        this.repositoryProduto = repositoryProduto;
        this.repositoryCategoria = repositoryCategoria;
        this.repositoryHistorico = repositoryHistorico;
        this.produtoService = new ProdutoService(repositoryProduto, repositoryCategoria, repositoryHistorico);
        this.view = new ProdutoView();
        setModo(new InclusaoProdutoState(this));
        configuraView();
    }
    
    public void setModo(ProdutoState modo){
        this.modo = modo;
    }

    private void configuraView(){
        view.setVisible(false);
        listarCategorias();

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

        view.getBtnEditar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    modo.editar();
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });

        view.getBtnHistorico().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    visualizarHistorico();
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });

        view.setDefaultCloseOperation(ProdutoView.DISPOSE_ON_CLOSE);
        view.setVisible(true);
    }

    public void visualizar(Produto produto){
        this.produto = produto;
        modo.visualizar();
    }
    
    private void listarCategorias() {
        view.getCmbCategoriaProduto().removeAllItems();
        for (Categoria categoria : repositoryCategoria.listar()) {
            view.getCmbCategoriaProduto().addItem(categoria);
        }
    }

    private void visualizarHistorico(){
        new ProdutoHistoricoPresenter(produto, repositoryHistorico);
    }

    public void fechar(){
        view.dispose();
    }

    private void salvar(){
        String nomeProduto = view.getTxtNomeProduto().getText();
        String textoPrecoCusto = view.getTxtPrecoCusto().getText();
        Categoria categoriaProduto = (Categoria) view.getCmbCategoriaProduto().getSelectedItem();

        if(nomeProduto.isBlank()){
            throw new IllegalArgumentException("Nome do produto nao pode ser vazio.");
        }
        if(textoPrecoCusto.isBlank()){
            throw new IllegalArgumentException("Preco de custo nao pode ser vazio.");
        }
        if(categoriaProduto == null){
            throw new IllegalArgumentException("Uma categoria deve ser selecionada.");
        }

        double precoCusto;
        try{
            precoCusto = Double.parseDouble(textoPrecoCusto.trim().replace(",", "."));
        }
        catch(NumberFormatException ex){
            throw new IllegalArgumentException("Preco de custo invalido. Informe apenas numeros, por exemplo 12,50.");
        }

        if(produto == null){
            produtoService.salvar(nomeProduto, precoCusto, categoriaProduto);
            JOptionPane.showMessageDialog(view, "Produto: " + nomeProduto + " salvo com sucesso");
            modo.novo();
        }
        else{
            produtoService.editar(produto, nomeProduto, precoCusto, categoriaProduto);
            JOptionPane.showMessageDialog(view, "Produto: " + nomeProduto + " salvo com sucesso");
            modo.visualizar();
        }
    }

    private void cancelar(){
        modo.cancelar();
    }

    public void modoInclusao(){
        view.getLblModo().setText("Modo: Inclusão");
        limparConteudoView();
        habilitarCampos(true);
        view.getBtnEditar().setVisible(false);
        view.getBtnSalvar().setVisible(true);
        view.getBtnCancelar().setVisible(true);
        view.getBtnHistorico().setVisible(false);
    }

    public void modoEdicao(){
        view.getLblModo().setText("Modo: Edição");
        habilitarCampos(true);
        view.getBtnEditar().setVisible(false);
        view.getBtnSalvar().setVisible(true);
        view.getBtnCancelar().setVisible(true);
        view.getBtnHistorico().setVisible(false);
    }

    public void modoVisualizacao(){
        view.getLblModo().setText("Modo: Visualização");
        habilitarCampos(false);
        view.getBtnEditar().setVisible(true);
        view.getBtnSalvar().setVisible(false);
        view.getBtnCancelar().setVisible(false);
        view.getBtnHistorico().setVisible(true);
        carregarDadosProduto();
    }

    private void habilitarCampos(boolean habilitado){
        view.getTxtNomeProduto().setEnabled(habilitado);
        view.getTxtPrecoCusto().setEnabled(habilitado);
        view.getCmbCategoriaProduto().setEnabled(habilitado);
    }

    private void carregarDadosProduto(){
        view.getTxtNomeProduto().setText(produto.getNomeProduto());
        view.getTxtPrecoCusto().setText(String.format("%.2f", produto.getPrecoCusto()));
        view.getCmbCategoriaProduto().setSelectedItem(produto.getCategoria());
        view.getTxtMargemLucro().setText(String.format("%.2f%%", produto.getMargemLucro()));
        view.getTxtPrecoVenda().setText(String.format("%.2f", produto.getPrecoVenda()));
    }

    private void limparConteudoView(){
        view.getTxtNomeProduto().setText("");
        view.getTxtPrecoCusto().setText("");
        view.getCmbCategoriaProduto().setSelectedIndex(-1);
        view.getTxtMargemLucro().setText("");
        view.getTxtPrecoVenda().setText("");
    }
}