package br.ufes.presenter;

import br.ufes.model.Categoria;
import br.ufes.repository.ICategoriaRepository;
import br.ufes.repository.IProdutoRepository;
import br.ufes.service.CategoriaService;
import br.ufes.state.CategoriaState;
import br.ufes.view.CategoriaView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import br.ufes.state.EdicaoCategoriaState;
import br.ufes.state.VisualizacaoCategoriaState;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

//categoriaPresenter inicia a tela de categoria, no modo de visualizacao
//ao clicar nos metodos respectivos das acoes, alterna entre os states de inclusao e edicao
//botao salvar eh o responsavel por incluir a nova categoria e tambem editar uma categoria alterada, verificando se a categoria ja existe
//metodo editar apenas seta na tela as informacoes da categoria selecionada

public class CategoriaPresenter {
    private CategoriaView view;
    private Categoria categoria;
    private ICategoriaRepository categoriaRepository;
    private CategoriaService categoriaService;
    private CategoriaState modo;
    
    public CategoriaPresenter(ICategoriaRepository categoriaRepository, IProdutoRepository produtoRepository){
        this.categoriaRepository = categoriaRepository;
        this.categoriaService = new CategoriaService(categoriaRepository, produtoRepository);
        view = new CategoriaView();
        setModo(new VisualizacaoCategoriaState(this));
        configuraView(); 
    }
    
    //alterna entre os estados(states)
    public void setModo(CategoriaState modo) {
        this.modo = modo;
    }

    private void configuraView() {
        view.setVisible(false);
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
        view.getBtnNovo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    modo.novo();
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        view.getBtnEditar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    editar();
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        view.getBtnExcluir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    excluir();
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        view.getBtnFechar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    fechar();
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        view.getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    modo.visualizar();
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        view.setVisible(true);
    }
    
    //pega o que esta nas caixas de texto, caso nao tenha linha selecionada, considera que eh para criar uma nova
    //caso tenha, chama editar
    private void salvar(){
        String nomeCategoria = view.getTxtNomeCategoria().getText(); 
        String textoPercentual = view.getTxtPercentualLucro().getText();
        
        if(nomeCategoria.isBlank()){
             throw new IllegalArgumentException("Nome da categoria nao pode ser vazio.");
        }
        if(textoPercentual.isBlank()){
            throw new IllegalArgumentException("Percentual de lucro nao pode ser vazio.");
        }

        double percentualLucro = Double.parseDouble(textoPercentual.replace(",", "."));
        int linha = view.getTblCategoriasCadastradas().getSelectedRow();
        
        if (linha == -1) {
            categoriaService.salvar(nomeCategoria, percentualLucro);
        } 
        else {
            categoria = categoriaService.listar().get(linha);
            categoriaService.editar(categoria, nomeCategoria, percentualLucro);
        }       
        JOptionPane.showMessageDialog(view, "Categoria: " + nomeCategoria + " salva com sucesso");
        setModo(new VisualizacaoCategoriaState(this));
    }
    
    //lista as cadastradas
    private void listar(){
        DefaultTableModel modelo = (DefaultTableModel) view.getTblCategoriasCadastradas().getModel();
        modelo.setRowCount(0);
        for (Categoria categoriaLista : categoriaService.listar()) {
            modelo.addRow(new Object[]{categoriaLista.getNomeCategoria(), categoriaLista.getPercentualLucro()});
        }
    }
    
    //alterna para o modo de edicao
    //captura a linha selecionada acha o indice diretamente no repository e seta os valores nos campos de texto
    private void editar(){
        setModo(new EdicaoCategoriaState(this));
        int linha = view.getTblCategoriasCadastradas().getSelectedRow(); 
        if (linha == -1) {
            throw new IllegalArgumentException("Selecione uma categoria na tabela para editar.");
        }
        Categoria categoriaLista = categoriaService.listar().get(linha);
        view.getTxtNomeCategoria().setText(categoriaLista.getNomeCategoria());
        view.getTxtPercentualLucro().setText(String.valueOf(categoriaLista.getPercentualLucro()));
    }
    
    //pega a linha selecionada, acha no repository, verifica se tem produto cadastrado atraves do indice da categoria
    private void excluir(){
        int linha = view.getTblCategoriasCadastradas().getSelectedRow();
        if (linha == -1) {
            throw new IllegalArgumentException("Selecione uma categoria na tabela para excluir.");
        }
        Categoria categoriaLista = categoriaService.listar().get(linha);
        categoriaService.validarExclusao(categoriaLista.getIdCategoria());
        int confirmacao = JOptionPane.showConfirmDialog(view, "Deseja realmente excluir a categoria " + categoriaLista.getNomeCategoria(), "Confirmacao de Exclusao", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION){
            categoriaService.excluir(categoriaLista.getIdCategoria());
            JOptionPane.showMessageDialog(view, "Categoria: " + categoriaLista.getNomeCategoria() + " excluida com sucesso");
        } 
        else if(confirmacao == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(view, "Exclusao cancelada");
        }
        setModo(new VisualizacaoCategoriaState(this));
    }
    
    private void fechar(){
        view.dispose();
    }
    
    public void modoInclusao(){
        view.getLblModo().setText("Modo: Inclusao");
        view.getTxtNomeCategoria().setEnabled(true);
        view.getTxtPercentualLucro().setEnabled(true);
        view.getBtnSalvar().setEnabled(true);
        view.getBtnCancelar().setEnabled(true);
        view.getBtnNovo().setEnabled(false);
        view.getBtnEditar().setEnabled(false);
        view.getBtnExcluir().setEnabled(false);
        view.getBtnFechar().setEnabled(false);
    }
    
    public void modoEdicao(){
        view.getLblModo().setText("Modo: Edicao");
        view.getTxtNomeCategoria().setEnabled(true);
        view.getTxtPercentualLucro().setEnabled(true);
        view.getBtnSalvar().setEnabled(true);
        view.getBtnCancelar().setEnabled(true);
        view.getBtnNovo().setEnabled(false);
        view.getBtnEditar().setEnabled(false);
        view.getBtnExcluir().setEnabled(false);
        view.getBtnFechar().setEnabled(false);
    }
    
    public void modoVisualizacao(){
        limparConteudoView();
        listar();
        view.getLblModo().setText("Modo: Visualizacao");
        view.getTxtNomeCategoria().setEnabled(false);
        view.getTxtPercentualLucro().setEnabled(false);
        view.getBtnSalvar().setEnabled(false);
        view.getBtnCancelar().setEnabled(false);
        view.getBtnNovo().setEnabled(true);
        view.getBtnEditar().setEnabled(true);
        view.getBtnExcluir().setEnabled(true);
        view.getBtnFechar().setEnabled(true);
    }
    
    private void limparConteudoView(){
        view.getTxtNomeCategoria().setText("");
        view.getTxtPercentualLucro().setText("");
    }
}