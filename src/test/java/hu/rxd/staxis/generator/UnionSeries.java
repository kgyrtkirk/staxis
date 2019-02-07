package hu.rxd.staxis.generator;

import java.util.Iterator;

import com.google.common.collect.Iterables;

import hu.rxd.staxis.api.SeriesGenerator;

public class UnionSeries implements SeriesGenerator {

  private SeriesGenerator[] s;

  public UnionSeries(SeriesGenerator... s) {
    this.s = s;
  }

  @Override
  public Iterator<Double> iterator() {
    return Iterables.concat(s).iterator();
  }

  @Override
  public int getN() {
    int n = 0;
    for (SeriesGenerator seriesGenerator : s) {
      n += seriesGenerator.getN();

    }
    return n;
  }

}
