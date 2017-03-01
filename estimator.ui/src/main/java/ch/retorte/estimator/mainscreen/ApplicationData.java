package ch.retorte.estimator.mainscreen;

import ch.retorte.estimator.estimations.EstimationData;

import java.time.LocalTime;
import java.util.List;

/**
 * Holds the state of the application.
 */
public class ApplicationData {
  private final LocalTime localTime;
  private final LocalTime localTime1;
  public LocalTime startTime;
  public LocalTime endTime;
  public List<EstimationData> estimationDataList;

  public ApplicationData(LocalTime localTime, LocalTime localTime1, List<EstimationData> estimatorDataList) {
    this.localTime = localTime;
    this.localTime1 = localTime1;
    estimationDataList = estimatorDataList;
  }
}
