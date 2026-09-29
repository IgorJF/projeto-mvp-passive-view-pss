package br.ufes.presenter;

import br.ufes.model.Produto;
import br.ufes.repository.IProdutoRepository;
import br.ufes.view.BuscaProdutoView;

/**
 *
 * @author igorj
 */
public class BuscaProdutoPresenter {
    private BuscaProdutoView view;
    private IProdutoRepository repository;
    private Produto produto;
    
    public BuscaProdutoPresenter(){
        view = new BuscaProdutoView();
    }
    
    
}
