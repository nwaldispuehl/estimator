package ch.retorte.estimator;

import ch.retorte.estimator.normal.NormalEstimator;
import ch.retorte.estimator.uniform.UniformEstimator;

/**
 * The main program file.
 */
public class EstimatorApp {

  public static void main(String... args) {
    Ui ui = new Ui();

    ui.addEstimator(new UniformEstimator());
    ui.addEstimator(new NormalEstimator());

    ui.launch();
  }
}
