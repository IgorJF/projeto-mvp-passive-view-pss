package br.ufes.presenter;

import br.ufes.model.Categoria;
import br.ufes.repository.ICategoriaRepository;
import br.ufes.service.CategoriaService;
import br.ufes.view.CategoriaView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CategoriaPresenter {
    private CategoriaView view;
    private Categoria categoria;
    private ICategoriaRepository repository;
    private CategoriaService categoriaService;
    
     public CategoriaPresenter(ICategoriaRepository repository){
        this.repository = repository;
        this.categoriaService = new CategoriaService(repository);
        view = new CategoriaView();
        configuraView(); 
    }

    private void configuraView() {
        view.setVisible(false);
        limparConteudoView();
        listar();
        modoVisualizacao();
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
                    modoInclusao();
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
                    cancelar();
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        view.setVisible(true);
    }
    
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
        limparConteudoView();
        listar();
        modoVisualizacao();
    }
    
    private void listar(){
        DefaultTableModel modelo = (DefaultTableModel) view.getTblCategoriasCadastradas().getModel();
        modelo.setRowCount(0);
        for (Categoria categoriaLista : categoriaService.listar()) {
            modelo.addRow(new Object[]{categoriaLista.getNomeCategoria(), categoriaLista.getPercentualLucro()});
        }
    }
    
    private void editar(){
        int linha = view.getTblCategoriasCadastradas().getSelectedRow(); 
        if (linha == -1) {
            throw new IllegalArgumentException("Selecione uma categoria na tabela para editar.");
        }
        Categoria categoriaLista = categoriaService.listar().get(linha);
        view.getTxtNomeCategoria().setText(categoriaLista.getNomeCategoria());
        view.getTxtPercentualLucro().setText(String.valueOf(categoriaLista.getPercentualLucro()));
        modoInclusao();
    }
    
    private void excluir(){
        int linha = view.getTblCategoriasCadastradas().getSelectedRow();
        if (linha == -1) {
            throw new IllegalArgumentException("Selecione uma categoria na tabela para excluir.");
        }
        Categoria categoriaLista = categoriaService.listar().get(linha);
        int confirmacao = JOptionPane.showConfirmDialog(view, "Deseja realmente excluir a categoria " + categoriaLista.getNomeCategoria(), "Confirmacao de Exclusao", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION){
            categoriaService.excluir(categoriaLista);
            JOptionPane.showMessageDialog(view, "Categoria: " + categoriaLista.getNomeCategoria() + " excluida com sucesso");
        } 
        else if(confirmacao == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(view, "Exclusao cancelada");
        }
        listar();
    }
    
    private void fechar(){
        view.dispose();
    }
    
    private void cancelar(){
        limparConteudoView();
        modoVisualizacao();
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
    
    public void modoVisualizacao(){
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