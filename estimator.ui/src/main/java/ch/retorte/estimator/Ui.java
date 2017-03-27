package ch.retorte.estimator;

import ch.retorte.estimator.mainscreen.MainScreenController;
import ch.retorte.estimator.storage.ApplicationData;
import ch.retorte.estimator.storage.Storage;
import ch.retorte.estimator.storage.WindowGeometry;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Starting point of the user interface.
 */
public class Ui extends Application {

  //---- Static

  private static final String MAIN_SCREEN_LAYOUT_FILE = "/layouts/MainScreen.fxml";

  private static ObservableList<Estimator> availableEstimators = FXCollections.observableArrayList();


  //---- Fields

  private AtomicBoolean dataLoaded = new AtomicBoolean(false);

  private Storage storage;

  private MainScreenController mainScreenController;

  private Timer timer;
  private Stage stage;

  private SimpleBooleanProperty ctrlPressed = new SimpleBooleanProperty(false);
  private SimpleIntegerProperty zoom = new SimpleIntegerProperty(100);


  //---- Methods

  void addEstimator(Estimator estimator) {
    availableEstimators.add(estimator);
  }

  void launch() {
    Application.launch();
  }

  @Override
  public void start(Stage stage) throws IOException {
    this.stage = stage;

    stage.setTitle("Estimator");
    stage.setScene(createScene());
    stage.show();

    initializeStorage();
    initializeMainScreenController();
    initializeUpdateTimer();
    loadData();
  }

  private Scene createScene() throws IOException {
    Scene scene = new Scene(getRoot());
    addEventListenersTo(scene);
    return scene;
  }

  private void addEventListenersTo(Scene scene) {
    scene.setOnKeyPressed(this::updateCtrlKeyWith);
    scene.setOnKeyReleased(keyEvent -> {
      updateCtrlKeyWith(keyEvent);
      if (keyEvent.isControlDown() && keyEvent.getCode().equals(KeyCode.DIGIT0)) {
        zoom.set(100);
      }
    });

    scene.setOnScroll(scrollEvent -> {
      if (ctrlPressed.get()) {
        if (isUpScrolling(scrollEvent)) {
          if (zoom.get() < 200) {
            zoom.set(zoom.get() + 5);
          }
        }
        else {
          if (25 < zoom.get()) {
            zoom.set(zoom.get() - 5);
          }
        }
      }
    });

    zoom.addListener(observable -> {
      // TODO: update zoom factor in ui
    });
  }

  private void updateCtrlKeyWith(KeyEvent keyEvent) {
    ctrlPressed.set(keyEvent.isControlDown());
  }

  private boolean isUpScrolling(ScrollEvent scrollEvent) {
    return 0 < scrollEvent.getDeltaY();
  }

  private void initializeStorage() {
    try {
      storage = new Storage(getHomeDirectory());
    } catch (Exception e) {
      showError("Was not able to initialize storage.", e);
    }
  }

  private String getHomeDirectory() {
    return System.getProperty("user.home") + File.separator;
  }

  private void initializeMainScreenController() {
    mainScreenController.setAvailableEstimators(availableEstimators);
    mainScreenController.setUpdateListener(new InputChangeListener());
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

  private void loadData() {
    ApplicationData applicationData = null;
    try {
      applicationData = storage.load();
    }
    catch (Exception e) {
      showError("Could not load data.", e);
    }

    if (applicationData != null) {
      mainScreenController.setData(applicationData);
      setWindowGeometry(applicationData.getWindowGeometry());
    }
    dataLoaded.set(true);
  }

  private void saveData() {
    if (dataLoaded.get()) {
      ApplicationData applicationData = mainScreenController.getData();
      try {
        applicationData.setWindowGeometry(getWindowGeometry());
        storage.save(applicationData);
      } catch (Exception e) {
        showError("Could not save data.", e);
      }
    }
  }

  private void setWindowGeometry(WindowGeometry windowGeometry) {
    if (windowGeometry != null) {
      stage.setWidth(windowGeometry.getWidth());
      stage.setHeight(windowGeometry.getHeight());
      stage.setX(windowGeometry.getX());
      stage.setY(windowGeometry.getY());
    }
  }

  private void showError(String message, Exception e) {
    mainScreenController.showError(message, e);
  }

  private WindowGeometry getWindowGeometry() {
    return new WindowGeometry(stage.getWidth(), stage.getHeight(), stage.getX(), stage.getY());
  }

  public class InputChangeListener implements ChangeListener<Object> {

    @Override
    public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
      mainScreenController.refresh();
      saveData();
    }
  }
}
