package hu.rxd.staxis.generator;

import hu.rxd.staxis.api.FSeriesGenerator;

public class LinearSeriesGenerator extends FSeriesGenerator {

  public LinearSeriesGenerator(int maxValue) {
    super(maxValue);
  }

  @Override
  protected Double f(double d) {
    return d;
  }

}
