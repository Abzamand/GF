//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.12.15 at 04:06:19 PM WIB 
//


package id.co.qualitas.domain.webservice.tposting;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ZTY_WB_TP_HEADER complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZTY_WB_TP_HEADER"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="MBLNR" type="{urn:sap-com:document:sap:rfc:functions}char10"/&gt;
 *         &lt;element name="BUDAT" type="{urn:sap-com:document:sap:rfc:functions}date10"/&gt;
 *         &lt;element name="BKTXT" type="{urn:sap-com:document:sap:rfc:functions}char25"/&gt;
 *         &lt;element name="WERKS" type="{urn:sap-com:document:sap:rfc:functions}char4"/&gt;
 *         &lt;element name="LGORT" type="{urn:sap-com:document:sap:rfc:functions}char4"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZTY_WB_TP_HEADER", propOrder = {
    "mblnr",
    "budat",
    "bktxt",
    "werks",
    "lgort"
})
public class ZTYWBTPHEADER {

    @XmlElement(name = "MBLNR", required = true)
    protected String mblnr;
    @XmlElement(name = "BUDAT", required = true)
    protected String budat;
    @XmlElement(name = "BKTXT", required = true)
    protected String bktxt;
    @XmlElement(name = "WERKS", required = true)
    protected String werks;
    @XmlElement(name = "LGORT", required = true)
    protected String lgort;

    /**
     * Gets the value of the mblnr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMBLNR() {
        return mblnr;
    }

    /**
     * Sets the value of the mblnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMBLNR(String value) {
        this.mblnr = value;
    }

    /**
     * Gets the value of the budat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBUDAT() {
        return budat;
    }

    /**
     * Sets the value of the budat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBUDAT(String value) {
        this.budat = value;
    }

    /**
     * Gets the value of the bktxt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBKTXT() {
        return bktxt;
    }

    /**
     * Sets the value of the bktxt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBKTXT(String value) {
        this.bktxt = value;
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

}
