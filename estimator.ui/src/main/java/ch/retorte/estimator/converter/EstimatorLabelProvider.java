package ch.retorte.estimator.converter;

import ch.retorte.estimator.Estimator;
import javafx.util.StringConverter;

import java.util.Map;

/**
 * Converts the {@link Estimator} to a human readable form.
 */
public class EstimatorLabelProvider extends StringConverter<Estimator> {

  //---- Fields

  private Map<String, String> labelsForIds;

  //---- Constructor

  public EstimatorLabelProvider(Map<String, String> labelsForIds) {
    this.labelsForIds = labelsForIds;
  }

  //---- Methods

  @Override
  public String toString(Estimator estimator) {
    if (labelsForIds.containsKey(estimator.getId())) {
      return labelsForIds.get(estimator.getId());
    }

    return estimator.getId();
  }

  @Override
  public Estimator fromString(String string) {
    // We do not need this.
    return null;
  }
}
