package br.ufes.presenter;

import br.ufes.model.Categoria;
import br.ufes.repository.ICategoriaRepository;
import br.ufes.view.CategoriaView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CategoriaPresenter {
    private CategoriaView view;
    private Categoria categoria;
    private ICategoriaRepository repository;
    
     public CategoriaPresenter(ICategoriaRepository repository){
        this.repository = repository;
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
        double percentualLucro = Double.parseDouble(view.getTxtPercentualLucro().getText());
        this.categoria = new Categoria(nomeCategoria, percentualLucro);
        int linha = view.getTblCategoriasCadastradas().getSelectedRow();
        if (linha == -1) {
            repository.salvar(categoria);
        } 
        else {
            categoria = repository.listar().get(linha);
            categoria.setNomeCategoria(nomeCategoria);
            categoria.setPercentualLucro(percentualLucro);
            repository.salvar(categoria);
        }
        JOptionPane.showMessageDialog(view, "Categoria: " + categoria.getNomeCategoria() + " salvo com sucesso");
        limparConteudoView();
        listar();
        modoVisualizacao();
    }
    
    private void listar(){
        DefaultTableModel modelo = (DefaultTableModel) view.getTblCategoriasCadastradas().getModel();
        modelo.setRowCount(0);
        for (Categoria categoria : repository.listar()) {
            modelo.addRow(new Object[]{
                categoria.getNomeCategoria(),
                categoria.getPercentualLucro()
            });
        }
    }
    
    private void editar(){
        int linha = view.getTblCategoriasCadastradas().getSelectedRow(); 
        Categoria categoria = repository.listar().get(linha);
        view.getTxtNomeCategoria().setText(categoria.getNomeCategoria());
        view.getTxtPercentualLucro().setText(String.valueOf(categoria.getPercentualLucro()));
        modoInclusao();
    }
    
    private void excluir(){
        int linha = view.getTblCategoriasCadastradas().getSelectedRow();
        Categoria categoria = repository.listar().get(linha);
        int confirmacao = JOptionPane.showConfirmDialog(view, "Deseja realmente excluir a categoria " + categoria.getNomeCategoria(), "Confirmacao de Exclusao", JOptionPane.YES_NO_OPTION);
         if (confirmacao == JOptionPane.YES_OPTION){
            repository.excluir(categoria);
            JOptionPane.showMessageDialog(view, "Categoria: " + categoria.getNomeCategoria() + " excluida com sucesso");
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
