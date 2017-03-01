package ch.retorte.estimator.converter;

import javafx.util.StringConverter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Converts {@link LocalTime} from and to strings of form 'HH:MM'
 */
public class LocalTimeStringConverter extends StringConverter<LocalTime> {

  //---- Static

  public static final DateTimeFormatter DEFAULT_TIME_PARSER = DateTimeFormatter.ISO_LOCAL_TIME;
  public static final DateTimeFormatter DEFAULT_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

  private static final String PART_DELIMITER = ":";

  //---- Fields

  private DateTimeFormatter parser;
  private DateTimeFormatter formatter;


  //---- Constructor

  public LocalTimeStringConverter() {
    this(DEFAULT_TIME_PARSER, DEFAULT_TIME_FORMATTER);
  }

  public LocalTimeStringConverter(DateTimeFormatter parser, DateTimeFormatter formatter) {
    this.parser = parser;
    this.formatter = formatter;
  }


  //---- Methods

  @Override
  public String toString(LocalTime object) {
    try {
      return formatter.format(object);
    }
    catch (Exception e) {
      return null;
    }

  }

  @Override
  public LocalTime fromString(String string) {
    try {
      // If only one digit is entered we add a leading zero
      if (string.length() == 1) {
        string = "0" + string;
      }

      // If the minutes are missing we add ':00';
      if (!string.contains(PART_DELIMITER)) {
        string = string + PART_DELIMITER + "00";
      }

      return LocalTime.parse(string, parser);
    }
    catch (Exception e) {
      return null;
    }
  }
}
