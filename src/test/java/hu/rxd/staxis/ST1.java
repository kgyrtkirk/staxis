package hu.rxd.staxis;

import org.apache.commons.math3.distribution.ZipfDistribution;
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