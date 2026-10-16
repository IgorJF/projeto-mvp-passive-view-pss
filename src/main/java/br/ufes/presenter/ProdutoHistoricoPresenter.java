package br.ufes.presenter;

import br.ufes.model.HistoricoPreco;
import br.ufes.model.Produto;
import br.ufes.repository.IHistoricoPrecoRepository;
import br.ufes.view.ProdutoHistoricoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ProdutoHistoricoPresenter {
    private ProdutoHistoricoView view;
    private Produto produto;
    private IHistoricoPrecoRepository historicoRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ProdutoHistoricoPresenter(Produto produto, IHistoricoPrecoRepository historicoRepository) {
        this.produto = produto;
        this.historicoRepository = historicoRepository;
        this.view = new ProdutoHistoricoView();
        configuraView();
    }

    private void configuraView() {
        view.setVisible(false);
        carregarDados();
        listar();
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
        view.setDefaultCloseOperation(ProdutoHistoricoView.DISPOSE_ON_CLOSE);
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

    private void listar() {
        DefaultTableModel modelo = (DefaultTableModel) view.getTblHistorico().getModel();
        modelo.setRowCount(0);
        for (HistoricoPreco historico : historicoRepository.listarPorProduto(produto)) {
            modelo.addRow(new Object[]{
                historico.getData().format(formatter),
                historico.getPercentualLucro(),
                historico.getPrecoVenda()
            });
        }
    }

    private void fechar() {
        view.dispose();
    }
}
