package ch.retorte.estimator.estimations;

import ch.retorte.estimator.Estimation;
import ch.retorte.estimator.Estimator;
import ch.retorte.estimator.storage.EstimationData;
import javafx.beans.property.*;
import javafx.scene.paint.Color;

/**
 * Model for the single estimations.
 */
public class EstimationEntry {

  //---- Static

  private static final double WARNING_THRESHOLD = 0.1;

  private static final String DEFAULT = "black";
  private static final String GOOD = "#4CAF50";
  private static final String WARNING = "#FFEB3B";
  private static final String BAD = "#FF9800";
  private static final String VERY_BAD = "#F44336";

  //---- Fields

  private StringProperty name = new SimpleStringProperty();
  private ObjectProperty<Estimator> estimator = new SimpleObjectProperty<>();

  private DoubleProperty currentValue = new SimpleDoubleProperty();
  private DoubleProperty correctionValue = new SimpleDoubleProperty();
  private DoubleProperty aggregatedCurrentValue = new SimpleDoubleProperty();

  private DoubleProperty estimatedValue = new SimpleDoubleProperty();

  private DoubleProperty availableResources = new SimpleDoubleProperty();
  private DoubleProperty availableResourcesDelta = new SimpleDoubleProperty();

  private StringProperty availableResourcesDeltaStyle = new SimpleStringProperty();

  //---- Constructor

  public EstimationEntry(EstimationData estimationData) {
    initializeBindings();

    this.name.set(estimationData.getName());
    this.estimator.set(estimationData.getEstimator());
    this.currentValue.set(estimationData.getCurrentValue());
    this.correctionValue.set(estimationData.getCorrectionValue());
    this.availableResources.set(estimationData.getAvailableResources());
  }

  //---- Methods

  private void initializeBindings() {
    availableResourcesDelta.bind(availableResources.subtract(estimatedValue));
    aggregatedCurrentValue.bind(currentValue.add(correctionValue));
  }

  public void calculateWith(int startTime, int endTime, int currentTime) {
    if (estimator.isNotNull().get()) {

      if (endTime < startTime) {
        endTime = startTime;
      }

      if (currentTime < startTime) {
        currentTime = startTime;
      }

      if (endTime < currentTime) {
        currentTime = endTime;
      }

      Estimation estimation = estimator.get().estimateTotalFrom(startTime, endTime, currentTime, aggregatedCurrentValue.get());
      estimatedValue.set(estimation.getValue());

      updateDeltaStylingWith(aggregatedCurrentValue.get(), availableResources.get(), availableResourcesDelta.get());
    }
    else {
      resetDeltaColor();
    }
  }

  private void updateDeltaStylingWith(double current, double available, double delta) {
    if (available < current) {
      styleDeltaWith(textColor(VERY_BAD));
    }
    else if (delta <= 0) {
      styleDeltaWith(textColor(BAD));
    }
    else if (delta <= available * WARNING_THRESHOLD) {
      styleDeltaWith(textColor(percentualGradientWith(WARNING, GOOD, delta / available * WARNING_THRESHOLD)));
    }
    else {
      styleDeltaWith(textColor(GOOD));
    }
  }

  private void resetDeltaColor() {
    styleDeltaWith(textColor(DEFAULT));
  }

  private void styleDeltaWith(String style) {
    availableResourcesDeltaStyle.set(style);
  }

  private String textColor(String color) {
    return "-fx-text-fill: " + color + ";";
  }

  private String percentualGradientWith(String rgbColor1, String rgbColor2, double factor) {
    Color c1 = Color.web(rgbColor1);
    Color c2 = Color.web(rgbColor2);

    float[] hsb1 = hsbFrom(c1);
    float[] hsb2 = hsbFrom(c2);

    float[] resultingHsb = new float[3];

    for (int i = 0; i < resultingHsb.length; i++) {
      float min = Math.min(hsb1[i], hsb2[i]);
      float max = Math.max(hsb1[i], hsb2[i]);

      resultingHsb[i] = min + ((max - min) * (float) factor);
    }

    Color result = hsbToColor(resultingHsb);

    // TODO: implement
    String resultString = toWebColor(result);

    return resultString;
  }

  private float[] hsbFrom(Color c) {
    return java.awt.Color.RGBtoHSB((int) c.getRed() * 255, (int) c.getGreen() * 255, (int) c.getBlue() * 255, null);
  }

  private Color hsbToColor(float[] hsv) {
    return Color.hsb(hsv[0], hsv[1], hsv[2]);
  }

  private String toWebColor( Color color ) {
    return String.format( "#%02X%02X%02X",
        (int)( color.getRed() * 255 ),
        (int)( color.getGreen() * 255 ),
        (int)( color.getBlue() * 255 ) );
  }

  public EstimationData getData() {
    return new EstimationData(name.get(), estimator.get(), currentValue.get(), correctionValue.get(), availableResources.get());
  }

  //---- Properties

  StringProperty nameProperty() {
    return name;
  }

  ObjectProperty<Estimator> estimatorProperty() {
    return estimator;
  }

  DoubleProperty currentValueProperty() {
    return currentValue;
  }

  DoubleProperty correctionValueProperty() {
    return correctionValue;
  }

  DoubleProperty estimatedValueProperty() {
    return estimatedValue;
  }

  DoubleProperty availableResourcesProperty() {
    return availableResources;
  }

  DoubleProperty availableResourcesDeltaProperty() {
    return availableResourcesDelta;
  }

  StringProperty getAvailableResourcesDeltaStyleProperty() {
    return availableResourcesDeltaStyle;
  }

}
