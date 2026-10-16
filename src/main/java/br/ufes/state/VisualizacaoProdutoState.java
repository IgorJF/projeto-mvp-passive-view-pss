package br.ufes.state;

import br.ufes.presenter.ProdutoPresenter;

public class VisualizacaoProdutoState extends ProdutoState{
    public VisualizacaoProdutoState(ProdutoPresenter produtoPresenter) {
        super(produtoPresenter);
        visualizar();
    }
    
    @Override
    public void visualizar() {
        produtoPresenter.modoVisualizacao();
    }
    
    @Override
    public void novo() {
        produtoPresenter.setModo(new InclusaoProdutoState(produtoPresenter));
    }
    
    @Override
    public void editar() {
        produtoPresenter.setModo(new EdicaoProdutoState(produtoPresenter));
    }
}
