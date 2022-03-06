
package br.edu.ifsul.cc.lpoo.cv.gui.venda.acessibilidade;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import br.edu.ifsul.cc.lpoo.cv.model.Cliente;
import br.edu.ifsul.cc.lpoo.cv.model.Fornecedor;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Pagamento;
import br.edu.ifsul.cc.lpoo.cv.model.Produto;
import br.edu.ifsul.cc.lpoo.cv.model.TipoProduto;
import br.edu.ifsul.cc.lpoo.cv.model.Venda;
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

public class JPanelAVendaFormulario extends JPanel implements ActionListener{
    
    
    private JPanelAVenda pnlAVenda;
    private Controle controle;
    
    private BorderLayout borderLayout;
    private JTabbedPane tbpAbas;
    
    private JPanel pnlDadosCadastrais;    
    private JPanel pnlCentroDadosCadastrais;
    private GridBagLayout gridBagLayoutDadosCadastrais;
    
    private JLabel lblId;
    private JTextField txfId;
    
    private JLabel lblObservacao;
    private JTextField txfObservacao;
    
    private JLabel lblValor_total;
    private JTextField txfValor_total;
    
    private JLabel lblPagamento;
    private JComboBox cbxPagamento;
    
    private JLabel lblCliente;
    private JComboBox cbxCliente;
    
    private JLabel lblFuncionario;
    private JComboBox cbxFuncionario;
    
    private Venda venda;
    private SimpleDateFormat format;
    
    private JPanel pnlSul;
    private JButton btnGravar;
    private JButton btnCancelar;
    
    private JPanel pnlDadosVendas;
    private JPanel pnlDadosProdutos;

    
    public JPanelAVendaFormulario(JPanelAVenda pnlAVenda, Controle controle) {
        
        this.pnlAVenda = pnlAVenda;
        this.controle = controle;
        
        initComponents();
        
    }
    
    public void populaComboPagamento(){
        
        cbxPagamento.removeAllItems();//zera o combo

        DefaultComboBoxModel model =  (DefaultComboBoxModel) cbxPagamento.getModel();

        model.addElement("Selecione"); //primeiro item        
        try {
            
            model.addElement(Pagamento.BOLETO);
            model.addElement(Pagamento.CARTAO_CREDITO);
            model.addElement(Pagamento.CARTAO_DEBITO);
            model.addElement(Pagamento.DINHEIRO);
            model.addElement(Pagamento.PIX);

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this, "Erro ao listar Pagamento -:"+ex.getLocalizedMessage(), "Pagamento", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }    
    }
    
