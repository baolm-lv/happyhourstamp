//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.07.06 at 12:21:49 PM CST 
//


package com.lvmama.vst.hhs.downgrade.eureka.generated;

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
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="name" type="{}dcNameType"/>
 *         &lt;element name="metadata" type="{}amazonMetdataType" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "dataCenterInfo")
public class DataCenterInfo {

    @XmlElement(required = true)
    protected DcNameType name;
    protected AmazonMetdataType metadata;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link DcNameType }
     *     
     */
    public DcNameType getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link DcNameType }
     *     
     */
    public void setName(DcNameType value) {
        this.name = value;
    }

    /**
     * Gets the value of the metadata property.
     * 
     * @return
     *     possible object is
     *     {@link AmazonMetdataType }
     *     
     */
    public AmazonMetdataType getMetadata() {
        return metadata;
    }

    /**
     * Sets the value of the metadata property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmazonMetdataType }
     *     
     */
    public void setMetadata(AmazonMetdataType value) {
        this.metadata = value;
    }

}
