package ch.retorte.estimator.storage;

import ch.retorte.estimator.Estimator;

/**
 * Holds the state of a single estimation line.
 */
public class EstimationData {

  private String name;
  private Estimator estimator;
  private double currentValue;
  private double availableResources;

  public EstimationData() {
  }

  public EstimationData(String name, Estimator estimator, double currentValue, double availableResources) {
    this.name = name;
    this.estimator = estimator;
    this.currentValue = currentValue;
    this.availableResources = availableResources;
  }

  public String getName() {
    return name;
  }

  public Estimator getEstimator() {
    return estimator;
  }

  public double getCurrentValue() {
    return currentValue;
  }

  public double getAvailableResources() {
    return availableResources;
  }
}
