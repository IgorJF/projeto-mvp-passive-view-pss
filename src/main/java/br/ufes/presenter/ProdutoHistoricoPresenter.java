/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufes.presenter;

import br.ufes.model.HistoricoPreco;
import br.ufes.model.Produto;
import br.ufes.repository.IHistoricoPrecoRepository;
import br.ufes.view.ProdutoHistoricoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author ana-luiza
 */
public class ProdutoHistoricoPresenter {
    private ProdutoHistoricoView view;
    private Produto produto;
    private IHistoricoPrecoRepository repositoryHistorico;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ProdutoHistoricoPresenter(Produto produto, IHistoricoPrecoRepository repositoryHistorico) {
        this.produto = produto;
        this.repositoryHistorico = repositoryHistorico;
        this.view = new ProdutoHistoricoView();
        configuraView();
    }

    private void configuraView() {
        view.setVisible(false);
        carregarDados();
        configurarTabela();
        carregarHistorico();

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

        view.setVisible(true);
    }

    private void carregarDados() {
        if (produto != null) {
            view.getTxtNomeProduto().setText(produto.getNomeProduto());
            if (produto.getCategoria() != null) {
                view.getTxtCategoria().setText(produto.getCategoria().getNomeCategoria());
            }
        }
    }

    private void configurarTabela() {
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Data", "Percentual de lucro (%)", "Preço de venda"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        view.getTblHistorico().setModel(tableModel);
    }

    private void carregarHistorico() {
        DefaultTableModel model = (DefaultTableModel) view.getTblHistorico().getModel();
        model.setRowCount(0);

        if (produto != null) {
            List<HistoricoPreco> historicos = repositoryHistorico.listarPorProduto(produto);

            for (HistoricoPreco h : historicos) {
                String dataFormatada = h.getData() != null
                        ? h.getData().format(formatter)
                        : "-";

                model.addRow(new Object[]{
                    dataFormatada,
                    String.format("%.2f%%", h.getPercentualLucro()),
                    String.format("%.2f", h.getPrecoVenda())
                });
            }
        }
    }

    private void fechar() {
        view.dispose();
    }
}
