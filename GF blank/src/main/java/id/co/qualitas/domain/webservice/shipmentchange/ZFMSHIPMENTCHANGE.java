//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.05.04 at 10:46:32 AM WIB 
//


package id.co.qualitas.domain.webservice.shipmentchange;

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
 *         &lt;element name="HEADERDATA" type="{urn:sap-com:document:sap:rfc:functions}BAPISHIPMENTHEADER"/&gt;
 *         &lt;element name="HEADERDATAACTION" type="{urn:sap-com:document:sap:rfc:functions}BAPISHIPMENTHEADERACTION"/&gt;
 *         &lt;element name="ITEMDATA" type="{urn:sap-com:document:sap:rfc:functions}TABLE_OF_BAPISHIPMENTITEM" minOccurs="0"/&gt;
 *         &lt;element name="ITEMDATAACTION" type="{urn:sap-com:document:sap:rfc:functions}TABLE_OF_BAPISHIPMENTITEMACTION" minOccurs="0"/&gt;
 *         &lt;element name="MATDOC" type="{urn:sap-com:document:sap:rfc:functions}TABLE_OF_ZTY_WB_SHIPMENT_CHANGE" minOccurs="0"/&gt;
 *         &lt;element name="RETURN" type="{urn:sap-com:document:sap:rfc:functions}TABLE_OF_BAPIRET2"/&gt;
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
    "headerdata",
    "headerdataaction",
    "itemdata",
    "itemdataaction",
    "matdoc",
    "_return"
})
@XmlRootElement(name = "ZFM_SHIPMENT_CHANGE")
public class ZFMSHIPMENTCHANGE {

    @XmlElement(name = "HEADERDATA", required = true)
    protected BAPISHIPMENTHEADER headerdata;
    @XmlElement(name = "HEADERDATAACTION", required = true)
    protected BAPISHIPMENTHEADERACTION headerdataaction;
    @XmlElement(name = "ITEMDATA")
    protected TABLEOFBAPISHIPMENTITEM itemdata;
    @XmlElement(name = "ITEMDATAACTION")
    protected TABLEOFBAPISHIPMENTITEMACTION itemdataaction;
    @XmlElement(name = "MATDOC")
    protected TABLEOFZTYWBSHIPMENTCHANGE matdoc;
    @XmlElement(name = "RETURN", required = true)
    protected TABLEOFBAPIRET2 _return;

    /**
     * Gets the value of the headerdata property.
     * 
     * @return
     *     possible object is
     *     {@link BAPISHIPMENTHEADER }
     *     
     */
    public BAPISHIPMENTHEADER getHEADERDATA() {
        return headerdata;
    }

    /**
     * Sets the value of the headerdata property.
     * 
     * @param value
     *     allowed object is
     *     {@link BAPISHIPMENTHEADER }
     *     
     */
    public void setHEADERDATA(BAPISHIPMENTHEADER value) {
        this.headerdata = value;
    }

    /**
     * Gets the value of the headerdataaction property.
     * 
     * @return
     *     possible object is
     *     {@link BAPISHIPMENTHEADERACTION }
     *     
     */
    public BAPISHIPMENTHEADERACTION getHEADERDATAACTION() {
        return headerdataaction;
    }

    /**
     * Sets the value of the headerdataaction property.
     * 
     * @param value
     *     allowed object is
     *     {@link BAPISHIPMENTHEADERACTION }
     *     
     */
    public void setHEADERDATAACTION(BAPISHIPMENTHEADERACTION value) {
        this.headerdataaction = value;
    }

    /**
     * Gets the value of the itemdata property.
     * 
     * @return
     *     possible object is
     *     {@link TABLEOFBAPISHIPMENTITEM }
     *     
     */
    public TABLEOFBAPISHIPMENTITEM getITEMDATA() {
        return itemdata;
    }

    /**
     * Sets the value of the itemdata property.
     * 
     * @param value
     *     allowed object is
     *     {@link TABLEOFBAPISHIPMENTITEM }
     *     
     */
    public void setITEMDATA(TABLEOFBAPISHIPMENTITEM value) {
        this.itemdata = value;
    }

    /**
     * Gets the value of the itemdataaction property.
     * 
     * @return
     *     possible object is
     *     {@link TABLEOFBAPISHIPMENTITEMACTION }
     *     
     */
    public TABLEOFBAPISHIPMENTITEMACTION getITEMDATAACTION() {
        return itemdataaction;
    }

    /**
     * Sets the value of the itemdataaction property.
     * 
     * @param value
     *     allowed object is
     *     {@link TABLEOFBAPISHIPMENTITEMACTION }
     *     
     */
    public void setITEMDATAACTION(TABLEOFBAPISHIPMENTITEMACTION value) {
        this.itemdataaction = value;
    }

    /**
     * Gets the value of the matdoc property.
     * 
     * @return
     *     possible object is
     *     {@link TABLEOFZTYWBSHIPMENTCHANGE }
     *     
     */
    public TABLEOFZTYWBSHIPMENTCHANGE getMATDOC() {
        return matdoc;
    }

    /**
     * Sets the value of the matdoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link TABLEOFZTYWBSHIPMENTCHANGE }
     *     
     */
    public void setMATDOC(TABLEOFZTYWBSHIPMENTCHANGE value) {
        this.matdoc = value;
    }

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link TABLEOFBAPIRET2 }
     *     
     */
    public TABLEOFBAPIRET2 getRETURN() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link TABLEOFBAPIRET2 }
     *     
     */
    public void setRETURN(TABLEOFBAPIRET2 value) {
        this._return = value;
    }

}
