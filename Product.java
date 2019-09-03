//Name: SHWETA DHAR
//Andrew id: shwetad

package hw3;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

/**
 * Product --- class contains member variables for a product: ndbNumber, productName, manufacturer, ingredients,
 * servingUom and householdUom. It also contains an inner class ProductNutrient which has variables: nutrientCode
 * and nutrientQuantity
 * @author Shweta Dhar
 * @version 1.0
 * @since 2018-05-11 
 */
public class Product {

	private ObservableMap<String, ProductNutrient> productNutrients = FXCollections.observableHashMap(); //hash map to store nutrients in the product
	private final StringProperty ndbNumber = new SimpleStringProperty();
	private final StringProperty productName = new SimpleStringProperty();
	private final StringProperty manufacturer = new SimpleStringProperty();
	private final StringProperty ingredients = new SimpleStringProperty();
	private final FloatProperty servingSize = new SimpleFloatProperty();
	private final StringProperty servingUom = new SimpleStringProperty();
	private final FloatProperty householdSize = new SimpleFloatProperty();
	private final StringProperty householdUom = new SimpleStringProperty();

	/**
	 * @return ObservableMap of productNutrients
	 */
	public ObservableMap<String, ProductNutrient> getProductNutrients() {
		return productNutrients;
	}
	
	/**
	 * @param productNutrients ObservableMap product nutrients to be set for the product
	 * (Key: nutrientCode, Value: ProductNutrient)
	 */
	public void setProductNutrients(ObservableMap<String, ProductNutrient> productNutrients) {
		this.productNutrients = productNutrients;
	}
	
	/**
	 * @return ndbNumber as a String
	 */
	public final String getNdbNumber() {
		return ndbNumber.get();
	}
	
	/**
	 * @param ndbNumber String ndb number to be set for the product
	 */
	public final void setNdbNumber(String ndbNumber) {
		this.ndbNumber.set(ndbNumber);
	}
	
	/**
	 * @return ndbNumber as a StringProperty
	 */
	public final StringProperty ndbNumberProperty() {
		return ndbNumber;
	}
	
	/**
	 * @return productName as a String
	 */
	public final String getProductName() {
		return productName.get();
	}
	
	/**
	 * @param productName String product name to be set for the product
	 */
	public final void setProductName(String productName) {
		this.productName.set(productName);
	}
	
	/**
	 * @return productName as a StringProperty
	 */
	public final StringProperty productNameProperty() {
		return productName;
	}
	
	/**
	 * @return manufacturer as a String
	 */
	public final String getManufacturer() {
		return manufacturer.get();
	}
	
	/**
	 * @param manufacturer String manufacturer to be set for the product
	 */
	public final void setManufacturer(String manufacturer) {
		this.manufacturer.set(manufacturer);
	}
	
	/**
	 * @return manufacturer as a StringProperty
	 */
	public final StringProperty manufacturerProperty() {
		return manufacturer;
	}
	
	/**
	 * @return ingredients as a String
	 */
	public final String getIngredients() {
		return ingredients.get();
	}
	
	/**
	 * @param ingredients String ingredients to be set for the product
	 */
	public final void setIngredients(String ingredients) {
		this.ingredients.set(ingredients);
	}
	
	/**
	 * @return ingredients as a StringProperty
	 */
	public final StringProperty ingredientsProperty() {
		return ingredients;
	}
	
	/**
	 * @return servingSize as a Float
	 */
	public final Float getServingSize() {
		return servingSize.get();
	}
	
	/**
	 * @param servingSize Float serving size to be set for the product
	 */
	public final void setServingSize(Float servingSize) {
		this.servingSize.set(servingSize);
	}
	
	/**
	 * @return servingSize as a FloatProperty
	 */
	public final FloatProperty servingSizeProperty() {
		return servingSize;
	}
	
	/**
	 * @return servingUom as a String
	 */
	public final String getServingUom() {
		return servingUom.get();
	}
	
	/**
	 * @param servingUom String serving unit of measure to be set for the product
	 */
	public final void setServingUom(String servingUom) {
		this.servingUom.set(servingUom);
	}
	
	/**
	 * @return servingUom as a StringProperty
	 */
	public final StringProperty servingUomProperty() {
		return servingUom;
	}
	
