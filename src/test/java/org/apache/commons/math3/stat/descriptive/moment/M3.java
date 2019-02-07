package org.apache.commons.math3.stat.descriptive.moment;

public class M3 extends FourthMoment {

  private static final long serialVersionUID = 1L;

  public
  double getSkewness() {
    Skewness s = new Skewness(this);
    return s.getResult();
  }

  public double getKurtosis() {
    Kurtosis s = new Kurtosis(this);
    return s.getResult();
  }

}
