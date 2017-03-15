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
  private static final String VERSION_PREFIX = ".V";


  //---- Fields

  private XStream xStream = new XStream();
  private File configDirectory;


  //---- Constructor

  public Storage(String homeDirectory) throws IOException {
    configDirectory = new File(homeDirectory + File.separator + STORAGE_DIRECTORY);
    if (!configDirectory.exists()) {
      boolean directoryCreated = configDirectory.mkdirs();
      if (!directoryCreated) {
        throw new IOException("Not able to create settings directory: " + configDirectory.getAbsolutePath());
      }
    }
  }


  //---- Methods

  /**
   * Saves the current application state to some permanent medium.
   */
  public synchronized void save(ApplicationData applicationData) throws IOException {
    String xml = xStream.toXML(applicationData);
    writeToStorageFile(ApplicationData.DATA_FORMAT_VERSION, xml);
  }

  /**
   * Retrieves the persisted application state again and returns it. If there is none null is returned.
   */
  public synchronized ApplicationData load() throws IOException {
    if (storageFileExists()) {
      String xml = loadFromStorageFile(ApplicationData.DATA_FORMAT_VERSION);
      return (ApplicationData) xStream.fromXML(xml);
    }
    else {
      return null;
    }
  }

  private boolean storageFileExists() {
    return getStorageFileWith(ApplicationData.DATA_FORMAT_VERSION).exists();
  }

  private File getStorageFileWith(int dataformatVersion) {
    return new File(configDirectory.getAbsolutePath() + File.separator + STORAGE_FILE_NAME + VERSION_PREFIX + String.valueOf(dataformatVersion));
  }

  private void writeToStorageFile(int dataformatVersion, String content) throws IOException {
    Files.write(getStorageFileWith(dataformatVersion).toPath(), content.getBytes(), StandardOpenOption.CREATE);
  }

  private String loadFromStorageFile(int dataformatVersion) throws IOException {
      return new String(Files.readAllBytes(getStorageFileWith(dataformatVersion).toPath()));
  }
}
