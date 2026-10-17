package br.ufes.presenter;

import br.ufes.model.Cliente;
import br.ufes.repository.IClienteRepository;
import br.ufes.repository.IUsuarioRepository;
import br.ufes.service.ClienteService;
import br.ufes.state.ClienteState;
import br.ufes.state.VisualizacaoClienteState;
import br.ufes.strategy.TipoClientePrata;
import br.ufes.view.ClienteView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

//clientePresenter inicia a tela de clientes no modo de visualizacao, seguindo o mesmo padrao mestre-detalhe de categorias
//a selecao de uma linha da tabela carrega o cliente no formulario superior e devolve a tela para o modo de visualizacao
//o tipo de cliente e o total de compras sao apenas apresentados, por isso nunca sao liberados para edicao
//acaoAoFechar permite que a tela de usuarios atualize a lista de clientes depois de uma inclusao feita por ela

public class ClientePresenter {
    private ClienteView view;
    private Cliente cliente;
    private ClienteService clienteService;
    private ClienteState modo;
    
    public ClientePresenter(IClienteRepository clienteRepository, IUsuarioRepository usuarioRepository){
        this.clienteService = new ClienteService(clienteRepository, usuarioRepository);
        this.view = new ClienteView();
        configuraView();
        setModo(new VisualizacaoClienteState(this));
    }

    //alterna entre os estados
    public void setModo(ClienteState modo) {
        this.modo = modo;
    }

    //abre a tela diretamente no modo de inclusao, usado pela tela de usuarios
    public void incluir() {
        modo.novo();
    }

    private void configuraView() {
        view.setVisible(false);
        listar();
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
        view.getTblClientesCadastrados().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int linha = view.getTblClientesCadastrados().getSelectedRow();
                if (linha == -1) {
                    return;
                }
                cliente = clienteService.listar().get(linha);
                modo.visualizar();
            }
        });
        view.setVisible(true);
    }

    private void salvar(){
        String nomeCliente = view.getTxtNomeCliente().getText();
        String logradouro = view.getTxtLogradouro().getText();
        String bairro = view.getTxtBairro().getText();
        String cidade = view.getTxtCidade().getText();
        String uf = view.getTxtUf().getText();

        if (cliente == null) {
            Cliente clienteSalvo = clienteService.salvar(nomeCliente, logradouro, bairro, cidade, uf);
            JOptionPane.showMessageDialog(view, "Cliente: " + clienteSalvo.getNomeCliente() + " salvo com sucesso");
        }
        else {
            clienteService.editar(cliente, nomeCliente, logradouro, bairro, cidade, uf);
            JOptionPane.showMessageDialog(view, "Cliente: " + cliente.getNomeCliente() + " salvo com sucesso");
        }
        listar();
        setModo(new VisualizacaoClienteState(this));
    }

    private void editar(){
        int linha = view.getTblClientesCadastrados().getSelectedRow();
        if (linha == -1) {
            throw new IllegalArgumentException("Selecione um cliente na tabela para editar.");
        }
        cliente = clienteService.listar().get(linha);
        modo.editar();
    }

    private void excluir(){
        int linha = view.getTblClientesCadastrados().getSelectedRow();
        if (linha == -1) {
            throw new IllegalArgumentException("Selecione um cliente na tabela para excluir.");
        }
        Cliente clienteLista = clienteService.listar().get(linha);
        clienteService.validarExclusao(clienteLista);
        int confirmacao = JOptionPane.showConfirmDialog(view, "Deseja realmente excluir o cliente " + clienteLista.getNomeCliente(), "Confirmacao de Exclusao", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION){
            clienteService.excluir(clienteLista);
            JOptionPane.showMessageDialog(view, "Cliente: " + clienteLista.getNomeCliente() + " excluido com sucesso");
            cliente = null;
            listar();
        }
        else if(confirmacao == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(view, "Exclusao cancelada");
        }
        setModo(new VisualizacaoClienteState(this));
    }

    private void cancelar(){
        modo.cancelar();
    }

    private void fechar(){
        view.dispose();
    }

    private void listar(){
        DefaultTableModel modelo = (DefaultTableModel) view.getTblClientesCadastrados().getModel();
        modelo.setRowCount(0);
        for (Cliente clienteLista : clienteService.listar()) {
            modelo.addRow(new Object[]{clienteLista.getNomeCliente(), clienteLista.getLogradouro(), clienteLista.getBairro(), clienteLista.getCidade(), clienteLista.getUf(), clienteLista.getTipoCliente().tipoAtualCliente(), clienteService.calcularTotalCompras(clienteLista)});
        }
    }

    public void modoInclusao(){
        cliente = null;
        limparConteudoView();
        view.getLblModo().setText("Modo: Inclusao");
        view.getTxtTotalCompras().setText(String.format("%.2f", 0.0));
        habilitarCampos(true);
        view.getBtnSalvar().setEnabled(true);
        view.getBtnCancelar().setEnabled(true);
        view.getBtnNovo().setEnabled(false);
        view.getBtnEditar().setEnabled(false);
        view.getBtnExcluir().setEnabled(false);
        view.getBtnFechar().setEnabled(false);
    }

    public void modoEdicao(){
        view.getLblModo().setText("Modo: Edicao");
        carregarDadosCliente();
        habilitarCampos(true);
        view.getBtnSalvar().setEnabled(true);
        view.getBtnCancelar().setEnabled(true);
        view.getBtnNovo().setEnabled(false);
        view.getBtnEditar().setEnabled(false);
        view.getBtnExcluir().setEnabled(false);
        view.getBtnFechar().setEnabled(false);
    }

    public void modoVisualizacao(){
        view.getLblModo().setText("Modo: Visualizacao");
        if (cliente == null) {
            limparConteudoView();
        }
        else {
            carregarDadosCliente();
        }
        habilitarCampos(false);
        view.getBtnSalvar().setEnabled(false);
        view.getBtnCancelar().setEnabled(false);
        view.getBtnNovo().setEnabled(true);
        view.getBtnEditar().setEnabled(true);
        view.getBtnExcluir().setEnabled(true);
        view.getBtnFechar().setEnabled(true);
    }

    private void habilitarCampos(boolean habilitado){
        view.getTxtNomeCliente().setEnabled(habilitado);
        view.getTxtLogradouro().setEnabled(habilitado);
        view.getTxtBairro().setEnabled(habilitado);
        view.getTxtCidade().setEnabled(habilitado);
        view.getTxtUf().setEnabled(habilitado);
    }

    private void carregarDadosCliente(){
        view.getTxtNomeCliente().setText(cliente.getNomeCliente());
        view.getTxtLogradouro().setText(cliente.getLogradouro());
        view.getTxtBairro().setText(cliente.getBairro());
        view.getTxtCidade().setText(cliente.getCidade());
        view.getTxtUf().setText(cliente.getUf());
        view.getTxtTipoCliente().setText(cliente.getTipoCliente().tipoAtualCliente());
        view.getTxtTotalCompras().setText(String.format("%.2f", clienteService.calcularTotalCompras(cliente)));
    }

    private void limparConteudoView(){
        view.getTxtNomeCliente().setText("");
        view.getTxtLogradouro().setText("");
        view.getTxtBairro().setText("");
        view.getTxtCidade().setText("");
        view.getTxtUf().setText("");
        view.getTxtTipoCliente().setText("");
        view.getTxtTotalCompras().setText("");
    }
}
