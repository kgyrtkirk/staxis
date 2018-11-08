package staxis;

import org.apache.commons.math3.special.Beta;
import org.apache.commons.math3.stat.descriptive.moment.SecondMoment;
import org.junit.Test;

public class ST1 {

  static class MyMoment1 {

    MyMoment m = new MyMoment();
    double min, max;

    public void increment(double d) {
      if (m.getN() == 0) {
        min = max = d;
      }
      if (d < min)
        min = d;
      if (d > max)
        max = d;
      m.increment(d);
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

  }

  static class MyMoment extends SecondMoment {

    private static final long serialVersionUID = 1L;

    public double get2() {
      return m2;
    }

    public double get1() {
      return m1;
    }
  }

  @Test
  public void asd() {

    MyMoment1 m2 = new MyMoment1();
    //    new SeriesGenerator(1000).;
    int U = 0;
    int V = 10;
    int L = 70;
    double cnt = 0;
    for (int i = U; i <= V; i++) {

      int d = i * i * i;// - (i - 50) * (i - 50);
      if (d < L) {
        cnt++;
      }
      m2.increment(d);
    }
    cnt /= V - U + 1;

    System.out.println(m2.get1());
    System.out.println(m2.get2());

    BetaDistributionOracle bb = new BetaDistributionOracle(m2);
    double ee = bb.estimateCDF(L);
    System.out.println(ee);
    System.out.println(cnt);
  }

}
