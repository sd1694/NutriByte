//Name: SHWETA DHAR
//Andrew id: shwetad

package hw3;

import hw3.NutriProfiler;
import hw3.NutriProfiler.PhysicalActivityEnum;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * NutriByte --- class implements functionality by using Model and View class to display the 
 * information (recommended nutrients) as per the requirements. Also launches the GUI of the application.
 * @author Shweta Dhar
 * @version 2.0
 * @since 2018-26-11  
 */
public class NutriByte extends Application{
	static Model model = new Model();  	//made static to make accessible in the controller
	static View view = new View();		//made static to make accessible in the controller
	static Person person;				//made static to make accessible in the controller


	Controller controller = new Controller();	//all event handlers 

	/**Uncomment the following three lines if you want to try out the full-size data files */
	//	static final String PRODUCT_FILE = "data/Products.csv";
	//	static final String NUTRIENT_FILE = "data/Nutrients.csv";
	//	static final String SERVING_SIZE_FILE = "data/ServingSize.csv";

	/**The following constants refer to the data files to be used for this application */
	static final String PRODUCT_FILE = "data/Nutri2Products.csv";
	static final String NUTRIENT_FILE = "data/Nutri2Nutrients.csv";
	static final String SERVING_SIZE_FILE = "data/Nutri2ServingSize.csv";

	static final String NUTRIBYTE_IMAGE_FILE = "NutriByteLogo.png"; //Refers to the file holding NutriByte logo image 

	static final String NUTRIBYTE_PROFILE_PATH = "profiles";  //folder that has profile data files

	static final int NUTRIBYTE_SCREEN_WIDTH = 1015;
	static final int NUTRIBYTE_SCREEN_HEIGHT = 675;

