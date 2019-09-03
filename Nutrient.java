//Name: SHWETA DHAR
//Andrew id: shwetad

package hw3;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Nutrient --- class contains member variables for a nutrient: nutrientCode, nutrientName and nutrientUom
 * @author Shweta Dhar
 * @version 1.0
 * @since 2018-05-11 
 */
public class Nutrient {

	private final StringProperty nutrientCode = new SimpleStringProperty();
	private final StringProperty nutrientName = new SimpleStringProperty();
	private final StringProperty nutrientUom = new SimpleStringProperty();
	
	/**
	 * @return nutrientCode as a String
	 */
	public final String getNutrientCode() {
		return nutrientCode.get();
	}
	
	/**
	 * @param nutrientCode String nutrient code to be set for the nutrient
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
	 * @return nutrientName as a String
	 */
	public final String getNutrientName() {
		return nutrientName.get();
	}
	
	/**
	 * @param nutrientName String nutrient name to be set for the nutrient
	 */
	public final void setNutrientName(String nutrientName) {
		this.nutrientName.set(nutrientName);
	}
	
	/**
	 * @return nutrientName as a StringProperty
	 */
	public final StringProperty nutrientNameProperty() {
		return nutrientName;
	}
	
	/**
	 * @return nutrientUom as a String
	 */
	public final String getNutrientUom() {
		return nutrientUom.get();
	}
	
	/**
	 * @param nutrientUom String nutrient unit of measure to be set for the nutrient
	 */
	public final void setNutrientUom(String nutrientUom) {
		this.nutrientUom.set(nutrientUom);
	}
	
	/**
	 * @return nutrientUom as a StringProperty
	 */
	public final StringProperty nutrientUomProperty() {
		return nutrientUom;
	}
	
	/**
	 * Default constructor initializes the member variables: nutrientCode, nutrientName and nutrientUom
	 * to empty strings
	 * @param No parameter
	 * @return No return type
	 */
	public Nutrient() {
		nutrientCode.set("");
		nutrientName.set("");
		nutrientUom.set("");
	}
	
	/**
	 * Constructor initializes the member variables: ndbNumber, productName, manufacturer, ingredients
	 * @param nutrientCode String nutrient code for the nutrient
	 * @param nutrientName String nutrient name for the nutrient
	 * @param nutrientUom String nutrient unit of measure for the nutrient
	 * @return No return type
	 */
	public Nutrient(String nutrientCode, String nutrientName, String nutrientUom) {
		this.nutrientCode.set(nutrientCode);
		this.nutrientName.set(nutrientName);
		this.nutrientUom.set(nutrientUom);
	}
}
