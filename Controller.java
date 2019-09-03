//Name: SHWETA DHAR
//Andrew id: shwetad

package hw3;

import java.io.File;
import java.util.Iterator;

import hw3.NutriProfiler.PhysicalActivityEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Controller --- class that holds all the event handlers for handling events
 * @author Shweta Dhar
 * @version 1.0
 * @since 2018-05-11 
 */
public class Controller {

	/**
	 * RecommendNutrientsButtonHandler --- This handler class takes all the data from GUI controls to create a 
	 * profile and then recommendeds nutrients for the user profile
	 * @author Shweta Dhar
	 * @version 1.0
	 * @since 2018-05-11
	 */
	class RecommendNutrientsButtonHandler implements EventHandler<ActionEvent> {
		/**
		 * This method handles the events that occur when the person presses the Recommend Nutrient button
		 * @param event ActionEvent event that occurs when we click the Recommend Nutrients button on the app
		 * @return No return type 
		 */
		@Override
		public void handle(ActionEvent event) {

			try {
				if(NutriByte.view.genderComboBox.getValue()==null) {
					throw new InvalidProfileException("Gender Information Missing");
				}

				if(NutriByte.view.ageTextField.getText().isEmpty()) {
					throw new InvalidProfileException("Age Information Missing");
				}

				try {
					if(Float.parseFloat(NutriByte.view.ageTextField.getText())<0) {
						throw new InvalidProfileException("Age must be a positive number");
					}
				}catch(NumberFormatException num) {
					throw new InvalidProfileException("Incorrect age input. Must be a number");
				}

				if(NutriByte.view.weightTextField.getText().isEmpty()) {
					throw new InvalidProfileException("Weight Information Missing");
				}
				try {
					if(Float.parseFloat(NutriByte.view.weightTextField.getText())<0) {
						throw new InvalidProfileException("Weight must be a positive number");
					}
				}catch(NumberFormatException num) {
					throw new InvalidProfileException("Incorrect weight input. Must be a number");
				}

				if(NutriByte.view.heightTextField.getText().isEmpty()) {
					throw new InvalidProfileException("Height Information Missing");
				}
				try {
					if(Float.parseFloat(NutriByte.view.heightTextField.getText())<0) {
						throw new InvalidProfileException("Height must be a positive number");
					}
				}catch(NumberFormatException num) {
					throw new InvalidProfileException("Incorrect height input. Must be a number");
				}

				float activityLevel = 1;
				for(PhysicalActivityEnum paEnum: PhysicalActivityEnum.values()) {
					if(paEnum.getName().equalsIgnoreCase(NutriByte.view.physicalActivityComboBox.getValue())) {
						activityLevel = paEnum.getPhysicalActivityLevel();
					}
				}

				if(NutriByte.view.genderComboBox.getValue() != null && NutriByte.view.genderComboBox.getValue().length()>0) {
					if(NutriByte.view.genderComboBox.getValue().equals("Male")) {
						NutriByte.person = new Male((NutriByte.view.ageTextField.getText().length()==0?0f:Float.valueOf(NutriByte.view.ageTextField.getText())),
								(NutriByte.view.weightTextField.getText().length()==0?0f:Float.valueOf(NutriByte.view.weightTextField.getText())),
								(NutriByte.view.heightTextField.getText().length()==0?0f:Float.valueOf(NutriByte.view.heightTextField.getText())),
								activityLevel, NutriByte.view.ingredientsToWatchTextArea.getText());
					}
					else if (NutriByte.view.genderComboBox.getValue().equals("Female")){
						NutriByte.person = new Female((NutriByte.view.ageTextField.getText().length()==0?0f:Float.valueOf(NutriByte.view.ageTextField.getText())),
								(NutriByte.view.weightTextField.getText().length()==0?0f:Float.valueOf(NutriByte.view.weightTextField.getText())),
								(NutriByte.view.heightTextField.getText().length()==0?0f:Float.valueOf(NutriByte.view.heightTextField.getText())),
								activityLevel, NutriByte.view.ingredientsToWatchTextArea.getText());
					}
					NutriProfiler.createNutriProfile(NutriByte.person);
					NutriByte.view.recommendedNutrientsTableView.setItems(NutriByte.person.recommendedNutrientsList);
				}

				else {
					NutriByte.view.recommendedNutrientsTableView.getItems().clear();
				}

			}catch(NumberFormatException | InvalidProfileException err) {

			}
		}			
	}

