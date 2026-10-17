package br.ufes.presenter;

import br.ufes.model.Cliente;
import br.ufes.model.Usuario;
import br.ufes.repository.IClienteRepository;
import br.ufes.repository.IUsuarioRepository;
import br.ufes.service.UsuarioService;
import br.ufes.state.UsuarioState;
import br.ufes.state.VisualizacaoUsuarioState;
import br.ufes.strategy.IPerfilUsuarioStrategy;
import br.ufes.strategy.UsuarioAdministradorStrategy;
import br.ufes.strategy.UsuarioAtendenteStrategy;
import br.ufes.strategy.UsuarioClienteStrategy;
import br.ufes.view.UsuarioView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

//usuarioPresenter mantem a tela de usuarios no mesmo padrao mestre-detalhe de categorias e clientes
//a manutencao vale apenas para usuarios nao administradores, por isso as acoes ficam indisponiveis quando o administrador esta selecionado
//a senha nunca eh apresentada no modo de visualizacao e, na edicao, uma senha vazia mantem a senha ja cadastrada
//o status eh alterado somente pelos botoes habilitar e desabilitar
//o botao incluir cliente abre a tela de clientes em modo de inclusao e, ao fechar, a lista de clientes associados eh atualizada

public class UsuarioPresenter {
    private UsuarioView view;
    private Usuario usuario;
    private IUsuarioRepository usuarioRepository;
    private IClienteRepository clienteRepository;
    private UsuarioService usuarioService;
    private UsuarioState modo;
    private boolean senhaVisivel;

    public UsuarioPresenter(IUsuarioRepository usuarioRepository, IClienteRepository clienteRepository){
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
        this.usuarioService = new UsuarioService(usuarioRepository);
        this.view = new UsuarioView();
        configuraView();
        setModo(new VisualizacaoUsuarioState(this));
    }

    //alterna entre os estados
    public void setModo(UsuarioState modo) {
        this.modo = modo;
    }

