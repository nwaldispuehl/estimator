package ch.retorte.estimator.converter;

import javafx.util.converter.NumberStringConverter;

/**
 * An extension to the {@link NumberStringConverter} which does not care if someone provides a non-number.
 */
public class ForgivingNumberStringConverter extends NumberStringConverter {

  @Override
  public Number fromString(String value) {
    try {
      return super.fromString(value);
    }
    catch (Exception e) {
      return 0;
    }
  }

  @Override
  public String toString() {
    try {
      return super.toString();
    }
    catch (Exception e) {
      return "";
    }
  }
}