	/**
	 * OpenMenuItemHandler --- This handler class opens the File chooser for the user to choose a profile data file from 
	 * locally stored csv or xml files and displays it in the GUI. 
	 * Also, populates the recommendedNutrientsList by invoking the createNutriProfile method.
	 * @author Shweta Dhar
	 * @version 1.0
	 * @since 2018-05-11
	 */
	class OpenMenuItemHandler implements EventHandler<ActionEvent> {
		/**
		 * This method handles the events that occur when the person selects the Open menu item in the NutriProfile tab
		 * @param event ActionEvent event that occurs when we click the Open menu item
		 * @return No return type 
		 */
		@Override
		public void handle(ActionEvent event) {
			NutriByte.view.root.setCenter(NutriByte.view.nutriTrackerPane);
			NutriByte.view.initializePrompts();
			NutriByte.view.recommendedNutrientsTableView.getItems().clear();
			NutriByte.view.productIngredientsTextArea.clear();
			NutriByte.view.servingSizeLabel.setText("");
			NutriByte.view.householdSizeLabel.setText("");
			NutriByte.view.dietServingUomLabel.setText("");
			NutriByte.view.dietHouseholdUomLabel.setText("");
			NutriByte.view.dietProductsTableView.getSelectionModel().clearSelection();
			NutriByte.view.productsComboBox.getItems().clear();
			NutriByte.view.nutriChart.clearChart();

			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select file");
			fileChooser.setInitialDirectory(new File(NutriByte.NUTRIBYTE_PROFILE_PATH));
			fileChooser.getExtensionFilters().addAll(
					new ExtensionFilter("CSV Files", "*.csv"),
					new ExtensionFilter("XML Files", "*.xml"));
			File file = null;
			if ((file = fileChooser.showOpenDialog(NutriByte.view.root.getScene().getWindow())) != null && 
					NutriByte.model.readProfile(file.getAbsolutePath()) != false){

				if(NutriByte.person!=null) {
					ObservableList<Product> tempDietProductsList = FXCollections.observableArrayList();
					tempDietProductsList = NutriByte.person.dietProductsList;

					if(NutriByte.person instanceof Male) {
						NutriByte.view.genderComboBox.setValue("Male");
					}
					else {
						NutriByte.view.genderComboBox.setValue("Female");
					}

					NutriByte.view.ingredientsToWatchTextArea.setText(String.valueOf(NutriByte.person.ingredientsToWatch).trim());
					for(PhysicalActivityEnum paEnum: PhysicalActivityEnum.values()) {
						if(paEnum.getPhysicalActivityLevel() == NutriByte.person.physicalActivityLevel) {
							NutriByte.view.physicalActivityComboBox.setValue(paEnum.getName());
						}
					}

					NutriByte.view.ageTextField.setText(String.valueOf(NutriByte.person.age));
					NutriByte.view.weightTextField.setText(String.valueOf(NutriByte.person.weight));
					NutriByte.view.heightTextField.setText(String.valueOf(NutriByte.person.height));

					NutriProfiler.createNutriProfile(NutriByte.person);

					NutriByte.view.dietProductsTableView.setItems(NutriByte.person.dietProductsList);
					NutriByte.view.productsComboBox.setItems(NutriByte.person.dietProductsList);

					NutriByte.person.dietProductsList = tempDietProductsList;
					NutriByte.view.dietProductsTableView.setItems(NutriByte.person.dietProductsList);

					NutriByte.view.productsComboBox.setItems(NutriByte.person.dietProductsList);
					if (NutriByte.person.dietProductsList.size() > 0) {
						NutriByte.view.productsComboBox.setValue(NutriByte.person.dietProductsList.get(0));
						Product productComboBoxValue = NutriByte.view.productsComboBox.getValue();

						if(productComboBoxValue!=null) {
							ObservableList<Product.ProductNutrient> prNrList = FXCollections.observableArrayList();
							prNrList.addAll(productComboBoxValue.getProductNutrients().values());
							NutriByte.view.productIngredientsTextArea.setText("Product ingredients: " + productComboBoxValue.getIngredients());
							NutriByte.view.productNutrientsTableView.setItems(prNrList);
							NutriByte.view.servingSizeLabel.setText(productComboBoxValue.getServingSize() + " " + productComboBoxValue.getServingUom());
							NutriByte.view.householdSizeLabel.setText(productComboBoxValue.getHouseholdSize() + " " + productComboBoxValue.getHouseholdUom());
							NutriByte.view.dietServingUomLabel.setText(productComboBoxValue.getServingUom());
							NutriByte.view.dietHouseholdUomLabel.setText(productComboBoxValue.getHouseholdUom());
						}	
						NutriByte.person.populateDietNutrientMap();
						NutriByte.view.nutriChart.updateChart();


					}
				} 
			}
		}
	}

