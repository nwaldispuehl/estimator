package ch.retorte.estimator.normal;

import ch.retorte.estimator.Estimator;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Unit tests for the {@link SD2NormalEstimator}.
 */
public class SD2NormalEstimatorTest {

  //---- Static

  private static final int START = 0;
  private static final int END = 1000;


  //---- Fields

  private Estimator sut = new SD2NormalEstimator();


  //---- Methods

  @Test
  public void shouldEstimateDistributionValues() {
    // Mean
    assertThat((int) sut.estimateTotalFrom(START, END, 500, 50).getValue(), is(100));

    // mu - 1 sigma (15.866 % of the data outside)
    assertThat((int) sut.estimateTotalFrom(START, END, 500/2, 15.866).getValue(), is(100));

    // mu - 2 sigma (2.275 % of the data outside, we round up to reach 100)
    assertThat((int) sut.estimateTotalFrom(START, END, 0, 2.276).getValue(), is(100));

    // mu + 2 sigma (should contain 97.725% of all the data)
    assertThat((int) sut.estimateTotalFrom(START, END, END, 97.725).getValue(), is(100));
  }

  @Test
  public void shouldEstimateRealWorldValues() {
    assertThat((int) sut.estimateTotalFrom(800, 1300, 1100, 29).getValue(), is(44));
    assertThat((int) sut.estimateTotalFrom(800, 1300, 1100, 52).getValue(), is(79));
  }

}
