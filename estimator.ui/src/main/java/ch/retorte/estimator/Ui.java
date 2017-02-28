package ch.retorte.estimator;

import ch.retorte.estimator.mainscreen.MainScreenController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Starting point of the user interface.
 */
public class Ui extends Application {

  //---- Static

  private static final String MAIN_SCREEN_LAYOUT_FILE = "/layouts/MainScreen.fxml";


  //---- Fields

  private static ObservableList<Estimator> availableEstimators = FXCollections.observableArrayList();

  private MainScreenController mainScreenController;

  private Timer timer;


  //---- Methods

  void addEstimator(Estimator estimator) {
    availableEstimators.add(estimator);
  }

  void launch() {
    Application.launch();
  }

  @Override
  public void start(Stage stage) throws IOException {
    stage.setTitle("Estimator");
    stage.setScene(new Scene(getRoot()));
    stage.show();

    initializeMainScreenController();
    initializeUpdateTimer();
  }

  private void initializeMainScreenController() {
    mainScreenController.setAvailableEstimators(availableEstimators);
  }

  private void initializeUpdateTimer() {
    timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        Platform.runLater(() -> mainScreenController.refresh());
      }
    }, 0, 1000);

  }

  private Parent getRoot() throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(MAIN_SCREEN_LAYOUT_FILE));
    Parent result = fxmlLoader.load();
    mainScreenController = fxmlLoader.getController();
    return result;
  }

  @Override
  public void stop() throws Exception {
    timer.cancel();
    super.stop();
  }
}
