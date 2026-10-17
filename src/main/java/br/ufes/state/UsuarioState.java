package br.ufes.state;

import br.ufes.presenter.UsuarioPresenter;

public abstract class UsuarioState {
    protected UsuarioPresenter usuarioPresenter;

    public UsuarioState(UsuarioPresenter usuarioPresenter) {
        this.usuarioPresenter = usuarioPresenter;
    }

    public void visualizar(){
        throw new RuntimeException("Nao e possivel utilizar a operacao de entrar no modo de " + getNomeModo());
    }
    public void novo(){
        throw new RuntimeException("Nao e possivel utilizar a operacao novo no modo de " + getNomeModo());
    }
    public void editar(){
        throw new RuntimeException("Nao e possivel utilizar a operacao editar no modo de " + getNomeModo());
    }
    public void cancelar(){
        throw new RuntimeException("Nao e possivel utilizar a operacao cancelar no modo de " + getNomeModo());
    }

    private String getNomeModo() {
        return this.getClass().getSimpleName();
    }
}
