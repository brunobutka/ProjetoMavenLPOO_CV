
package br.edu.ifsul.cc.lpoo.cv.gui.autenticacao;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author bruno
 */

public class JPanelAutenticacao extends JPanel implements ActionListener {
    
    private Controle controle;
    private GridBagLayout gridLayout;
    private GridBagConstraints posicionador;
    
    private JLabel lblCpf;
    private JLabel lblSenha;
    private JTextField txfCpf;
    private JPasswordField psfSenha;
    private JButton btnLogar;
    
    // Construtor da classe que recebe um parametro.
    public JPanelAutenticacao(Controle controle) {
        
        this.controle = controle;
        initComponents();
        
    }
    
    private void initComponents() {
    
        gridLayout = new GridBagLayout(); // Inicializando o gerenciador de layout.
        this.setLayout(gridLayout); // Definie o gerenciador para este painel.
        
        lblCpf = new JLabel("CPF: ");
        lblCpf.setFocusable(true); // Acessibilidade. 
        lblCpf.setToolTipText("lblCpf"); // Acessibilidade.
        posicionador = new GridBagConstraints();
        posicionador.gridy = 0; // Posição da linha (vertical).
        posicionador.gridx = 0; // Posição da coluna (horizontal).
        this.add(lblCpf, posicionador); // O add adiciona o rotulo no painel.
        
        txfCpf = new JTextField(10);
        txfCpf.setFocusable(true); // Acessibilidade.
        txfCpf.setToolTipText("txfCpf"); // Acessibilidade.
        posicionador = new GridBagConstraints();
        posicionador.gridy = 0; // Posição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        this.add(txfCpf, posicionador); // O add adiciona o rotulo no painel.
        
        lblSenha = new JLabel("Senha: ");
        lblSenha.setFocusable(true); // Acessibilidade    
        lblSenha.setToolTipText("lblSenha"); // Acessibilidade
        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1; // Posição da linha (vertical).
        posicionador.gridx = 0;// Posição da coluna (horizontal).
        this.add(lblSenha, posicionador); // O add adiciona o rotulo no painel.
        
        psfSenha = new JPasswordField(10);
        psfSenha.setFocusable(true); // Acessibilidade.
        psfSenha.setToolTipText("psfSenha"); // Acessibilidade.
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1; // Posição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        this.add(psfSenha, posicionador); // O add adiciona o rotulo no painel.

        btnLogar = new JButton("Autenticar");
        btnLogar.setFocusable(true); // Acessibilidade.
        btnLogar.setToolTipText("btnLogar"); // Acessibilidade.
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2; // Posição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        btnLogar.addActionListener(this); // Registrar o botão no Listener.
        btnLogar.setActionCommand("comando_autenticar");
        this.add(btnLogar, posicionador); // O add adiciona o rotulo no painel.

    }

    @Override
    public void actionPerformed(ActionEvent e) {
                
        // Testa para verificar se o botão btnLogar foi clicado.
        if(e.getActionCommand().equals(btnLogar.getActionCommand())) {

            // Validacao do formulário.
            if(txfCpf.getText().trim().length() > 4 && 
            new String(psfSenha.getPassword()).trim().length() > 3 ) {

                controle.autenticar(txfCpf.getText().trim(), new String(psfSenha.getPassword()).trim());

            } else {
                JOptionPane.showMessageDialog(this, "Informe os dados para CPF e Senha.", 
                                             "Autenticação", JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }
    
}