    public void populaComboFuncionario(){
        
        cbxFuncionario.removeAllItems();//zera o combo

        DefaultComboBoxModel model =  (DefaultComboBoxModel) cbxFuncionario.getModel();

        model.addElement("Selecione"); //primeiro item        
        try {

            List<Funcionario> listFuncionarios = controle.getConexaoJDBC().listFuncionarios();
            for(Funcionario f : listFuncionarios){
                model.addElement(f);
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this, "Erro ao listar Funcionários -:"+ex.getLocalizedMessage(), "Funcionários", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }     
    }
    
    public void populaComboCliente(){
        
        cbxCliente.removeAllItems();//zera o combo

        DefaultComboBoxModel model =  (DefaultComboBoxModel) cbxCliente.getModel();

        model.addElement("Selecione"); //primeiro item        
        try {

            List<Cliente> listClientes = controle.getConexaoJDBC().listClientes();
            for(Cliente c : listClientes){
                model.addElement(c);
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this, "Erro ao listar Clientes -:"+ex.getLocalizedMessage(), "Clientes", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }     
    }
    
    public Venda getVendabyFormulario(){
        
        //validacao do formulario
         if(txfObservacao.getText().trim().length() > 2 /*&& 
            new String(txfSenha.getPassword()).trim().length() > 3 && 
            cbxCargo.getSelectedIndex() > 0*/){

            Venda v = new Venda();
             
            v.setId(Integer.parseInt(txfId.getText().trim()));    
            
            v.setObservacao(txfObservacao.getText().trim());
            v.setValor_total(Float.parseFloat(txfValor_total.getText().trim()));
            v.setPagamento((Pagamento) cbxPagamento.getSelectedItem());
            v.setCliente((Cliente) cbxCliente.getSelectedItem());
            v.setFuncionario((Funcionario) cbxFuncionario.getSelectedItem());
            
            return v;
         }
         return null;
    }
    
    public void setVendaFormulario(Venda v){

        if(v == null){//se o parametro estiver nullo, limpa o formulario
            
            txfId.setEditable(false);
            
            txfObservacao.setText("");
                
            txfValor_total.setText("");
            
            cbxPagamento.setSelectedIndex(0);
            
            cbxFuncionario.setSelectedIndex(0);
            
            cbxCliente.setSelectedIndex(0);
            
            venda = null;

        }else{

            venda = v;
            
            txfId.setEditable(false);
            txfId.setText(venda.getId().toString());
            
            txfObservacao.setEditable(false);
            txfObservacao.setText(venda.getObservacao());
            
            txfValor_total.setText(venda.getValor_total().toString());
            
            cbxPagamento.getModel().setSelectedItem(venda.getPagamento());
            cbxFuncionario.getModel().setSelectedItem(venda.getFuncionario());
            cbxCliente.getModel().setSelectedItem(venda.getCliente());
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
        
        lblObservacao = new JLabel("Observação: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1; // Posição da linha (vertical).
        posicionador.gridx = 0; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblObservacao, posicionador); // O add adiciona o rótulo no painel.
        
        txfObservacao = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1; // Polição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; //Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfObservacao, posicionador); // O add adiciona o rótulo no painel.
        
        lblValor_total = new JLabel("Valor Total: ");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2; // Posição da linha (vertical).
        posicionador.gridx = 0; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblValor_total, posicionador); // O add adiciona o rótulo no painel.
        
        txfValor_total = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2; // Polição da linha (vertical).
        posicionador.gridx = 1; // Posição da coluna (horizontal).
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START; //Ancoragem a esquerda.
        pnlDadosCadastrais.add(txfValor_total, posicionador); // O add adiciona o rótulo no painel.
        
        lblPagamento = new JLabel("Pagamento:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosCadastrais.add(lblPagamento, posicionador);//o add adiciona o rotulo no painel  
                
        cbxPagamento = new JComboBox();
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosCadastrais.add(cbxPagamento, posicionador);//o add adiciona o rotulo no painel
        
        lblFuncionario = new JLabel("Funcionário:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosCadastrais.add(lblFuncionario, posicionador);//o add adiciona o rotulo no painel  
                
        cbxFuncionario = new JComboBox();
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosCadastrais.add(cbxFuncionario, posicionador);//o add adiciona o rotulo no painel
        
        lblCliente = new JLabel("Cliente:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosCadastrais.add(lblCliente, posicionador);//o add adiciona o rotulo no painel  
                
        cbxCliente = new JComboBox();
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosCadastrais.add(cbxCliente, posicionador);//o add adiciona o rotulo no painel
        
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
        btnGravar.setActionCommand("botao_gravar_formulario_venda");
        
        pnlSul.add(btnGravar);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);
        btnCancelar.setFocusable(true); // Acessibilidade.
        btnCancelar.setToolTipText("btnCancelarVenda"); // Acessibilidade.
        btnCancelar.setActionCommand("botao_cancelar_formulario_venda");
        
        pnlSul.add(btnCancelar);
        
        this.add(pnlSul, BorderLayout.SOUTH);
        
        format = new SimpleDateFormat("dd/MM/yyyy");
        
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if(arg0.getActionCommand().equals(btnGravar.getActionCommand())) {
            
            Venda v = getVendabyFormulario(); // Recupera os dados do formulário.
            
            if(v != null) {

                try {
                    
                    pnlAVenda.getControle().getConexaoJDBC().persist(v);
                    
                    JOptionPane.showMessageDialog(this, "Venda armazenada!", "Salvar", JOptionPane.INFORMATION_MESSAGE);
            
                    pnlAVenda.showTela("tela_venda_listagem");
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar Venda: " + ex.getMessage(), "Salvar", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Preencha o formulário.", "Edição", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } else if(arg0.getActionCommand().equals(btnCancelar.getActionCommand())) {
            pnlAVenda.showTela("tela_venda_listagem");
        }
    }
}
