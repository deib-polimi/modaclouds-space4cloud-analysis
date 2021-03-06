//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.07.31 at 05:18:06 PM CEST 
//


package it.polimi.modaclouds.space4cloud.analysis.results;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import org.jvnet.jaxb2_commons.lang.CopyStrategy;
import org.jvnet.jaxb2_commons.lang.CopyTo;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy;
import org.jvnet.jaxb2_commons.lang.HashCode;
import org.jvnet.jaxb2_commons.lang.HashCodeStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBCopyStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBHashCodeStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBMergeStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBToStringStrategy;
import org.jvnet.jaxb2_commons.lang.MergeFrom;
import org.jvnet.jaxb2_commons.lang.MergeStrategy;
import org.jvnet.jaxb2_commons.lang.ToString;
import org.jvnet.jaxb2_commons.lang.ToStringStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.jvnet.jaxb2_commons.locator.util.LocatorUtils;


/**
 * <p>Java class for HourlyRTType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HourlyRTType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="hour" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="responseTime" type="{http://www.w3.org/2001/XMLSchema}double" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HourlyRTType", namespace = "")
public class HourlyRTType
    implements Cloneable, CopyTo, Equals, HashCode, MergeFrom, ToString
{

    @XmlAttribute(name = "hour")
    protected Integer hour;
    @XmlAttribute(name = "responseTime")
    protected Double responseTime;

    /**
     * Gets the value of the hour property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHour() {
        return hour;
    }

    /**
     * Sets the value of the hour property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHour(Integer value) {
        this.hour = value;
    }

    /**
     * Gets the value of the responseTime property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getResponseTime() {
        return responseTime;
    }

    /**
     * Sets the value of the responseTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setResponseTime(Double value) {
        this.responseTime = value;
    }

    public String toString() {
        final ToStringStrategy strategy = JAXBToStringStrategy.INSTANCE;
        final StringBuilder buffer = new StringBuilder();
        append(null, buffer, strategy);
        return buffer.toString();
    }

    public StringBuilder append(ObjectLocator locator, StringBuilder buffer, ToStringStrategy strategy) {
        strategy.appendStart(locator, this, buffer);
        appendFields(locator, buffer, strategy);
        strategy.appendEnd(locator, this, buffer);
        return buffer;
    }

    public StringBuilder appendFields(ObjectLocator locator, StringBuilder buffer, ToStringStrategy strategy) {
        {
            Integer theHour;
            theHour = this.getHour();
            strategy.appendField(locator, this, "hour", buffer, theHour);
        }
        {
            Double theResponseTime;
            theResponseTime = this.getResponseTime();
            strategy.appendField(locator, this, "responseTime", buffer, theResponseTime);
        }
        return buffer;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof HourlyRTType)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final HourlyRTType that = ((HourlyRTType) object);
        {
            Integer lhsHour;
            lhsHour = this.getHour();
            Integer rhsHour;
            rhsHour = that.getHour();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "hour", lhsHour), LocatorUtils.property(thatLocator, "hour", rhsHour), lhsHour, rhsHour)) {
                return false;
            }
        }
        {
            Double lhsResponseTime;
            lhsResponseTime = this.getResponseTime();
            Double rhsResponseTime;
            rhsResponseTime = that.getResponseTime();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "responseTime", lhsResponseTime), LocatorUtils.property(thatLocator, "responseTime", rhsResponseTime), lhsResponseTime, rhsResponseTime)) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object object) {
        final EqualsStrategy strategy = JAXBEqualsStrategy.INSTANCE;
        return equals(null, null, object, strategy);
    }

    public int hashCode(ObjectLocator locator, HashCodeStrategy strategy) {
        int currentHashCode = 1;
        {
            Integer theHour;
            theHour = this.getHour();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "hour", theHour), currentHashCode, theHour);
        }
        {
            Double theResponseTime;
            theResponseTime = this.getResponseTime();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "responseTime", theResponseTime), currentHashCode, theResponseTime);
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

    public Object clone() {
        return copyTo(createNewInstance());
    }

    public Object copyTo(Object target) {
        final CopyStrategy strategy = JAXBCopyStrategy.INSTANCE;
        return copyTo(null, target, strategy);
    }

    public Object copyTo(ObjectLocator locator, Object target, CopyStrategy strategy) {
        final Object draftCopy = ((target == null)?createNewInstance():target);
        if (draftCopy instanceof HourlyRTType) {
            final HourlyRTType copy = ((HourlyRTType) draftCopy);
            if (this.hour!= null) {
                Integer sourceHour;
                sourceHour = this.getHour();
                Integer copyHour = ((Integer) strategy.copy(LocatorUtils.property(locator, "hour", sourceHour), sourceHour));
                copy.setHour(copyHour);
            } else {
                copy.hour = null;
            }
            if (this.responseTime!= null) {
                Double sourceResponseTime;
                sourceResponseTime = this.getResponseTime();
                Double copyResponseTime = ((Double) strategy.copy(LocatorUtils.property(locator, "responseTime", sourceResponseTime), sourceResponseTime));
                copy.setResponseTime(copyResponseTime);
            } else {
                copy.responseTime = null;
            }
        }
        return draftCopy;
    }

    public Object createNewInstance() {
        return new HourlyRTType();
    }

    public void mergeFrom(Object left, Object right) {
        final MergeStrategy strategy = JAXBMergeStrategy.INSTANCE;
        mergeFrom(null, null, left, right, strategy);
    }

    public void mergeFrom(ObjectLocator leftLocator, ObjectLocator rightLocator, Object left, Object right, MergeStrategy strategy) {
        if (right instanceof HourlyRTType) {
            final HourlyRTType target = this;
            final HourlyRTType leftObject = ((HourlyRTType) left);
            final HourlyRTType rightObject = ((HourlyRTType) right);
            {
                Integer lhsHour;
                lhsHour = leftObject.getHour();
                Integer rhsHour;
                rhsHour = rightObject.getHour();
                Integer mergedHour = ((Integer) strategy.merge(LocatorUtils.property(leftLocator, "hour", lhsHour), LocatorUtils.property(rightLocator, "hour", rhsHour), lhsHour, rhsHour));
                target.setHour(mergedHour);
            }
            {
                Double lhsResponseTime;
                lhsResponseTime = leftObject.getResponseTime();
                Double rhsResponseTime;
                rhsResponseTime = rightObject.getResponseTime();
                Double mergedResponseTime = ((Double) strategy.merge(LocatorUtils.property(leftLocator, "responseTime", lhsResponseTime), LocatorUtils.property(rightLocator, "responseTime", rhsResponseTime), lhsResponseTime, rhsResponseTime));
                target.setResponseTime(mergedResponseTime);
            }
        }
    }

}
