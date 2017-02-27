package ch.retorte.estimator.converter;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Unit test for {@link LocalTimeStringConverter}.
 */
public class LocalTimeStringConverterTest {

  //---- Fields

  private LocalTimeStringConverter sut = new LocalTimeStringConverter();


  //---- Methods

  @Test
  public void shouldConvertTimeString() {
    // given
    String time = "14:23:01";

    // when
    LocalTime localTime = sut.fromString(time);

    // then
    assertThat(localTime.getHour(), is(14));
    assertThat(localTime.getMinute(), is(23));
    assertThat(localTime.getSecond(), is(1));
  }

  @Test
  public void shouldConvertTimeStringWithoutSeconds() {
    // given
    String time = "23:55";

    // when
    LocalTime localTime = sut.fromString(time);

    // then
    assertThat(localTime.getHour(), is(23));
    assertThat(localTime.getMinute(), is(55));
    assertThat(localTime.getSecond(), is(0));
  }

  @Test
  public void shouldConvertTimeStringWithoutMinutes() {
    // given
    String time = "16";

    // when
    LocalTime localTime = sut.fromString(time);

    // then
    assertThat(localTime.getHour(), is(16));
    assertThat(localTime.getMinute(), is(0));
    assertThat(localTime.getSecond(), is(0));
  }

  @Test
  public void shouldConvertOneDigitTimeStringWithoutMinutes() {
    // given
    String time = "9";

    // when
    LocalTime localTime = sut.fromString(time);

    // then
    assertThat(localTime.getHour(), is(9));
    assertThat(localTime.getMinute(), is(0));
    assertThat(localTime.getSecond(), is(0));
  }

  @Test
  public void shouldConvertLocalTimeToString() {
    // given
    LocalTime localTime = LocalTime.of(23, 45, 18);

    // when
    String time = sut.toString(localTime);

    // then
    assertThat(time, is("23:45:18"));
  }

  @Test
  public void shouldHandleEmptyValues() {
    assertThat(sut.toString(null), nullValue());
    assertThat(sut.fromString(null), nullValue());
  }
}
