package ch.retorte.estimator;

/**
 * Holds the estimated value and an error measure.
 */
public class Estimation {

  private double value;
  private double error;

  public Estimation(double value, double error) {
    this.value = value;
    this.error = error;
  }

  public double getValue() {
    return value;
  }

  public double getError() {
    return error;
  }
}
