
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
 *         &lt;element name="Status_Cliente_Sem_Credito_Pre_PagoResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "statusClienteSemCreditoPrePagoResult"
})
@XmlRootElement(name = "Status_Cliente_Sem_Credito_Pre_PagoResponse")
public class StatusClienteSemCreditoPrePagoResponse {

    @XmlElement(name = "Status_Cliente_Sem_Credito_Pre_PagoResult")
    protected String statusClienteSemCreditoPrePagoResult;

    /**
     * Gets the value of the statusClienteSemCreditoPrePagoResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusClienteSemCreditoPrePagoResult() {
        return statusClienteSemCreditoPrePagoResult;
    }

    /**
     * Sets the value of the statusClienteSemCreditoPrePagoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusClienteSemCreditoPrePagoResult(String value) {
        this.statusClienteSemCreditoPrePagoResult = value;
    }

}
