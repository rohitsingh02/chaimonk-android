//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.05.19 at 03:23:59 PM IST 
//

package www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model;

import java.io.Serializable;
import java.util.List;



public class IngredientDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2576611384607231273L;


	private String _id;

	protected CompositeProductData compositeProduct;
	protected List<IngredientProduct> products;
	protected List<IngredientVariant> variants;
	protected List<IngredientProductDetail> components;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public List<IngredientProduct> getProducts() {
		return this.products;
	}

	public List<IngredientVariant> getVariants() {
		return this.variants;
	}

	public List<IngredientProductDetail> getComponents() {
		return this.components;
	}

	public void setProducts(List<IngredientProduct> products) {
		this.products = products;
	}

	public void setVariants(List<IngredientVariant> variants) {
		this.variants = variants;
	}

	public void setComponents(List<IngredientProductDetail> components) {
		this.components = components;
	}

	public CompositeProductData getCompositeProduct() {
		return compositeProduct;
	}

	public void setCompositeProduct(CompositeProductData compositeProduct) {
		this.compositeProduct = compositeProduct;
	}

}
