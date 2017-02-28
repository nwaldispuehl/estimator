package ch.retorte.estimator.estimations;

import ch.retorte.estimator.Estimator;
import ch.retorte.estimator.converter.ForgivingNumberStringConverter;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.converter.NumberStringConverter;

/**
 * Created by nw on 26.02.17.
 */

public class EstimationEntryView extends GridPane {

  //---- Static

  private static final String LAYOUT_FILE = "/layouts/EstimationEntry.fxml";
  private static final NumberStringConverter NUMBER_STRING_CONVERTER = new ForgivingNumberStringConverter();

  private static final String DECIMAL_FORMAT = "%.1f";

  //---- FX Fields

  @FXML
  private TextField estimationName;

  @FXML
  private ComboBox<Estimator> estimator;

  @FXML
  private TextField currentValue;

  @FXML
  private Label estimatedValue;

  @FXML
  private TextField availableResources;

  @FXML
  private Label availableResourcesDelta;

  @FXML
  private Button delete;


  //---- Fields

  private final ObservableList<Estimator> availableEstimators;
  private final EstimationEntry estimationEntry;


  //---- Constructor

  public EstimationEntryView(ObservableList<Estimator> availableEstimators, EstimationEntry estimationEntry) {
    setupLayout();

    this.availableEstimators = availableEstimators;
    this.estimationEntry = estimationEntry;

    initializeEditableFields();
    initializeReadOnlyFields();
    initializeOperations();
  }



  private void setupLayout() {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(LAYOUT_FILE));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
    } catch (Exception exception) {
      throw new RuntimeException(exception);
    }
  }

  private void initializeEditableFields() {
    // Name
    estimationEntry.nameProperty().bindBidirectional(estimationName.textProperty());

    // Estimator combo
    estimator.setItems(availableEstimators);
    estimator.getSelectionModel().select(0);
    estimationEntry.estimatorProperty().bind(estimator.getSelectionModel().selectedItemProperty());

    // Current value
    currentValue.textProperty().bindBidirectional(estimationEntry.currentValueProperty(), NUMBER_STRING_CONVERTER);
    availableResources.textProperty().bindBidirectional(estimationEntry.availableResourcesProperty(), NUMBER_STRING_CONVERTER);
  }

  private void initializeReadOnlyFields() {
    estimatedValue.textProperty().bind(estimationEntry.estimatedValueProperty().asString(DECIMAL_FORMAT));
    availableResourcesDelta.textProperty().bind(estimationEntry.availableResourcesDeltaProperty().asString(DECIMAL_FORMAT));
  }

  private void initializeOperations() {

  }

}