	/**
	 * NewMenuItemHandler --- This handler class changes the view of the window from welcome to tracker pane. 
	 * @author Shweta Dhar
	 * @version 1.0
	 * @since 2018-05-11
	 */
	class NewMenuItemHandler implements EventHandler<ActionEvent> {
		/**
		 * This method handles the events that occur when the person selects the New menu item in the NutriProfile tab
		 * @param event ActionEvent event that occurs when we click the New menu item
		 * @return No return type 
		 */
		@Override
		public void handle(ActionEvent event) {
			NutriByte.view.root.setCenter(NutriByte.view.nutriTrackerPane);
			NutriByte.view.initializePrompts();
			NutriByte.view.recommendedNutrientsTableView.getItems().clear();
			NutriByte.view.nutriChart.clearChart();

			NutriByte.view.productSearchTextField.clear();
			NutriByte.view.nutrientSearchTextField.clear();
			NutriByte.view.ingredientSearchTextField.clear();

			NutriByte.view.searchResultSizeLabel.setText("");
			NutriByte.view.productsComboBox.getItems().clear();
			NutriByte.view.productIngredientsTextArea.setText("");
			NutriByte.view.servingSizeLabel.setText("");
			NutriByte.view.householdSizeLabel.setText("");
			NutriByte.view.dietServingUomLabel.setText("");
			NutriByte.view.dietHouseholdUomLabel.setText("");

			NutriByte.view.dietHouseholdSizeTextField.clear();
			NutriByte.view.dietServingSizeTextField.clear();
			NutriByte.view.dietProductsTableView.getItems().clear();
		}
	}

