package hu.rxd.staxis.consumer;

import org.apache.commons.math3.stat.descriptive.moment.M3;

import hu.rxd.staxis.api.AbstractConsumer;
import hu.rxd.staxis.api.SeriesGenerator;

public class StatMomentsConsumer extends AbstractConsumer {

  public static class MyMoment extends M3 {

    private static final long serialVersionUID = 1L;

    public double get3() {
      return m3;
    }

    public double get2() {
      return m2;
    }

    public double get1() {
      return m1;
    }
  }

  public MyMoment m = new MyMoment();
  public double min;
  public double max;

  public StatMomentsConsumer(SeriesGenerator series) {
    initialize(series);
  }

  public double get1() {
    return m.get1();
  }

  public double get2() {
    return m.get2();
  }

  public double get3() {
    return m.get3();
  }

  public double getN() {
    return m.getN();
  }

  @Override
  protected void finish() {
  }

  @Override
  protected void consume(Double d) {
    if (m.getN() == 0) {
      min = max = d;
    }
    if (d < min) {
      min = d;
    }
    if (d > max) {
      max = d;
    }
    m.increment(d);
  }

  @Override
  protected void advertiseN(int n) {
  }
}