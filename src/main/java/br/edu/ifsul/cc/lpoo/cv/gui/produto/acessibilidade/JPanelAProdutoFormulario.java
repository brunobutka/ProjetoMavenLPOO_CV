package br.edu.ifsul.cc.lpoo.cv.gui.produto.acessibilidade;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import br.edu.ifsul.cc.lpoo.cv.model.Fornecedor;
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
import java.util.List;
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
    
    private JLabel lblFornecedor;
    private JComboBox cbxFornecedor;
    
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
            for(TipoProduto tipoProduto : TipoProduto.values()){
                model.addElement(tipoProduto.toString());
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this, "Erro ao listar Tipo Produto -:"+ex.getLocalizedMessage(), "Tipo Produto", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }    
    }
    
    public void populaComboFornecedor(){
        cbxFornecedor.removeAllItems();//zera o combo

        DefaultComboBoxModel model =  (DefaultComboBoxModel) cbxFornecedor.getModel();

        model.addElement("Selecione"); //primeiro item        
        try {

            List<Fornecedor> listFornecedores = controle.getConexaoJDBC().listFornecedores();
            for(Fornecedor f : listFornecedores){
                model.addElement(f);
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this, "Erro ao listar Fornecedores -:"+ex.getLocalizedMessage(), "Fornecedores", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }    
    }
    
    public Produto getProdutobyFormulario(){
        
        //validacao do formulario
         if(txfNome.getText().trim().length() > 1 &&  cbxTipo_produto.getSelectedIndex() > 0 &&
            cbxFornecedor.getSelectedIndex() > 0){

            Produto p = new Produto();         
            
            p.setNome(txfNome.getText().trim());
            try{
                p.setQuantidade(Float.parseFloat(txfQuantidade.getText().trim()));
                p.setValor(Float.parseFloat(txfValor.getText().trim()));
            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(this, "Coloque numeros em Quantidade e Valor ", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            
            p.setTipo_produto(TipoProduto.getTipoProduto(cbxTipo_produto.getSelectedItem().toString()));
            p.setFornecedor((Fornecedor) cbxFornecedor.getSelectedItem());
            
            if(produto != null){
                p.setId(produto.getId());
            }
            
            return p;
         }
         return null;
    }
    
    public void setProdutoFormulario(Produto p){
        if(p == null){//se o parametro estiver nullo, limpa o formulario
            txfId.setEditable(false);
            txfId.setText("");
            txfNome.setText(""); 
            txfQuantidade.setText("");         
            txfValor.setText(""); 
            cbxTipo_produto.setSelectedIndex(0);           
            cbxFornecedor.setSelectedIndex(0);
            
            produto = null;

        }else{

            produto = p;
            
            txfId.setEditable(false);
            txfId.setText(produto.getId().toString());
            txfNome.setText(produto.getNome());
            txfQuantidade.setText(produto.getQuantidade().toString());
            txfValor.setText(produto.getValor().toString());
            cbxTipo_produto.getModel().setSelectedItem(produto.getTipo_produto().toString());
            cbxFornecedor.getModel().setSelectedItem(produto.getFornecedor());
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
        
        // ------------------------------------ ID --------------------------------------
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
        
        // ------------------------------------ NOME --------------------------------------
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
        
        // ------------------------------------ QUANTIDADE --------------------------------------
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
        
        // ------------------------------------ VALOR --------------------------------------
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
        
        // ------------------------------------ TIPO PRODUTO --------------------------------------
        lblTipo_produto = new JLabel("Tipo Produto:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblTipo_produto, posicionador);//o add adiciona o rotulo no painel  
                
        cbxTipo_produto = new JComboBox();
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosCadastrais.add(cbxTipo_produto, posicionador);//o add adiciona o rotulo no painel
        
        // ------------------------------------ FORNECEDOR --------------------------------------
        lblFornecedor = new JLabel("Fornecedor:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblFornecedor, posicionador);//o add adiciona o rotulo no painel  
                
        cbxFornecedor = new JComboBox();
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosCadastrais.add(cbxFornecedor, posicionador);//o add adiciona o rotulo no painel
        
        tbpAbas.addTab("Dados cadastrais", pnlDadosCadastrais);
        
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