
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
 *         &lt;element name="Sincronizar_Cadastro_Empresa_BOResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "sincronizarCadastroEmpresaBOResult"
})
@XmlRootElement(name = "Sincronizar_Cadastro_Empresa_BOResponse")
public class SincronizarCadastroEmpresaBOResponse {

    @XmlElement(name = "Sincronizar_Cadastro_Empresa_BOResult")
    protected int sincronizarCadastroEmpresaBOResult;

    /**
     * Gets the value of the sincronizarCadastroEmpresaBOResult property.
     * 
     */
    public int getSincronizarCadastroEmpresaBOResult() {
        return sincronizarCadastroEmpresaBOResult;
    }

    /**
     * Sets the value of the sincronizarCadastroEmpresaBOResult property.
     * 
     */
    public void setSincronizarCadastroEmpresaBOResult(int value) {
        this.sincronizarCadastroEmpresaBOResult = value;
    }

}
