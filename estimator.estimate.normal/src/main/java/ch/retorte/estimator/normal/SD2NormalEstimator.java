package ch.retorte.estimator.normal;

/**
 * Estimator based on a normal distribution with a standard deviation of +- 2. This means that 95.45% of all samples are expected to be in the observed [0, 1] range.
 */
public class SD2NormalEstimator extends NormalEstimator {

  @Override
  public String getId() {
    return "normal_sd2";
  }

  @Override
  double getStandardDeviation() {
    return 2;
  }
}
