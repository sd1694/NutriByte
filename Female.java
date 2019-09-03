//Name: SHWETA DHAR
//Andrew id: shwetad

package hw3;

/**
 * Female --- class (extends Person) that captures profile data of a person who is a female and
 * calculates the energy requirement for that female
 * @author Shweta Dhar
 * @version 1.0
 * @since 2018-05-11 
 */
public class Female extends Person {

	float[][] nutriConstantsTableFemale = new float[][]{
		//AgeGroups: 3M, 6M, 1Y, 3Y, 8Y, 13Y, 18Y, 30Y, 50Y, ABOVE 
		{1.52f, 1.52f, 1.2f, 1.05f, 0.95f, 0.95f, 0.71f, 0.8f, 0.8f, 0.8f}, //0: Protein constants
		{60, 60, 95, 130, 130, 130, 130, 130, 130, 130}, //1: Carbohydrate
		{19, 19, 19, 19, 25, 26, 26, 25, 25, 21},  //2: Fiber constants
		{36, 36, 32, 21, 16, 15, 14, 14, 14, 14}, 	//3: Histidine
		{88, 88, 43, 28, 22, 21, 19, 19, 19, 19}, 	//4: isoleucine
		{156, 156, 93, 63, 49, 47, 44 , 42, 42, 42},//5: leucine
		{107, 107, 89, 58, 46, 43, 40, 38, 38, 38}, //6: lysine
		{59, 59, 43, 28, 22, 21, 19, 19, 19, 19}, 	//7: methionine
		{59, 59, 43, 28, 22, 21, 19, 19, 19, 19}, 	//8: cysteine
		{135, 135, 84, 54, 41, 38, 35, 33, 33, 33}, //9: phenylalanine
		{135, 135, 84, 54, 41, 38, 35, 33, 33, 33}, //10: phenylalanine
		{73, 73, 49, 32, 24, 22, 21, 20, 20, 20}, 	//11: threonine
		{28, 28, 13, 8, 6, 6, 5, 5, 5, 5}, 			//12: tryptophan
		{87, 87, 58, 37, 28, 27, 24, 24, 24, 24	}  	//13: valine
	};
	
	/**
	 * Constructor initializes the variables: age, weight, height, physicalActivityLevel and ingredientsToWatch.
	 * Also invokes initializeNutriConstantsTable method of its parent class, person
	 * @param age Float age of the female
	 * @param weight Float weight of the female
	 * @param height Float height of the female
	 * @param physicalActivityLevel Float physical activity level of the female
	 * @param ingredientsToWatch String ingredients to watch for the female
	 * @return No return type
	 */
	Female(float age, float weight, float height, float physicalActivityLevel, String ingredientsToAvoid) {
		super(age, weight, height, physicalActivityLevel, ingredientsToAvoid);
		this.initializeNutriConstantsTable();
	}
	
	/**
	 * This method calculates the energy requirement of the person who is a female
	 * @param No parameter
	 * @return float energy requirement of the female
	 */
	@Override
	float calculateEnergyRequirement() {
		if(ageGroup.getAge() == 0.25) {
			return (float)(89*weight-(-75));
		}
		
		else if(ageGroup.getAge() == 0.5) {
			return (float)(89*weight-(44));
		}
		
		else if(ageGroup.getAge() == 1.0) {
			return (float)(89*weight-(78));
		}
		
		else if(ageGroup.getAge()== 3.0) {
			return (float)(89*weight-(80));
		}
		
		else if(ageGroup.getAge() == 8.0 || ageGroup.getAge() == 13.0 || ageGroup.getAge() == 18.0) {
			return (float)(135.3-(30.8*age)+ physicalActivityLevel*(10*weight + 934*height/100)+20);
		}
		
		else {
			return (float)(354-(6.91*age)+ physicalActivityLevel*(9.36*weight + 726*height/100));
		}
	}
	
	/**
	 * This method initializes the nutriConstantsTable for a female
	 * @param No parameter
	 * @return No return type 
	 */
	@Override
	void initializeNutriConstantsTable() {
		this.nutriConstantsTable = nutriConstantsTableFemale;
	}
}
