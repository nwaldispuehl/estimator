package ch.retorte.estimator.estimations;

import ch.retorte.estimator.Estimation;
import ch.retorte.estimator.Estimator;
import ch.retorte.estimator.storage.EstimationData;
import javafx.beans.property.*;

/**
 * Model for the single estimations.
 */
public class EstimationEntry {

  //---- Fields

  private StringProperty name = new SimpleStringProperty();
  private ObjectProperty<Estimator> estimator = new SimpleObjectProperty<>();

  private DoubleProperty currentValue = new SimpleDoubleProperty();
  private DoubleProperty estimatedValue = new SimpleDoubleProperty();

  private DoubleProperty availableResources = new SimpleDoubleProperty();
  private DoubleProperty availableResourcesDelta = new SimpleDoubleProperty();


  //---- Constructor

  public EstimationEntry() {
    availableResourcesDelta.bind(availableResources.subtract(estimatedValue));
  }

  public EstimationEntry(EstimationData estimationData) {
    this();

    this.name.setValue(estimationData.getName());
    this.estimator.set(estimationData.getEstimator());
    this.currentValue.set(estimationData.getCurrentValue());
    this.availableResources.set(estimationData.getAvailableResources());
  }

  //---- Methods

  public void calculateWith(int startTime, int endTime, int currentTime) {
    if (estimator.isNotNull().get()) {
      Estimation estimation = estimator.get().estimateTotalFrom(startTime, endTime, currentTime, currentValue.get());
      estimatedValue.set(estimation.getValue());
    }
  }

  public EstimationData getData() {
    return new EstimationData(name.get(), estimator.get(), currentValue.get(), availableResources.get());
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

  DoubleProperty estimatedValueProperty() {
    return estimatedValue;
  }

  DoubleProperty availableResourcesProperty() {
    return availableResources;
  }

  DoubleProperty availableResourcesDeltaProperty() {
    return availableResourcesDelta;
  }

}
