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

  //---- Static

  private static final String STORAGE_DIRECTORY = ".estimator";
  private static final String STORAGE_FILE_NAME = "currentState";


  //---- Fields

  private XStream xStream = new XStream();

  private String storageFilePath;


  //---- Constructor

  public Storage(String homeDirectory) throws IOException {
    File configDirectory = new File(homeDirectory + File.separator + STORAGE_DIRECTORY);
    if (!configDirectory.exists()) {
      boolean directoryCreated = configDirectory.mkdirs();
      if (!directoryCreated) {
        throw new IOException("Not able to create settings directory: " + configDirectory.getAbsolutePath());
      }
    }

    this.storageFilePath = configDirectory.getAbsolutePath() + File.separator + STORAGE_FILE_NAME;
  }


  //---- Methods

  /**
   * Saves the current application state to some permanent medium.
   */
  public synchronized void save(ApplicationData applicationData) throws IOException {
    String xml = xStream.toXML(applicationData);
    writeToStorageFile(xml);
  }

  /**
   * Retrieves the persisted application state again and returns it. If there is none null is returned.
   */
  public synchronized ApplicationData load() throws IOException {
    if (storageFileExists()) {
      String xml = loadFromStorageFile();
      return (ApplicationData) xStream.fromXML(xml);
    }
    else {
      return null;
    }
  }

  private boolean storageFileExists() {
    return new File(storageFilePath).exists();
  }

  private void writeToStorageFile(String content) throws IOException {
    Files.write(Paths.get(storageFilePath), content.getBytes(), StandardOpenOption.CREATE);
  }

  private String loadFromStorageFile() throws IOException {
      return new String(Files.readAllBytes(Paths.get(storageFilePath)));
  }
}
