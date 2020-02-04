
package br.com.comven.corews.transacao.client.prepago.cpf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Confirma_Compromisso_Estoque_Pre_PagoResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "confirmaCompromissoEstoquePrePagoResult"
})
@XmlRootElement(name = "Confirma_Compromisso_Estoque_Pre_PagoResponse")
public class ConfirmaCompromissoEstoquePrePagoResponse {

    @XmlElement(name = "Confirma_Compromisso_Estoque_Pre_PagoResult")
    protected int confirmaCompromissoEstoquePrePagoResult;

    /**
     * Gets the value of the confirmaCompromissoEstoquePrePagoResult property.
     * 
     */
    public int getConfirmaCompromissoEstoquePrePagoResult() {
        return confirmaCompromissoEstoquePrePagoResult;
    }

    /**
     * Sets the value of the confirmaCompromissoEstoquePrePagoResult property.
     * 
     */
    public void setConfirmaCompromissoEstoquePrePagoResult(int value) {
        this.confirmaCompromissoEstoquePrePagoResult = value;
    }

}
