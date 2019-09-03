//Name: SHWETA DHAR
//Andrew id: shwetad

package hw3;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * RecommendedNutrient --- class contains member variables for a nutrient: nutrientCode and nutrientQuantity
 * @author Shweta Dhar
 * @version 1.0
 * @since 2018-05-11 
 */
public class RecommendedNutrient {

	private final StringProperty nutrientCode = new SimpleStringProperty();
	private final FloatProperty nutrientQuantity = new SimpleFloatProperty();
	
	/**
	 * @return nutrientCode as a String
	 */
	public final String getNutrientCode() {
		return nutrientCode.get();
	}
	
	/**
	 * @param nutrientCode String nutrient code to be set for the recommended nutrient
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
	 * @param nutrientQuantity Float nutrient quantity to be set for the recommended nutrient
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
	 * Default constructor initializes the member variable, nutrientCode, to an empty string
	 * @param No parameter
	 * @return No return type
	 */
	public RecommendedNutrient() {
		nutrientCode.set("");	
	}
	
	/**
	 * Constructor initializes the member variables: ndbNumber, productName, manufacturer, ingredients
	 * @param nutrientCode String nutrient code for the recommended nutrient
	 * @param nutrientQuantity Float nutrient quantity for the recommended nutrient
	 * @return No return type
	 */
	public RecommendedNutrient(String nutrientCode, Float nutrientQuantity) {
		this.nutrientCode.set(nutrientCode);
		this.nutrientQuantity.set(nutrientQuantity);
	}

}
