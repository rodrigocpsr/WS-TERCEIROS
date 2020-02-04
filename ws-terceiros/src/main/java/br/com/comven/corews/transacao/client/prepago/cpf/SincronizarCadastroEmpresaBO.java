
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
 *         &lt;element name="Tipo_Empresa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Grupo_Empresarial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Tipo_Faturamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Aceita_Procuracao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Nome_Razao_Social" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Telefone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DDD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UF" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Municipio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Tipo_Logradouro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Logradouro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Numero" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Complemento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Bairro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CEP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Aceita_Procuracao_UF" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "cpfcnpj",
    "tipoEmpresa",
    "grupoEmpresarial",
    "tipoFaturamento",
    "aceitaProcuracao",
    "nomeRazaoSocial",
    "telefone",
    "ddd",
    "uf",
    "municipio",
    "tipoLogradouro",
    "logradouro",
    "numero",
    "complemento",
    "bairro",
    "cep",
    "status",
    "aceitaProcuracaoUF"
})
@XmlRootElement(name = "Sincronizar_Cadastro_Empresa_BO")
public class SincronizarCadastroEmpresaBO {

    @XmlElement(name = "Credencial")
    protected String credencial;
    @XmlElement(name = "CPF_CNPJ")
    protected String cpfcnpj;
    @XmlElement(name = "Tipo_Empresa")
    protected String tipoEmpresa;
    @XmlElement(name = "Grupo_Empresarial")
    protected String grupoEmpresarial;
    @XmlElement(name = "Tipo_Faturamento")
    protected String tipoFaturamento;
    @XmlElement(name = "Aceita_Procuracao")
    protected String aceitaProcuracao;
    @XmlElement(name = "Nome_Razao_Social")
    protected String nomeRazaoSocial;
    @XmlElement(name = "Telefone")
    protected String telefone;
    @XmlElement(name = "DDD")
    protected String ddd;
    @XmlElement(name = "UF")
    protected String uf;
    @XmlElement(name = "Municipio")
    protected String municipio;
    @XmlElement(name = "Tipo_Logradouro")
    protected String tipoLogradouro;
    @XmlElement(name = "Logradouro")
    protected String logradouro;
    @XmlElement(name = "Numero")
    protected String numero;
    @XmlElement(name = "Complemento")
    protected String complemento;
    @XmlElement(name = "Bairro")
    protected String bairro;
    @XmlElement(name = "CEP")
    protected String cep;
    @XmlElement(name = "Status")
    protected String status;
    @XmlElement(name = "Aceita_Procuracao_UF")
    protected String aceitaProcuracaoUF;

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

    /**
     * Gets the value of the tipoEmpresa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoEmpresa() {
        return tipoEmpresa;
    }

    /**
     * Sets the value of the tipoEmpresa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoEmpresa(String value) {
        this.tipoEmpresa = value;
    }

    /**
     * Gets the value of the grupoEmpresarial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrupoEmpresarial() {
        return grupoEmpresarial;
    }

    /**
     * Sets the value of the grupoEmpresarial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrupoEmpresarial(String value) {
        this.grupoEmpresarial = value;
    }

    /**
     * Gets the value of the tipoFaturamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoFaturamento() {
        return tipoFaturamento;
    }

    /**
     * Sets the value of the tipoFaturamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoFaturamento(String value) {
        this.tipoFaturamento = value;
    }

    /**
     * Gets the value of the aceitaProcuracao property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAceitaProcuracao() {
        return aceitaProcuracao;
    }

    /**
     * Sets the value of the aceitaProcuracao property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAceitaProcuracao(String value) {
        this.aceitaProcuracao = value;
    }

    /**
     * Gets the value of the nomeRazaoSocial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeRazaoSocial() {
        return nomeRazaoSocial;
    }

    /**
     * Sets the value of the nomeRazaoSocial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeRazaoSocial(String value) {
        this.nomeRazaoSocial = value;
    }

    /**
     * Gets the value of the telefone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Sets the value of the telefone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefone(String value) {
        this.telefone = value;
    }

    /**
     * Gets the value of the ddd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDDD() {
        return ddd;
    }

    /**
     * Sets the value of the ddd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDDD(String value) {
        this.ddd = value;
    }

    /**
     * Gets the value of the uf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUF() {
        return uf;
    }

    /**
     * Sets the value of the uf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUF(String value) {
        this.uf = value;
    }

    /**
     * Gets the value of the municipio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMunicipio() {
        return municipio;
    }

    /**
     * Sets the value of the municipio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMunicipio(String value) {
        this.municipio = value;
    }

    /**
     * Gets the value of the tipoLogradouro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoLogradouro() {
        return tipoLogradouro;
    }

    /**
     * Sets the value of the tipoLogradouro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoLogradouro(String value) {
        this.tipoLogradouro = value;
    }

    /**
     * Gets the value of the logradouro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogradouro() {
        return logradouro;
    }

    /**
     * Sets the value of the logradouro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogradouro(String value) {
        this.logradouro = value;
    }

    /**
     * Gets the value of the numero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Sets the value of the numero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumero(String value) {
        this.numero = value;
    }

    /**
     * Gets the value of the complemento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComplemento() {
        return complemento;
    }

    /**
     * Sets the value of the complemento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComplemento(String value) {
        this.complemento = value;
    }

    /**
     * Gets the value of the bairro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBairro() {
        return bairro;
    }

    /**
     * Sets the value of the bairro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBairro(String value) {
        this.bairro = value;
    }

    /**
     * Gets the value of the cep property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCEP() {
        return cep;
    }

    /**
     * Sets the value of the cep property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCEP(String value) {
        this.cep = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the aceitaProcuracaoUF property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAceitaProcuracaoUF() {
        return aceitaProcuracaoUF;
    }

    /**
     * Sets the value of the aceitaProcuracaoUF property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAceitaProcuracaoUF(String value) {
        this.aceitaProcuracaoUF = value;
    }

}
