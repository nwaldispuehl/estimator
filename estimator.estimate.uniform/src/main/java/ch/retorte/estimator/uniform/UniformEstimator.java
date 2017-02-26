package ch.retorte.estimator.uniform;

import ch.retorte.estimator.AbstractEstimator;
import ch.retorte.estimator.Estimation;

/**
 * Estimates a value at the end of a time frame assuming a uniform distribution.
 */
public class UniformEstimator extends AbstractEstimator {

  @Override
  public String getId() {
    return "uniform";
  }

  @Override
  public Estimation validatedEstimateTotalFrom(int startTime, int endTime, int currentTime, double currentValue) {
    int relativeTimeFrame = endTime - startTime;
    int relativeCurrentTime = currentTime - startTime;

    double result = currentValue / relativeCurrentTime * relativeTimeFrame;

    return new Estimation(result, 0);
  }

}
