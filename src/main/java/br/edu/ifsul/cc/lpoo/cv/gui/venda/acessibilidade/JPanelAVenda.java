package br.edu.ifsul.cc.lpoo.cv.gui.venda.acessibilidade;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 *
 * @author bruno
 */

public class JPanelAVenda extends JPanel {
    
    private CardLayout cardLayout;
    private Controle controle;
    
    private JPanelAVendaFormulario formulario;
    private JPanelAVendaListagem listagem;
    
    public JPanelAVenda(Controle controle) {
        
        this.controle = controle;
        initComponents();
    }
    
    private void initComponents() {
        
        cardLayout = new CardLayout();
        this.setLayout(cardLayout);
        
        formulario = new JPanelAVendaFormulario(this, controle);
        listagem = new JPanelAVendaListagem(this, controle);
        
        this.add(formulario, "tela_venda_formulario");
        this.add(listagem, "tela_venda_listagem");
        
        cardLayout.show(this, "tela_venda_listagem");
    }
    
    public void showTela(String nomeTela) {
        
        if(nomeTela.equals("tela_venda_listagem")) {
            
            listagem.populaTable();
            
        } else if(nomeTela.equals("tela_venda_formulario")) {
            
            getFormulario().populaComboPagamento();
            getFormulario().populaComboFuncionario();
            getFormulario().populaComboCliente();
        }
        
        cardLayout.show(this, nomeTela);
    }

    /**
     * @return the controle
     */
    
    public Controle getControle() {
        return controle;
    }
    
    /**
     * @return the formulario
     */
    public JPanelAVendaFormulario getFormulario() {
        return formulario;
    }
    
}