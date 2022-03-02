
package br.edu.ifsul.cc.lpoo.cv.gui.funcionario;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 *
 * @author bruno
 */
public class JPanelFuncionario extends JPanel {
    
    private CardLayout cardLayout;
    private Controle controle;
    
    private JPanelFuncionarioListagem listagem;
    private JPanelFuncionarioFormulario formulario; 
    
    
    public JPanelFuncionario(Controle controle){
        
        this.controle = controle;
        initComponents();
    }
    
    private void initComponents(){
        
        cardLayout = new CardLayout();  //Inicializa o gerenciador de layout
        this.setLayout(cardLayout);     //Define o gerenciador de layout para este painel
        
        listagem = new JPanelFuncionarioListagem(this, controle);
        formulario = new JPanelFuncionarioFormulario(this, controle);
        
        this.add(getListagem(), "tela_funcionario_listagem");    //Adiciona uma carta
        this.add(formulario, "tela_funcionario_formulario");    //Adiciona a segunda carta do baralho
        
        cardLayout.show(this, "tela_funcionario_listagem"); //Por padrão mostra o painel de listagem
        
    }
    
    public void showTela(String nomeTela){
        
        if(nomeTela.equals("tela_funcionario_listagem")){
            
            listagem.populaTable();
            
        }else if(nomeTela.equals("tela_funcionario_formulario")){
            
            //getFormulario().populaComboEndereco();
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
    public JPanelFuncionarioFormulario getFormulario() {
        return formulario;
    }

    /**
     * @return the listagem
     */
    public JPanelFuncionarioListagem getListagem() {
        return listagem;
    }
    
    
    
}