	/**
	 * This method sets the stage of the application i.e. the GUI
	 * @param stage Stage stage of the NutriByte application
	 * @return No return type 
	 */
	@Override
	public void start(Stage stage) throws Exception {
		model.readProducts(PRODUCT_FILE);
		model.readNutrients(NUTRIENT_FILE);
		model.readServingSizes(SERVING_SIZE_FILE );
		view.setupMenus();
		view.setupNutriTrackerGrid();
		view.root.setCenter(view.setupWelcomeScene());
		Background b = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));
		view.root.setBackground(b);
		Scene scene = new Scene (view.root, NUTRIBYTE_SCREEN_WIDTH, NUTRIBYTE_SCREEN_HEIGHT);
		view.root.requestFocus();  //this keeps focus on entire window and allows the textfield-prompt to be visible
		setupBindings();
		stage.setTitle("NutriByte 3.0");
		stage.setScene(scene);

		//To set the binding of the recommended nutrients button
		personBinding.addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				person = newValue;
				NutriProfiler.createNutriProfile(person);
				if(!view.ageTextField.getText().isEmpty() && !view.weightTextField.getText().isEmpty() &&
						!view.heightTextField.getText().isEmpty()) {
					NutriByte.view.recommendedNutrientsTableView.setItems(NutriByte.person.recommendedNutrientsList);
				}
			} else {
				person = null;
			}
		});
		stage.show();
	}


	ObjectBinding<Person> personBinding = new ObjectBinding<>() {
		{
			super.bind(view.genderComboBox.valueProperty(),view.ageTextField.textProperty(),
					view.weightTextField.textProperty(), view.physicalActivityComboBox.valueProperty(),
					view.heightTextField.textProperty());
		}

		@Override
		protected Person computeValue() {
			int gender;
			Float age = 0f, weight = 0f, height = 0f, physicalActivityLevel = 1f;

			try {
				gender = NutriByte.view.genderComboBox.getSelectionModel().getSelectedIndex();
				if(gender<0) {
					throw new Exception();
				}

				age = Float.parseFloat(view.ageTextField.getText().trim());
				weight = Float.parseFloat(view.weightTextField.getText().trim());
				height = Float.parseFloat(view.heightTextField.getText().trim());

				for(PhysicalActivityEnum paEnum: PhysicalActivityEnum.values()) {
					if(paEnum.getName().equalsIgnoreCase(NutriByte.view.physicalActivityComboBox.getValue())) {
						physicalActivityLevel = paEnum.getPhysicalActivityLevel();
					}
				}

				if(NutriByte.view.genderComboBox.getValue().equals("Female")) {
					return new Female(age, weight, height, physicalActivityLevel, view.ingredientsToWatchTextArea.getText());
				}

				else if(NutriByte.view.genderComboBox.getValue().equals("Male")){
					return new Male(age, weight, height, physicalActivityLevel, view.ingredientsToWatchTextArea.getText());
				}

				else {
					return null;
				}

			} catch (Exception e) {
				return null;
			} 
		}
	};


	/**
	 * This method launches the application
	 * @param args[] String a string array containing the command line arguments.
	 * @return No return type 
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * This method binds the application components to their controllers and callbacks
	 * @param No parameters
	 * @return No return type 
	 */
	void setupBindings() {
		view.newNutriProfileMenuItem.setOnAction(controller.new NewMenuItemHandler());
		view.openNutriProfileMenuItem.setOnAction(controller.new OpenMenuItemHandler());
		view.exitNutriProfileMenuItem.setOnAction(event -> Platform.exit());
		view.aboutMenuItem.setOnAction(controller.new AboutMenuItemHandler());
		view.closeNutriProfileMenuItem.setOnAction(controller.new CloseMenuItemHandler());

		view.saveNutriProfileMenuItem.setOnAction(controller.new SaveMenuItemHandler());
		view.searchButton.setOnAction(controller.new SearchButtonHandler());
		view.clearButton.setOnAction(controller.new ClearButtonHandler());
		view.productsComboBox.setOnAction(controller.new ProductsComboBoxListener());
		view.addDietButton.setOnAction(controller.new AddDietButtonHandler());
		view.removeDietButton.setOnAction(controller.new RemoveDietButtonHandler());

		view.recommendedNutrientNameColumn.setCellValueFactory(recommendedNutrientNameCallback);
		view.recommendedNutrientQuantityColumn.setCellValueFactory(recommendedNutrientQuantityCallback);
		view.recommendedNutrientUomColumn.setCellValueFactory(recommendedNutrientUomCallback);

		view.productNutrientNameColumn.setCellValueFactory(productNutrientNameCallback);
		view.productNutrientQuantityColumn.setCellValueFactory(productNutrientQuantityCallback);
		view.productNutrientUomColumn.setCellValueFactory(productNutrientUomCallback);

		//		view.dietProductNameColumn.setCellValueFactory(dietProductNameCallback);
		//		view.dietServingSizeColumn.setCellValueFactory(dietServingSizeCallback);
		//		view.dietServingUomColumn.setCellValueFactory(dietServingUomCallback);
		//		view.dietHouseholdSizeColumn.setCellValueFactory(dietHouseholdSizeCallback);
		//		view.dietHouseholdUomColumn.setCellValueFactory(dietHouseholdUomCallback);

		view.createProfileButton.setOnAction(controller.new RecommendNutrientsButtonHandler());

		view.ageTextField.textProperty().addListener((Observable, oldValue, newValue) ->{
			view.ageTextField.setStyle("-fx-text-fill: black;");
			try {
				if(Float.parseFloat(newValue)<0) {
					view.ageTextField.setStyle("-fx-text-fill: red;");
				}
				else {
					view.ageTextField.setStyle("-fx-text-fill: black;");
				}
			}catch(NumberFormatException err) {
				view.ageTextField.setStyle("-fx-text-fill: red;");
			}
		});

		view.weightTextField.textProperty().addListener((Observable, oldValue, newValue) ->{
			view.weightTextField.setStyle("-fx-text-fill: black;");
			try {
				if(Float.parseFloat(newValue)<0) {
					view.weightTextField.setStyle("-fx-text-fill: red;");
				}
				else {
					view.weightTextField.setStyle("-fx-text-fill: black;");
				}
			}catch(NumberFormatException err) {
				view.weightTextField.setStyle("-fx-text-fill: red;");
			}
		});

		view.heightTextField.textProperty().addListener((Observable, oldValue, newValue) ->{
			view.heightTextField.setStyle("-fx-text-fill: black;");
			try {
				if(Float.parseFloat(newValue)<0) {
					view.heightTextField.setStyle("-fx-text-fill: red;");
				}
				else {
					view.heightTextField.setStyle("-fx-text-fill: black;");
				}
			}catch(NumberFormatException err) {
				view.heightTextField.setStyle("-fx-text-fill: red;");
			}
		});

	}

	/**
	 * Callback that finds the nutrient's name from the nutrientMap and returns it
	 */
	Callback<CellDataFeatures<RecommendedNutrient, String>, ObservableValue<String>> recommendedNutrientNameCallback = new Callback<CellDataFeatures<RecommendedNutrient, String>, ObservableValue<String>>() {
		@Override
		public ObservableValue<String> call(CellDataFeatures<RecommendedNutrient, String> arg0) {
			Nutrient nutrient = Model.nutrientsMap.get(arg0.getValue().getNutrientCode());
			return nutrient.nutrientNameProperty();
		}
	};

	/**
	 * Callback that converts the value of nutrient quantity to a string to print a float value with only two decimal points
	 */
	Callback<CellDataFeatures<RecommendedNutrient, String>, ObservableValue<String>> recommendedNutrientQuantityCallback = new Callback<CellDataFeatures<RecommendedNutrient, String>, ObservableValue<String>>() {
		@Override
		public ObservableValue<String> call(CellDataFeatures<RecommendedNutrient, String> arg0) {
			return (new SimpleStringProperty(String.format("%.2f",arg0.getValue().getNutrientQuantity())));
		}
	};

	/**
	 * Callback that finds the nutrient's unit of measure from the nutrientMap and returns it
	 */
	Callback<CellDataFeatures<RecommendedNutrient, String>, ObservableValue<String>> recommendedNutrientUomCallback = new Callback<CellDataFeatures<RecommendedNutrient, String>, ObservableValue<String>>() {
		@Override
		public ObservableValue<String> call(CellDataFeatures<RecommendedNutrient, String> arg0) {
			Nutrient nutrient = Model.nutrientsMap.get(arg0.getValue().getNutrientCode());
			return nutrient.nutrientUomProperty();
		}
	};

	/**
	 * Callback that finds the product nutrient's name from the nutrientMap and returns it
	 */
	Callback<CellDataFeatures<Product.ProductNutrient, String>, ObservableValue<String>> productNutrientNameCallback = new Callback<CellDataFeatures<Product.ProductNutrient, String>, ObservableValue<String>>() {
		@Override
		public ObservableValue<String> call(CellDataFeatures<Product.ProductNutrient, String> arg0) {
			Nutrient nutrient = Model.nutrientsMap.get(arg0.getValue().getNutrientCode());
			return nutrient.nutrientNameProperty();
		}
	};

	/**
	 * Callback that finds the product nutrient's quantity from the nutrientMap and returns it
	 */
	Callback<CellDataFeatures<Product.ProductNutrient, String>, ObservableValue<String>> productNutrientQuantityCallback = new Callback<CellDataFeatures<Product.ProductNutrient, String>, ObservableValue<String>>() {
		@Override
		public ObservableValue<String> call(CellDataFeatures<Product.ProductNutrient, String> arg0) {
			return (new SimpleStringProperty(String.format("%.2f",arg0.getValue().getNutrientQuantity())));
		}
	};

	/**
	 * Callback that finds the product nutrient's unit of measure from the nutrientMap and returns it
	 */
	Callback<CellDataFeatures<Product.ProductNutrient, String>, ObservableValue<String>> productNutrientUomCallback = new Callback<CellDataFeatures<Product.ProductNutrient, String>, ObservableValue<String>>() {
		@Override
		public ObservableValue<String> call(CellDataFeatures<Product.ProductNutrient, String> arg0) {
			Nutrient nutrient = Model.nutrientsMap.get(arg0.getValue().getNutrientCode());
			return nutrient.nutrientUomProperty();

		}
	};

	//	Callback<CellDataFeatures<Product, String>, ObservableValue<String>> dietProductNameCallback = new Callback<CellDataFeatures<Product, String>, ObservableValue<String>>() {
	//		@Override
	//		public ObservableValue<String> call(CellDataFeatures<Product, String> arg0) {
	//			Product product = Model.productsMap.get(arg0.getValue().getProductName());
	//			return product.productNameProperty();
	//
	//		}
	//	};
	//
	//	Callback<CellDataFeatures<Product, Float>, ObservableValue<Float>> dietServingSizeCallback = new Callback<CellDataFeatures<Product, Float>, ObservableValue<Float>>() {
	//		@Override
	//		public ObservableValue<Float> call(CellDataFeatures<Product, Float> arg0) {
	//			return (arg0.getValue().servingSizeProperty().asObject());
	//		}
	//	};
	//
	//	Callback<CellDataFeatures<Product, String>, ObservableValue<String>> dietServingUomCallback = new Callback<CellDataFeatures<Product, String>, ObservableValue<String>>() {
	//		@Override
	//		public ObservableValue<String> call(CellDataFeatures<Product, String> arg0) {
	//			Product product = Model.productsMap.get(arg0.getValue().getServingUom());
	//			return product.servingUomProperty();
	//
	//		}
	//	};
	//
	//	Callback<CellDataFeatures<Product, Float>, ObservableValue<Float>> dietHouseholdSizeCallback = new Callback<CellDataFeatures<Product, Float>, ObservableValue<Float>>() {
	//		@Override
	//		public ObservableValue<Float> call(CellDataFeatures<Product, Float> arg0) {
	//			return (arg0.getValue().householdSizeProperty().asObject());
	//		}
	//	};
	//
	//	Callback<CellDataFeatures<Product, String>, ObservableValue<String>> dietHouseholdUomCallback = new Callback<CellDataFeatures<Product, String>, ObservableValue<String>>() {
	//		@Override
	//		public ObservableValue<String> call(CellDataFeatures<Product, String> arg0) {
	//			Product product = Model.productsMap.get(arg0.getValue().getHouseholdUom());
	//			return product.householdUomProperty();
	//
	//		}
	//	};

}
