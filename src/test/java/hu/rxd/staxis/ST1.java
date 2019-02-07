package hu.rxd.staxis;

import org.apache.commons.math3.distribution.ZipfDistribution;
import org.apache.commons.math3.special.Beta;
import org.apache.commons.math3.stat.descriptive.moment.M3;

import org.junit.Test;

import hu.rxd.staxis.api.SeriesGenerator;
import hu.rxd.staxis.consumer.Freq;
import hu.rxd.staxis.consumer.MeasurePointConsumer;
import hu.rxd.staxis.consumer.StatMomentsConsumer;
import hu.rxd.staxis.generator.DistributionAdapter;
import hu.rxd.staxis.generator.OrderedSeries;
import hu.rxd.staxis.generator.UnionSeries;

public class ST1 {

  static class BetaDistributionOracle {

    private StatMomentsConsumer m2;
    private double a;
    private double b;

    public BetaDistributionOracle(StatMomentsConsumer m2) {
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

  @Test
  public void asd() {

    //    MyMoment1 m2 = new MyMoment1();

    //    SeriesGenerator series = new SeriesGenerator(1000);
    SeriesGenerator series =
        new UnionSeries(
            new DistributionAdapter(new ZipfDistribution(200_000, 2.0))
        //            new PowerSeries(2000000, 5)
        //            new LogSeries(20000000, .01)
        //            new PowerSeries(3, 11)
            );


    OrderedSeries os = new OrderedSeries(series);

    MeasurePointConsumer mpc = new MeasurePointConsumer(os, 11);
    StatMomentsConsumer m2 = new StatMomentsConsumer(os);
    Freq ff = new Freq(os, 20);


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

    System.out.println("m1: " + m2.get1());
    System.out.println("m2: " + m2.get2() / (m2.getN() - 1));
    System.out.println("m3: " + m2.get3() / (m2.getN() - 1));
    System.out.println("sSkew: " + m2.m.getSkewness());
    System.out.println("sKurt: " + m2.m.getKurtosis());

    BetaDistributionOracle bb = new BetaDistributionOracle(m2);
    System.out.println(bb);


    double ee = bb.estimateCDF(L);
    System.out.println(ee);
    System.out.println(cnt);
    System.out.println(ff);

    for (SeriesPosition p : mpc.getPoints()) {
      double eIdx = bb.estimateCDF(p.getValue());
      double aErr = Math.abs(eIdx - p.getIdx());
      System.out.printf("%f %f  %f  %f\n", p.getIdx(), p.getValue(), eIdx, aErr);

    }

  }


}