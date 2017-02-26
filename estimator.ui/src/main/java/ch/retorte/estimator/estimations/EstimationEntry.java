package ch.retorte.estimator.estimations;

import ch.retorte.estimator.Estimation;
import ch.retorte.estimator.Estimator;
import javafx.beans.Observable;
import javafx.beans.property.*;

/**
 * Model for the single estimations.
 */
public class EstimationEntry {

  //---- Fields

  private StringProperty name = new SimpleStringProperty();
  private ObjectProperty<Estimator> estimator = new SimpleObjectProperty();

  private DoubleProperty currentValue = new SimpleDoubleProperty();
  private DoubleProperty estimatedValue = new SimpleDoubleProperty();

  private DoubleProperty availableResources = new SimpleDoubleProperty();
  private DoubleProperty availableResourcesDelta = new SimpleDoubleProperty();


  //---- Constructor

  public EstimationEntry() {
    availableResourcesDelta.bind(availableResources.subtract(estimatedValue));
  }

  //---- Methods

  public void calculateWith(int startTime, int endTime, int currentTime) {
    if (estimator.isNotNull().get()) {
      Estimation estimation = estimator.get().estimateTotalFrom(startTime, endTime, currentTime, currentValue.get());
      estimatedValue.set(estimation.getValue());
    }
  }

  //---- Properties

  public StringProperty nameProperty() {
    return name;
  }

  public ObjectProperty<Estimator> estimatorProperty() {
    return estimator;
  }

  public DoubleProperty currentValueProperty() {
    return currentValue;
  }

  public DoubleProperty estimatedValueProperty() {
    return estimatedValue;
  }

  public DoubleProperty availableResourcesProperty() {
    return availableResources;
  }

  public DoubleProperty availableResourcesDeltaProperty() {
    return availableResourcesDelta;
  }

}
