package br.ufes.presenter;

import br.ufes.model.Usuario;
import br.ufes.repository.ICategoriaRepository;
import br.ufes.repository.IHistoricoPrecoRepository;
import br.ufes.repository.IUsuarioRepository;
import br.ufes.repository.IProdutoRepository;
import br.ufes.service.UsuarioService;
import br.ufes.view.LoginView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class LoginPresenter {
    private LoginView view;
    private UsuarioService usuarioService;
    private IUsuarioRepository usuarioRepository;
    private ICategoriaRepository categoriaRepository;
    private IProdutoRepository produtoRepository;
    private IHistoricoPrecoRepository historicoRepository;

    public LoginPresenter(IUsuarioRepository usuarioRepository,ICategoriaRepository categoriaRepository,IProdutoRepository produtoRepository,IHistoricoPrecoRepository historicoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
        this.historicoRepository = historicoRepository;
        this.usuarioService = new UsuarioService(usuarioRepository);
        this.view = new LoginView();
        configuraView();
    }

    private void configuraView(){
        view.setLocationRelativeTo(null);
        view.getBtnEntrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    entrar();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view,ex.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        view.getBtnFechar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                fechar();
            }
        });
        view.setVisible(true);
    }

    private void entrar() {
        String identificacao = view.getTxtUsuarioOuEmail().getText();
        String senha = new String(view.getTxtSenha().getPassword());
        Usuario usuario = usuarioService.autenticar(identificacao,senha);
        if (usuario == null) {
            JOptionPane.showMessageDialog(view, "Usuario ou senha invalidos.", "Falha na autenticacao", JOptionPane.ERROR_MESSAGE);
            return;
        }
        fechar();
        new TelaPrincipalPresenter(categoriaRepository, produtoRepository,historicoRepository);
    }

    private void fechar() {
        view.dispose();
    }
}