	/**
	 * @return householdSize as Float
	 */
	public final Float getHouseholdSize() {
		return householdSize.get();
	}
	
	/**
	 * @param householdSize Float household size to be set for the product
	 */
	public final void setHouseholdSize(Float householdSize) {
		this.householdSize.set(householdSize);
	}
	
	/**
	 * @return householdSize as FloatProperty
	 */
	public final FloatProperty householdSizeProperty() {
		return householdSize;
	}
	
	/**
	 * @return householdUom as a String
	 */
	public final String getHouseholdUom() {
		return householdUom.get();
	}
	
	/**
	 * @param householdUom String household unit of measure to be set for the product
	 */
	public final void setHouseholdUom(String householdUom) {
		this.householdUom.set(householdUom);
	}
	
	/**
	 * @return householdUom as a StringProperty
	 */
	public final StringProperty householdUomProperty() {
		return householdUom;
	}
	
	/**
	 * ProductNutrient --- inner class contains member variables for a product nutrient: nutrientCode and nutrientQuantity 
	 * @author Shweta Dhar
	 * @version 1.0
	 * @since 2018-05-11 
	 */
	public class ProductNutrient{
		private StringProperty nutrientCode = new SimpleStringProperty();
		private FloatProperty nutrientQuantity = new SimpleFloatProperty();
		
		/**
		 * @return nutrientCode as a String
		 */
		public final String getNutrientCode() {
			return nutrientCode.get();
		}
		
		/**
		 * @param nutrientCode String nutrient code to be set for the product nutrient
		 */
		public final void setNutrientCode(String nutrientCode) {
			this.nutrientCode.set(nutrientCode);
		}
		
		/**
		 * @return nutrientCode as a StringProperty
		 */
		public final StringProperty nutrientCodeProperty() {
			return nutrientCode;
		}
		
		/**
		 * @return nutrientQuantity as a Float
		 */
		public final Float getNutrientQuantity() {
			return nutrientQuantity.get();
		}
		
		/**
		 * @param nutrientQuantity Float nutrient quantity to be set for the product nutrient
		 */
		public final void setNutrientQuantity(Float nutrientQuantity) {
			this.nutrientQuantity.set(nutrientQuantity);
		}
		
		/**
		 * @return nutrientQuantity as a FloatProperty
		 */
		public final FloatProperty nutrientQuantityProperty() {
			return nutrientQuantity;
		}
		
		/**
		 * Default constructor initializes the member variable: nutrientCode to an empty string
		 * @param No parameter
		 * @return No return type
		 */
		public ProductNutrient() {
			nutrientCode.set("");
		}
		
		/**
		 * Constructor initializes the member variables: nutrientCode and nutrientQuantity
		 * @param nutrientCode String nutrient code for the product nutrient
		 * @param nutrientQuantity String nutrient quantity for the product nutrient
		 * @return No return type
		 */
		public ProductNutrient(String nutrientCode, float nutrientQuantity) {
			this.nutrientCode.set(nutrientCode);
			this.nutrientQuantity.set(nutrientQuantity);
		}
	}
	
	/**
	 * Default constructor initializes the member variables: ndbNumber, productName, manufacturer, ingredients, servingUom
	 * and householdUom to empty strings
	 * @param No parameter
	 * @return No return type
	 */
	public Product() {
		ndbNumber.set("");
		productName.set("");
		manufacturer.set("");
		ingredients.set("");
		servingUom.set("");
		householdUom.set("");
	}
	
	/**
	 * Constructor initializes the member variables: ndbNumber, productName, manufacturer, ingredients
	 * @param ndbNumber String ndb number for the product
	 * @param productName String product name for the product
	 * @param manufacturer String manufacturer for the product
	 * @param ingredients String ingredients for the product
	 * @return No return type
	 */
	public Product(String ndbNumber, String productName, String manufacturer, String ingredients) {
		this.ndbNumber.set(ndbNumber);
		this.productName.set(productName);
		this.manufacturer.set(manufacturer);
		this.ingredients.set(ingredients);
	}
	
	/**
	 * This method overrides toString() function to display products in the dropdown list
	 * @param no parameters
	 * @return String
	 */
	public String toString() {
		return (getProductName() + " by " + getManufacturer());
	}
}
