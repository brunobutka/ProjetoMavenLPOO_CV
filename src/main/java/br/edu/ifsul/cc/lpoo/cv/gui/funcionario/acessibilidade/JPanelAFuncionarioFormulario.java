package br.edu.ifsul.cc.lpoo.cv.gui.funcionario.acessibilidade;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import br.edu.ifsul.cc.lpoo.cv.model.Cargo;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author bruno
 */

public class JPanelAFuncionarioFormulario extends JPanel implements ActionListener{
    private JPanelAFuncionario pnlAFuncionario;
    private Controle controle;
    
    private BorderLayout borderLayout;
    private JTabbedPane tbpAbas;
    
    private JPanel pnlDadosCadastrais;    
    private JPanel pnlCentroDadosCadastrais;
    
    private GridBagLayout gridBagLayoutDadosCadastrais;
    private JLabel lblCpf;
    private JTextField txfCpf;
    
    private JLabel lblRg;
    private JTextField txfRg;
    
    private JLabel lblNome;
    private JTextField txfNome;
    
    private JLabel lblSenha;
    private JPasswordField txfSenha;
    
    private JLabel lblNumero_celular;
    private JTextField txfNumero_celular;
    
    private JLabel lblEmail;
    private JTextField txfEmail;
    
    private JLabel lblCep;
    private JTextField txfCep;
    
    private JLabel lblEndereco;
    private JTextField txfEndereco;
    
    private JLabel lblComplemento;
    private JTextField txfComplemento;
    
    private JLabel lblNumero_ctps;
    private JTextField txfNumero_ctps;
    
    private JLabel lblNumero_pis;
    private JTextField txfNumero_pis;
    
    private JLabel lblData_nascimento;
    private JFormattedTextField txfData_nascimento;
    
    private JLabel lblCargo;
    private JComboBox cbxCargo;
        
    private JLabel lblDataCadastro;
    private JTextField txfDataCadastro;
    
    private Funcionario funcionario;
    private SimpleDateFormat format;
    
    private JPanel pnlSul;
    private JButton btnGravar;
    private JButton btnCancelar;
    
    
    public JPanelAFuncionarioFormulario(JPanelAFuncionario pnlAFuncionario, Controle controle) { 
        this.pnlAFuncionario = pnlAFuncionario;
        this.controle = controle;
        
        initComponents();
        
    }
    
    public void populaComboCargo(){
        cbxCargo.removeAllItems();//zera o combo

        DefaultComboBoxModel model =  (DefaultComboBoxModel) cbxCargo.getModel();

        model.addElement("Selecione"); //primeiro item        
        try {
            model.addElement(Cargo.ADESTRADOR);
            model.addElement(Cargo.ATENDENTE);
            model.addElement(Cargo.AUXILIAR_VETERINARIO);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao listar Cargos -:"+ex.getLocalizedMessage(), "Cargos", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            
        } 
    }
    
    public Funcionario getFuncionariobyFormulario(){
        // Validacao do formulario
         if(txfCpf.getText().trim().length() == 11 && txfRg.getText().trim().length() == 10 &&
            txfNome.getText().trim().length() > 3 && new String(txfSenha.getPassword()).trim().length() > 3 &&
            txfNumero_celular.getText().trim().length() > 7 && txfEmail.getText().trim().length() > 8 && 
            txfCep.getText().trim().length() == 8 && txfNumero_ctps.getText().trim().length() == 8 && 
            txfNumero_pis.getText().trim().length() > 9 && cbxCargo.getSelectedIndex() > 0){

            Funcionario f = new Funcionario();
            
            f.setCpf(txfCpf.getText().trim());   
            f.setRg(txfRg.getText().trim());
            f.setNome(txfNome.getText().trim());
            f.setSenha(new String(txfSenha.getPassword()).trim());
            
            if(funcionario != null)
                f.setData_cadastro(funcionario.getData_cadastro());
            
            f.setNumero_celular(txfNumero_celular.getText().trim());
            f.setEmail(txfEmail.getText().trim());
            f.setCep(txfCep.getText().trim());
            f.setEndereco(txfEndereco.getText().trim());
            f.setComplemento(txfComplemento.getText().trim());
            f.setNumero_ctps(txfNumero_ctps.getText().trim());
            f.setNumero_pis(txfNumero_pis.getText().trim());
            
            String strData = txfData_nascimento.getText();
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
             try {
                 cal.setTime(sdf.parse(strData));
                 f.setData_nascimento(cal);
             } catch (ParseException ex) {
                 Logger.getLogger(JPanelAFuncionarioFormulario.class.getName()).log(Level.SEVERE, null, ex);
             }
                        
            f.setCargo((Cargo) cbxCargo.getSelectedItem());
            
            return f;
         }

         return null;
    }
    