	/**
	 * AboutMenuItemHandler --- This handler class opens a pop-up that describes the application 
	 * @author Shweta Dhar
	 * @version 1.0
	 * @since 2018-05-11
	 */
	class AboutMenuItemHandler implements EventHandler<ActionEvent> {
		/**
		 * This method handles the events that occur when the person selects the About menu item
		 * @param event ActionEvent event that occurs when we click the About menu item
		 * @return No return type 
		 */
		@Override
		public void handle(ActionEvent event) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("About");
			alert.setHeaderText("NutriByte");
			alert.setContentText("Version 2.0 \nRelease 1.0\nCopyleft Java Nerds\nThis software is designed purely for educational purposes.\nNo commercial use intended");
			Image image = new Image(getClass().getClassLoader().getResource(NutriByte.NUTRIBYTE_IMAGE_FILE).toString());
			ImageView imageView = new ImageView();
			imageView.setImage(image);
			imageView.setFitWidth(300);
			imageView.setPreserveRatio(true);
			imageView.setSmooth(true);
			alert.setGraphic(imageView);
			alert.showAndWait();
		}
	}

	/**
	 * SaveMenuItemHandler --- This handler class saves the profile data in a file 
	 * @author Shweta Dhar
	 * @version 1.0
	 * @since 2018-26-11
	 */
	class SaveMenuItemHandler implements EventHandler<ActionEvent>{
		/**
		 * This method handles the events that occur when the person selects the Save menu item
		 * @param event ActionEvent event that occurs when we click the Save menu item
		 * @return No return type 
		 */
		@Override
		public void handle(ActionEvent event) {
			try {
				if(NutriByte.view.genderComboBox.getValue()==null) {
					throw new InvalidProfileException("Gender Information Missing");
				}

				if(NutriByte.view.ageTextField.getText().isEmpty()) {
					throw new InvalidProfileException("Age Information Missing");
				}

				try {
					if(Float.parseFloat(NutriByte.view.ageTextField.getText())<0) {
						throw new InvalidProfileException("Age must be a positive number");
					}
				}catch(NumberFormatException num) {
					throw new InvalidProfileException("Incorrect age input. Must be a number");
				}

				if(NutriByte.view.weightTextField.getText().isEmpty()) {
					throw new InvalidProfileException("Weight Information Missing");
				}
				try {
					if(Float.parseFloat(NutriByte.view.weightTextField.getText())<0) {
						throw new InvalidProfileException("Weight must be a positive number");
					}
				}catch(NumberFormatException num) {
					throw new InvalidProfileException("Incorrect weight input. Must be a number");
				}

				if(NutriByte.view.heightTextField.getText().isEmpty()) {
					throw new InvalidProfileException("Height Information Missing");
				}
				try {
					if(Float.parseFloat(NutriByte.view.heightTextField.getText())<0) {
						throw new InvalidProfileException("Height must be a positive number");
					}
				}catch(NumberFormatException num) {
					throw new InvalidProfileException("Incorrect height input. Must be a number");
				}
				//NutriByte.view.root.setCenter(NutriByte.view.nutriTrackerPane);
				//NutriByte.view.initializePrompts();
//				float activityLevel = 1;
//				for(PhysicalActivityEnum paEnum: PhysicalActivityEnum.values()) {
//					if(paEnum.getName().equalsIgnoreCase(NutriByte.view.physicalActivityComboBox.getValue())) {
//						activityLevel = paEnum.getPhysicalActivityLevel();
//					}
//				}
//
//				
//				if(NutriByte.view.genderComboBox.getValue().equals("Male")) {
//					NutriByte.person = new Male((NutriByte.view.ageTextField.getText().length()==0?0f:Float.valueOf(NutriByte.view.ageTextField.getText())),
//							(NutriByte.view.weightTextField.getText().length()==0?0f:Float.valueOf(NutriByte.view.weightTextField.getText())),
//							(NutriByte.view.heightTextField.getText().length()==0?0f:Float.valueOf(NutriByte.view.heightTextField.getText())),
//							activityLevel, NutriByte.view.ingredientsToWatchTextArea.getText());
//				}
//				else if (NutriByte.view.genderComboBox.getValue().equals("Female")){
//					NutriByte.person = new Female((NutriByte.view.ageTextField.getText().length()==0?0f:Float.valueOf(NutriByte.view.ageTextField.getText())),
//							(NutriByte.view.weightTextField.getText().length()==0?0f:Float.valueOf(NutriByte.view.weightTextField.getText())),
//							(NutriByte.view.heightTextField.getText().length()==0?0f:Float.valueOf(NutriByte.view.heightTextField.getText())),
//							activityLevel, NutriByte.view.ingredientsToWatchTextArea.getText());
//				}
//				NutriProfiler.createNutriProfile(NutriByte.person);

				
				
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Save file");
				fileChooser.setInitialDirectory(new File(NutriByte.NUTRIBYTE_PROFILE_PATH));
				fileChooser.getExtensionFilters().addAll(
						new ExtensionFilter("CSV Files", "*.csv"));
				File file = null;

				if ((file = fileChooser.showSaveDialog(NutriByte.view.root.getScene().getWindow())) != null) {
					NutriByte.model.writeProfile(file.getAbsolutePath());
				}

			}catch(NumberFormatException | InvalidProfileException err) {

			}
						
		}
	}

	/**
	 * SearchMenuItemHandler --- This handler class handles all search functionality
	 * @author Shweta Dhar
	 * @version 1.0
	 * @since 2018-26-11
	 */
	class SearchButtonHandler implements EventHandler<ActionEvent>{
		/**
		 * This method handles the events that occur when the person clicks the Search button
		 * @param event ActionEvent event that occurs when we click the Search button
		 * @return No return type 
		 */
		@Override
		public void handle(ActionEvent event) {

			NutriByte.model.searchResultsList.clear();
			NutriByte.model.searchResultsList.addAll(Model.productsMap.values());

			if(NutriByte.view.productSearchTextField.getText().isEmpty() && 
					NutriByte.view.nutrientSearchTextField.getText().isEmpty() && 
					NutriByte.view.ingredientSearchTextField.getText().isEmpty()) {

				NutriByte.view.productsComboBox.getSelectionModel().clearSelection();
				NutriByte.view.productsComboBox.setItems(NutriByte.model.searchResultsList);
				if(!NutriByte.model.searchResultsList.isEmpty()) {
					NutriByte.view.productsComboBox.getSelectionModel().selectFirst();
				}
				NutriByte.view.searchResultSizeLabel.setText(NutriByte.model.searchResultsList.size()+" product(s) found");
			}

			else {
				ObservableList<Product> prodList = FXCollections.observableArrayList();
				Iterator<Product> proditer = Model.productsMap.values().iterator();

				while(proditer.hasNext()) 
				{
					Product product = proditer.next();
					ObservableMap<String, Product.ProductNutrient> prodNut = product.getProductNutrients();
					Iterator<String> nutiter = prodNut.keySet().iterator();

					if (!NutriByte.view.nutrientSearchTextField.getText().isEmpty())
					{
						boolean checkProduct = true;
						while(nutiter.hasNext()) {
							String nextNut = nutiter.next();
							if(Model.nutrientsMap.get(nextNut).getNutrientName().toLowerCase().contains(NutriByte.view.nutrientSearchTextField.getText().toLowerCase())) {
								checkProduct=false;
							}
						}
						if(checkProduct)
							prodList.add(product);
					}

					if(!NutriByte.view.productSearchTextField.getText().isEmpty()) {
						if(!product.getProductName().toLowerCase().contains(NutriByte.view.productSearchTextField.getText().toLowerCase())) {
							prodList.add(product);
						}
					}

					if(!NutriByte.view.ingredientSearchTextField.getText().isEmpty()) {
						if(!product.getIngredients().toLowerCase().contains(NutriByte.view.ingredientSearchTextField.getText().toLowerCase())) {
							prodList.add(product);
						}
					}
				}

				NutriByte.model.searchResultsList.removeAll(prodList);
				NutriByte.view.productsComboBox.getSelectionModel().clearSelection();
				NutriByte.view.productsComboBox.setItems(NutriByte.model.searchResultsList);
				if(NutriByte.model.searchResultsList.size()>0) {
					NutriByte.view.productsComboBox.getSelectionModel().selectFirst();
				}
				NutriByte.view.searchResultSizeLabel.setText( NutriByte.model.searchResultsList.size()+" product(s) found");
			}
		}
	}

	/**
	 * ClearButtonHandler --- This handler class clears product, nutrient and ingredient search boxes and
	 * products from the products combo box 
	 * @author Shweta Dhar
	 * @version 1.0
	 * @since 2018-26-11
	 */
	class ClearButtonHandler implements EventHandler<ActionEvent>{
		/**
		 * This method handles the events that occur when the person clicks the clear search button
		 * @param event ActionEvent event that occurs when we click the Clear Search button
		 * @return No return type 
		 */
		@Override
		public void handle(ActionEvent event) {
			NutriByte.view.productSearchTextField.clear();
			NutriByte.view.nutrientSearchTextField.clear();
			NutriByte.view.ingredientSearchTextField.clear();
			NutriByte.view.searchResultSizeLabel.setText("");
			NutriByte.view.productsComboBox.getItems().clear();
			NutriByte.view.productIngredientsTextArea.setText("");
			NutriByte.view.servingSizeLabel.setText("0.00");
			NutriByte.view.householdSizeLabel.setText("0.00");
			NutriByte.view.dietServingUomLabel.setText("");
			NutriByte.view.dietHouseholdUomLabel.setText("");
		}
	}

	/**
	 * ProductsComboBoxListener --- This handler class displays data in the app for the chosen product 
	 * @author Shweta Dhar
	 * @version 1.0
	 * @since 2018-26-11
	 */
	class ProductsComboBoxListener implements EventHandler<ActionEvent>{
		/**
		 * This method handles the events that occur when the person selects a specific product in the products combo box
		 * @param event ActionEvent event that occurs when we select a specific product in the products combo box
		 * @return No return type 
		 */
		@Override
		public void handle(ActionEvent event) {
			Product comboBoxValue = NutriByte.view.productsComboBox.getValue();
			NutriByte.view.productNutrientsTableView.getItems().clear();

			if(comboBoxValue!=null) {
				ObservableList<Product.ProductNutrient> prNrList = FXCollections.observableArrayList();
				prNrList.addAll(comboBoxValue.getProductNutrients().values());
				NutriByte.view.productIngredientsTextArea.setText("Product ingredients: " + comboBoxValue.getIngredients());
				NutriByte.view.productNutrientsTableView.setItems(prNrList);
				NutriByte.view.servingSizeLabel.setText(comboBoxValue.getServingSize() + " " + comboBoxValue.getServingUom());
				NutriByte.view.householdSizeLabel.setText(comboBoxValue.getHouseholdSize() + " " + comboBoxValue.getHouseholdUom());
				NutriByte.view.dietServingUomLabel.setText(comboBoxValue.getServingUom());
				NutriByte.view.dietHouseholdUomLabel.setText(comboBoxValue.getHouseholdUom());
			}
		}
	}

	/**
	 * AddDietButtonHandler --- This handler class displays data in region 3 of the app and populates the nutriChart
	 * @author Shweta Dhar
	 * @version 1.0
	 * @since 2018-26-11
	 */
	class AddDietButtonHandler implements EventHandler<ActionEvent>{
		/**
		 * This method handles the events that occur when the person clicks the add diet button
		 * @param event ActionEvent event that occurs when we click the add diet button
		 * @return No return type 
		 */
		@Override
		public void handle(ActionEvent event) {
			Product comboBoxValue = NutriByte.view.productsComboBox.getValue();
			ObservableList<Product> dietProdList = FXCollections.observableArrayList();
		    dietProdList = NutriByte.view.dietProductsTableView.getItems();
			//NutriByte.view.dietProductsTableView.getSelectionModel().clearSelection();
			//NutriByte.view.dietProductsTableView.getItems().clear();
			
			if(NutriByte.view.dietServingSizeTextField.getText().isEmpty() &&
					NutriByte.view.dietHouseholdSizeTextField.getText().isEmpty()) {
//				if(dietProdList.contains(comboBoxValue)) {
					dietProdList.add(comboBoxValue);
					//handle exception
//				}
			}

			else if((!NutriByte.view.dietServingSizeTextField.getText().isEmpty() &&
					NutriByte.view.dietHouseholdSizeTextField.getText().isEmpty())) { //||
//				if(dietProdList.contains(comboBoxValue)) {
//					dietProdList.remove(comboBoxValue);
//				}

				comboBoxValue.setHouseholdSize((Float.parseFloat(NutriByte.view.dietServingSizeTextField.getText())
						/comboBoxValue.getServingSize())*comboBoxValue.getHouseholdSize());
				comboBoxValue.setServingSize(Float.parseFloat(NutriByte.view.dietServingSizeTextField.getText()));
				dietProdList.add(comboBoxValue);
				//handle exception

			}

			else if(NutriByte.view.dietServingSizeTextField.getText().isEmpty() &&
					!NutriByte.view.dietHouseholdSizeTextField.getText().isEmpty()) {
//				if(dietProdList.contains(comboBoxValue)) {
//					dietProdList.remove(comboBoxValue);
//				}

				comboBoxValue.setServingSize((Float.parseFloat(NutriByte.view.dietHouseholdSizeTextField.getText())
						/comboBoxValue.getHouseholdSize())* comboBoxValue.getServingSize());
				comboBoxValue.setHouseholdSize(Float.parseFloat(NutriByte.view.dietHouseholdSizeTextField.getText()));
				dietProdList.add(comboBoxValue);
				//handle exception

			}

			else if(!NutriByte.view.dietServingSizeTextField.getText().isEmpty() &&
					!NutriByte.view.dietHouseholdSizeTextField.getText().isEmpty()) {
//				if(dietProdList.contains(comboBoxValue)) {
//					dietProdList.remove(comboBoxValue);
//				}

				comboBoxValue.setHouseholdSize((Float.parseFloat(NutriByte.view.dietServingSizeTextField.getText())
						/comboBoxValue.getServingSize())*comboBoxValue.getHouseholdSize());
				comboBoxValue.setServingSize(Float.parseFloat(NutriByte.view.dietServingSizeTextField.getText()));
				dietProdList.add(comboBoxValue);
				//handle exception

			}

			
			NutriByte.view.dietProductsTableView.setItems(dietProdList);
			//if(!NutriByte.person.dietProductsList.contains(comboBoxValue)) {
				NutriByte.person.dietProductsList.add(comboBoxValue);
			//}

			if(NutriByte.person != null) {
				NutriByte.person.populateDietNutrientMap();
				NutriByte.view.nutriChart.updateChart();
			}
		}
	}

	/**
	 * RemoveDietButtonHandler --- This handler class removes products from region 3 of the app and updates nutriChart
	 * @author Shweta Dhar
	 * @version 1.0
	 * @since 2018-26-11
	 */
	class RemoveDietButtonHandler implements EventHandler<ActionEvent>{
		/**
		 * This method handles the events that occur when the person clicks the remove diet button
		 * @param event ActionEvent event that occurs when we click the remove diet button
		 * @return No return type 
		 */
		@Override
		public void handle(ActionEvent event) {
			int index = NutriByte.view.dietProductsTableView.getSelectionModel().getSelectedIndex();

			if(NutriByte.person.dietProductsList.size()>0) {
				NutriByte.person.dietProductsList.remove(index);
				NutriByte.view.dietProductsTableView.setItems(NutriByte.person.dietProductsList);
				NutriByte.person.populateDietNutrientMap();
				NutriByte.view.nutriChart.updateChart();
			}
			if(NutriByte.person.dietProductsList.size()==0) {
				NutriByte.view.dietProductsTableView.getSelectionModel().clearSelection();
				NutriByte.person.populateDietNutrientMap();
				NutriByte.view.nutriChart.clearChart();
			}
		}
	}
	
	/**
	 * CloseMenuItemHandler --- This handler class opens the welcome screen
	 * @author Shweta Dhar
	 * @version 1.0
	 * @since 2018-26-11
	 */
	class CloseMenuItemHandler implements EventHandler<ActionEvent>{
		/**
		 * This method handles the events that occur when the person clicks the close menu item
		 * @param event ActionEvent event that occurs when we click the close menu item
		 * @return No return type 
		 */
		@Override
		public void handle(ActionEvent arg0) {
			NutriByte.view.root.setCenter(NutriByte.view.setupWelcomeScene());	
		}
	}

}
