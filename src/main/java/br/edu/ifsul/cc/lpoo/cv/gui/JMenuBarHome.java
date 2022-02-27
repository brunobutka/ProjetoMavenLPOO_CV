
package br.edu.ifsul.cc.lpoo.cv.gui;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author bruno
 */

public class JMenuBarHome extends JMenuBar implements ActionListener {
    
    private JMenu menuArquivo;
    private JMenuItem menuItemSair;

    private JMenu menuCadastro;
    private JMenuItem menuItemFuncionario;    

    private Controle controle;
    
    public JMenuBarHome(Controle controle) {
        
        this.controle = controle;        
        
        initComponents();
    }
    
    private void initComponents() {
        
        menuArquivo = new JMenu("Arquivo");
        menuArquivo.setMnemonic(KeyEvent.VK_A); // Ativa o ALT + A para acessar esse menu - acessibilidade.
        menuArquivo.setToolTipText("Arquivo"); // Acessibilidade.
        menuArquivo.setFocusable(true); // Acessibilidade.

                
        menuItemSair = new JMenuItem("Sair");
        menuItemSair.setToolTipText("Sair"); // Acessibilidade.
        menuItemSair.setFocusable(true); // Acessibilidade.

        menuItemSair.addActionListener(this);
        menuItemSair.setActionCommand("menu_sair");
        menuArquivo.add(menuItemSair);

        menuCadastro = new JMenu("Cadastros");
        menuCadastro.setMnemonic(KeyEvent.VK_C); // Ativa o ALT + C para acessar esse menu - acessibilidade.
        menuCadastro.setToolTipText("Cadastro"); // Acessibilidade.
        menuCadastro.setFocusable(true); // Acessibilidade.
        
        menuItemFuncionario = new JMenuItem("Funcionário");
        menuItemFuncionario.setToolTipText("Funcionário"); // Acessibilidade.
        menuItemFuncionario.setFocusable(true); // Acessibilidade.

        menuItemFuncionario.addActionListener(this);
        menuItemFuncionario.setActionCommand("menu_Funcionario");
        menuCadastro.add(menuItemFuncionario);   

        this.add(menuArquivo);
        this.add(menuCadastro);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getActionCommand().equals(menuItemSair.getActionCommand())){
        
            // Se o usuario clicou no menuitem Sair.
            int d = JOptionPane.showConfirmDialog(this, "Deseja realmente sair do sistema? ", "Sair", 
                                                 JOptionPane.YES_NO_OPTION);
            if(d == 0) {                
                controle.fecharBD(); // Fecha a conexao com o banco de dados.
                System.exit(0); // Finaliza o processo do programa.
            }
            
        } else if(e.getActionCommand().equals(menuItemFuncionario.getActionCommand())) {
            
            // Se o usuario clicou no menuitem Usuario.
            controle.showTela("tela_funcionario");
            
        }
        
    }
       
}