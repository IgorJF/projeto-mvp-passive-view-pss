package br.ufes.state;

import br.ufes.presenter.UsuarioPresenter;

public class InclusaoUsuarioState extends UsuarioState{
    public InclusaoUsuarioState(UsuarioPresenter usuarioPresenter) {
        super(usuarioPresenter);
        novo();
    }

    @Override
    public void novo(){
        usuarioPresenter.modoInclusao();
    }

    @Override
    public void visualizar(){
        usuarioPresenter.setModo(new VisualizacaoUsuarioState(usuarioPresenter));
    }

    @Override
    public void cancelar(){
        usuarioPresenter.setModo(new VisualizacaoUsuarioState(usuarioPresenter));
    }
}
