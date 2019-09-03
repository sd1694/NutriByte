//Name: SHWETA DHAR
//Andrew id: shwetad

package hw3;

import java.util.Iterator;

import hw3.NutriProfiler.NutriEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

/**
 * Person --- abstract class that captures profile data of the person
 * @author Shweta Dhar
 * @version 1.0
 * @since 2018-05-11 
 */
public abstract class Person {

	ObservableList<RecommendedNutrient> recommendedNutrientsList = FXCollections.observableArrayList();
	ObservableList<Product> dietProductsList = FXCollections.observableArrayList();
	ObservableMap<String, RecommendedNutrient> dietNutrientsMap = FXCollections.observableHashMap();
	float age, weight, height, physicalActivityLevel; //age in years, weight in kg, height in cm
	String ingredientsToWatch;
	float[][] nutriConstantsTable = new float[NutriProfiler.RECOMMENDED_NUTRI_COUNT][NutriProfiler.AGE_GROUP_COUNT];


	NutriProfiler.AgeGroupEnum ageGroup;

	abstract void initializeNutriConstantsTable();
	abstract float calculateEnergyRequirement();

	/**
	 * Constructor initializes the variables: age, weight, height, physicalActivityLevel, ingredientsToWatch and ageGroup
	 * @param age Float age of the person
	 * @param weight Float weight of the person
	 * @param height Float height of the person
	 * @param physicalActivityLevel Float physical activity level of the person
	 * @param ingredientsToWatch String ingredients to watch for the person
	 * @return No return type
	 */
	Person(float age, float weight, float height, float physicalActivityLevel, String ingredientsToWatch) {
		this.age = age;
		this.weight = weight;
		this.height = height;
		this.physicalActivityLevel = physicalActivityLevel;
		this.ingredientsToWatch = ingredientsToWatch;

		for(NutriProfiler.AgeGroupEnum agegroup: NutriProfiler.AgeGroupEnum.values()) {
			if(agegroup.getAge()>=age) {
				this.ageGroup = agegroup; //ageGroup helps identify appropriate constants from nutriConstantsTable to be used to calculate nutrient quantities as recommendations
				break;
			}
		}
	}

	//returns an array of nutrient values of size NutriProfiler.RECOMMENDED_NUTRI_COUNT. 
	//Each value is calculated as follows:
	//For Protein, it multiples the constant with the person's weight.
	//For Carb and Fiber, it simply takes the constant from the 
	//nutriConstantsTable based on NutriEnums' nutriIndex and the person's ageGroup
	//For others, it multiples the constant with the person's weight and divides by 1000.
	//Try not to use any literals or hard-coded values for age group, nutrient name, array-index, etc. 

	/**
	 * This method uses the nutriConstantsTable populated by Male or Female class to determine the constant factor
	 * For Carbohydrates and Fiber, it returns the constant. For proteins, it returns constant*weight.
	 * @param No parameter
	 * @return float[] array of nutrient values of size NutriProfiler.RECOMMENDED_NUTRI_COUNT 
	 */
	float[] calculateNutriRequirement() {
		float[] nutrientRequirement = new float[NutriProfiler.RECOMMENDED_NUTRI_COUNT];

		for( int i=0; i<nutriConstantsTable.length; i++) {
			if(i == NutriEnum.PROTEIN.getNutriIndex()) {
				nutrientRequirement[i] = nutriConstantsTable[i][ageGroup.getAgeGroupIndex()]*weight;
			}

			else if(i == NutriEnum.CARBOHYDRATE.getNutriIndex()) {
				nutrientRequirement[i] = nutriConstantsTable[i][ageGroup.getAgeGroupIndex()];
			}

			else if(i == NutriEnum.FIBER.getNutriIndex()) {
				nutrientRequirement[i] = nutriConstantsTable[i][ageGroup.getAgeGroupIndex()];
			}

			else {
				nutrientRequirement[i] = nutriConstantsTable[i][ageGroup.getAgeGroupIndex()]*weight/1000;
			}
		}
		return nutrientRequirement;
	}

	/**
	 * This method uses the dietProductList or AddDietButtonHandler and populates the dietNutrientsMap
	 * @param No parameter
	 * @return No return type
	 */
	public void populateDietNutrientMap() {	
		dietNutrientsMap.clear();

		for(int i=0;i<dietProductsList.size();i++) {	
			Product product = dietProductsList.get(i);

			ObservableMap<String, Product.ProductNutrient> prodNut = product.getProductNutrients();			
			Iterator<String> nutiter = prodNut.keySet().iterator();

			while(nutiter.hasNext()) {
				String nextNut = nutiter.next();

				if(dietNutrientsMap.containsKey(nextNut)) {
					RecommendedNutrient recNut = new RecommendedNutrient(nextNut,
							(prodNut.get(nextNut).getNutrientQuantity()*product.getServingSize())/100 +
							(dietNutrientsMap.get(nextNut).getNutrientQuantity()));
					dietNutrientsMap.put(nextNut,recNut);
				}

				else {
					RecommendedNutrient recNut = new RecommendedNutrient(nextNut, (prodNut.get(nextNut).getNutrientQuantity()*product.getServingSize())/100);
					dietNutrientsMap.put(nextNut,recNut);
				}
			}
		}
	}
}
