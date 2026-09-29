package br.ufes.presenter;

import br.ufes.model.Categoria;
import br.ufes.model.Produto;
import br.ufes.repository.ICategoriaRepository;
import br.ufes.repository.IHistoricoPrecoRepository;
import br.ufes.view.ProdutoVisualizacaoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author ana-luiza
 */
public class ProdutoVisualizacaoPresenter {
    private ProdutoVisualizacaoView view;
    private Produto produto;
    private IHistoricoPrecoRepository repositoryHistorico;
    private ICategoriaRepository repositoryCategoria;

    public ProdutoVisualizacaoPresenter(Produto produto, IHistoricoPrecoRepository repositoryHistorico, ICategoriaRepository repositoryCategoria){
        this.produto = produto;
        this.repositoryHistorico = repositoryHistorico;
        this.repositoryCategoria = repositoryCategoria;
        this.view = new ProdutoVisualizacaoView();
        configuraView();
    }

    private void configuraView() {
        view.setVisible(false);
        carregarDados();
        view.getBtnFechar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    fechar();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        view.getBtnEditar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    editar();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "FAlha: " + ex.getMessage());
                }
            }
        });

        view.getBtnHistorico().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    visualizarHistorico();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        view.setVisible(true);
    }

    private void carregarDados() {
        if (produto != null) {
            view.getTxtNomeProduto().setText(produto.getNomeProduto());
            view.getTxtPrecoCusto().setText(String.format("%.2f", produto.getPrecoCusto()));
            carregarCategorias(repositoryCategoria);
            view.getTxtMargemLucro().setText(String.format("%.2f%%", produto.getMargemLucro()));
            view.getTxtPrecoVenda().setText(String.format("%.2f", produto.getPrecoVenda()));
        }
    }

    private void fechar() {
        view.dispose();
    }

    private void editar() {
        //conecta com tela edicao
    }

    private void visualizarHistorico() {
        if (this.produto != null) {
            new ProdutoHistoricoPresenter(this.produto, repositoryHistorico);
        } else {
            JOptionPane.showMessageDialog(view, "Nenhum produto selecionado para exibir o historico.");
        }
    }
    
    private void carregarCategorias(ICategoriaRepository repositoryCategoria) {
    for (Categoria categoria : repositoryCategoria.listar()) {
        view.getCbCategoria().addItem(categoria.getNomeCategoria());
    }
    if (produto.getCategoria() != null) {
        view.getCbCategoria().setSelectedItem(produto.getCategoria().getNomeCategoria());
    }
}
}
