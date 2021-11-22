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
 *         &lt;element name="DETAIL_DO" type="{urn:sap-com:document:sap:rfc:functions}TABLE_OF_ZTY_WB_SHIPM_DO_DETAIL_DO"/&gt;
 *         &lt;element name="DETAIL_ITEM_DO" type="{urn:sap-com:document:sap:rfc:functions}TABLE_OF_ZTY_WB_SHIPM_DO_DETAIL_ITEM_DO"/&gt;
 *         &lt;element name="SHIPMENT" type="{urn:sap-com:document:sap:rfc:functions}TABLE_OF_ZTY_WB_SHIPM_DO_SHIPMENT"/&gt;
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
    "detaildo",
    "detailitemdo",
    "shipment"
})
@XmlRootElement(name = "ZFM_WB_SHIPM_DOResponse")
public class ZFMWBSHIPMDOResponse {

    @XmlElement(name = "DETAIL_DO", required = true)
    protected TABLEOFZTYWBSHIPMDODETAILDO detaildo;
    @XmlElement(name = "DETAIL_ITEM_DO", required = true)
    protected TABLEOFZTYWBSHIPMDODETAILITEMDO detailitemdo;
    @XmlElement(name = "SHIPMENT", required = true)
    protected TABLEOFZTYWBSHIPMDOSHIPMENT shipment;

    /**
     * Gets the value of the detaildo property.
     * 
     * @return
     *     possible object is
     *     {@link TABLEOFZTYWBSHIPMDODETAILDO }
     *     
     */
    public TABLEOFZTYWBSHIPMDODETAILDO getDETAILDO() {
        return detaildo;
    }

    /**
     * Sets the value of the detaildo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TABLEOFZTYWBSHIPMDODETAILDO }
     *     
     */
    public void setDETAILDO(TABLEOFZTYWBSHIPMDODETAILDO value) {
        this.detaildo = value;
    }

    /**
     * Gets the value of the detailitemdo property.
     * 
     * @return
     *     possible object is
     *     {@link TABLEOFZTYWBSHIPMDODETAILITEMDO }
     *     
     */
    public TABLEOFZTYWBSHIPMDODETAILITEMDO getDETAILITEMDO() {
        return detailitemdo;
    }

    /**
     * Sets the value of the detailitemdo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TABLEOFZTYWBSHIPMDODETAILITEMDO }
     *     
     */
    public void setDETAILITEMDO(TABLEOFZTYWBSHIPMDODETAILITEMDO value) {
        this.detailitemdo = value;
    }

    /**
     * Gets the value of the shipment property.
     * 
     * @return
     *     possible object is
     *     {@link TABLEOFZTYWBSHIPMDOSHIPMENT }
     *     
     */
    public TABLEOFZTYWBSHIPMDOSHIPMENT getSHIPMENT() {
        return shipment;
    }

    /**
     * Sets the value of the shipment property.
     * 
     * @param value
     *     allowed object is
     *     {@link TABLEOFZTYWBSHIPMDOSHIPMENT }
     *     
     */
    public void setSHIPMENT(TABLEOFZTYWBSHIPMDOSHIPMENT value) {
        this.shipment = value;
    }

}
