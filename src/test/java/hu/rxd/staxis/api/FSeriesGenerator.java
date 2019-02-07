package hu.rxd.staxis.api;

import hu.rxd.staxis.api.FSeriesGenerator.IC;

public abstract class FSeriesGenerator
    extends ICSeriesGenerator<IC> {

  public FSeriesGenerator(int n) {
    super(n);
  }

  static final class IC {
  }

  @Override
  protected final Double f(IC ic, double d) {
    return f(d);
  }

  @Override
  protected final IC newIteratorContext() {
    return new IC();
  }

  protected abstract Double f(double d);

}
