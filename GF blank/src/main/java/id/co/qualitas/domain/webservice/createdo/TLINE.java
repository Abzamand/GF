//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.05.07 at 02:39:17 PM WIB 
//


package id.co.qualitas.domain.webservice.createdo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TLINE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TLINE"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TDFORMAT" type="{urn:sap-com:document:sap:rfc:functions}char2"/&gt;
 *         &lt;element name="TDLINE" type="{urn:sap-com:document:sap:rfc:functions}char132"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TLINE", propOrder = {
    "tdformat",
    "tdline"
})
public class TLINE {

    @XmlElement(name = "TDFORMAT", required = true)
    protected String tdformat;
    @XmlElement(name = "TDLINE", required = true)
    protected String tdline;

    /**
     * Gets the value of the tdformat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTDFORMAT() {
        return tdformat;
    }

    /**
     * Sets the value of the tdformat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTDFORMAT(String value) {
        this.tdformat = value;
    }

    /**
     * Gets the value of the tdline property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTDLINE() {
        return tdline;
    }

    /**
     * Sets the value of the tdline property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTDLINE(String value) {
        this.tdline = value;
    }

}