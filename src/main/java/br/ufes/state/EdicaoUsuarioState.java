package br.ufes.state;

import br.ufes.presenter.UsuarioPresenter;

public class EdicaoUsuarioState extends UsuarioState{
    public EdicaoUsuarioState(UsuarioPresenter usuarioPresenter) {
        super(usuarioPresenter);
        editar();
    }

    @Override
    public void editar(){
        usuarioPresenter.modoEdicao();
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
