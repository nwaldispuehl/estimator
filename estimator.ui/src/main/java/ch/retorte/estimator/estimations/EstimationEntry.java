package ch.retorte.estimator.estimations;

import ch.retorte.estimator.Estimation;
import ch.retorte.estimator.Estimator;
import ch.retorte.estimator.storage.EstimationData;
import javafx.beans.property.*;

/**
 * Model for the single estimations.
 */
public class EstimationEntry {

  //---- Static

  private static final double WARNING_THRESHOLD = 0.1;

  private static final String DEFAULT = "black";
  private static final String GOOD = "#4CAF50";
  private static final String WARNING = "#FFCA28";
  private static final String BAD = "#FF9800";
  private static final String VERY_BAD = "#F44336";

  //---- Fields

  private StringProperty name = new SimpleStringProperty();
  private ObjectProperty<Estimator> estimator = new SimpleObjectProperty<>();

  private DoubleProperty currentValue = new SimpleDoubleProperty();
  private DoubleProperty correctionValue = new SimpleDoubleProperty();
  private DoubleProperty aggregatedCurrentValue = new SimpleDoubleProperty();

  private DoubleProperty estimatedValue = new SimpleDoubleProperty();

  private DoubleProperty availableResources = new SimpleDoubleProperty();
  private DoubleProperty availableResourcesDelta = new SimpleDoubleProperty();

  private StringProperty availableResourcesDeltaStyle = new SimpleStringProperty();

  //---- Constructor

  public EstimationEntry(EstimationData estimationData) {
    initializeBindings();

    this.name.set(estimationData.getName());
    this.estimator.set(estimationData.getEstimator());
    this.currentValue.set(estimationData.getCurrentValue());
    this.correctionValue.set(estimationData.getCorrectionValue());
    this.availableResources.set(estimationData.getAvailableResources());
  }

  //---- Methods

  private void initializeBindings() {
    availableResourcesDelta.bind(availableResources.subtract(estimatedValue));
    aggregatedCurrentValue.bind(currentValue.add(correctionValue));
  }

  public void calculateWith(int startTime, int endTime, int currentTime) {
    if (estimator.isNotNull().get()) {

      if (endTime < startTime) {
        endTime = startTime;
      }

      if (currentTime < startTime) {
        currentTime = startTime;
      }

      if (endTime < currentTime) {
        currentTime = endTime;
      }

      Estimation estimation = estimator.get().estimateTotalFrom(startTime, endTime, currentTime, aggregatedCurrentValue.get());
      estimatedValue.set(estimation.getValue());

      updateDeltaStylingWith(aggregatedCurrentValue.get(), availableResources.get(), availableResourcesDelta.get());
    }
    else {
      resetDeltaColor();
    }
  }

  private void updateDeltaStylingWith(double current, double available, double delta) {
    if (available < current) {
      styleDeltaWith(textColor(VERY_BAD));
    }
    else if (delta <= 0) {
      styleDeltaWith(textColor(BAD));
    }
    else if (delta <= available * WARNING_THRESHOLD) {
      styleDeltaWith(textColor(WARNING));
    }
    else {
      styleDeltaWith(textColor(GOOD));
    }
  }

  private void resetDeltaColor() {
    styleDeltaWith(textColor(DEFAULT));
  }

  private void styleDeltaWith(String style) {
    availableResourcesDeltaStyle.set(style);
  }

  private String textColor(String color) {
    return "-fx-text-fill: " + color + ";";
  }

  public EstimationData getData() {
    return new EstimationData(name.get(), estimator.get(), currentValue.get(), correctionValue.get(), availableResources.get());
  }

  //---- Properties

  StringProperty nameProperty() {
    return name;
  }

  ObjectProperty<Estimator> estimatorProperty() {
    return estimator;
  }

  DoubleProperty currentValueProperty() {
    return currentValue;
  }

  DoubleProperty correctionValueProperty() {
    return correctionValue;
  }

  DoubleProperty estimatedValueProperty() {
    return estimatedValue;
  }

  DoubleProperty availableResourcesProperty() {
    return availableResources;
  }

  DoubleProperty availableResourcesDeltaProperty() {
    return availableResourcesDelta;
  }

  StringProperty getAvailableResourcesDeltaStyleProperty() {
    return availableResourcesDeltaStyle;
  }

}
