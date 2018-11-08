package staxis;

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
  }

  static class BetaDistributionOracle {

    private MyMoment1 m2;
    private double a;
    private double b;

    public BetaDistributionOracle(MyMoment1 m2) {
      this.m2 = m2;
      double mean = m2.get1();
      double var = m2.get2();

      if (!(var < mean * (1 - mean))) {
        throw new RuntimeException("error");
      }
      a = mean * (mean * (1 - mean) / var - 1);
      b = (1 - mean) * (mean * (1 - mean) / var - 1);
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
    int V = 100;
    for (int i = U; i <= V; i++)
      m2.increment(i);

    System.out.println(m2.get1());
    System.out.println(m2.get2());

    new BetaDistributionOracle(m2);
  }

}
