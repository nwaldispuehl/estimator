package ch.retorte.estimator.normal;

import ch.retorte.estimator.AbstractEstimator;
import ch.retorte.estimator.Estimation;
import org.apache.commons.math3.distribution.NormalDistribution;

/**
 * This {@link NormalEstimator} is a base class for all normal distributions being normed to the [0, 1] range with mu = 0.5.
 */
public abstract class NormalEstimator extends AbstractEstimator {

  //---- Static

  private static final double NORMED_MEAN = 0.5;



  //---- Method

  /**
   * Returns the SD to be used in the normal distribution with mean of 0.5.
   */
  abstract double getStandardDeviation();

  @Override
  public Estimation validatedEstimateTotalFrom(int startTime, int endTime, int currentTime, double currentValue) {

    double x = getXFrom(startTime, currentTime, endTime);
    double cumulativeProbability = normalizedCumulativeProbabilityOf(x);

    double result = currentValue / cumulativeProbability;

    return new Estimation(result,0);
  }

  private double getXFrom(int startTime, int currentTime, int endTime) {
    int relativeTimeFrame = endTime - startTime;
    int relativeCurrentTime = currentTime - startTime;
    return (double) relativeCurrentTime / (double) relativeTimeFrame;
  }

  private double normalizedCumulativeProbabilityOf(double x) {
    return normalizedNormalDistribution().cumulativeProbability(x);
  }

  /**
   * This normal distribution is 'normalized' in the sense that we only consider the [0, 1] range.
   */
  private NormalDistribution normalizedNormalDistribution() {
    return new NormalDistribution(NORMED_MEAN, getNormedStandardDeviation());
  }

  /**
   * We norm the standard deviation to fit in our observed [0, 1] range.
   */
  private double getNormedStandardDeviation() {
    return 1.0 / (2 * getStandardDeviation());
  }


}
