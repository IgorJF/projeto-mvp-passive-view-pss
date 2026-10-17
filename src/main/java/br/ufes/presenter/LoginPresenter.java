package br.ufes.presenter;

import br.ufes.model.Usuario;
import br.ufes.repository.ICategoriaRepository;
import br.ufes.repository.IClienteRepository;
import br.ufes.repository.IHistoricoPrecoRepository;
import br.ufes.repository.IProdutoRepository;
import br.ufes.repository.IUsuarioRepository;
import br.ufes.service.AutenticacaoSenhaUsuarioService;
import br.ufes.view.LoginView;
import br.ufes.view.TelaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

//a tela de autenticacao eh o ponto de entrada do sistema e aparece centralizada na area de trabalho
//a identificacao informada eh usada como nome de usuario ou e-mail, e somente um usuario habilitado chega a tela principal
//fechar encerra a aplicacao sem iniciar uma sessao

public class LoginPresenter {
    private LoginView view;
    private AutenticacaoSenhaUsuarioService autenticacaoUsuarioService;
    private ICategoriaRepository categoriaRepository;
    private IProdutoRepository produtoRepository;
    private IHistoricoPrecoRepository historicoRepository;
    private IClienteRepository clienteRepository;
    private IUsuarioRepository usuarioRepository;

    public LoginPresenter(AutenticacaoSenhaUsuarioService autenticacaoUsuarioService, ICategoriaRepository categoriaRepository,  IProdutoRepository produtoRepository, IHistoricoPrecoRepository historicoRepository, IClienteRepository clienteRepository, IUsuarioRepository usuarioRepository){
        this.autenticacaoUsuarioService = autenticacaoUsuarioService;
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
        this.historicoRepository = historicoRepository;
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
        this.view = new LoginView();
        configuraView();
    }

    private void configuraView() {
        view.setVisible(false);
        view.getBtnEntrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    entrar();
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
        view.setVisible(true);
    }

    private void entrar(){
        String login = view.getTxtUsuario().getText();
        String senha = new String(view.getTxtSenha().getPassword());

        if(login.isBlank()){
            throw new IllegalArgumentException("Informe o usuario ou e-mail.");
        }
        if(senha.isBlank()){
            throw new IllegalArgumentException("Informe a senha.");
        }

        Usuario usuarioLogado = autenticacaoUsuarioService.autenticarSenhaUsuario(login, senha);
        if (usuarioLogado != null) {
            new TelaPrincipalPresenter(usuarioLogado, autenticacaoUsuarioService, categoriaRepository, produtoRepository, historicoRepository, clienteRepository, usuarioRepository);
            fechar();
        }
    }

    private void fechar(){
        view.dispose();
    }
}
