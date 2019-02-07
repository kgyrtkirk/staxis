package hu.rxd.staxis.consumer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import hu.rxd.staxis.api.AbstractConsumer;
import hu.rxd.staxis.api.SeriesGenerator;

import java.util.Set;

public class Freq extends AbstractConsumer {

  private int n;

  public Freq(SeriesGenerator series, int n) {
    this.n = n;
    initialize(series);
  }

  @Override
  protected void finish() {
  }

  @Override
  protected void consume(Double d) {
    valueMap.inc(d);
    if (valueMap.size() > n) {
      valueMap.decAll();
    }
  }

  @Override
  public String toString() {
    return "values: [" + valueMap + "]";
  }

  @Override
  protected void advertiseN(int n) {
  }

  FreqMap0 valueMap = new FreqMap0();
  //  FreqMap valueMap = new FreqMap();
  static class FreqMap0 {

    @Override
    public String toString() {
      StringBuilder sb=new StringBuilder();
      for (Entry<Double, Node> e : m.entrySet()) {

        sb.append(e.getKey() + ": " + e.getValue().cnt + "\n");
      }
      return sb.toString();
    }

    static class Node {

      private Double d;
      private int cnt;

      public Node(Double d) {
        this.d = d;
      }

      public void inc() {
        cnt++;
      }

    }

    Map<Double, Node> m = new HashMap<>();

    public void inc(Double d) {
      Node node = m.get(d);
      if (node == null) {
        node = new Node(d);
        m.put(d, node);
      }
      node.inc();
    }

    public void decAll() {
      Set<Double> purge = new HashSet<>();
      for (Entry<Double, Node> e : m.entrySet()) {
        if (--e.getValue().cnt <= 0) {
          purge.add(e.getKey());
        }
      }
      for (Double key : purge) {
        m.remove(key);
      }
    }

    public int size() {
      return m.size();
    }

  }

  static class FreqMap {
    static class Node0<T, NT> {
      T value;
      NT next;
      NT prev;

    }

    static class Node extends Node0<Double, Node> {

      //      private Double d;
      private Group group;

      //      private Node groupNext;
      //      private Node groupPrev;

      public Node(Double d, Group groupOne) {
        value = d;
        group = groupOne;
        group.enroll(this);
      }

      public void incGroup(int dir) {
        Group otherGroup = group.neigh(dir);

      }

    }

    static class Group extends Node0<Node, Group> {

      public Group neigh(int dir) {
        if (dir > 0) {
          return next;
        } else {
          return prev;
        }
      }

      public void enroll(Node node) {
        if (value == null) {
          value = node;
          node.next = node.prev = node;
        } else {
          value.next.prev = node;
          node.next = value.next;
          node.prev = value;
          value.next = node;
        }
      }

    }

    Group groupOne = new Group();
    Map<Double, Node> hashMap = new HashMap<>();

    public void inc(Double d) {
      Node node = hashMap.get(d);
      if (node == null) {
        node = new Node(d, groupOne);
        hashMap.put(d, node);
      } else {
        node.incGroup(+1);
      }
    }

    public void decAll() {
      throw new RuntimeException();
      // TODO Auto-generated method stub
      //
    }

    public int size() {
      throw new RuntimeException();
      // TODO Auto-generated method stub
      // return 0;
    }

  }
}
