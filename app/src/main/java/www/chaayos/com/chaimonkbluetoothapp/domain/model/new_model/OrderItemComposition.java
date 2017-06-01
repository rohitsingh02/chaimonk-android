package www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderItemComposition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5083045930894371724L;
	private String objectId;

	private Long version;

	/**
	 * Added to avoid a runtime error whereby the detachAll property is checked
	 * for existence but not actually used.
	 */
	private String detachAll;
	protected List<IngredientVariantDetail> variants;
	protected List<IngredientProductDetail> products;
	protected List<IngredientProductDetail> addons;
	protected List<OrderItem> menuProducts;

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
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

	public List<IngredientVariantDetail> getVariants() {
		if (variants == null) {
			variants = new ArrayList<>();
		}
		return variants;
	}

	public void setVariants(List<IngredientVariantDetail> variants) {
		this.variants = variants;
	}

	public List<IngredientProductDetail> getProducts() {
		if (products == null) {
			products = new ArrayList<>();
		}
		return products;
	}

	public void setProducts(List<IngredientProductDetail> products) {
		this.products = products;
	}

	public List<IngredientProductDetail> getAddons() {
		if (addons == null) {
			addons = new ArrayList<>();
		}
		return addons;
	}

	public void setAddons(List<IngredientProductDetail> addons) {
		this.addons = addons;
	}

	public List<OrderItem> getMenuProducts() {
		if (menuProducts == null) {
			menuProducts = new ArrayList<>();
		}
		return menuProducts;
	}

	public void setMenuProducts(List<OrderItem> menuProducts) {
		this.menuProducts = menuProducts;
	}

	public boolean hasDefaultVariant() {
		for (IngredientVariantDetail variant : getVariants()) {
			if (!variant.isDefaultSetting()) {
				return true;
			}
		}
		return false;
	}

}
