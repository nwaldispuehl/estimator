package ch.retorte.estimator.mainscreen;

import ch.retorte.estimator.Estimator;
import ch.retorte.estimator.converter.LocalTimeStringConverter;
import ch.retorte.estimator.estimations.EstimationEntry;
import ch.retorte.estimator.estimations.EstimationEntryView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Created by nw on 26.02.17.
 */
public class MainScreenController implements Initializable {

  //---- Static

  private final LocalTimeStringConverter TIME_CONVERTER = new LocalTimeStringConverter();

  //---- FX fields

  @FXML
  private Button addButton;

  @FXML
  private TextField startTime;

  @FXML
  private Label currentTime;

  @FXML
  private TextField endTime;

  @FXML
  private ProgressBar progress;

  @FXML
  private VBox estimationItems;


  //---- Fields

  private ObservableList<Estimator> availableEstimators;
  private ObservableList<EstimationEntry> estimationEntries = FXCollections.observableArrayList();

  private ObjectProperty<LocalTime> startTimeProperty = new SimpleObjectProperty();
  private ObjectProperty<LocalTime> currentTimeProperty = new SimpleObjectProperty();
  private ObjectProperty<LocalTime> endTimeProperty = new SimpleObjectProperty();


  //---- Methods

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    initializeAddButton();
    initializeTimeFields();
    initializeProgressBar();
  }



  private void initializeAddButton() {
    addButton.setOnAction(event -> addNewEstimatorEntry());
  }

  private void initializeTimeFields() {
    startTime.textProperty().bindBidirectional(startTimeProperty, TIME_CONVERTER);
    currentTime.textProperty().bindBidirectional(currentTimeProperty, new LocalTimeStringConverter(LocalTimeStringConverter.DEFAULT_TIME_PARSER, DateTimeFormatter.ofPattern("HH:mm:ss")));
    endTime.textProperty().bindBidirectional(endTimeProperty, TIME_CONVERTER);
  }

  private void initializeProgressBar() {
//    progress.progressProperty().
  }

  private void addNewEstimatorEntry() {
    EstimationEntryView estimationEntryView = new EstimationEntryView(availableEstimators, createNewEstimationEntry());
    estimationItems.getChildren().add(estimationEntryView);
  }

  private EstimationEntry createNewEstimationEntry() {
    EstimationEntry estimationEntry = new EstimationEntry();
    estimationEntries.add(estimationEntry);
    return estimationEntry;
  }

  public void setAvailableEstimators(ObservableList<Estimator> availableEstimators) {
    this.availableEstimators = availableEstimators;
  }

  public void refresh() {
    currentTimeProperty.setValue(LocalTime.now());
    calculateTimeDependentFields();
  }

  private void calculateTimeDependentFields() {
    if (isTimeValid()) {
      int startTime = startTimeProperty.get().toSecondOfDay();
      int currentTime = currentTimeProperty.get().toSecondOfDay();
      int endTime = endTimeProperty.get().toSecondOfDay();

      calculateProgressWith(startTime, endTime, currentTime);
      calculateEstimationEntriesWith(startTime, endTime, currentTime);
    }
  }

  private boolean isTimeValid() {
    return startTimeProperty.isNotNull().and(currentTimeProperty.isNotNull()).and(endTimeProperty.isNotNull()).get();
  }

  private void calculateProgressWith(int startTime, int endTime, int currentTime) {
    double relativeTimeFrame = endTime - startTime;
    double relativeCurrent = currentTime - startTime;
    progress.progressProperty().setValue(relativeCurrent / relativeTimeFrame);
  }

  private void calculateEstimationEntriesWith(int startTime, int endTime, int currentTime) {
    for (EstimationEntry e : estimationEntries) {
      e.calculateWith(startTime, endTime, currentTime);
    }
  }


}
