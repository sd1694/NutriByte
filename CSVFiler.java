//Name: SHWETA DHAR
//Andrew id: shwetad

package hw3;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * CSVFiler --- DataFiler's child class that implements the two abstract methods readFile and writeFile to read and
 * write csv files respectively
 * @author Shweta Dhar
 * @version 2.0
 * @since 2018-26-11 
 */
public class CSVFiler extends DataFiler {

	public CSVFiler() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method writes the data in a csv file
	 * @param filename String file name that needs to be written
	 * @return No return type 
	 */
	@Override
	public void writeFile(String filename) {
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(filename));

			if(NutriByte.person instanceof Male) {
				bw.write(String.format("%s,%s,%s,%s,%s,%s%n", "Male", NutriByte.person.age, NutriByte.person.weight,
						NutriByte.person.height, NutriByte.person.physicalActivityLevel, NutriByte.person.ingredientsToWatch));
			}
			else if(NutriByte.person instanceof Female) {
				bw.write(String.format("%s,%s,%s,%s,%s,%s%n", "Female", NutriByte.person.age, NutriByte.person.weight, 
						NutriByte.person.height, NutriByte.person.physicalActivityLevel, NutriByte.person.ingredientsToWatch));
			}
			
			for(int i=0;i<NutriByte.person.dietProductsList.size();i++) {
				Product pr = NutriByte.person.dietProductsList.get(i);
				bw.write(String.format("%s,%s,%s%n", pr.getNdbNumber(),pr.getServingSize(),pr.getHouseholdSize()));
			}
			bw.close();
		}catch(IOException e1) {e1.printStackTrace();}
		catch(Exception e2) {e2.printStackTrace();}
	}

	/**
	 * This method takes csv file and reads the first line containing age, weight, height, physical activity level
	 * and a series of comma separated ingredients to watch to create a male or female object and assigns it to person
	 * @param filename String file name that needs to be read
	 * @return boolean true if file is read successfully; false otherwise 
	 */
	@Override
	public boolean readFile(String filename) {
		CSVFormat csvFormat = CSVFormat.DEFAULT;

		try {
			CSVParser csvParser = CSVParser.parse(new FileReader(filename), csvFormat);
			List<CSVRecord> csvrecord = csvParser.getRecords();
			int row = 0;
			for (CSVRecord csvRecord : csvrecord) {

				if(row == 0) {

					String dataValid="";
					for(int j=0;j<csvRecord.size();j++) {
						dataValid = dataValid + csvRecord.get(j).trim() +",";
					}
					if(validatePersonData(dataValid.substring(0,dataValid.length()-1))) {
						
						StringBuilder ingredientsToWatch = new StringBuilder();
						for(int i=5;i<csvRecord.size();i++) {
							String ingredient = csvRecord.get(i);
							if (ingredient != null && !ingredient.isEmpty()) {
								ingredientsToWatch.append(ingredient + ", ");
							}
						}

						if (ingredientsToWatch.length() > 0) {
							ingredientsToWatch.replace(ingredientsToWatch.toString().length()-2, ingredientsToWatch.toString().length()-1, "");
						}

						if(csvRecord.get(0).trim().equalsIgnoreCase("Female")) {
							NutriByte.person = new Female(Float.valueOf(csvRecord.get(1).trim()),
									Float.valueOf(csvRecord.get(2).trim()), Float.valueOf(csvRecord.get(3).trim()),
									Float.valueOf(csvRecord.get(4).trim()), ingredientsToWatch.toString().trim());
						}

						else if (csvRecord.get(0).trim().equalsIgnoreCase("Male")){
							NutriByte.person = new Male(Float.valueOf(csvRecord.get(1).trim()),
									Float.valueOf(csvRecord.get(2).trim()), Float.valueOf(csvRecord.get(3).trim()),
									Float.valueOf(csvRecord.get(4).trim()), ingredientsToWatch.toString().trim());
						}
					}
				}
				else {
					String dataValid="";

					for(int j=0;j<csvRecord.size();j++) {
						dataValid = dataValid + csvRecord.get(j).trim() +",";
					}

					if(NutriByte.person!=null && validateProductData(dataValid.substring(0,dataValid.length()-1))){
						if(Model.productsMap.containsKey(csvRecord.get(0).trim())){
							Product product = Model.productsMap.get(csvRecord.get(0).trim());
							product.setServingSize(Float.parseFloat(csvRecord.get(1).trim()));
								product.setHouseholdSize(Float.parseFloat(csvRecord.get(2).trim()));
							NutriByte.person.dietProductsList.add(product);
						}
					}
				}
				row++;
			}
			return true;
		}
		catch (Exception e1) { 
			NutriByte.person= null;
			e1.printStackTrace();
			return false;
		}
	}
	
	/**
	 * This method validates the person data from the file
	 * @param data String data to be validated
	 * @return boolean false when exceptions found true otherwise
	 */
	public boolean validatePersonData(String data) {
		String arr[] = data.split(",");
		try {
			String gender = arr[0].trim();	
			if(!gender.equalsIgnoreCase("Female") && !gender.equalsIgnoreCase("Male")) {
				throw new InvalidProfileException("The profile must have gender: Female or Male as first word");
			}

			String age = arr[1].trim();
			if(age.isEmpty()) {
				throw new InvalidProfileException("Missing age information");
			}
			try {
				if(Float.parseFloat(age)<0) {
					throw new InvalidProfileException("Invalid date for Age: "+ age+ "\nAge must be a positive number");
				}
			}catch(NumberFormatException err) {
				throw new InvalidProfileException("Invalid data for Age: "+ age+ "\nAge must be a number");
			}

			String weight = arr[2].trim();
			if(weight.isEmpty()) {
				throw new InvalidProfileException("Missing weight information");
			}
			try {
				if(Float.parseFloat(weight)<0) {
					throw new InvalidProfileException("Invalid date for Weight: "+ weight+ "\nWeight must be a positive number");
				}
			}catch(NumberFormatException err) {
				throw new InvalidProfileException("Invalid date for Weight: "+ weight+ "\nWeight must be a number");
			}

			String height = arr[3].trim();
			if(height.isEmpty()) {
				throw new InvalidProfileException("Missing height information");
			}
			try {
				if(Float.parseFloat(arr[3])<0) {
					throw new InvalidProfileException("Invalid date for Height: "+ height+ "\nHeight must be a positive number");
				}
			}catch(NumberFormatException err) {
				throw new InvalidProfileException("Invalid date for Height: "+ height+ "\nHeight must be a positive number");
			}		
			Float actLevel=1f;
			try {
				actLevel = Float.parseFloat(arr[4].trim());
				//				if(!(actLevel>= 1 && actLevel<= 1.49)) {
				if(!(Float.compare(actLevel, 1.0f) == 0) && !(Float.compare(actLevel, 1.25f) == 0) 
						&& !(Float.compare(actLevel, 1.48f) == 0) && !(Float.compare(actLevel, 1.1f) == 0)) {
					throw new InvalidProfileException("Invalid physical activity level: "+ actLevel+ "\nMust be 1.0, 1.1, 1.25 or 1.48");
				}
			}catch(NumberFormatException err) {
				throw new InvalidProfileException("Invalid physical activity level: "+ arr[4].trim() + "\nMust be 1.0, 1.1, 1.25 or 1.48");
			}
		}
		catch(InvalidProfileException err) {
			try {
				throw new InvalidProfileException("Could not read profile data");
			}catch(InvalidProfileException err1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This method validates the product data from the file
	 * @param data String data to be validated
	 * @return boolean false when exceptions found true otherwise
	 */
	public boolean validateProductData(String data) {
		String arr[] = data.split(",");
		try {
			try {
				Float ndbNumber = Float.parseFloat(arr[0].trim());
				Float serSize = Float.parseFloat(arr[1].trim());
				Float houseSize = Float.parseFloat(arr[2].trim());

				if(ndbNumber < 0 || serSize < 0 || houseSize < 0 ){
					throw new InvalidProfileException("Cannot read: "+ data + "  \nThe data must be - String, number, number - for ndb number,\n serving size, household size");
				}
			}catch(NumberFormatException | ArrayIndexOutOfBoundsException err) {
				throw new InvalidProfileException("Cannot read: "+ data + "  \nThe data must be - String, number, number - for ndb number,\n serving size, household size");
			}

			if(!Model.productsMap.containsKey(arr[0].trim())) {
				throw new InvalidProfileException("No product found with this code: "+ arr[0].trim());
			}
		}
		catch(InvalidProfileException err) {
			return false;
		}
		return true;
	}

}
