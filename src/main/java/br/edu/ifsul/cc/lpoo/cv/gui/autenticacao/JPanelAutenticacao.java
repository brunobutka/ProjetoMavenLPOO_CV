
package br.edu.ifsul.cc.lpoo.cv.gui.autenticacao;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import br.edu.ifsul.cc.lpoo.cv.util.Util;
import java.awt.Color;
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
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
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
    private Border defaultBorder;
    
    // Construtor da classe que recebe um parametro.
    public JPanelAutenticacao(Controle controle) {
        this.controle = controle;
        
        initComponents();
        
    }
    
    private void initComponents() {
        gridLayout = new GridBagLayout(); // Inicializando o gerenciador de layout.
        this.setLayout(gridLayout); // Definie o gerenciador para este painel.
        
        lblCpf = new JLabel("CPF: "); 
        lblCpf.setToolTipText("lblCpf"); // Acessibilidade.
        posicionador = new GridBagConstraints();
        posicionador.gridy = 0; // Posição da linha (vertical).
        posicionador.gridx = 0; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        this.add(lblCpf, posicionador); // O add adiciona o rotulo no painel.
        
        txfCpf = new JTextField(10);
        txfCpf.setFocusable(true); // Acessibilidade.
        txfCpf.setToolTipText("txfCpf"); // Acessibilidade.
        Util.considerarEnterComoTab(txfCpf);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 0; // Posição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        defaultBorder = txfCpf.getBorder();
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; //Ancoragem a direita.
        this.add(txfCpf, posicionador); // O add adiciona o rotulo no painel.
        
        lblSenha = new JLabel("Senha: ");
        lblSenha.setToolTipText("lblSenha"); // Acessibilidade  
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1; // Posição da linha (vertical).
        posicionador.gridx = 0;// Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        this.add(lblSenha, posicionador); // O add adiciona o rotulo no painel.
        
        psfSenha = new JPasswordField(10);
        psfSenha.setFocusable(true); // Acessibilidade.
        psfSenha.setToolTipText("psfSenha"); // Acessibilidade.
        Util.considerarEnterComoTab(psfSenha);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1; // Posição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; //Ancoragem a direita.
        this.add(psfSenha, posicionador); // O add adiciona o rotulo no painel.

        btnLogar = new JButton("Autenticar");
        btnLogar.setFocusable(true); // Acessibilidade.
        btnLogar.setToolTipText("btnLogar"); // Acessibilidade.
        Util.registraEnterNoBotao(btnLogar);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2; // Posição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        btnLogar.addActionListener(this); // Registrar o botão no Listener.
        btnLogar.setActionCommand("comando_autenticar");
        this.add(btnLogar, posicionador); // O add adiciona o rotulo no painel.

    }
    
    public void requestFocus(){
        txfCpf.requestFocus();
        
    }
    
    public void cleanForm(){
        txfCpf.setText("");
        psfSenha.setText("");        
        
        txfCpf.setBorder(defaultBorder);        
        psfSenha.setBorder(defaultBorder);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {   
        if(e.getActionCommand().equals(btnLogar.getActionCommand())){
            if(txfCpf.getText().trim().length() == 11) {
                txfCpf.setBorder(new LineBorder(Color.green,1));

                if(new String(psfSenha.getPassword()).trim().length() > 3 ) {
                    psfSenha.setBorder(new LineBorder(Color.green,1));

                    controle.autenticar(txfCpf.getText().trim(), new String(psfSenha.getPassword()).trim());

                } else {
                    JOptionPane.showMessageDialog(this, "Informe Senha com 4 ou mais dígitos.", "Autenticação", JOptionPane.ERROR_MESSAGE);
                    psfSenha.setBorder(new LineBorder(Color.red, 1));
                    psfSenha.requestFocus();                        

                }

            } else {
                JOptionPane.showMessageDialog(this, "Informe CPF com 11 dígitos.", "Autenticação", JOptionPane.ERROR_MESSAGE);                    
                txfCpf.setBorder(new LineBorder(Color.red, 1));
                txfCpf.requestFocus();
            }                                      
        }        
    }   
}