    public void setFuncionarioFormulario(Funcionario f){
        if(f == null){ // Se o parametro estiver nullo, limpa o formulario
            txfCpf.setEditable(true);
            txfCpf.setText("");
            
            txfRg.setEditable(true);
            txfRg.setText("");
            
            txfNome.setEditable(true);
            txfNome.setText("");
            
            txfSenha.setText("");
            
            txfDataCadastro.setText("");
            
            txfNumero_celular.setText("");
            
            txfEmail.setText("");
            
            txfCep.setText("");
            
            txfEndereco.setText("");
            
            txfComplemento.setText("");
            
            txfNumero_ctps.setEditable(true);
            txfNumero_ctps.setText("");
            
            txfNumero_pis.setEditable(true);
            txfNumero_pis.setText("");
            
            txfData_nascimento.setText("");            
            
            cbxCargo.setSelectedIndex(0);
   
            funcionario = null;

        }else{
            funcionario = f;
            
            txfCpf.setEditable(false);
            txfCpf.setText(funcionario.getCpf());
            
            txfRg.setEditable(false);
            txfRg.setText(funcionario.getRg());
            
            txfNome.setEditable(false);
            txfNome.setText(funcionario.getNome());
            
            txfSenha.setText(funcionario.getSenha());
            
            if(f.getData_cadastro() != null)
                txfDataCadastro.setText(format.format(f.getData_cadastro().getTime()));
            
            txfNumero_celular.setText(funcionario.getNumero_celular());
            
            txfEmail.setText(funcionario.getEmail());
            
            txfCep.setText(funcionario.getCep());
            
            txfEndereco.setText(funcionario.getEndereco());
            
            txfComplemento.setText(funcionario.getComplemento());
            
            txfNumero_ctps.setEditable(false);
            txfNumero_ctps.setText(funcionario.getNumero_ctps());
            
            txfNumero_pis.setEditable(false);
            txfNumero_pis.setText(funcionario.getNumero_pis());
            
            if(f.getData_nascimento()!= null)
                txfData_nascimento.setText(format.format(f.getData_nascimento().getTime()));
            
            cbxCargo.getModel().setSelectedItem(funcionario.getCargo());

        }
    }
    
