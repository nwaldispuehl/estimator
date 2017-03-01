package ch.retorte.estimator.estimations;

import ch.retorte.estimator.Estimator;

/**
 * Holds the state of a single estimation line.
 */
public class EstimationData {

  String name;
  Estimator estimator;
  double currentValue;
  double availableResources;

  public EstimationData() {
  }

  EstimationData(String name, Estimator estimator, double currentValue, double availableResources) {
    this.name = name;
    this.estimator = estimator;
    this.currentValue = currentValue;
    this.availableResources = availableResources;
  }

}
