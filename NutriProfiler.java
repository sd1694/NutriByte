//Name: SHWETA DHAR
//Andrew id: shwetad

package hw3;

/**
 * NutriProfiler --- class that creates the nutrient profile of a person and recommends nutrients to the person
 * @author Shweta Dhar
 * @version 1.0
 * @since 2018-05-11 
 */
public class NutriProfiler {  
	
	static final int RECOMMENDED_NUTRI_COUNT = 14;  //this includes Protein, Carbohydrate, fiber, and 11 amino acids
	static final int AGE_GROUP_COUNT = 10; 
	
	static final String ENERGY_NUTRIENT_CODE = "208"; 
	
	/**
	 * This enum type variable stores nutrient codes and their index into the nutriConstantTable
	 */
	public enum NutriEnum {
		PROTEIN("203", 0), CARBOHYDRATE("205", 1), FIBER("291", 2), 
		
		//The following nutrients are aminoacids 
		HISTIDINE("512", 3), ISOLEUCINE("503", 4), LEUCINE("504", 5), LYSINE("505", 6), 
		METHIONINE("506", 7), CYSTEINE("526", 8), PHENYLALANINE("508", 9), TYROSINE("509", 10), 
		THREONINE("502", 11), TRYPTOPHAN("501", 12), VALINE("510", 13);
		
		private String nutrientCode;
		private int nutriIndex;
		NutriEnum(String nutrientCode, int nutriIndex) {
			this.nutrientCode = nutrientCode;
			this.nutriIndex = nutriIndex;
		}

		String getNutrientCode() { return nutrientCode; }
		int getNutriIndex() { return nutriIndex; }
	}
	
	/**
	 * This enum type variable stores ten age groups and their index into the nutriConstantTable
	 */
	public enum AgeGroupEnum{
		//The age group enum represents two things: highest age in years in a group, and the index to fetch the value for that age in Person's nutriConstantsTable
		MAX_AGE_3M(0.25f, 0), MAX_AGE_6M(0.5f, 1), MAX_AGE_1Y(1, 2), MAX_AGE_3Y(3, 3), MAX_AGE_8Y(8, 4), MAX_AGE_13Y(13, 5), 
		MAX_AGE_18Y(18, 6), MAX_AGE_30Y(30, 7), MAX_AGE_50Y(50, 8), MAX_AGE_ABOVE(150, 9);  //age 150 is given to provide some numeric limit. 
		private float age;
		private int ageGroupIndex;
		AgeGroupEnum(float age, int ageGroupIndex) { 
			this.age = age; 
			this.ageGroupIndex = ageGroupIndex;
		}
		float getAge() { return age; }
		int getAgeGroupIndex() { return ageGroupIndex; }
	}
	
	/**
	 * This enum type variable stores names and related numeric constants for four levels of physical activity
	 * to calculate energy requirement
	 */
	public enum PhysicalActivityEnum {
		//The physical activity enum represents two things: text to display in View's combo box, and the constant value to be used for calculations
		SEDENTARY("Sedentary", 1), LOW_ACTIVE("Low active", 1.1f), ACTIVE("Active", 1.25f), VERY_ACTIVE("Very active", 1.48f);
		private String name;
		private float physicalActivityLevel;

		PhysicalActivityEnum(String name, float level) { 
			this.name= name;
			physicalActivityLevel = level; 
		}
		String getName() { return name; }
		float getPhysicalActivityLevel() { return physicalActivityLevel;}
	}
	
	/**
	 * This method populates the recommendedNutrientsList using person's energy and nutrient requirements
	 * @param person Person person whose nutrient profile needs to be created
	 * @return No return type
	 */
	static void createNutriProfile(Person person) {
		person.recommendedNutrientsList.clear();
		
		person.recommendedNutrientsList.add(new RecommendedNutrient(ENERGY_NUTRIENT_CODE,person.calculateEnergyRequirement()));
		
		float[] nutReq = person.calculateNutriRequirement();
		for(NutriEnum nutri: NutriEnum.values()) {
			person.recommendedNutrientsList.add(new RecommendedNutrient(nutri.nutrientCode,nutReq[nutri.nutriIndex]));
		}
	}
}
