
package br.edu.ifsul.cc.lpoo.cv.gui.venda.acessibilidade;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import br.edu.ifsul.cc.lpoo.cv.model.Cliente;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Pagamento;
import br.edu.ifsul.cc.lpoo.cv.model.Produto;
import br.edu.ifsul.cc.lpoo.cv.model.Venda;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

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
    
    private JPanel pnlSul;
    private JButton btnGravar;
    private JButton btnCancelar;
    
    private JPanel pnlDadosProdutos;
    private JScrollPane scpListagemProduto;
    private JTable tblListagemProduto;
    private JComboBox cbxProduto;
    private JButton btnAdicionarProduto;
    private JButton btnRemoverProduto;
    private DefaultTableModel modeloTabelaProduto;
    private JLabel lblProdutoAdicionar;

    
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
            
            for(Pagamento pagamento : Pagamento.values()){
                model.addElement(pagamento.toString());
            }

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
    
    public void populaComboProduto(){
        
        cbxProduto.removeAllItems();//zera o combo

        DefaultComboBoxModel model =  (DefaultComboBoxModel) cbxProduto.getModel();

        model.addElement("Selecione"); //primeiro item        
        try {

            List<Produto> listProdutos = controle.getConexaoJDBC().listProdutos();
            for(Produto p : listProdutos){
                model.addElement(p);
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this, "Erro ao listar Produtos -:"+ex.getLocalizedMessage(), "Produtos", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }  
        
        
    }
    
    public Venda getVendabyFormulario(){
        
        //validacao do formulario
         if(txfObservacao.getText().trim().length() > 2 && txfValor_total.getText().trim().length() > 0 &&
            cbxPagamento.getSelectedIndex() > 0 && cbxCliente.getSelectedIndex() > 0 &&
            cbxFuncionario.getSelectedIndex() > 0){

            Venda v = new Venda(); 
            
            v.setObservacao(txfObservacao.getText().trim());
            try{
                v.setValor_total(Float.parseFloat(txfValor_total.getText().trim()));
             }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(this, "Coloque numeros em Valor Total ", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            
            v.setPagamento(Pagamento.getPagamento(cbxPagamento.getSelectedItem().toString()));
            v.setCliente((Cliente) cbxCliente.getSelectedItem());
            v.setFuncionario((Funcionario) cbxFuncionario.getSelectedItem());
            
            for(Vector linha : modeloTabelaProduto.getDataVector()){
                Produto p = (Produto) linha.get(0);
                 
                v.setProduto(p); 
            }
            
            if(venda != null){
                v.setId(venda.getId());
            }
            
            return v;
         }
         return null;
    }
    
    public void setVendaFormulario(Venda v){

        if(v == null){//se o parametro estiver nullo, limpa o formulario
            
            txfId.setEditable(false);
            txfId.setText("");
            txfObservacao.setText("");          
            txfValor_total.setText("");  
            cbxPagamento.setSelectedIndex(0); 
            cbxFuncionario.setSelectedIndex(0);
            cbxCliente.setSelectedIndex(0);
            modeloTabelaProduto.setNumRows(0);
            
            
            venda = null;

        }else{

            venda = v;
            
            txfId.setEditable(false);
            txfId.setText(venda.getId().toString());
            txfObservacao.setText(venda.getObservacao());
            txfValor_total.setText(venda.getValor_total().toString());
            cbxPagamento.getModel().setSelectedItem(venda.getPagamento().toString());
            cbxFuncionario.getModel().setSelectedItem(venda.getFuncionario());
            cbxCliente.getModel().setSelectedItem(venda.getCliente());
            populaTableProdutos(v.getProdutos());
            
        }

    }
    
    public void populaTableProdutos(List<Produto> list) {
        DefaultTableModel model =  (DefaultTableModel) tblListagemProduto.getModel(); // Recuperação do modelo da tabela.
        model.setRowCount(0); // Elimina as linhas existentes (reset na tabela).
        
        try {
            if(list != null){
                for(Produto p : list){                          
                    model.addRow(new Object[]{p, p.getNome(), p.getFornecedor()});
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao listar Produtos: " + ex.getLocalizedMessage(), "Produtos", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
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
        
        // ---------------------------------- ID ----------------------------------
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
        
        // ---------------------------------- OBSERVACAO ----------------------------------
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
        
        // ---------------------------------- VALOR TOTAL ----------------------------------
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
        
        // ---------------------------------- PAGAMENTO ----------------------------------
        lblPagamento = new JLabel("Pagamento:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblPagamento, posicionador);//o add adiciona o rotulo no painel  
                
        cbxPagamento = new JComboBox();
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosCadastrais.add(cbxPagamento, posicionador);//o add adiciona o rotulo no painel
        
        // ---------------------------------- FUNCIONARIO ----------------------------------
        lblFuncionario = new JLabel("Funcionário:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblFuncionario, posicionador);//o add adiciona o rotulo no painel  
                
        cbxFuncionario = new JComboBox();
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosCadastrais.add(cbxFuncionario, posicionador);//o add adiciona o rotulo no painel
        
        // ---------------------------------- CLIENTE ----------------------------------
        lblCliente = new JLabel("Cliente:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_END; //Ancoragem a direita.
        pnlDadosCadastrais.add(lblCliente, posicionador);//o add adiciona o rotulo no painel  
                
        cbxCliente = new JComboBox();
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosCadastrais.add(cbxCliente, posicionador);//o add adiciona o rotulo no painel
        
        tbpAbas.addTab("Dados cadastrais", pnlDadosCadastrais);
        tbpAbas.setMnemonicAt(0, KeyEvent.VK_D);
        
        pnlDadosProdutos = new JPanel();
        
        pnlDadosProdutos.setLayout(new GridBagLayout());
        
        scpListagemProduto = new JScrollPane();
        tblListagemProduto = new JTable();
        
        modeloTabelaProduto = new DefaultTableModel(
            new String [] {
                "ID", "Nome", "CPF Fornecedor"
            }, 0
             
        ){
            @Override
            public boolean isCellEditable(final int row, final int column) {
		return false;
            }
	};
        
        tblListagemProduto.setModel(modeloTabelaProduto);
        
        scpListagemProduto.setViewportView(tblListagemProduto);
        
        cbxProduto = new JComboBox();
        lblProdutoAdicionar = new JLabel("Escolha o Produto para adicionar:");
        
        btnAdicionarProduto = new JButton("Adicionar");
        btnAdicionarProduto.addActionListener(this);
        btnAdicionarProduto.setFocusable(true);    //acessibilidade    
        btnAdicionarProduto.setToolTipText("btnAdicionarProduto"); //acessibilidade
        btnAdicionarProduto.setMnemonic(KeyEvent.VK_I);
        btnAdicionarProduto.setActionCommand("botao_adicionar_produto_jogador");
        
        btnRemoverProduto = new JButton("Remover");
        btnRemoverProduto.addActionListener(this);
        btnRemoverProduto.setFocusable(true);    //acessibilidade    
        btnRemoverProduto.setToolTipText("btnRemoverProduto"); //acessibilidade
        btnRemoverProduto.setMnemonic(KeyEvent.VK_O);
        btnRemoverProduto.setActionCommand("botao_remover_produto_jogador");
        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 0;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosProdutos.add(scpListagemProduto, posicionador);//o add adiciona o rotulo no painel
        
        JPanel pnlLinha = new JPanel();
        pnlLinha.setLayout(new FlowLayout());
        pnlLinha.add(lblProdutoAdicionar);
        pnlLinha.add(cbxProduto);
        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosProdutos.add(pnlLinha, posicionador);//o add adiciona o rotulo no painel
        
        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosProdutos.add(btnAdicionarProduto, posicionador);//o add adiciona o rotulo no painel
        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosProdutos.add(btnRemoverProduto, posicionador);//o add adiciona o rotulo no painel
        
        tbpAbas.addTab("Produtos", pnlDadosProdutos);
        tbpAbas.setMnemonicAt(1, KeyEvent.VK_P);
        
        
        pnlSul = new JPanel();
        pnlSul.setLayout(new FlowLayout());
        
        btnGravar = new JButton("Gravar");
        btnGravar.addActionListener(this);
        btnGravar.setFocusable(true); // Acessibilidade.
        btnGravar.setToolTipText("btnGravarVenda"); // Acessibilidade.
        btnGravar.setMnemonic(KeyEvent.VK_G);
        btnGravar.setActionCommand("botao_gravar_formulario_venda");
        
        pnlSul.add(btnGravar);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);
        btnCancelar.setFocusable(true); // Acessibilidade.
        btnCancelar.setToolTipText("btnCancelarVenda"); // Acessibilidade.
        btnCancelar.setMnemonic(KeyEvent.VK_V);
        btnCancelar.setActionCommand("botao_cancelar_formulario_venda");
        
        pnlSul.add(btnCancelar);
        
        this.add(pnlSul, BorderLayout.SOUTH);
        
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
            
        } else if(arg0.getActionCommand().equals(btnAdicionarProduto.getActionCommand())) {
            
            if(cbxProduto.getSelectedIndex() > 0){
                Produto p = (Produto) cbxProduto.getSelectedItem();
                modeloTabelaProduto.addRow(new Object[]{p, p.getNome(), p.getFornecedor()});
            } else
                JOptionPane.showMessageDialog(this, "Selecione um Produto", "Produto", JOptionPane.INFORMATION_MESSAGE);
            
        } else if(arg0.getActionCommand().equals(btnRemoverProduto.getActionCommand())) {
            
            int indice = tblListagemProduto.getSelectedRow();
            
            if(indice > -1){
                DefaultTableModel model = (DefaultTableModel) tblListagemProduto.getModel(); // Recuperação do modelo da table.

                model.removeRow(tblListagemProduto.getSelectedRow());         

            } else {
                  JOptionPane.showMessageDialog(this, "Selecione uma linha para remover.", "Remoção", JOptionPane.INFORMATION_MESSAGE);
            }
          
        } 
    }
}
