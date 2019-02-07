package hu.rxd.staxis.consumer;

import java.util.ArrayList;
import java.util.List;

import hu.rxd.staxis.SeriesPosition;
import hu.rxd.staxis.api.AbstractConsumer;
import hu.rxd.staxis.api.SeriesGenerator;

public class MeasurePointConsumer extends AbstractConsumer {

  private int numP;
  private int n;
  private int idx;
  private int nextCapIdx;

  List<SeriesPosition> points;

  public MeasurePointConsumer(SeriesGenerator series, int numP) {
    this.numP = numP;
    nextCapIdx = 0;
    points = new ArrayList<>();
    initialize(series);
  }

  @Override
  public void advertiseN(int n) {
    this.n = n;
  }

  @Override
  protected void consume(Double d) {
    if (nextCapIdx == idx) {
      points.add(new SeriesPosition((double) idx / n, d));
      nextCapIdx = points.size() * n / (numP - 1);
      if (nextCapIdx == n) {
        nextCapIdx -= 1;
      }
    }
    idx++;
  }

  @Override
  public void finish() {
    if (idx != n) {
      throw new RuntimeException();
    }
  }

  public List<SeriesPosition> getPoints() {
    return points
    ;
  }

}
