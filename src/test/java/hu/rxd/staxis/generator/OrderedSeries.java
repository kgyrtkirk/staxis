package hu.rxd.staxis.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import hu.rxd.staxis.api.SeriesGenerator;

public class OrderedSeries implements SeriesGenerator {

  private List<Double> valueList = new ArrayList<>();

  public OrderedSeries(SeriesGenerator series) {
    for (Double double1 : series) {
      valueList.add(double1);
    }
    Collections.sort(valueList);
  }

  @Override
  public Iterator<Double> iterator() {
    // oh-oh! this gonna hurt!
    return valueList.iterator();
  }

  @Override
  public int getN() {
    return valueList.size();
  }

}
