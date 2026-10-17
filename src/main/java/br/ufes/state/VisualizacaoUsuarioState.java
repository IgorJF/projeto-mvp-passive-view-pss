package br.ufes.state;

import br.ufes.presenter.UsuarioPresenter;

public class VisualizacaoUsuarioState extends UsuarioState{
    public VisualizacaoUsuarioState(UsuarioPresenter usuarioPresenter) {
        super(usuarioPresenter);
        visualizar();
    }

    @Override
    public void visualizar() {
        usuarioPresenter.modoVisualizacao();
    }

    @Override
    public void novo() {
        usuarioPresenter.setModo(new InclusaoUsuarioState(usuarioPresenter));
    }

    @Override
    public void editar() {
        usuarioPresenter.setModo(new EdicaoUsuarioState(usuarioPresenter));
    }
}
