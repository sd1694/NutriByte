//Name: SHWETA DHAR
//Andrew id: shwetad

package hw3;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import hw3.Product.ProductNutrient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

/**
 * Model --- class handles everything related to reading Products, Nutrients and Serving Size from
 * data files and storing them in its member variables (i.e. required product and nutrient maps). 
 * @author Shweta Dhar
 * @version 1.0
 * @since 2018-05-11 
 */
public class Model{

	ObservableList<Product> searchResultsList = FXCollections.observableArrayList();
	static public ObservableMap<String, Product> productsMap = FXCollections.observableHashMap();
	static public ObservableMap<String, Nutrient> nutrientsMap = FXCollections.observableHashMap();

	/**
	 * This method reads NutriByte.NUTRIENT_FILE to load objects in the nutrients and productNutrients maps
	 * @param filename String file to be read
	 * @return No return type 
	 */
	void readNutrients(String filename) {
		CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader();
		try {
			CSVParser csvParser = CSVParser.parse(new FileReader(filename), csvFormat);
			for (CSVRecord csvRecord : csvParser) {
				Nutrient nutrient = new Nutrient(csvRecord.get(1), csvRecord.get(2), csvRecord.get(5));
				nutrientsMap.put(csvRecord.get(1), nutrient);

				if(Float.valueOf(csvRecord.get(4).trim())>0 && csvRecord.get(4).trim().length()>0){
					Product product = productsMap.get(csvRecord.get(0));
					Product.ProductNutrient productnutrient = product.new ProductNutrient(csvRecord.get(1).trim(), Float.valueOf(csvRecord.get(4).trim()));
					ObservableMap<String, ProductNutrient> prNut = product.getProductNutrients();
					prNut.put(csvRecord.get(1),productnutrient);
					product.setProductNutrients(prNut);
					productsMap.put(csvRecord.get(0), product);
				}			
			}
		}
		catch (FileNotFoundException e1) { e1.printStackTrace(); }
		catch (IOException e1) { e1.printStackTrace(); }
	}


	/**
	 * This method reads NutriByte.PRODUCT_FILE file to load product objects in the productsMap
	 * @param filename String file to be read
	 * @return No return type 
	 */
	void readProducts(String filename) {
		CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader();
		try {
			CSVParser csvParser = CSVParser.parse(new FileReader(filename), csvFormat);
			for (CSVRecord csvRecord : csvParser) {
				Product product = new Product(csvRecord.get(0).trim(), csvRecord.get(1).trim(),
						csvRecord.get(4).trim(), csvRecord.get(7).trim());
				productsMap.put(csvRecord.get(0), product);
			}
		}
		catch (FileNotFoundException e1) { e1.printStackTrace(); }
		catch (IOException e1) { e1.printStackTrace(); }
	}

	/**
	 * This method reads NutriByte.SERVING_SIZE_FILE to populate four fields – servingSize, 
	 * servingUom, householdSize, householdUom - in each product object in the productsMaps.
	 * @param filename String file to be read
	 * @return No return type 
	 */
	void readServingSizes(String filename) {
		CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader();
		try {
			CSVParser csvParser = CSVParser.parse(new FileReader(filename), csvFormat);
			for (CSVRecord csvRecord : csvParser) {
				Product product = productsMap.get(csvRecord.get(0));
				product.setServingSize(csvRecord.get(1).trim().length()==0?0f:Float.valueOf(csvRecord.get(1).trim()));
				product.setServingUom(csvRecord.get(2).trim());
				product.setHouseholdSize(csvRecord.get(3).trim().length()==0?0f:Float.valueOf(csvRecord.get(3).trim()));
				product.setHouseholdUom(csvRecord.get(4).trim().length()==0?"null":(csvRecord.get(4).trim()));
				productsMap.put(csvRecord.get(0), product);
			}
		}
		catch (FileNotFoundException e1) { e1.printStackTrace(); }
		catch (IOException e1) { e1.printStackTrace(); }
	}

	/**
	 * This method reads the profile file chosen by the user - uses DataFiler's child classes to read
	 * CSV or XML files depending on their extension.
	 * @param filename String file to be read
	 * @return No return type 
	 */
	public boolean readProfile(String filename) {	
		String fileExt = "";
			if((filename.lastIndexOf(".") != -1) && (filename.lastIndexOf(".") != 0)) {
				fileExt = filename.substring(filename.lastIndexOf(".")+1);
			}
			if(fileExt.equals("csv")) {
				CSVFiler csvFiler = new CSVFiler();
				return csvFiler.readFile(filename);
			}
			else {
				XMLFiler xmlFiler = new XMLFiler();
				return xmlFiler.readFile(filename);
			}
	}

	/**
	 * This method allows user to save profile data and diet products in a new profile file as csv
	 * @param filename String file name to be written
	 * @return No return type 
	 */
	public void writeProfile(String filename) {
		try {
			CSVFiler csvFiler = new CSVFiler();
			csvFiler.writeFile(filename);

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
