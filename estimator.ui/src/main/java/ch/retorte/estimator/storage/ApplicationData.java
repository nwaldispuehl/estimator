package ch.retorte.estimator.storage;

import java.time.LocalTime;
import java.util.List;

/**
 * Holds the state of the application.
 */
public class ApplicationData {

  private WindowGeometry windowGeometry;

  private final LocalTime startTime;
  private final LocalTime endTime;
  private List<EstimationData> estimationDataList;

  public ApplicationData(LocalTime startTime, LocalTime endTime, List<EstimationData> estimatorDataList) {
    this.startTime = startTime;
    this.endTime = endTime;
    estimationDataList = estimatorDataList;
  }

  public void setWindowGeometry(WindowGeometry windowGeometry) {
    this.windowGeometry = windowGeometry;
  }

  public WindowGeometry getWindowGeometry() {
    return windowGeometry;
  }

  public LocalTime getStartTime() {
    return startTime;
  }

  public LocalTime getEndTime() {
    return endTime;
  }

  public List<EstimationData> getEstimationDataList() {
    return estimationDataList;
  }
}
