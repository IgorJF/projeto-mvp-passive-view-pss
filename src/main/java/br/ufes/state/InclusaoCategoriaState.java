package br.ufes.state;

import br.ufes.presenter.CategoriaPresenter;

public class InclusaoCategoriaState extends CategoriaState{
    public InclusaoCategoriaState(CategoriaPresenter categoriaPresenter) {
        super(categoriaPresenter);
        novo();
    }
    
    @Override
    public void novo(){
        categoriaPresenter.modoInclusao();
    }
    
    @Override
    public void visualizar(){
        categoriaPresenter.setModo(new VisualizacaoCategoriaState(categoriaPresenter));
    }
    
    @Override
    public void cancelar(){
        categoriaPresenter.setModo(new VisualizacaoCategoriaState(categoriaPresenter));
    }
}


