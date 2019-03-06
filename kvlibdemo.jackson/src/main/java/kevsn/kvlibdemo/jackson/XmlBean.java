/*
 * 2016年4月19日 
 */
package kevsn.kvlibdemo.jackson;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Kevin
 *
 */
@XmlRootElement(name = "xmlbean")
public class XmlBean {

	private String stringValue;

	private int intValue;

	private boolean boolValue;

	@XmlElement(name = "xml_string_value")
	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	// @XmlAttribute(name = "xml_int_value")
	@XmlElement(name = "xml_int_value")
	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	// @XmlAttribute(name = "xml_bool_value")
	@XmlElement(name = "xml_bool_value")
	public boolean isBoolValue() {
		return boolValue;
	}

	public void setBoolValue(boolean boolValue) {
		this.boolValue = boolValue;
	}

}
