package www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model;

import java.io.Serializable;
import java.util.List;



public class CompositeProductData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6220579205051084768L;

	private String _id;
	protected int maxQuantity;
	protected List<CompositeIngredientData> details;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public int getMaxQuantity() {
		return maxQuantity;
	}

	public void setMaxQuantity(int maxQuantity) {
		this.maxQuantity = maxQuantity;
	}

	public List<CompositeIngredientData> getDetails() {
		return details;
	}

	public void setDetails(List<CompositeIngredientData> details) {
		this.details = details;
	}

}
