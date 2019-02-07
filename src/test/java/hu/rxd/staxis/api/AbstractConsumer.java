package hu.rxd.staxis.api;

public abstract class AbstractConsumer {

  protected final void initialize(SeriesGenerator series) {
    advertiseN(series.getN());
    for (Double d : series) {
      consume(d);
    }
    finish();
  }

  protected abstract void finish();

  protected abstract void consume(Double d);

  protected abstract void advertiseN(int n);

}
