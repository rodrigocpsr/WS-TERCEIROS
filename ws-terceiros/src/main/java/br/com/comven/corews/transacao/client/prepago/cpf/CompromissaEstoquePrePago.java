
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
 *         &lt;element name="Credencial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CPF_CNPJ" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "credencial",
    "cpfcnpj"
})
@XmlRootElement(name = "Compromissa_Estoque_Pre_Pago")
public class CompromissaEstoquePrePago {

    @XmlElement(name = "Credencial")
    protected String credencial;
    @XmlElement(name = "CPF_CNPJ")
    protected String cpfcnpj;

    /**
     * Gets the value of the credencial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCredencial() {
        return credencial;
    }

    /**
     * Sets the value of the credencial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCredencial(String value) {
        this.credencial = value;
    }

    /**
     * Gets the value of the cpfcnpj property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCPFCNPJ() {
        return cpfcnpj;
    }

    /**
     * Sets the value of the cpfcnpj property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCPFCNPJ(String value) {
        this.cpfcnpj = value;
    }

}
