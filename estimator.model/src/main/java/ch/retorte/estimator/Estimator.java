package ch.retorte.estimator;

/**
 * The {@link Estimator} tries to estimate some value at the end of some time frame based on the value at a given time and assuming the value grows based on a given distribution.
 */
public interface Estimator {

  /**
   * The identifier of this estimator implementation.
   */
  String getId();

  /**
   * Given some time information and a current value, estimates what the value could be at the end time if it grows following a certain rule.
   */
  Estimation estimateTotalFrom(int startTime, int endTime, int currentTime, double currentValue);

}
