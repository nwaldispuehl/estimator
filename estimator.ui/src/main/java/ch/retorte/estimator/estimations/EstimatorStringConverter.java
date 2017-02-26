package ch.retorte.estimator.estimations;

import ch.retorte.estimator.Estimator;
import com.google.common.collect.Maps;
import javafx.util.StringConverter;

import java.util.Map;

/**
 * Created by nw on 26.02.17.
 */
public class EstimatorStringConverter extends StringConverter<Estimator> {

  private static final Map<String, Estimator> ESTIMATOR_LABELS = Maps.newHashMap();

  @Override
  public String toString(Estimator object) {
    return null;
  }

  @Override
  public Estimator fromString(String string) {
    return null;
  }
}
