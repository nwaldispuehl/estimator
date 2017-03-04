package ch.retorte.estimator;

import ch.retorte.estimator.normal.SD2NormalEstimator;
import ch.retorte.estimator.normal.SD3NormalEstimator;
import ch.retorte.estimator.uniform.UniformEstimator;

/**
 * The main program file.
 */
public class EstimatorApp {

  public static void main(String... args) {
    Ui ui = new Ui();

    ui.addEstimator(new SD2NormalEstimator());
    ui.addEstimator(new SD3NormalEstimator());
    ui.addEstimator(new UniformEstimator());

    ui.launch();
  }
}
