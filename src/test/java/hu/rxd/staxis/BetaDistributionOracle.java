package hu.rxd.staxis;

import org.apache.commons.math3.special.Beta;

import hu.rxd.staxis.consumer.StatMomentsConsumer;

class BetaDistributionOracle {

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

  double estimateCDF(double x) {
    return Beta.regularizedBeta(map(x), a, b);
  }

  @Override
  public String toString() {
    return String.format("alpha: %f, beta: %f", a, b);
  }
}