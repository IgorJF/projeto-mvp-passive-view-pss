package br.ufes.presenter;

import br.ufes.model.Produto;
import br.ufes.repository.IProdutoRepository;
import br.ufes.service.CalculoPrecoService;
import br.ufes.view.CalculoMargemPrecoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author igorj
 */
public class CalculoMargemPrecoPresenter {
    private CalculoPrecoService calculoMargem;
    private IProdutoRepository produtoRepository;
    private CalculoMargemPrecoView view;
    private LocalDate data;
    
    public CalculoMargemPrecoPresenter(IProdutoRepository produtoRepository){
        this.produtoRepository = produtoRepository;
        calculoMargem = new CalculoPrecoService(produtoRepository);
        data = LocalDate.now();
        view = new CalculoMargemPrecoView();
        configuraView();
    }
    
    private void configuraView(){
        view.setVisible(false);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = data.format(formatter);
        view.getCmbData().addItem(dataFormatada);
        view.getCmbData().setSelectedItem(dataFormatada);
        listarProdutosNaTabela();
        view.getBtnCalcular().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    calcular();
                } 
                catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        view.setVisible(true);
    }
    
    private void listarProdutosNaTabela(){
        DefaultTableModel modeloTabela = (DefaultTableModel) view.getTblProdutosCalculados().getModel();
        modeloTabela.setNumRows(0);
        
        for (Produto produto : produtoRepository.listar()) {
            modeloTabela.addRow(new Object[]{
                produto.getNomeProduto(),
                produto.getPrecoCusto(),
                produto.getCategoria(),
                produto.getMargemLucro(),
                produto.getPrecoVenda()
            });
        }
    }
    
    private void calcular(){
        boolean podeCalcular = calculoMargem.calcularPrecos();
        if (!podeCalcular) {
            JOptionPane.showMessageDialog(view, "Ainda se nao passaram 10 dias desde o ultimo calculo.");
            return;
        }
        listarProdutosNaTabela();
    }
}
