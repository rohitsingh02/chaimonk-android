package www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model;

import java.io.Serializable;
import java.util.List;

public class CompositeIngredientData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3473314520039812409L;
	private String _id;
	private String name;
	private String status;
	protected List<IngredientProductDetail> menuProducts;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<IngredientProductDetail> getMenuProducts() {
		return menuProducts;
	}

	public void setMenuProducts(List<IngredientProductDetail> menuProducts) {
		this.menuProducts = menuProducts;
	}

}
