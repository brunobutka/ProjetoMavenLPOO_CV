
package br.edu.ifsul.cc.lpoo.cv;

import javax.swing.JOptionPane;

/**
 *
 * @author bruno
 */
public class CVMain {
    
    private Controle controle;
    
    // Construtor.
    public CVMain(){        
        // Estabelecer uma conexao com o banco de dados.
        try {
            controle = new Controle(); // Cria a instancia e atribui para o atributo controle.

            if(controle.conectarBD()){
                controle.initComponents();
            }else{
                JOptionPane.showMessageDialog(null, "Não conectou no Banco de Dados!", "Banco de Dados", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar conectar no Banco de Dados: "+ex.getLocalizedMessage(), "Banco de Dados", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        // Em caso de sucesso - iniciar a interface gráfica.  
    }
    
    public static void main(String[] args){
        new CVMain();              
    } 
}
