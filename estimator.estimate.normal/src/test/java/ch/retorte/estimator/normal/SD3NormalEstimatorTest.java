package ch.retorte.estimator.normal;

import ch.retorte.estimator.Estimator;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Unit tests for the {@link SD3NormalEstimator}.
 */
public class SD3NormalEstimatorTest {

  //---- Static

  private static final int START = 0;
  private static final int END = 1000;


  //---- Fields

  private Estimator sut = new SD3NormalEstimator();


  //---- Methods

  @Test
  public void shouldEstimateDistributionValues() {
    // Mean
    assertThat((int) sut.estimateTotalFrom(START, END, 500, 50).getValue(), is(100));

    // mu - 1 sigma (15.866 % of the data outside, we had to round a bit)
    assertThat((int) sut.estimateTotalFrom(START, END, 500/3*2, 15.8).getValue(), is(100));

    // mu - 2 sigma (2.275 % of the data outside, we round up to reach 100)
    assertThat((int) sut.estimateTotalFrom(START, END, 500/3, 2.275).getValue(), is(100));

    // mu - 3 sigma (0.135 % of the data outside, we round up to reach 100)
    assertThat((int) sut.estimateTotalFrom(START, END, 0, 0.135).getValue(), is(100));

    // mu + 3 sigma (should contain 99.865 % of the data)
    assertThat((int) sut.estimateTotalFrom(START, END, END, 99.87).getValue(), is(100));
  }


}
