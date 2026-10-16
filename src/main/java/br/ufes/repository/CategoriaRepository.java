package br.ufes.repository;

import br.ufes.model.Categoria;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//a variavel contadorId eh feita para acompanhar os ultimos indices que foram cadastrados, tentando deixar correspondente ao indice do
//arrayList, por ordem de adicao, o primeiro item cadastrado sera o id = 0, e logo apos seu salvamento, mudara para 1, e o proximo item
//tera id = 1. 
//o metodo de buscar por id ajudara a realizar as operacoes de exclusao, para que ache o id correspondente a categoria que sera selecionada
//ai o metodo de exclusao utiliza iterator para uma exclusao mais segura
//o metodo de editar funciona rodando o vetor categorias e pegando o id de cada cateegoria encontrada, caso seja igual ao da categoria que
//quero editar (passada como parametro), ele faz a substituicao com o set

public class CategoriaRepository implements ICategoriaRepository{
    private List<Categoria> categorias;
    private int contadorId;
    
    public CategoriaRepository(){
        categorias = new ArrayList<>();
        contadorId = 0;
    }
    
    @Override
    public Categoria buscarPorId(int id){
        for (Categoria categoria : categorias) {
            if (categoria.getIdCategoria() == id) {
                return categoria;
            }
        }
        return null;
    }

    @Override
    public void salvar(Categoria categoria){
        if(categoria == null){
            throw new IllegalArgumentException("Categoria nao pode ser nula");
        }
        categoria.setIdCategoria(contadorId);
        categorias.add(categoria);
        contadorId++;  
    }

    @Override
    public void excluir(Categoria categoria){
        if(categoria == null){
            throw new IllegalArgumentException("Categoria nao pode ser vazia");
        }
        Iterator<Categoria> it = categorias.iterator();
        while(it.hasNext()) {
            Categoria categoriaAtual = it.next();
            if (categoriaAtual.getIdCategoria() == categoria.getIdCategoria()) {
                it.remove();
            }
        }
    }

    @Override
    public List<Categoria> listar(){
        return categorias;
    }

    @Override
    public void editar(Categoria categoria){
        if(categoria == null){
            throw new IllegalArgumentException("Categoria nao pode ser nula");
        }
        for (int i = 0; i < categorias.size(); i++) {
            Categoria categoriaSelecionada = categorias.get(i);
            if (categoriaSelecionada.getIdCategoria() == categoria.getIdCategoria()) {
                categorias.set(i, categoria);
            }
        }
    }
}