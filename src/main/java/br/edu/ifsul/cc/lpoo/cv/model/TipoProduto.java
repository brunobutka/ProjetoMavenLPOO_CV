/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package br.edu.ifsul.cc.lpoo.cv.model;

/**
 *
 * @author bruno
 */
public enum TipoProduto {
    MEDICAMENTO, SESSAO_FISIOTERAPIA, SESSAO_ADESTRAMENTO, ATENDIMENTO_AMBULATORIAL, CONSULTA, CONSULTA_REVISAO;
    
    public static TipoProduto getTipoProduto(String nameEnum){
        if(nameEnum.equals(TipoProduto.MEDICAMENTO.toString()))
            
            return TipoProduto.MEDICAMENTO;
        
        else if(nameEnum.equals(TipoProduto.SESSAO_FISIOTERAPIA.toString())){
            
            return TipoProduto.SESSAO_FISIOTERAPIA;
            
        }else if(nameEnum.equals(TipoProduto.SESSAO_ADESTRAMENTO.toString())){
            
            return TipoProduto.SESSAO_ADESTRAMENTO;
            
        }else if(nameEnum.equals(TipoProduto.ATENDIMENTO_AMBULATORIAL.toString())){
            
            return TipoProduto.ATENDIMENTO_AMBULATORIAL;
            
        }else if(nameEnum.equals(TipoProduto.CONSULTA.toString())){
            
            return TipoProduto.CONSULTA;
            
        }else if(nameEnum.equals(TipoProduto.CONSULTA_REVISAO.toString())){
            
            return TipoProduto.CONSULTA_REVISAO;     
        }else{
            return null;
        }
    
    }
    
}


