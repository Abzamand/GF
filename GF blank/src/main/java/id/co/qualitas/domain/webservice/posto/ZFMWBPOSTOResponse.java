//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.04.20 at 01:50:15 PM WIB 
//


package id.co.qualitas.domain.webservice.posto;

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
 *         &lt;element name="DETAIL" type="{urn:sap-com:document:sap:rfc:functions}TABLE_OF_ZTY_WB_PO_STO_DETAIL"/&gt;
 *         &lt;element name="HEADER" type="{urn:sap-com:document:sap:rfc:functions}TABLE_OF_ZTY_WB_PO_STO_HEADER"/&gt;
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
    "header"
})
@XmlRootElement(name = "ZFM_WB_PO_STOResponse")
public class ZFMWBPOSTOResponse {

    @XmlElement(name = "DETAIL", required = true)
    protected TABLEOFZTYWBPOSTODETAIL detail;
    @XmlElement(name = "HEADER", required = true)
    protected TABLEOFZTYWBPOSTOHEADER header;

    /**
     * Gets the value of the detail property.
     * 
     * @return
     *     possible object is
     *     {@link TABLEOFZTYWBPOSTODETAIL }
     *     
     */
    public TABLEOFZTYWBPOSTODETAIL getDETAIL() {
        return detail;
    }

    /**
     * Sets the value of the detail property.
     * 
     * @param value
     *     allowed object is
     *     {@link TABLEOFZTYWBPOSTODETAIL }
     *     
     */
    public void setDETAIL(TABLEOFZTYWBPOSTODETAIL value) {
        this.detail = value;
    }

    /**
     * Gets the value of the header property.
     * 
     * @return
     *     possible object is
     *     {@link TABLEOFZTYWBPOSTOHEADER }
     *     
     */
    public TABLEOFZTYWBPOSTOHEADER getHEADER() {
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value
     *     allowed object is
     *     {@link TABLEOFZTYWBPOSTOHEADER }
     *     
     */
    public void setHEADER(TABLEOFZTYWBPOSTOHEADER value) {
        this.header = value;
    }

}
