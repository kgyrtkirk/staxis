package hu.rxd.staxis.api;

import java.util.Iterator;

public abstract class ICSeriesGenerator<IteratorContext> implements SeriesGenerator {
  private int maxValue;

  public ICSeriesGenerator(int n) {
    this.maxValue = n;
  }

  @Override
  public Iterator<Double> iterator() {
    return new Iterator<Double>() {

      double val = 0;
      IteratorContext ic = newIteratorContext();

      @Override
      public Double next() {
        if (!hasNext()) {
          throw new RuntimeException();
        }
        return f(ic, val++);
      }

      @Override
      public boolean hasNext() {
        return val < maxValue;
      }
    };
  }

  @Override
  public int getN() {
    return maxValue;
  }

  protected abstract IteratorContext newIteratorContext();

  protected abstract Double f(IteratorContext ic, double d);

}
