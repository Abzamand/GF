//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.01.07 at 04:19:12 PM WIB 
//


package id.co.qualitas.domain.webservice.goodsmvt;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for _-spe_-bapi2017GmRefEwm complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="_-spe_-bapi2017GmRefEwm"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RefDocEwm" type="{urn:sap-com:document:sap:rfc:functions}char16"/&gt;
 *         &lt;element name="Logsys" type="{urn:sap-com:document:sap:rfc:functions}char10"/&gt;
 *         &lt;element name="GtsScrapNo" type="{urn:sap-com:document:sap:rfc:functions}char35"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "_-spe_-bapi2017GmRefEwm", propOrder = {
    "refDocEwm",
    "logsys",
    "gtsScrapNo"
})
public class SpeBapi2017GmRefEwm {

    @XmlElement(name = "RefDocEwm", required = true)
    protected String refDocEwm;
    @XmlElement(name = "Logsys", required = true)
    protected String logsys;
    @XmlElement(name = "GtsScrapNo", required = true)
    protected String gtsScrapNo;

    /**
     * Gets the value of the refDocEwm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefDocEwm() {
        return refDocEwm;
    }

    /**
     * Sets the value of the refDocEwm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefDocEwm(String value) {
        this.refDocEwm = value;
    }

    /**
     * Gets the value of the logsys property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogsys() {
        return logsys;
    }

    /**
     * Sets the value of the logsys property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogsys(String value) {
        this.logsys = value;
    }

    /**
     * Gets the value of the gtsScrapNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGtsScrapNo() {
        return gtsScrapNo;
    }

    /**
     * Sets the value of the gtsScrapNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGtsScrapNo(String value) {
        this.gtsScrapNo = value;
    }

}
