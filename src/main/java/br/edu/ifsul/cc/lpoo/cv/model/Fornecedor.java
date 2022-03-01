
package br.edu.ifsul.cc.lpoo.cv.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author bruno
 */
@Entity
@Table(name = "tb_fornecedor")
@DiscriminatorValue("Forn")
public class Fornecedor extends Pessoa{
    
    @Column(nullable = false)
    private String cnpj;
    
    @Column(nullable = false)
    private String ie;
    
    public Fornecedor(){
        
    }

    /**
     * @return the cnpj
     */
    public String getCnpj() {
        return cnpj;
    }

    /**
     * @param cnpj the cnpj to set
     */
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    /**
     * @return the ie
     */
    public String getIe() {
        return ie;
    }

    /**
     * @param ie the ie to set
     */
    public void setIe(String ie) {
        this.ie = ie;
    }
    
}
