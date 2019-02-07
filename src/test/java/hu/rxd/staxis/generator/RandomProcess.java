package hu.rxd.staxis.generator;

import java.util.Random;

import hu.rxd.staxis.api.ICSeriesGenerator;
import hu.rxd.staxis.generator.RandomProcess.RPIC;

public class RandomProcess extends ICSeriesGenerator<RPIC> {

  private int d;

  public RandomProcess(int n, int d) {
    super(n);
    this.d = d;
  }

  static class RPIC {
    double lastVal = 0;
    Random rnd = new Random(84);
  }

  @Override
  protected RPIC newIteratorContext() {
    return new RPIC();
  }

  @Override
  protected Double f(RPIC ic, double d) {
    ic.lastVal += ic.rnd.nextDouble();
    return ic.lastVal;
  }

}
