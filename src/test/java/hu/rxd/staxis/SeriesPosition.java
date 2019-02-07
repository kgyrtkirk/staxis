package hu.rxd.staxis;

public class SeriesPosition {

  private double idx;
  private double d;

  public SeriesPosition(double idx, double d) {
    this.idx = idx;
    this.d = d;
  }

  @Override
  public String toString() {
    return String.format("(%f,%f)", idx, d);
  }

  public double getValue() {
    return d;
  }

  public double getIdx() {
    return idx;
  }

}
