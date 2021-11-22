//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.05.05 at 05:39:20 PM WIB 
//


package id.co.qualitas.domain.webservice.shipmdo;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ZTY_WB_SHIPM_DO_DETAIL_ITEM_DO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZTY_WB_SHIPM_DO_DETAIL_ITEM_DO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="VBELN" type="{urn:sap-com:document:sap:rfc:functions}char10"/&gt;
 *         &lt;element name="POSNR" type="{urn:sap-com:document:sap:rfc:functions}numeric6"/&gt;
 *         &lt;element name="UECHA" type="{urn:sap-com:document:sap:rfc:functions}numeric6"/&gt;
 *         &lt;element name="MATNR" type="{urn:sap-com:document:sap:rfc:functions}char18"/&gt;
 *         &lt;element name="ARKTX" type="{urn:sap-com:document:sap:rfc:functions}char40"/&gt;
 *         &lt;element name="CHARG" type="{urn:sap-com:document:sap:rfc:functions}char10"/&gt;
 *         &lt;element name="VFDAT" type="{urn:sap-com:document:sap:rfc:functions}date10"/&gt;
 *         &lt;element name="LFIMG" type="{urn:sap-com:document:sap:rfc:functions}quantum13.3"/&gt;
 *         &lt;element name="VRKME" type="{urn:sap-com:document:sap:rfc:functions}unit3"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZTY_WB_SHIPM_DO_DETAIL_ITEM_DO", propOrder = {
    "vbeln",
    "posnr",
    "uecha",
    "matnr",
    "arktx",
    "charg",
    "vfdat",
    "lfimg",
    "vrkme"
})
public class ZTYWBSHIPMDODETAILITEMDO {

    @XmlElement(name = "VBELN", required = true)
    protected String vbeln;
    @XmlElement(name = "POSNR", required = true)
    protected String posnr;
    @XmlElement(name = "UECHA", required = true)
    protected String uecha;
    @XmlElement(name = "MATNR", required = true)
    protected String matnr;
    @XmlElement(name = "ARKTX", required = true)
    protected String arktx;
    @XmlElement(name = "CHARG", required = true)
    protected String charg;
    @XmlElement(name = "VFDAT", required = true)
    protected String vfdat;
    @XmlElement(name = "LFIMG", required = true)
    protected BigDecimal lfimg;
    @XmlElement(name = "VRKME", required = true)
    protected String vrkme;

    /**
     * Gets the value of the vbeln property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVBELN() {
        return vbeln;
    }

    /**
     * Sets the value of the vbeln property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVBELN(String value) {
        this.vbeln = value;
    }

    /**
     * Gets the value of the posnr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPOSNR() {
        return posnr;
    }

    /**
     * Sets the value of the posnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPOSNR(String value) {
        this.posnr = value;
    }

    /**
     * Gets the value of the uecha property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUECHA() {
        return uecha;
    }

    /**
     * Sets the value of the uecha property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUECHA(String value) {
        this.uecha = value;
    }

    /**
     * Gets the value of the matnr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMATNR() {
        return matnr;
    }

    /**
     * Sets the value of the matnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMATNR(String value) {
        this.matnr = value;
    }

    /**
     * Gets the value of the arktx property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getARKTX() {
        return arktx;
    }

    /**
     * Sets the value of the arktx property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setARKTX(String value) {
        this.arktx = value;
    }

    /**
     * Gets the value of the charg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCHARG() {
        return charg;
    }

    /**
     * Sets the value of the charg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCHARG(String value) {
        this.charg = value;
    }

    /**
     * Gets the value of the vfdat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVFDAT() {
        return vfdat;
    }

    /**
     * Sets the value of the vfdat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVFDAT(String value) {
        this.vfdat = value;
    }

    /**
     * Gets the value of the lfimg property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLFIMG() {
        return lfimg;
    }

    /**
     * Sets the value of the lfimg property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLFIMG(BigDecimal value) {
        this.lfimg = value;
    }

    /**
     * Gets the value of the vrkme property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVRKME() {
        return vrkme;
    }

    /**
     * Sets the value of the vrkme property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVRKME(String value) {
        this.vrkme = value;
    }

}
