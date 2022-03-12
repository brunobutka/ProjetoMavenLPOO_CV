
package br.edu.ifsul.cc.lpoo.cv.gui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 *
 * @author bruno
 */
public class JFramePrincipal extends JFrame implements WindowListener {
    public CardLayout cardLayout;
    
    public JPanel painel; // Painel.
    
    public JFramePrincipal(){
        initComponents();        
        
    }
    
    
    private void initComponents(){
        // Customização do JFrame
        this.setTitle("Sista para CRUD - Clinica Veterinaria"); // Seta o título do jframe
        
        this.setMinimumSize(new Dimension(600,600)); // Tamanho minimo quando for reduzido.
        
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); // Por padrão abre maximizado.
        
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ); // Finaliza o processo quando o frame é fechado.  
        
        this.addWindowListener(this); // Adiciona o listener no frame
        
        cardLayout = new CardLayout(); // Iniciando o gerenciador de layout para esta JFrame
        painel = new JPanel(); // Inicializacao
                
        painel.setLayout(cardLayout); // Definindo o cardLayout para o paineldeFundo
                
        this.add(painel); // Adiciona no JFrame o paineldeFundo
                
    }
    
    public void addTela(JPanel p, String nome){
        painel.add(p, nome); // Adiciona uma "carta no baralho".
        
    }

    public void showTela(String nome){
        cardLayout.show(painel, nome); // Localiza a "carta no baralho" e mostra.
        
    }

    @Override
    public void windowOpened(WindowEvent we) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void windowClosing(WindowEvent we) {
        System.out.println("Fechando o jframe ..");
      
    }

    @Override
    public void windowClosed(WindowEvent we) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void windowIconified(WindowEvent we) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void windowActivated(WindowEvent we) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
