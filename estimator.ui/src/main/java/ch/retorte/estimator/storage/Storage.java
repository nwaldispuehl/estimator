package ch.retorte.estimator.storage;

/**
 * Saves and retrieves application state.
 */
public class Storage {

  private String path;

  public Storage(String path) {
    this.path = path;
  }

  /**
   * Saves the current application state to some permanent medium.
   */
  public void save(ApplicationData applicationData) {

  }

  /**
   * Retrieves the persisted application state again and returns it. If there is none null is returned.
   */
  public ApplicationData load() {
    return null;
  }
}
