//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.12.18 at 12:24:04 PM WIB 
//


package id.co.qualitas.domain.webservice.pofg;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ZTY_WB_POFG_HEADER complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZTY_WB_POFG_HEADER"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EBELN" type="{urn:sap-com:document:sap:rfc:functions}char10"/&gt;
 *         &lt;element name="AEDAT" type="{urn:sap-com:document:sap:rfc:functions}date10"/&gt;
 *         &lt;element name="BSART" type="{urn:sap-com:document:sap:rfc:functions}char4"/&gt;
 *         &lt;element name="WERKS" type="{urn:sap-com:document:sap:rfc:functions}char4"/&gt;
 *         &lt;element name="LGORT" type="{urn:sap-com:document:sap:rfc:functions}char4"/&gt;
 *         &lt;element name="HTEXT" type="{urn:sap-com:document:sap:rfc:functions}char100"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZTY_WB_POFG_HEADER", propOrder = {
    "ebeln",
    "aedat",
    "bsart",
    "werks",
    "lgort",
    "htext"
})
public class ZTYWBPOFGHEADER {

    @XmlElement(name = "EBELN", required = true)
    protected String ebeln;
    @XmlElement(name = "AEDAT", required = true)
    protected String aedat;
    @XmlElement(name = "BSART", required = true)
    protected String bsart;
    @XmlElement(name = "WERKS", required = true)
    protected String werks;
    @XmlElement(name = "LGORT", required = true)
    protected String lgort;
    @XmlElement(name = "HTEXT", required = true)
    protected String htext;

    /**
     * Gets the value of the ebeln property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEBELN() {
        return ebeln;
    }

    /**
     * Sets the value of the ebeln property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEBELN(String value) {
        this.ebeln = value;
    }

    /**
     * Gets the value of the aedat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAEDAT() {
        return aedat;
    }

    /**
     * Sets the value of the aedat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAEDAT(String value) {
        this.aedat = value;
    }

    /**
     * Gets the value of the bsart property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBSART() {
        return bsart;
    }

    /**
     * Sets the value of the bsart property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBSART(String value) {
        this.bsart = value;
    }

    /**
     * Gets the value of the werks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWERKS() {
        return werks;
    }

    /**
     * Sets the value of the werks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWERKS(String value) {
        this.werks = value;
    }

    /**
     * Gets the value of the lgort property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLGORT() {
        return lgort;
    }

    /**
     * Sets the value of the lgort property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLGORT(String value) {
        this.lgort = value;
    }

    /**
     * Gets the value of the htext property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHTEXT() {
        return htext;
    }

    /**
     * Sets the value of the htext property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHTEXT(String value) {
        this.htext = value;
    }

}