package hu.rxd.staxis.generator;

import hu.rxd.staxis.api.FSeriesGenerator;

public class LogSeries extends FSeriesGenerator {
  private double p;

  public LogSeries(int n, double p) {
    super(n);
    this.p = p;
  }

  @Override
  protected Double f(double d) {
    return Math.log(d + 1);
  }


}
