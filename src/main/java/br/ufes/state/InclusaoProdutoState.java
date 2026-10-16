package br.ufes.state;

import br.ufes.presenter.ProdutoPresenter;

public class InclusaoProdutoState extends ProdutoState{
    public InclusaoProdutoState(ProdutoPresenter produtoPresenter) {
        super(produtoPresenter);
        novo();
    }
    
    @Override
    public void novo(){
        produtoPresenter.modoInclusao();
    }
    
    @Override
    public void visualizar(){
        produtoPresenter.setModo(new VisualizacaoProdutoState(produtoPresenter));
    }
    
    @Override
    public void cancelar(){
        produtoPresenter.fechar();
    }
}
