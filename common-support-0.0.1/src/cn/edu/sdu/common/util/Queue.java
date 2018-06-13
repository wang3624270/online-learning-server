package cn.edu.sdu.common.util;

import java.util.LinkedList;

public class Queue {
  private LinkedList list = new LinkedList();

  public Queue() {
  }

  public void put(Object v) {
    list.addFirst(v);
  }

  public Object get() {
    return list.removeLast();
  }

  public boolean isEmpty() {
    return list.isEmpty();
  }

}
