package br.edu.ifsul.cc.lpoo.cv.gui.produto.acessibilidade;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 *
 * @author bruno
 */

public class JPanelAProduto extends JPanel {
    
    private CardLayout cardLayout;
    private Controle controle;
    
    private JPanelAProdutoFormulario formulario;
    private JPanelAProdutoListagem listagem;
    
    public JPanelAProduto(Controle controle) {
        this.controle = controle;
        initComponents();
    }
    
    private void initComponents() {
        cardLayout = new CardLayout();
        this.setLayout(cardLayout);
        
        formulario = new JPanelAProdutoFormulario(this, controle);
        listagem = new JPanelAProdutoListagem(this, controle);
        
        this.add(getFormulario(), "tela_produto_formulario");
        this.add(listagem, "tela_produto_listagem");
        
        cardLayout.show(this, "tela_produto_listagem");
    }
    
    public void showTela(String nomeTela) {  
        if(nomeTela.equals("tela_produto_listagem")) {
            
            listagem.populaTable();
            
        } else if(nomeTela.equals("tela_produto_formulario")) {
            
            getFormulario().populaComboTipoProduto();
            getFormulario().populaComboFornecedor();
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
    public JPanelAProdutoFormulario getFormulario() {
        return formulario;
    }
    
}