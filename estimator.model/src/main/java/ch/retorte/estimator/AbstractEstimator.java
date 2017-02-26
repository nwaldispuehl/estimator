package ch.retorte.estimator;

import com.google.common.base.Preconditions;

/**
 * Base class for all estimators which performs common tasks like input validation.
 */
public abstract class AbstractEstimator implements Estimator {

  @Override
  public final Estimation estimateTotalFrom(int startTime, int endTime, int currentTime, double currentValue) {

    validateEndTimeIsAfterStartTime(startTime, endTime);
    validateCurrentTimeIsInTimeFrame(startTime, endTime, currentTime);

    return validatedEstimateTotalFrom(startTime, endTime, currentTime, currentValue);
  }

  protected abstract Estimation validatedEstimateTotalFrom(int startTime, int endTime, int currentTime, double currentValue);

  private void validateEndTimeIsAfterStartTime(int startTime, int endTime) {
    Preconditions.checkArgument(startTime <= endTime, "End time must be after start time.");
  }

  private void validateCurrentTimeIsInTimeFrame(int startTime, int endTime, int currentTime) {
    Preconditions.checkArgument(startTime <= currentTime && currentTime <= endTime, "Current time must be between start and end time.");
  }
}
