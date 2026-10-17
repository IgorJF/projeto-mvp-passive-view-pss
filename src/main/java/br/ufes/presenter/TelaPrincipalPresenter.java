package br.ufes.presenter;

import br.ufes.model.Usuario;
import br.ufes.repository.ICategoriaRepository;
import br.ufes.repository.IClienteRepository;
import br.ufes.repository.IProdutoRepository;
import br.ufes.repository.IUsuarioRepository;
import br.ufes.service.AutenticacaoSenhaUsuarioService;
import br.ufes.view.TelaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import br.ufes.repository.IHistoricoPrecoRepository;
import br.ufes.state.InclusaoClienteState;

//a tela principal eh aberta depois de uma autenticacao concluida e inicia maximizada
//apresenta a identificacao do usuario corrente e disponibiliza apenas as opcoes compatieis com o perfil autenticado
//a manutencao de usuarios fica indisponivel para quem nao pode gerenciar usuarios
//sair encerra a sessao corrente e devolve a tela de autenticacao centralizada

public class TelaPrincipalPresenter {
    private TelaPrincipal view;
    private Usuario usuarioLogado;
    private AutenticacaoSenhaUsuarioService autenticacaoUsuarioService;
    private ICategoriaRepository categoriaRepository;
    private IProdutoRepository produtoRepository;
    private IHistoricoPrecoRepository historicoRepository;
    private IClienteRepository clienteRepository;
    private IUsuarioRepository usuarioRepository;

    public TelaPrincipalPresenter(Usuario usuarioLogado, AutenticacaoSenhaUsuarioService autenticacaoUsuarioService, ICategoriaRepository categoriaRepository, IProdutoRepository produtoRepository, IHistoricoPrecoRepository historicoRepository, IClienteRepository clienteRepository, IUsuarioRepository usuarioRepository){
        this.view = new TelaPrincipal();
        this.usuarioLogado = usuarioLogado;
        this.autenticacaoUsuarioService = autenticacaoUsuarioService;
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
        this.historicoRepository = historicoRepository;
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
        configuraView();
    }

    private void configuraView(){
        apresentarUsuario();
        aplicarPermissoes();

        view.getjMenuItemCategorias().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    abrirCategorias();
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });

        view.getjMenuItemIncluirProdutos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    new ProdutoPresenter(produtoRepository, categoriaRepository, historicoRepository);
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });

        view.getjMenuItemBuscarProdutos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    abrirBuscaProdutos();
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });

        view.getjMenuItemCalcularLucro().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    new CalculoMargemPrecoPresenter(produtoRepository, historicoRepository);
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });

        view.getjMenuItemClientes().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    abrirClientes();
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });

        view.getjMenuItemManterUsuarios().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    abrirUsuarios();
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });

        /*view.getjMenuItemEntregadores().addActionListener(new ActionListener() {;
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });

        view.getjMenuItemTaxasEntrega().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });*/

        view.getjMenuItemSair().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    sair();
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        
        view.getBtnNovoCliente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    botaoAtalhoNovoCliente();
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        
        view.getBtnNovoProduto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    botaoAtalhoNovoProduto();
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        
        view.getBtnBuscarClientes().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    abrirClientes();
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        view.setVisible(true);
    }
    
    private void botaoAtalhoNovoProduto(){
        new ProdutoPresenter(produtoRepository, categoriaRepository, historicoRepository);
    }
    
    private void botaoAtalhoNovoCliente(){
        ClientePresenter telaCliente = new ClientePresenter(clienteRepository, usuarioRepository);
        telaCliente.setModo(new InclusaoClienteState(telaCliente));
    }
    
    
    private void apresentarUsuario(){
        view.getLblUsuario().setText("Usuário: " + usuarioLogado.getPerfil().perfilUsuario());
    }

    //a navegacao para usuarios respeita o perfil autenticado
    private void aplicarPermissoes(){
        boolean podeGerenciarUsuarios = usuarioLogado.getPerfil().podeGerenciarUsuarios();
        view.getjMenuUsuarios().setVisible(podeGerenciarUsuarios);
    }

    private void abrirCategorias(){
        new CategoriaPresenter(categoriaRepository, produtoRepository);
    }

    private void abrirBuscaProdutos(){
        new BuscaProdutoPresenter(produtoRepository, categoriaRepository, historicoRepository);
    }

    private void abrirClientes(){
        new ClientePresenter(clienteRepository, usuarioRepository);
    }

    private void abrirUsuarios(){
        if (!usuarioLogado.getPerfil().podeGerenciarUsuarios()) {
            throw new IllegalArgumentException("A manutencao de usuarios esta disponivel apenas para o Administrador.");
        }
        new UsuarioPresenter(usuarioRepository, clienteRepository);
    }

    private void sair(){
        view.dispose();
        new LoginPresenter(autenticacaoUsuarioService, categoriaRepository, produtoRepository, historicoRepository, clienteRepository, usuarioRepository);
    }
}
