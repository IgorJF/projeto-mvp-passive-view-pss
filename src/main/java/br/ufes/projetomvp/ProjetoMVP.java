package br.ufes.projetomvp;

import br.projeto.painelpedidos.repositorio.IPedidoRepository;
import br.ufes.presenter.LoginPresenter;
import br.ufes.repository.CategoriaRepository;
import br.ufes.repository.ClienteRepository;
import br.ufes.repository.HistoricoPrecoRepository;
import br.ufes.repository.ICategoriaRepository;
import br.ufes.repository.IClienteRepository;
import br.ufes.repository.IHistoricoPrecoRepository;
import br.ufes.repository.IProdutoRepository;
import br.ufes.repository.IUsuarioRepository;
import br.ufes.repository.PedidoRepository;
import br.ufes.repository.ProdutoRepository;
import br.ufes.repository.UsuarioRepository;
import br.ufes.seeder.Seeder;
import br.ufes.service.AutenticacaoSenhaUsuarioService;

public class ProjetoMVP {
    public static void main(String[] args) {
        ICategoriaRepository categorias = new CategoriaRepository();
        IProdutoRepository produtos = new ProdutoRepository();
        IHistoricoPrecoRepository historico = new HistoricoPrecoRepository();
        IUsuarioRepository usuarios = new UsuarioRepository();
        IClienteRepository clientes = new ClienteRepository();
        IPedidoRepository pedidos = new PedidoRepository();
        Seeder seeder = new Seeder(categorias, produtos, historico, clientes, usuarios, pedidos);
        AutenticacaoSenhaUsuarioService autenticacaoUsuarioService = new AutenticacaoSenhaUsuarioService(usuarios);
        new LoginPresenter(autenticacaoUsuarioService, categorias, produtos, historico, clientes, usuarios, pedidos);
    }
}
