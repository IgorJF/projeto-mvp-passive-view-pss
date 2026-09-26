package br.ufes.presenter;

import br.ufes.view.TelaPrincipal;

public class TelaPrincipalPresenter {
    private TelaPrincipal telaPrincipal;
    
    public TelaPrincipalPresenter(){
        telaPrincipal = new TelaPrincipal();
        configuraView();
    }
    
    private void configuraView() {
        telaPrincipal.setVisible(false);
        telaPrincipal.setVisible(true);
    }
}
