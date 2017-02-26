package ch.retorte.estimator.estimations;

import ch.retorte.estimator.Estimator;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by nw on 26.02.17.
 */

public class EstimationEntryView extends GridPane {

  //---- Static

  private static final String LAYOUT_FILE = "/layouts/EstimationEntry.fxml";
  private static final NumberStringConverter NUMBER_STRING_CONVERTER = new ForgivingNumberStringConverter();


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
    estimationName.textProperty().bindBidirectional(estimationEntry.nameProperty());

    estimator.setItems(availableEstimators);
    estimationEntry.estimatorProperty().bind(estimator.getSelectionModel().selectedItemProperty());

    currentValue.textProperty().bindBidirectional(estimationEntry.currentValueProperty(), NUMBER_STRING_CONVERTER);
    availableResources.textProperty().bindBidirectional(estimationEntry.availableResourcesProperty(), NUMBER_STRING_CONVERTER);
  }

  private void initializeReadOnlyFields() {
    estimatedValue.textProperty().bind(estimationEntry.estimatedValueProperty().asString());
    availableResourcesDelta.textProperty().bind(estimationEntry.availableResourcesDeltaProperty().asString());
  }

  private void initializeOperations() {

  }

}