    private void initComponents(){
        borderLayout = new BorderLayout();
        this.setLayout(borderLayout);
        
        tbpAbas = new JTabbedPane();
        this.add(tbpAbas, BorderLayout.CENTER);
        
        pnlDadosCadastrais = new JPanel();
        gridBagLayoutDadosCadastrais = new GridBagLayout();
        pnlDadosCadastrais.setLayout(gridBagLayoutDadosCadastrais);
        
        // ---------------------------------- CPF ----------------------------------
        lblCpf = new JLabel("CPF: ");
        GridBagConstraints posicionador = new GridBagConstraints();
        posicionador.gridy = 0; // Posi????o da linha (vertical).
        posicionador.gridx = 0; // Posi????o da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblCpf, posicionador); // O add adiciona o r??tulo no painel.
        
        txfCpf = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 0; // Poli????o da linha (vertical).
        posicionador.gridx = 1; // Posi????o da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; //Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfCpf, posicionador); // O add adiciona o r??tulo no painel.
        
        // ---------------------------------- RG ----------------------------------
        lblRg = new JLabel("Rg: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1; // Posi????o da linha (vertical).
        posicionador.gridx = 0; // Posi????o da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblRg, posicionador); // O add adiciona o r??tulo no painel.
        
        txfRg = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1; // Poli????o da linha (vertical).
        posicionador.gridx = 1; // Posi????o da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; //Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfRg, posicionador); // O add adiciona o r??tulo no painel.
        
        // ---------------------------------- NOME ----------------------------------
        lblNome = new JLabel("Nome: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2; // Posi????o da linha (vertical).
        posicionador.gridx = 0; // Posi????o da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblNome, posicionador); // O add adiciona o r??tulo no painel.
        
        txfNome = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2; // Poli????o da linha (vertical).
        posicionador.gridx = 1; // Posi????o da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; //Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfNome, posicionador); // O add adiciona o r??tulo no painel.
        
        // ---------------------------------- SENHA ----------------------------------
        lblSenha = new JLabel("Senha:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3; // Poli????o da linha (vertical).
        posicionador.gridx = 0; // Posi????o da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblSenha, posicionador);//o add adiciona o rotulo no painel  
        
        txfSenha = new JPasswordField(10);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3; // Posi????o da linha (vertical).
        posicionador.gridx = 1; // Posi????o da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; // Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfSenha, posicionador); // O add adiciona o rotulo no painel.
        
        // ---------------------------------- DATA CADASTRO ----------------------------------
        lblDataCadastro = new JLabel("Data de cadastro: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4; // Poli????o da linha (vertical).
        posicionador.gridx = 0; // Posi????o da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblDataCadastro, posicionador);//o add adiciona o rotulo no painel         
        
        txfDataCadastro = new JTextField(20);
        txfDataCadastro.setEditable(false);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4; // Poli????o da linha (vertical).
        posicionador.gridx = 1; // Posi????o da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; // Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfDataCadastro, posicionador); // O add adiciona o rotulo no painel.
        
        // ---------------------------------- NUMERO DE CELULAR ----------------------------------
        lblNumero_celular = new JLabel("N??mero de Celular: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5; // Posi????o da linha (vertical).
        posicionador.gridx = 0; // Posi????o da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblNumero_celular, posicionador); // O add adiciona o r??tulo no painel.
        
        txfNumero_celular = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5; // Poli????o da linha (vertical).
        posicionador.gridx = 1; // Posi????o da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; //Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfNumero_celular, posicionador); // O add adiciona o r??tulo no painel.
        
        // ---------------------------------- EMAIL ----------------------------------
        lblEmail = new JLabel("Email: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 6; // Posi????o da linha (vertical).
        posicionador.gridx = 0; // Posi????o da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblEmail, posicionador); // O add adiciona o r??tulo no painel.
        
        txfEmail = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 6; // Poli????o da linha (vertical).
        posicionador.gridx = 1; // Posi????o da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; //Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfEmail, posicionador); // O add adiciona o r??tulo no painel.
        
        // ---------------------------------- CEP ----------------------------------
        lblCep = new JLabel("Cep: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 7; // Posi????o da linha (vertical).
        posicionador.gridx = 0; // Posi????o da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblCep, posicionador); // O add adiciona o r??tulo no painel.
        
        txfCep = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 7; // Poli????o da linha (vertical).
        posicionador.gridx = 1; // Posi????o da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; //Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfCep, posicionador); // O add adiciona o r??tulo no painel.
        
        // ---------------------------------- ENDERECO ----------------------------------
        lblEndereco = new JLabel("Endere??o: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 8; // Posi????o da linha (vertical).
        posicionador.gridx = 0; // Posi????o da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblEndereco, posicionador); // O add adiciona o r??tulo no painel.
        
        txfEndereco = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 8; // Poli????o da linha (vertical).
        posicionador.gridx = 1; // Posi????o da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; //Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfEndereco, posicionador); // O add adiciona o r??tulo no painel.
        
        // ---------------------------------- COMPLEMENTO ----------------------------------
        lblComplemento = new JLabel("Complemento: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 9; // Posi????o da linha (vertical).
        posicionador.gridx = 0; // Posi????o da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblComplemento, posicionador); // O add adiciona o r??tulo no painel.
        
        txfComplemento = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 9; // Poli????o da linha (vertical).
        posicionador.gridx = 1; // Posi????o da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; //Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfComplemento, posicionador); // O add adiciona o r??tulo no painel.
        
        // ---------------------------------- NUMERO CTPS ----------------------------------
        lblNumero_ctps = new JLabel("N??mero CTPS: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 10; // Posi????o da linha (vertical).
        posicionador.gridx = 0; // Posi????o da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblNumero_ctps, posicionador); // O add adiciona o r??tulo no painel.
        
        txfNumero_ctps = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 10; // Poli????o da linha (vertical).
        posicionador.gridx = 1; // Posi????o da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; //Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfNumero_ctps, posicionador); // O add adiciona o r??tulo no painel.
        
        // ---------------------------------- NUMERO PIS ----------------------------------
        lblNumero_pis = new JLabel("N??mero PIS: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 11; // Posi????o da linha (vertical).
        posicionador.gridx = 0; // Posi????o da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblNumero_pis, posicionador); // O add adiciona o r??tulo no painel.
        
        txfNumero_pis = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 11; // Poli????o da linha (vertical).
        posicionador.gridx = 1; // Posi????o da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; //Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfNumero_pis, posicionador); // O add adiciona o r??tulo no painel.
        
        // ---------------------------------- DATA DE NASCIMENTO ----------------------------------
        lblData_nascimento = new JLabel("Data de nascimento: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 12; // Posi????o da linha (vertical).
        posicionador.gridx = 0; // Posi????o da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblData_nascimento, posicionador); // O add adiciona o r??tulo no painel.
        try {
            txfData_nascimento = new JFormattedTextField(new MaskFormatter("##/##/####"));
        } catch (ParseException ex) {
            Logger.getLogger(JPanelAFuncionarioFormulario.class.getName()).log(Level.SEVERE, null, ex);
        }
        txfData_nascimento.setColumns(6);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 12; // Poli????o da linha (vertical).
        posicionador.gridx = 1; // Posi????o da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; //Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfData_nascimento, posicionador); // O add adiciona o r??tulo no painel.
        
        // ---------------------------------- CARGO ----------------------------------
        lblCargo = new JLabel("Cargo:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 13;//policao da linha (vertical)
        posicionador.gridx = 0;// posi????o da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblCargo, posicionador);//o add adiciona o rotulo no painel  
                
        cbxCargo = new JComboBox();
        posicionador = new GridBagConstraints();
        posicionador.gridy = 13;//policao da linha (vertical)
        posicionador.gridx = 1;// posi????o da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosCadastrais.add(cbxCargo, posicionador);//o add adiciona o rotulo no painel
        
        
        tbpAbas.addTab("Dados cadastrais", pnlDadosCadastrais);
        
        pnlSul = new JPanel();
        pnlSul.setLayout(new FlowLayout());
        
        btnGravar = new JButton("Gravar");
        btnGravar.addActionListener(this);
        btnGravar.setFocusable(true); // Acessibilidade.
        btnGravar.setToolTipText("btnGravarFuncionario"); // Acessibilidade.
        btnGravar.setMnemonic(KeyEvent.VK_G);
        btnGravar.setActionCommand("botao_gravar_formulario_funcionario");
        
        pnlSul.add(btnGravar);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);
        btnCancelar.setFocusable(true); // Acessibilidade.
        btnCancelar.setToolTipText("btnCancelarFuncionario"); // Acessibilidade.
        btnCancelar.setMnemonic(KeyEvent.VK_V);
        btnCancelar.setActionCommand("botao_cancelar_formulario_funcionario");
        
        pnlSul.add(btnCancelar);
        
        this.add(pnlSul, BorderLayout.SOUTH);
        
        format = new SimpleDateFormat("dd/MM/yyyy");
        
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if(arg0.getActionCommand().equals(btnGravar.getActionCommand())) {
            
            Funcionario f = getFuncionariobyFormulario(); // Recupera os dados do formul??rio.
            
            if(f != null) {

                try { 
                    pnlAFuncionario.getControle().getConexaoJDBC().persist(f);
                    
                    JOptionPane.showMessageDialog(this, "Funcionario armazenado!", "Salvar", JOptionPane.INFORMATION_MESSAGE);
            
                    pnlAFuncionario.showTela("tela_funcionario_listagem");
                    
                } catch (Exception ex) {
                    
                    JOptionPane.showMessageDialog(this, "Erro ao salvar funcion??rio: " + ex.getMessage(), "Salvar", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                    
                }
            } else {
                JOptionPane.showMessageDialog(this, "Preencha o formul??rio.", "Edi????o", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } else if(arg0.getActionCommand().equals(btnCancelar.getActionCommand())) {
            
            pnlAFuncionario.showTela("tela_funcionario_listagem");
            
        }
    }
}