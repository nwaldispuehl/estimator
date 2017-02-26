package ch.retorte.estimator.uniform;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Unit tests for the {@link UniformEstimator}.
 */
public class UniformEstimatorTest {

  //---- Static

  private static final int START = 0;
  private static final int END = 1000;

  //---- Fields

  private UniformEstimator sut = new UniformEstimator();

  //---- Methods

  @Test
  public void shouldEstimateUniformValue() {
    // When at starting time
    assertThat((int) sut.estimateTotalFrom(START, END, 0, 0).getValue(), is(0));
    assertThat(sut.estimateTotalFrom(START, END, 0, 100).getValue(), is(Double.POSITIVE_INFINITY));

    // At 25%
    assertThat((int) sut.estimateTotalFrom(START, END, 250, 25).getValue(), is(100));

    // At 50%
    assertThat((int) sut.estimateTotalFrom(START, END, 500, 50).getValue(), is(100));

    // At 75%
    assertThat((int) sut.estimateTotalFrom(START, END, 750, 75).getValue(), is(100));

    // At 100%
    assertThat((int) sut.estimateTotalFrom(START, END, 1000, 100).getValue(), is(100));
  }
}
