package hu.rxd.staxis.generator;

import hu.rxd.staxis.api.FSeriesGenerator;

public class PowerSeries extends FSeriesGenerator {

  private double p;

  public PowerSeries(int n, double p) {
    super(n);
    this.p = p;
  }

  @Override
  protected Double f(double d) {
    return Math.pow(d, p);
  }

}
