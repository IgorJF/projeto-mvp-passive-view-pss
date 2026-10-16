package br.ufes.service;

import br.ufes.model.Categoria;
import br.ufes.model.Produto;
import br.ufes.repository.ICategoriaRepository;
import br.ufes.repository.IProdutoRepository;
import java.util.List;

//salvar apenas serve para salvar uma nova categoria
//excluir recebe o id da categoria e chama o buscaPorId para retornar a categoria e exclui-la
//editar recebe as novas informacoes e usa o metodo editar de repository para trocar na lista
//listar apenas lista as categorias chamando o metodo listar
//o nome da categoria nao pode se repetir (sem diferenciar maiusculas de minusculas)
//uma categoria que possui produtos associados nao pode ser excluida

public class CategoriaService {
    private ICategoriaRepository categoriaRepository;
    private IProdutoRepository produtoRepository;

    public CategoriaService(ICategoriaRepository categoriaRepository, IProdutoRepository produtoRepository){
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
    }

    public void salvar(String nomeCategoria, double percentualLucro){
        if (nomeCategoria == null || nomeCategoria.isBlank()) {
            throw new IllegalArgumentException("O nome da categoria nao pode ser vazio.");
        }
        if (percentualLucro < 0) {
            throw new IllegalArgumentException("O percentual de lucro nao pode ser menor que zero.");
        }
        validarNomeDuplicado(nomeCategoria, null);
        Categoria categoria = new Categoria(nomeCategoria, percentualLucro);
        categoriaRepository.salvar(categoria);
    }

    public void editar(Categoria categoria, String nomeCategoria, double percentualLucro){
        if (categoria == null) {
            throw new IllegalArgumentException("Categoria nao selecionada para edicao.");
        }
        if (nomeCategoria == null || nomeCategoria.isBlank()) {
            throw new IllegalArgumentException("O nome da categoria nao pode ser vazio.");
        }
        if (percentualLucro < 0) {
            throw new IllegalArgumentException("O percentual de lucro nao pode ser menor que zero.");
        }
        validarNomeDuplicado(nomeCategoria, categoria);
        categoria.setNomeCategoria(nomeCategoria);
        categoria.setPercentualLucro(percentualLucro);
        categoriaRepository.editar(categoria);
    }

    //buscar por id é o id interno da categoria, nao o indice do array
    public void excluir(int id){
        if(id < 0){
            throw new IllegalArgumentException("Nao tem categoria com id menor que zero");
        }
        validarExclusao(id);
        Categoria categoriaBuscada = categoriaRepository.buscarPorId(id);
        categoriaRepository.excluir(categoriaBuscada);
    }

    //lanca excecao se existir algum produto associado a categoria
    public void validarExclusao(int id){
        for (Produto produto : produtoRepository.listar()) {
            if (produto.getCategoria().getIdCategoria() == id) {
                throw new IllegalArgumentException("Existem produtos associados a esta categoria. Ela nao pode ser excluida.");
            }
        }
    }

    public List<Categoria> listar(){
        return categoriaRepository.listar();
    }

    //categoriaAtual eh a propria categoria sendo editada, que pode manter o mesmo nome
    private void validarNomeDuplicado(String nomeCategoria, Categoria categoriaAtual){
        String nomeNovo = nomeCategoria.trim();
        for (Categoria existente : categoriaRepository.listar()) {
            if (existente == categoriaAtual) {
                continue;
            }
            if (existente.getNomeCategoria().trim().equalsIgnoreCase(nomeNovo)) {
                throw new IllegalArgumentException("Ja existe uma categoria com o nome " + nomeNovo + ".");
            }
        }
    }
}
