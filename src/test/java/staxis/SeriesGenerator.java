package staxis;

import java.util.Iterator;

public class SeriesGenerator implements G {

  private int maxValue;

  public SeriesGenerator(int maxValue) {
    this.maxValue = maxValue;
  }

  @Override
  public Iterator<Double> iterator() {
    return new Iterator<Double>() {

      double val = 0;

      @Override
      public Double next() {
        if (!hasNext()) {
          throw new RuntimeException();
        }
        return val++;
      }

      @Override
      public boolean hasNext() {
        return val < maxValue;
      }
    };
  }

  public int getN() {
    return maxValue;
  }

}
