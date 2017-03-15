package ch.retorte.estimator.mainscreen;

import ch.retorte.estimator.Estimator;
import ch.retorte.estimator.Ui;
import ch.retorte.estimator.converter.LocalTimeStringConverter;
import ch.retorte.estimator.storage.EstimationData;
import ch.retorte.estimator.estimations.EstimationEntry;
import ch.retorte.estimator.estimations.EstimationEntryView;
import ch.retorte.estimator.storage.ApplicationData;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import static ch.retorte.estimator.converter.LocalTimeStringConverter.DEFAULT_TIME_PARSER;
import static java.util.stream.Collectors.toList;

/**
 * Controller for the top level ui element.
 */
public class MainScreenController implements Initializable, EntryController {

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

  private ObjectProperty<LocalTime> startTimeProperty = new SimpleObjectProperty<>();
  private ObjectProperty<LocalTime> currentTimeProperty = new SimpleObjectProperty<>();
  private ObjectProperty<LocalTime> endTimeProperty = new SimpleObjectProperty<>();

  private Ui.InputChangeListener inputChangeListener;


  //---- Methods

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    initializeAddButton();
    initializeStartTimeField();
    initializeCurrentTimeField();
    initializeEndTimeField();
  }

  private void initializeStartTimeField() {
    startTime.textProperty().bindBidirectional(startTimeProperty, TIME_CONVERTER);
  }

  private void initializeCurrentTimeField() {
    currentTime.textProperty().bindBidirectional(currentTimeProperty, new LocalTimeStringConverter(DEFAULT_TIME_PARSER, DateTimeFormatter.ofPattern("HH:mm:ss")));
  }

  private void initializeEndTimeField() {
    endTime.textProperty().bindBidirectional(endTimeProperty, TIME_CONVERTER);

  }

  private void initializeAddButton() {
    addButton.setOnAction(event -> {
      addNewEstimatorEntry(new EstimationData());
      triggerChangeListener();
    });
  }

  private void addNewEstimatorEntry(EstimationData estimationData) {
    EstimationEntry newEstimationEntry = createNewEstimationEntryWith(estimationData);
    estimationEntries.add(newEstimationEntry);

    EstimationEntryView estimationEntryView = new EstimationEntryView(this, availableEstimators, newEstimationEntry, inputChangeListener);
    estimationItems.getChildren().add(estimationEntryView);
  }

  private EstimationEntry createNewEstimationEntryWith(EstimationData estimationData) {
    return new EstimationEntry(estimationData);
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

  public void setUpdateListener(Ui.InputChangeListener inputChangeListener) {
    this.inputChangeListener = inputChangeListener;

    startTime.textProperty().addListener(inputChangeListener);
    endTime.textProperty().addListener(inputChangeListener);
  }

  public void setData(ApplicationData applicationData) {
    startTimeProperty.setValue(applicationData.getStartTime());
    endTimeProperty.setValue(applicationData.getEndTime());
    applicationData.getEstimationDataList().forEach(this::addNewEstimatorEntry);
  }

  public ApplicationData getData() {
    return new ApplicationData(startTimeProperty.get(), endTimeProperty.get(), getEstimatorDataList());
  }

  private List<EstimationData> getEstimatorDataList() {
    return estimationEntries.stream().map(EstimationEntry::getData).collect(toList());
  }

  @Override
  public void delete(EstimationEntryView estimationEntryView) {
    estimationItems.getChildren().remove(estimationEntryView);
    estimationEntries.remove(estimationEntryView.getEntry());
    triggerChangeListener();
  }

  private void triggerChangeListener() {
    inputChangeListener.changed(null, null, null);
  }

  public void showError(String message, Exception e) {
    e.printStackTrace();
    
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(message);
    alert.setContentText(e.getLocalizedMessage());
    alert.setResizable(true);
    alert.showAndWait();
  }
}
