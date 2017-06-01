//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.05.19 at 03:23:59 PM IST 
//

package www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model;

import java.io.Serializable;
import java.math.BigDecimal;


public class IngredientVariantDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2198505699784866775L;

	private String _id;
	private Long version;

	/**
	 * Added to avoid a runtime error whereby the detachAll property is checked
	 * for existence but not actually used.
	 */
	private String detachAll;
	protected int productId;
	protected String alias;
	protected UnitOfMeasure uom;

	protected BigDecimal quantity;
	protected boolean defaultSetting;
	protected String status = "ACTIVE";
	protected boolean captured = true;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getDetachAll() {
		return detachAll;
	}

	public void setDetachAll(String detachAll) {
		this.detachAll = detachAll;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public UnitOfMeasure getUom() {
		return uom;
	}

	public void setUom(UnitOfMeasure uom) {
		this.uom = uom;
	}

	/**
	 * Gets the value of the alias property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * Sets the value of the alias property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setAlias(String value) {
		this.alias = value;
	}

	/**
	 * Gets the value of the quantity property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public BigDecimal getQuantity() {
		return quantity;
	}

	/**
	 * Sets the value of the quantity property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setQuantity(BigDecimal value) {
		this.quantity = value;
	}

	/**
	 * Gets the value of the default property.
	 * 
	 */
	public boolean isDefaultSetting() {
		return defaultSetting;
	}

	/**
	 * Sets the value of the default property.
	 * 
	 */
	public void setDefaultSetting(boolean value) {
		this.defaultSetting = value;
	}

	/**
	 * Gets the value of the status property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the value of the status property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setStatus(String value) {
		this.status = value;
	}

	/**
	 * @return the captured
	 */
	public boolean isCaptured() {
		return captured;
	}

	/**
	 * @param captured
	 *            the captured to set
	 */
	public void setCaptured(boolean captured) {
		this.captured = captured;
	}

}
