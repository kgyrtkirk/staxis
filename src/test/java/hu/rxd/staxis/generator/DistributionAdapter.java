package hu.rxd.staxis.generator;

import org.apache.commons.math3.distribution.ZipfDistribution;

import hu.rxd.staxis.api.FSeriesGenerator;

public class DistributionAdapter extends FSeriesGenerator {

  private ZipfDistribution dist;

  public DistributionAdapter(ZipfDistribution dist) {
    super(dist.getNumberOfElements());
    this.dist = dist;

  }

  @Override
  protected Double f(double d) {
    return (double) dist.sample();
  }

}
