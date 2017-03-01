package ch.retorte.estimator.storage;

import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Saves and retrieves application state.
 */
public class Storage {

  private static final String STORAGE_DIRECTORY = ".estimator";
  private static final String STORAGE_FILE_NAME = "currentState";

  private String storageFilePath;

  public Storage(String homeDirectory) {
    File configDirectory = new File(homeDirectory + File.separator + STORAGE_DIRECTORY);
    if (!configDirectory.exists()) {
      configDirectory.mkdirs();
    }

    this.storageFilePath = configDirectory.getAbsolutePath() + File.separator + STORAGE_FILE_NAME;
  }

  /**
   * Saves the current application state to some permanent medium.
   */
  public void save(ApplicationData applicationData) {
    XStream xStream = new XStream();
    String xml = xStream.toXML(applicationData);

    writeToStorageFile(xml);
  }

  /**
   * Retrieves the persisted application state again and returns it. If there is none null is returned.
   */
  public ApplicationData load() {
    if (storageFileExists()) {
      String xml = loadFromStorageFile();

      XStream xStream = new XStream();
      return (ApplicationData) xStream.fromXML(xml);
    }
    else {
      return null;
    }
  }

  private boolean storageFileExists() {
    return new File(storageFilePath).exists();
  }

  private void writeToStorageFile(String content) {
    try {
      Files.write(Paths.get(storageFilePath), content.getBytes(), StandardOpenOption.CREATE);
    }
    catch (IOException e) {
      e.printStackTrace(); // TODO
    }
  }

  private String loadFromStorageFile() {
    try {
      return new String(Files.readAllBytes(Paths.get(storageFilePath)));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
