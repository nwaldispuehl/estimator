package ch.retorte.estimator.normal;

import ch.retorte.estimator.AbstractEstimator;
import ch.retorte.estimator.Estimation;
import org.apache.commons.math3.distribution.NormalDistribution;

/**
 * Assuming samples are normal distributed, tries to predict number of items at the end of a time period based on the number of items at some given point in time.
 */
public class NormalEstimator extends AbstractEstimator {

  //---- Static

  private static final double NORMED_MEAN = 0.5;
  private static final double NORMED_STANDARD_DEVIATION = 1.0/4;


  //---- Method

  @Override
  public String getId() {
    return "normal_sd2";
  }

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
   * This normal distribution is 'normalized' in the sense that 95.4% (i.e. mu +- 2 * sigma, resp. mean +- 2 * the standard deviation)
   * of all values are in the [0,1] range.
   */
  private NormalDistribution normalizedNormalDistribution() {
    return new NormalDistribution(NORMED_MEAN, NORMED_STANDARD_DEVIATION);
  }


}
