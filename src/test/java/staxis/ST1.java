package staxis;

import org.apache.commons.math3.distribution.ZipfDistribution;
import org.apache.commons.math3.special.Beta;
import org.apache.commons.math3.stat.descriptive.moment.M3;

import org.junit.Test;

public class ST1 {

  static class MyMoment1 extends AbstractConsuemer {

    MyMoment m = new MyMoment();
    double min, max;

    public MyMoment1(SeriesGenerator series) {
      initialize(series);
    }

    public double get1() {
      return m.get1();
    }

    public double get2() {
      return m.get2();
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

  static class BetaDistributionOracle {

    private MyMoment1 m2;
    private double a;
    private double b;

    public BetaDistributionOracle(MyMoment1 m2) {
      this.m2 = m2;
      double mean = map(m2.get1());
      double var = m2.get2() / (m2.max - m2.min) / (m2.max - m2.min) / (m2.getN() - 1);

      System.out.println(mean);
      System.out.println(var);
      if (!(var < mean * (1 - mean))) {
        throw new RuntimeException("error");
      }
      a = mean * (mean * (1 - mean) / var - 1);
      b = (1 - mean) * (mean * (1 - mean) / var - 1);
    }

    private double map(double get1) {
      return (get1 - m2.min) / (m2.max - m2.min);
    }

    private double estimateCDF(double x) {
      return Beta.regularizedBeta(map(x), a, b);
    }

    @Override
    public String toString() {
      return String.format("alpha: %f, beta: %f", a, b);
    }
  }

  static class MyMoment extends M3 {

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

  @Test
  public void asd() {

    //    MyMoment1 m2 = new MyMoment1();

    //    SeriesGenerator series = new SeriesGenerator(1000);
    SeriesGenerator series =
        new UnionSeries(
            new DistributionAdapter(new ZipfDistribution(200_000, .9))
        //            new PowerSeries(2000000, 10)
        //            new LogSeries(20000000, .01)
        //            new PowerSeries(3, 11)
            );


    OrderedSeries os = new OrderedSeries(series);

    MeasurePointConsumer mpc = new MeasurePointConsumer(os, 11);
    MyMoment1 m2 = new MyMoment1(os);


    //    dispatch(series, mpc, m2);
    int U = 0;
    int V = 1000;
    int L = 999;
    double cnt = 0;
    for (int i = U; i <= V; i++) {

      int d = i;// - (i - 50) * (i - 50);
      if (d < L) {
        cnt++;
      }
      //      m2.increment(d);
    }
    cnt /= V - U + 1;

    System.out.println(m2.get1());
    System.out.println(m2.get2());

    BetaDistributionOracle bb = new BetaDistributionOracle(m2);
    System.out.println(bb);


    double ee = bb.estimateCDF(L);
    System.out.println(ee);
    System.out.println(cnt);

    for (SeriesPosition p : mpc.getPoints()) {
      double eIdx = bb.estimateCDF(p.getValue());
      double aErr = Math.abs(eIdx - p.getIdx());
      System.out.printf("%f %f  %f  %f\n", p.getIdx(), p.getValue(), eIdx, aErr);

    }

  }


}