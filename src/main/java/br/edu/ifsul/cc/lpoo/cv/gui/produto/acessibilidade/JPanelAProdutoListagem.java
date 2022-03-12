
package br.edu.ifsul.cc.lpoo.cv.gui.produto.acessibilidade;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import br.edu.ifsul.cc.lpoo.cv.model.Produto;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author bruno
 */

public class JPanelAProdutoListagem extends JPanel implements ActionListener {
    private JPanelAProduto pnlAProduto;
    private Controle controle;
    
    private BorderLayout borderLayout;
    private JPanel pnlNorte;
    private JLabel lblFiltro;
    private JTextField txfFiltro;
    private JButton btnFiltro;
    
    private JPanel pnlCentro;
    private JScrollPane scpListagem;
    private JTable tblListagem;
    private DefaultTableModel modeloTabela;
    
    private JPanel pnlSul;
    private JButton btnNovo;
    private JButton btnAlterar;
    private JButton btnRemover;
    
    
    public JPanelAProdutoListagem(JPanelAProduto pnlAProduto, Controle controle) {
        this.pnlAProduto = pnlAProduto;
        this.controle = controle;
        
        initComponents();
    }
    
    public void populaTable() {
        DefaultTableModel model =  (DefaultTableModel) tblListagem.getModel(); // Recuperação do modelo da tabela.
        model.setRowCount(0); // Elimina as linhas existentes (reset na tabela).
        
        try {
            List<Produto> listProdutos = controle.getConexaoJDBC().listProdutos();
            
            for(Produto p : listProdutos){                          
                model.addRow(new Object[]{p, p.getNome(), p.getFornecedor()});
            }
        } catch (Exception ex) {
            
            JOptionPane.showMessageDialog(this, "Erro ao listar produtos: " + ex.getLocalizedMessage(), "Produtos", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            
        }          
    }
    
    private void initComponents(){
        borderLayout = new BorderLayout();
        this.setLayout(borderLayout); // Seta o gerenciado border para este painel.
        
        pnlNorte = new JPanel();
        pnlNorte.setLayout(new FlowLayout());
        
        lblFiltro = new JLabel("Filtrar por ID:");
        pnlNorte.add(lblFiltro);
        
        txfFiltro = new JTextField(20);
        pnlNorte.add(txfFiltro);
        
        btnFiltro = new JButton("Filtrar");
        btnFiltro.addActionListener(this);
        btnFiltro.setFocusable(true); // Acessibilidade.
        btnFiltro.setToolTipText("btnFiltrar"); // Acessibilidade.
        btnFiltro.setActionCommand("botao_filtro");
        pnlNorte.add(btnFiltro);
        
        this.add(pnlNorte, BorderLayout.NORTH); // Adiciona o painel na posição norte.
        
        pnlCentro = new JPanel();
        pnlCentro.setLayout(new BorderLayout());
            
        scpListagem = new JScrollPane();
        tblListagem =  new JTable();
        
        modeloTabela = new DefaultTableModel(
            new String [] {
                "ID", "Nome", "CPF Fornecedor"
            }, 0
             
        ){
            @Override
            public boolean isCellEditable(final int row, final int column) {
		return false;
            }
	};
        
        tblListagem.setModel(modeloTabela);
        scpListagem.setViewportView(tblListagem);
    
        pnlCentro.add(scpListagem, BorderLayout.CENTER);
        
        this.add(pnlCentro, BorderLayout.CENTER); // Adiciona o painel na posição norte.
        
        pnlSul = new JPanel();
        pnlSul.setLayout(new FlowLayout());
        
        btnNovo = new JButton("Novo");
        btnNovo.addActionListener(this);
        btnNovo.setFocusable(true); // Acessibilidade.
        btnNovo.setToolTipText("btnNovo"); // Acessibilidade.
        btnNovo.setMnemonic(KeyEvent.VK_N);
        btnNovo.setActionCommand("botao_novo");
        
        pnlSul.add(btnNovo);
        
        btnAlterar = new JButton("Editar");
        btnAlterar.addActionListener(this);
        btnAlterar.setFocusable(true); // Acessibilidade.
        btnAlterar.setToolTipText("btnAlterar"); // Acessibilidade.
        btnAlterar.setActionCommand("botao_alterar");
        
        pnlSul.add(btnAlterar);
        
        btnRemover = new JButton("Remover");
        btnRemover.addActionListener(this);
        btnRemover.setFocusable(true); // Acessibilidade.
        btnRemover.setToolTipText("btnRemvoer"); // Acessibilidade.
        btnRemover.setActionCommand("botao_remover");
        
        pnlSul.add(btnRemover); // Adiciona o botão na fila organizada pelo flowlayout.
        
        this.add(pnlSul, BorderLayout.SOUTH); // Adiciona o painel na posição norte.
        
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        
        if(arg0.getActionCommand().equals(btnNovo.getActionCommand())) {   
            
            pnlAProduto.showTela("tela_produto_formulario");
            pnlAProduto.getFormulario().setProdutoFormulario(null); // Limpando o formulário.
            
        } else if(arg0.getActionCommand().equals(btnAlterar.getActionCommand())) {
            
            int indice = tblListagem.getSelectedRow(); // Recupera a linha selecionada.
            
            if(indice > -1) {
                DefaultTableModel model =  (DefaultTableModel) tblListagem.getModel(); // Recuperação do modelo da table.
                
                Vector linha = (Vector) model.getDataVector().get(indice); // Recupera o vetor de dados da linha selecionada.

                Produto p = (Produto) linha.get(0); // model.addRow(new Object[]{u, u.getNome(), ...

                pnlAProduto.showTela("tela_produto_formulario");
                pnlAProduto.getFormulario().setProdutoFormulario(p); 
            
            } else {
                  JOptionPane.showMessageDialog(this, "Selecione uma linha para editar.", "Edição", JOptionPane.INFORMATION_MESSAGE);
            }
          
        } else if(arg0.getActionCommand().equals(btnRemover.getActionCommand())) {           
            
            int indice = tblListagem.getSelectedRow(); // Recupera a linha selecionada.
            if(indice > -1){
                DefaultTableModel model =  (DefaultTableModel) tblListagem.getModel(); // Recuperação do modelo da table.
                
                Vector linha = (Vector) model.getDataVector().get(indice); // Recupera o vetor de dados da linha selecionada.

                Produto p = (Produto) linha.get(0); // model.addRow(new Object[]{u, u.getNome(), ...

                try {
                    pnlAProduto.getControle().getConexaoJDBC().remover(p);
                    JOptionPane.showMessageDialog(this, "Produto removido.", "Produto", JOptionPane.INFORMATION_MESSAGE);
                    populaTable(); // Refresh na tabela.

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao remover Produto: " + ex.getLocalizedMessage(), "Produtos", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }                        

            } else {
                  JOptionPane.showMessageDialog(this, "Selecione uma linha para remover.", "Remoção", JOptionPane.INFORMATION_MESSAGE);
            }
            
        }   
    
    }
    
}
