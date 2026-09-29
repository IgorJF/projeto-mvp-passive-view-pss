package br.ufes.seeder;

import br.ufes.model.Categoria;
import br.ufes.model.Produto;
import br.ufes.repository.ICategoriaRepository;
import br.ufes.repository.IProdutoRepository;

public class Seeder {
    public Seeder(ICategoriaRepository categorias, IProdutoRepository produtos){
        criarCategoriasIniciais(categorias);
        criarProdutosIniciais(produtos, categorias);
    }
    
    private void criarCategoriasIniciais(ICategoriaRepository categorias){
        categorias.salvar(new Categoria("Educação", 25.0));
        categorias.salvar(new Categoria("Papelaria", 30.0));
        categorias.salvar(new Categoria("Alimentação", 22.0));
        categorias.salvar(new Categoria("Lazer", 35.0));
        categorias.salvar(new Categoria("Entretenimento", 40.0));
        categorias.salvar(new Categoria("Higiene", 28.0));
        categorias.salvar(new Categoria("Limpeza", 25.0));
    }
    
    private void criarProdutosIniciais(IProdutoRepository produtos, ICategoriaRepository categorias) {
        Categoria educacao = buscarCategoria(categorias, "Educação");
        Categoria papelaria = buscarCategoria(categorias, "Papelaria");
        Categoria alimentacao = buscarCategoria(categorias, "Alimentação");
        Categoria lazer = buscarCategoria(categorias, "Lazer");
        Categoria entretenimento = buscarCategoria(categorias, "Entretenimento");
        Categoria higiene = buscarCategoria(categorias, "Higiene");
        Categoria limpeza = buscarCategoria(categorias, "Limpeza");

        adicionarProduto(produtos, "Livro didático", 45.00, educacao);
        adicionarProduto(produtos, "Livro paradidático", 30.00, educacao);
        adicionarProduto(produtos, "Mochila escolar", 70.00, educacao);

        adicionarProduto(produtos, "Caderno universitário", 16.00, papelaria);
        adicionarProduto(produtos, "Lápis grafite HB", 1.20, papelaria);
        adicionarProduto(produtos, "Caneta esferográfica azul", 2.20, papelaria);
        adicionarProduto(produtos, "Borracha branca", 1.00, papelaria);
        adicionarProduto(produtos, "Apontador com depósito", 3.50, papelaria);

        adicionarProduto(produtos, "Jogo de tabuleiro", 55.00, lazer);
        adicionarProduto(produtos, "Bola recreativa", 40.00, lazer);
        adicionarProduto(produtos, "Quebra-cabeça 500 peças", 35.00, lazer);

        adicionarProduto(produtos, "Fone de ouvido", 48.00, entretenimento);
        adicionarProduto(produtos, "Caixa de som portátil", 80.00, entretenimento);
        adicionarProduto(produtos, "Revista de passatempos", 12.00, entretenimento);

        adicionarProduto(produtos, "Biscoito integral", 5.50, alimentacao);
        adicionarProduto(produtos, "Suco de uva 1 L", 9.00, alimentacao);
        adicionarProduto(produtos, "Barra de cereal", 3.20, alimentacao);

        adicionarProduto(produtos, "Sabonete", 2.80, higiene);
        adicionarProduto(produtos, "Creme dental", 5.50, higiene);

        adicionarProduto(produtos, "Detergente líquido", 2.60, limpeza);
        adicionarProduto(produtos, "Esponja multiuso", 1.70, limpeza);
    }
    
    private Categoria buscarCategoria(ICategoriaRepository categorias, String nome) {
        for (Categoria categoria : categorias.listar()) {
            if (categoria.getNomeCategoria().equalsIgnoreCase(nome)) {
                return categoria;
            }
        }
        return null;
    }
    
    private void adicionarProduto(IProdutoRepository produtos, String nome, double precoCusto, Categoria categoria) {
        if (categoria != null) {
            double margemLucro = categoria.getPercentualLucro();
            double precoVenda = precoCusto * (1 + margemLucro / 100);
            precoVenda = Math.round(precoVenda * 100.0) / 100.0;
            Produto produto = new Produto(nome, precoCusto, categoria, margemLucro, precoVenda);
            produtos.salvar(produto);
        }
    }
}
