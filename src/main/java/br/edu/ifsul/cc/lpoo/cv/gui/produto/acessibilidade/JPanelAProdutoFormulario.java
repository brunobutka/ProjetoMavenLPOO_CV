package br.edu.ifsul.cc.lpoo.cv.gui.produto.acessibilidade;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import br.edu.ifsul.cc.lpoo.cv.model.Produto;
import br.edu.ifsul.cc.lpoo.cv.model.TipoProduto;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
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

public class JPanelAProdutoFormulario extends JPanel implements ActionListener{
    
    
    private JPanelAProduto pnlAProduto;
    private Controle controle;
    
    private BorderLayout borderLayout;
    private JTabbedPane tbpAbas;
    
    private JPanel pnlDadosCadastrais;    
    private JPanel pnlCentroDadosCadastrais;
    private GridBagLayout gridBagLayoutDadosCadastrais;
    
    private JLabel lblId;
    private JTextField txfId;
    
    private JLabel lblNome;
    private JTextField txfNome;
    
    private JLabel lblQuantidade;
    private JTextField txfQuantidade;
    
    private JLabel lblValor;
    private JTextField txfValor;
    
    private JLabel lblTipo_produto;
    private JComboBox cbxTipo_produto;
    
    private Produto produto;
    private SimpleDateFormat format;
    
    private JPanel pnlSul;
    private JButton btnGravar;
    private JButton btnCancelar;
    
    private JPanel pnlDadosVendas;
    private JPanel pnlDadosProdutos;

    
    public JPanelAProdutoFormulario(JPanelAProduto pnlAProduto, Controle controle) {
        
        this.pnlAProduto = pnlAProduto;
        this.controle = controle;
        
        initComponents();
        
    }
    
    public void populaComboTipoProduto(){
        
        cbxTipo_produto.removeAllItems();//zera o combo

        DefaultComboBoxModel model =  (DefaultComboBoxModel) cbxTipo_produto.getModel();

        model.addElement("Selecione"); //primeiro item        
        try {
            
            model.addElement(TipoProduto.ATENDIMENTO_AMBULATORIAL);
            model.addElement(TipoProduto.CONSULTA);
            model.addElement(TipoProduto.CONSULTA_REVISAO);
            model.addElement(TipoProduto.MEDICAMENTO);
            model.addElement(TipoProduto.SESSAO_ADESTRAMENTO);
            model.addElement(TipoProduto.SESSAO_FISIOTERAPIA);


        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this, "Erro ao listar Tipo Produto -:"+ex.getLocalizedMessage(), "Tipo Produto", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }  
        
        
    }
    
    public Produto getProdutobyFormulario(){
        
        //validacao do formulario
         if(txfNome.getText().trim().length() < 2 /*&& 
            new String(txfSenha.getPassword()).trim().length() > 3 && 
            cbxCargo.getSelectedIndex() > 0*/){

            Produto p = new Produto();
            
            String strId = txfId.getText();
            Integer id_convert = Integer.parseInt(strId);
            p.setId(id_convert); 
            
            p.setNome(txfNome.getText().trim());
            // j.setPontos(Integer.parseInt(txfPontos.getText().trim()));
            p.setQuantidade(Float.parseFloat(txfQuantidade.getText().trim()));
            p.setValor(Float.parseFloat(txfValor.getText().trim()));
            //p.setValor(txfValor.getText().trim());
            
            p.setTipo_produto((TipoProduto) cbxTipo_produto.getSelectedItem());
            
            
            return p;
         }
         return null;
    }
    