    private void configuraView() {
        view.setVisible(false);
        listarPerfis();
        listarClientes();
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
        view.getBtnHabilitar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    habilitar();
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        view.getBtnDesabilitar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    desabilitar();
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
                    modo.cancelar();
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
        view.getTblUsuariosCadastrados().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                try{
                    int linha = view.getTblUsuariosCadastrados().getSelectedRow();
                    if (linha == -1) {
                        return;
                    }
                    usuario = usuarioService.listar().get(linha);
                    modo.visualizar();
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        view.setVisible(true);
    }

    private void salvar(){
        String nome = view.getTxtNomeCompleto().getText();
        String nomeUsuario = view.getTxtNomeUsuario().getText();
        String email = view.getTxtEmail().getText();
        String senha = new String(view.getTxtSenha().getPassword());
        String confirmacaoSenha = new String(view.getTxtConfirmarSenha().getPassword());
        IPerfilUsuarioStrategy perfil = perfilSelecionado();
        Cliente clienteAssociado = (Cliente) view.getCmbClienteAssociado().getSelectedItem();

        if (!senha.equals(confirmacaoSenha)) {
            throw new IllegalArgumentException("As senhas nao conferem.");
        }
        if (perfil != null && !perfil.perfilUsuario().equals("Cliente")) {
            clienteAssociado = null;
        }
        //falta dizer ao selecionar um cliente associado quando nao for cliente
        if (usuario == null) {
            usuarioService.salvar(nome, nomeUsuario, email, senha, perfil, clienteAssociado);
        }
        else {
            usuarioService.editar(usuario, nome, nomeUsuario, email, senha, perfil, clienteAssociado);
        }
        JOptionPane.showMessageDialog(view, "Usuario: " + nomeUsuario + " salvo com sucesso");
        listar();
        setModo(new VisualizacaoUsuarioState(this));
    }

    private void editar(){
        int linha = view.getTblUsuariosCadastrados().getSelectedRow();
        if (linha == -1) {
            throw new IllegalArgumentException("Selecione um usuario na tabela para editar.");
        }
        usuario = usuarioService.listar().get(linha);
        if (usuario.isAdministrador()) {
            throw new IllegalArgumentException("O registro do Administrador nao pode ser alterado.");
        }
        modo.editar();
    }

    private void excluir(){
        int linha = view.getTblUsuariosCadastrados().getSelectedRow();
        if (linha == -1) {
            throw new IllegalArgumentException("Selecione um usuario na tabela para excluir.");
        }
        Usuario usuarioLista = usuarioService.listar().get(linha);
        int confirmacao = JOptionPane.showConfirmDialog(view, "Deseja realmente excluir o usuario " + usuarioLista.getNomeUsuario(), "Confirmacao de Exclusao", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION){
            usuarioService.excluir(usuarioLista);
            JOptionPane.showMessageDialog(view, "Usuario: " + usuarioLista.getNomeUsuario() + " excluido com sucesso");
            usuario = null;
            listar();
        }
        else if(confirmacao == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(view, "Exclusao cancelada");
        }
        setModo(new VisualizacaoUsuarioState(this));
    }

    private void habilitar(){
        int linha = view.getTblUsuariosCadastrados().getSelectedRow();
        if (linha == -1) {
            throw new IllegalArgumentException("Selecione um usuario na tabela para habilitar.");
        }
        usuario = usuarioService.listar().get(linha);
        usuarioService.habilitar(usuario);
        JOptionPane.showMessageDialog(view, "Usuario: " + usuario.getNomeUsuario() + " habilitado");
        listar();
        modo.visualizar();
    }

    private void desabilitar(){
        int linha = view.getTblUsuariosCadastrados().getSelectedRow();
        if (linha == -1) {
            throw new IllegalArgumentException("Selecione um usuario na tabela para desabilitar.");
        }
        usuario = usuarioService.listar().get(linha);
        usuarioService.desabilitar(usuario);
        JOptionPane.showMessageDialog(view, "Usuario: " + usuario.getNomeUsuario() + " desabilitado");
        listar();
        modo.visualizar();
    }

    private IPerfilUsuarioStrategy perfilSelecionado(){
        String perfil = (String) view.getCmbPerfil().getSelectedItem();
        if (perfil == null) {
            return null;
        }
        if(perfil.equals("Administrador")){
            return new UsuarioAdministradorStrategy();
        }
        if(perfil.equals("Cliente")){
            return new UsuarioClienteStrategy();
        }
        if(perfil.equals("Atendente")){
            return new UsuarioAtendenteStrategy();
        }
        return null;
    }

    private void fechar(){
        view.dispose();
    }

    private void listarPerfis(){
        view.getCmbPerfil().removeAllItems();
        view.getCmbPerfil().addItem(new UsuarioAdministradorStrategy().perfilUsuario());
        view.getCmbPerfil().addItem(new UsuarioAtendenteStrategy().perfilUsuario());
        view.getCmbPerfil().addItem(new UsuarioClienteStrategy().perfilUsuario());
    }

    public void listarClientes(){
        view.getCmbClienteAssociado().removeAllItems();
        for (Cliente cliente : clienteRepository.listar()) {
            view.getCmbClienteAssociado().addItem(cliente);
        }
        view.getCmbClienteAssociado().setSelectedIndex(-1);
    }

    private void listar(){
        DefaultTableModel modelo = (DefaultTableModel) view.getTblUsuariosCadastrados().getModel();
        modelo.setRowCount(0);
        for (Usuario usuarioLista : usuarioService.listar()) {
            modelo.addRow(new Object[]{usuarioLista.getNome(), usuarioLista.getEmail(), usuarioLista.getNomeUsuario(), usuarioLista.getPerfil().perfilUsuario(), descreverStatus(usuarioLista), descreverClienteAssociado(usuarioLista)});
        }
    }

    private String descreverStatus(Usuario usuarioLista){
        if (usuarioLista.isStatus()) {
            return "Habilitado";
        }
        return "Desabilitado";
    }

    private String descreverClienteAssociado(Usuario usuarioLista){
        if (usuarioLista.getClienteAssociado() == null) {
            return "";
        }
        return usuarioLista.getClienteAssociado().getNomeCliente();
    }

    public void modoInclusao(){
        usuario = null;
        limparConteudoView();
        view.getLblModo().setText("Modo: Inclusao");
        view.getTxtStatus().setText("Habilitado");
        habilitarCampos(true);
        view.getBtnSalvar().setEnabled(true);
        view.getBtnCancelar().setEnabled(true);
        view.getBtnNovo().setEnabled(false);
        view.getBtnEditar().setEnabled(false);
        view.getBtnExcluir().setEnabled(false);
        view.getBtnHabilitar().setEnabled(false);
        view.getBtnDesabilitar().setEnabled(false);
        view.getBtnFechar().setEnabled(false);
    }

    public void modoEdicao(){
        view.getLblModo().setText("Modo: Edicao");
        carregarDadosUsuario();
        habilitarCampos(true);
        view.getBtnSalvar().setEnabled(true);
        view.getBtnCancelar().setEnabled(true);
        view.getBtnNovo().setEnabled(false);
        view.getBtnEditar().setEnabled(false);
        view.getBtnExcluir().setEnabled(false);
        view.getBtnHabilitar().setEnabled(false);
        view.getBtnDesabilitar().setEnabled(false);
        view.getBtnFechar().setEnabled(false);
    }

    public void modoVisualizacao(){
        view.getLblModo().setText("Modo: Visualizacao");
        if (usuario == null) {
            limparConteudoView();
        }
        else {
            carregarDadosUsuario();
        }
        habilitarCampos(false);
        view.getBtnSalvar().setEnabled(false);
        view.getBtnCancelar().setEnabled(false);
        view.getBtnNovo().setEnabled(true);
        view.getBtnFechar().setEnabled(true);
        habilitarAcoesDoUsuarioSelecionado();
    }

    private void habilitarAcoesDoUsuarioSelecionado(){
        boolean naoAdministrador = usuario != null && !usuario.isAdministrador();
        view.getBtnEditar().setEnabled(naoAdministrador);
        view.getBtnExcluir().setEnabled(naoAdministrador);
        view.getBtnHabilitar().setEnabled(naoAdministrador && !usuario.isStatus());
        view.getBtnDesabilitar().setEnabled(naoAdministrador && usuario.isStatus());
    }

    private void habilitarCampos(boolean habilitado){
        view.getTxtNomeCompleto().setEnabled(habilitado);
        view.getTxtEmail().setEnabled(habilitado);
        view.getTxtNomeUsuario().setEnabled(habilitado);
        view.getTxtSenha().setEnabled(habilitado);
        view.getTxtConfirmarSenha().setEnabled(habilitado);
        view.getCmbPerfil().setEnabled(habilitado);
        view.getCmbClienteAssociado().setEnabled(habilitado);
    }

    private void carregarDadosUsuario(){
        view.getTxtNomeCompleto().setText(usuario.getNome());
        view.getTxtEmail().setText(usuario.getEmail());
        view.getTxtNomeUsuario().setText(usuario.getNomeUsuario());
        view.getCmbPerfil().setSelectedItem(usuario.getPerfil().perfilUsuario());
        view.getTxtStatus().setText(descreverStatus(usuario));
        view.getCmbClienteAssociado().setSelectedItem(usuario.getClienteAssociado());
    }

    private void limparConteudoView(){
        view.getTxtNomeCompleto().setText("");
        view.getTxtEmail().setText("");
        view.getTxtNomeUsuario().setText("");
        view.getTxtSenha().setText("");
        view.getTxtConfirmarSenha().setText("");
        view.getTxtStatus().setText("");
        view.getCmbPerfil().setSelectedItem(new UsuarioAtendenteStrategy().perfilUsuario());
        view.getCmbClienteAssociado().setSelectedIndex(-1);
    }
}
