//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.05.05 at 05:39:20 PM WIB 
//


package id.co.qualitas.domain.webservice.shipmdo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ZTY_WB_SHIPM_DO_SHIPMENT complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZTY_WB_SHIPM_DO_SHIPMENT"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TKNUM" type="{urn:sap-com:document:sap:rfc:functions}char10"/&gt;
 *         &lt;element name="SI_NUMBER" type="{urn:sap-com:document:sap:rfc:functions}char10"/&gt;
 *         &lt;element name="SI_ETD" type="{urn:sap-com:document:sap:rfc:functions}date10"/&gt;
 *         &lt;element name="LGORT" type="{urn:sap-com:document:sap:rfc:functions}char4"/&gt;
 *         &lt;element name="WERKS" type="{urn:sap-com:document:sap:rfc:functions}char4"/&gt;
 *         &lt;element name="TDLNR" type="{urn:sap-com:document:sap:rfc:functions}char10"/&gt;
 *         &lt;element name="TNDR_TRKID" type="{urn:sap-com:document:sap:rfc:functions}char35"/&gt;
 *         &lt;element name="EXTI1" type="{urn:sap-com:document:sap:rfc:functions}char20"/&gt;
 *         &lt;element name="VSART" type="{urn:sap-com:document:sap:rfc:functions}char2"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZTY_WB_SHIPM_DO_SHIPMENT", propOrder = {
    "tknum",
    "sinumber",
    "sietd",
    "lgort",
    "werks",
    "tdlnr",
    "tndrtrkid",
    "exti1",
    "vsart"
})
public class ZTYWBSHIPMDOSHIPMENT {

    @XmlElement(name = "TKNUM", required = true)
    protected String tknum;
    @XmlElement(name = "SI_NUMBER", required = true)
    protected String sinumber;
    @XmlElement(name = "SI_ETD", required = true)
    protected String sietd;
    @XmlElement(name = "LGORT", required = true)
    protected String lgort;
    @XmlElement(name = "WERKS", required = true)
    protected String werks;
    @XmlElement(name = "TDLNR", required = true)
    protected String tdlnr;
    @XmlElement(name = "TNDR_TRKID", required = true)
    protected String tndrtrkid;
    @XmlElement(name = "EXTI1", required = true)
    protected String exti1;
    @XmlElement(name = "VSART", required = true)
    protected String vsart;

    /**
     * Gets the value of the tknum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTKNUM() {
        return tknum;
    }

    /**
     * Sets the value of the tknum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTKNUM(String value) {
        this.tknum = value;
    }

    /**
     * Gets the value of the sinumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSINUMBER() {
        return sinumber;
    }

    /**
     * Sets the value of the sinumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSINUMBER(String value) {
        this.sinumber = value;
    }

    /**
     * Gets the value of the sietd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSIETD() {
        return sietd;
    }

    /**
     * Sets the value of the sietd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSIETD(String value) {
        this.sietd = value;
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
     * Gets the value of the tdlnr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTDLNR() {
        return tdlnr;
    }

    /**
     * Sets the value of the tdlnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTDLNR(String value) {
        this.tdlnr = value;
    }

    /**
     * Gets the value of the tndrtrkid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTNDRTRKID() {
        return tndrtrkid;
    }

    /**
     * Sets the value of the tndrtrkid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTNDRTRKID(String value) {
        this.tndrtrkid = value;
    }

    /**
     * Gets the value of the exti1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEXTI1() {
        return exti1;
    }

    /**
     * Sets the value of the exti1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEXTI1(String value) {
        this.exti1 = value;
    }

    /**
     * Gets the value of the vsart property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVSART() {
        return vsart;
    }

    /**
     * Sets the value of the vsart property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVSART(String value) {
        this.vsart = value;
    }

}