    public void setProdutoFormulario(Produto p){

        if(p == null){//se o parametro estiver nullo, limpa o formulario
            
            txfId.setEditable(false);
            txfId.setText("");
            
            //txfNome.setEditable(true);
            txfNome.setText("");
            
            //txfQuantidade.setEditable(true);
            txfQuantidade.setText("");
            
            //txfValor.setEditable(true);
            txfValor.setText("");
            
            cbxTipo_produto.setSelectedIndex(0);
            
            produto = null;

        }else{

            produto = p;
            
            
            //String strId = txfId.getText();
            //Integer id_convert = Integer.parseInt(strId);
            txfId.setEditable(false);
            txfId.setText(produto.getId().toString());
            
            txfNome.setEditable(false);
            txfNome.setText(produto.getNome());
            
            //txfQuantidade.setEditable(false);
            txfQuantidade.setText(produto.getQuantidade().toString());
            txfValor.setText(produto.getValor().toString());
            
            //float n pode ser convertido para string
            
            cbxTipo_produto.getModel().setSelectedItem(produto.getTipo_produto());
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
        
        lblId = new JLabel("ID: ");
        GridBagConstraints posicionador = new GridBagConstraints();
        posicionador.gridy = 0; // Posição da linha (vertical).
        posicionador.gridx = 0; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblId, posicionador); // O add adiciona o rótulo no painel.
        
        txfId = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 0; // Polição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; //Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfId, posicionador); // O add adiciona o rótulo no painel.
        
        lblNome = new JLabel("Nome: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1; // Posição da linha (vertical).
        posicionador.gridx = 0; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblNome, posicionador); // O add adiciona o rótulo no painel.
        
        txfNome = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1; // Polição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; //Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfNome, posicionador); // O add adiciona o rótulo no painel.
        
        lblQuantidade = new JLabel("Quantidade: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2; // Posição da linha (vertical).
        posicionador.gridx = 0; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblQuantidade, posicionador); // O add adiciona o rótulo no painel.
        
        txfQuantidade = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2; // Polição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; //Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfQuantidade, posicionador); // O add adiciona o rótulo no painel.
        
        lblValor = new JLabel("Valor: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3; // Posição da linha (vertical).
        posicionador.gridx = 0; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblValor, posicionador); // O add adiciona o rótulo no painel.
        
        txfValor = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3; // Polição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; //Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfValor, posicionador); // O add adiciona o rótulo no painel.
        
        lblTipo_produto = new JLabel("Tipo Produto:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosCadastrais.add(lblTipo_produto, posicionador);//o add adiciona o rotulo no painel  
                
        cbxTipo_produto = new JComboBox();
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosCadastrais.add(cbxTipo_produto, posicionador);//o add adiciona o rotulo no painel
        
        /*lblRg = new JLabel("Rg: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1; // Posição da linha (vertical).
        posicionador.gridx = 0; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblRg, posicionador); // O add adiciona o rótulo no painel.
        
        txfRg = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1; // Polição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; //Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfRg, posicionador); // O add adiciona o rótulo no painel.
        
        lblNome = new JLabel("Nome: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2; // Posição da linha (vertical).
        posicionador.gridx = 0; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblNome, posicionador); // O add adiciona o rótulo no painel.
        
        txfNome = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2; // Polição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; //Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfNome, posicionador); // O add adiciona o rótulo no painel.
        
        lblSenha = new JLabel("Senha:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3; // Polição da linha (vertical).
        posicionador.gridx = 0; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblSenha, posicionador);//o add adiciona o rotulo no painel  
        
        txfSenha = new JPasswordField(10);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3; // Posição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; // Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfSenha, posicionador); // O add adiciona o rotulo no painel.
        
        lblDataCadastro = new JLabel("Data de cadastro: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4; // Polição da linha (vertical).
        posicionador.gridx = 0; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblDataCadastro, posicionador);//o add adiciona o rotulo no painel         
        
        txfDataCadastro = new JTextField(20);
        txfDataCadastro.setEditable(false);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4; // Polição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        pnlDadosCadastrais.add(txfDataCadastro, posicionador); // O add adiciona o rotulo no painel.
        
        lblNumero_celular = new JLabel("Número de Celular: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5; // Posição da linha (vertical).
        posicionador.gridx = 0; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblNumero_celular, posicionador); // O add adiciona o rótulo no painel.
        
        txfNumero_celular = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5; // Polição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; //Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfNumero_celular, posicionador); // O add adiciona o rótulo no painel.
        
        lblEmail = new JLabel("Email: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 6; // Posição da linha (vertical).
        posicionador.gridx = 0; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblEmail, posicionador); // O add adiciona o rótulo no painel.
        
        txfEmail = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 6; // Polição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; //Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfEmail, posicionador); // O add adiciona o rótulo no painel.
        
        lblCep = new JLabel("Cep: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 7; // Posição da linha (vertical).
        posicionador.gridx = 0; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblCep, posicionador); // O add adiciona o rótulo no painel.
        
        txfCep = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 7; // Polição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; //Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfCep, posicionador); // O add adiciona o rótulo no painel.
        
        lblEndereco = new JLabel("Endereço: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 8; // Posição da linha (vertical).
        posicionador.gridx = 0; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblEndereco, posicionador); // O add adiciona o rótulo no painel.
        
        txfEndereco = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 8; // Polição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; //Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfEndereco, posicionador); // O add adiciona o rótulo no painel.
        
        lblComplemento = new JLabel("Complemento: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 9; // Posição da linha (vertical).
        posicionador.gridx = 0; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblComplemento, posicionador); // O add adiciona o rótulo no painel.
        
        txfComplemento = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 9; // Polição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; //Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfComplemento, posicionador); // O add adiciona o rótulo no painel.
        
        lblNumero_ctps = new JLabel("Número CTPS: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 10; // Posição da linha (vertical).
        posicionador.gridx = 0; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblNumero_ctps, posicionador); // O add adiciona o rótulo no painel.
        
        txfNumero_ctps = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 10; // Polição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; //Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfNumero_ctps, posicionador); // O add adiciona o rótulo no painel.
        
        lblNumero_pis = new JLabel("Número PIS: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 11; // Posição da linha (vertical).
        posicionador.gridx = 0; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblNumero_pis, posicionador); // O add adiciona o rótulo no painel.
        
        txfNumero_pis = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 11; // Polição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; //Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfNumero_pis, posicionador); // O add adiciona o rótulo no painel.
        
        lblData_nascimento = new JLabel("Data de nascimento: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 12; // Posição da linha (vertical).
        posicionador.gridx = 0; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblData_nascimento, posicionador); // O add adiciona o rótulo no painel.
        try {
            //txfData_nascimento = new JFormattedTextField(DateFormat.getDateInstance(DateFormat.MEDIUM));
            txfData_nascimento = new JFormattedTextField(new MaskFormatter("##/##/####"));
        } catch (ParseException ex) {
            Logger.getLogger(JPanelAFuncionarioFormulario.class.getName()).log(Level.SEVERE, null, ex);
        }
        txfData_nascimento.setColumns(6);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 12; // Polição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; //Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfData_nascimento, posicionador); // O add adiciona o rótulo no painel.
        
        lblCargo = new JLabel("Cargo:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 13;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblCargo, posicionador);//o add adiciona o rotulo no painel  
                
        cbxCargo = new JComboBox();
        posicionador = new GridBagConstraints();
        posicionador.gridy = 13;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosCadastrais.add(cbxCargo, posicionador);//o add adiciona o rotulo no painel
        */
        
        tbpAbas.addTab("Dados cadastrais", pnlDadosCadastrais);
        
        pnlDadosVendas = new JPanel();
        tbpAbas.addTab("Vendas", pnlDadosVendas);
        
        pnlDadosProdutos = new JPanel();
        tbpAbas.addTab("Produtos", pnlDadosProdutos);
        
        pnlSul = new JPanel();
        pnlSul.setLayout(new FlowLayout());
        
        btnGravar = new JButton("Gravar");
        btnGravar.addActionListener(this);
        btnGravar.setFocusable(true); // Acessibilidade.
        btnGravar.setToolTipText("btnGravarProduto"); // Acessibilidade.
        btnGravar.setMnemonic(KeyEvent.VK_G);
        btnGravar.setActionCommand("botao_gravar_formulario_produto");
        
        pnlSul.add(btnGravar);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);
        btnCancelar.setFocusable(true); // Acessibilidade.
        btnCancelar.setToolTipText("btnCancelarProduto"); // Acessibilidade.
        btnCancelar.setActionCommand("botao_cancelar_formulario_produto");
        
        pnlSul.add(btnCancelar);
        
        this.add(pnlSul, BorderLayout.SOUTH);
        
        format = new SimpleDateFormat("dd/MM/yyyy");
        
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if(arg0.getActionCommand().equals(btnGravar.getActionCommand())) {
            
            Produto p = getProdutobyFormulario(); // Recupera os dados do formulário.
            
            if(p != null) {

                try {
                    
                    pnlAProduto.getControle().getConexaoJDBC().persist(p);
                    
                    JOptionPane.showMessageDialog(this, "Produto armazenado!", "Salvar", JOptionPane.INFORMATION_MESSAGE);
            
                    pnlAProduto.showTela("tela_produto_listagem");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar Produto: " + ex.getMessage(), "Salvar", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Preencha o formulário.", "Edição", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } else if(arg0.getActionCommand().equals(btnCancelar.getActionCommand())) {
            pnlAProduto.showTela("tela_produto_listagem");
        }
    }
}