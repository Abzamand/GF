//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.05.05 at 05:42:42 PM WIB 
//


package id.co.qualitas.domain.webservice.postoso;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ZTY_WB_POSTO_SO_DETAIL complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ZTY_WB_POSTO_SO_DETAIL"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ZDOCNO" type="{urn:sap-com:document:sap:rfc:functions}char10"/&gt;
 *         &lt;element name="POSNR" type="{urn:sap-com:document:sap:rfc:functions}numeric6"/&gt;
 *         &lt;element name="MATNR" type="{urn:sap-com:document:sap:rfc:functions}char18"/&gt;
 *         &lt;element name="TXZ01" type="{urn:sap-com:document:sap:rfc:functions}char40"/&gt;
 *         &lt;element name="MENGE" type="{urn:sap-com:document:sap:rfc:functions}quantum17.0"/&gt;
 *         &lt;element name="MEINS" type="{urn:sap-com:document:sap:rfc:functions}unit3"/&gt;
 *         &lt;element name="ZPJQTY" type="{urn:sap-com:document:sap:rfc:functions}quantum13.3"/&gt;
 *         &lt;element name="SI_NUMBER" type="{urn:sap-com:document:sap:rfc:functions}char10"/&gt;
 *         &lt;element name="TKNUM" type="{urn:sap-com:document:sap:rfc:functions}char10"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZTY_WB_POSTO_SO_DETAIL", propOrder = {
    "zdocno",
    "posnr",
    "matnr",
    "txz01",
    "menge",
    "meins",
    "zpjqty",
    "sinumber",
    "tknum"
})
public class ZTYWBPOSTOSODETAIL {

    @XmlElement(name = "ZDOCNO", required = true)
    protected String zdocno;
    @XmlElement(name = "POSNR", required = true)
    protected String posnr;
    @XmlElement(name = "MATNR", required = true)
    protected String matnr;
    @XmlElement(name = "TXZ01", required = true)
    protected String txz01;
    @XmlElement(name = "MENGE", required = true)
    protected BigDecimal menge;
    @XmlElement(name = "MEINS", required = true)
    protected String meins;
    @XmlElement(name = "ZPJQTY", required = true)
    protected BigDecimal zpjqty;
    @XmlElement(name = "SI_NUMBER", required = true)
    protected String sinumber;
    @XmlElement(name = "TKNUM", required = true)
    protected String tknum;

    /**
     * Gets the value of the zdocno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZDOCNO() {
        return zdocno;
    }

    /**
     * Sets the value of the zdocno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZDOCNO(String value) {
        this.zdocno = value;
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
     * Gets the value of the txz01 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTXZ01() {
        return txz01;
    }

    /**
     * Sets the value of the txz01 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTXZ01(String value) {
        this.txz01 = value;
    }

    /**
     * Gets the value of the menge property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMENGE() {
        return menge;
    }

    /**
     * Sets the value of the menge property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMENGE(BigDecimal value) {
        this.menge = value;
    }

    /**
     * Gets the value of the meins property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMEINS() {
        return meins;
    }

    /**
     * Sets the value of the meins property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMEINS(String value) {
        this.meins = value;
    }

    /**
     * Gets the value of the zpjqty property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getZPJQTY() {
        return zpjqty;
    }

    /**
     * Sets the value of the zpjqty property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setZPJQTY(BigDecimal value) {
        this.zpjqty = value;
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

}
