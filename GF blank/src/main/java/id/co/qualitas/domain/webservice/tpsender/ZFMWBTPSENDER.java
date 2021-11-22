//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.01.12 at 01:26:31 PM WIB 
//


package id.co.qualitas.domain.webservice.tpsender;

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
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DETAIL" type="{urn:sap-com:document:sap:rfc:functions}TABLE_OF_ZTY_WB_TP_DETAIL"/&gt;
 *         &lt;element name="HEADER" type="{urn:sap-com:document:sap:rfc:functions}TABLE_OF_ZTY_WB_TP_HEADER"/&gt;
 *         &lt;element name="P_BUDAT" type="{urn:sap-com:document:sap:rfc:functions}numeric6"/&gt;
 *         &lt;element name="P_LGORT" type="{urn:sap-com:document:sap:rfc:functions}char4"/&gt;
 *         &lt;element name="P_WERKS" type="{urn:sap-com:document:sap:rfc:functions}char4"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "detail",
    "header",
    "pbudat",
    "plgort",
    "pwerks"
})
@XmlRootElement(name = "ZFM_WB_TP_SENDER")
public class ZFMWBTPSENDER {

    @XmlElement(name = "DETAIL", required = true)
    protected TABLEOFZTYWBTPDETAIL detail;
    @XmlElement(name = "HEADER", required = true)
    protected TABLEOFZTYWBTPHEADER header;
    @XmlElement(name = "P_BUDAT", required = true)
    protected String pbudat;
    @XmlElement(name = "P_LGORT", required = true)
    protected String plgort;
    @XmlElement(name = "P_WERKS", required = true)
    protected String pwerks;

    /**
     * Gets the value of the detail property.
     * 
     * @return
     *     possible object is
     *     {@link TABLEOFZTYWBTPDETAIL }
     *     
     */
    public TABLEOFZTYWBTPDETAIL getDETAIL() {
        return detail;
    }

    /**
     * Sets the value of the detail property.
     * 
     * @param value
     *     allowed object is
     *     {@link TABLEOFZTYWBTPDETAIL }
     *     
     */
    public void setDETAIL(TABLEOFZTYWBTPDETAIL value) {
        this.detail = value;
    }

    /**
     * Gets the value of the header property.
     * 
     * @return
     *     possible object is
     *     {@link TABLEOFZTYWBTPHEADER }
     *     
     */
    public TABLEOFZTYWBTPHEADER getHEADER() {
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value
     *     allowed object is
     *     {@link TABLEOFZTYWBTPHEADER }
     *     
     */
    public void setHEADER(TABLEOFZTYWBTPHEADER value) {
        this.header = value;
    }

    /**
     * Gets the value of the pbudat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPBUDAT() {
        return pbudat;
    }

    /**
     * Sets the value of the pbudat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPBUDAT(String value) {
        this.pbudat = value;
    }

    /**
     * Gets the value of the plgort property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPLGORT() {
        return plgort;
    }

    /**
     * Sets the value of the plgort property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPLGORT(String value) {
        this.plgort = value;
    }

    /**
     * Gets the value of the pwerks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPWERKS() {
        return pwerks;
    }

    /**
     * Sets the value of the pwerks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPWERKS(String value) {
        this.pwerks = value;
    }

}