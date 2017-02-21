package com.newland.wechat.proxy;

import java.util.Iterator;

class NodeIterator implements Iterator<AlipayNode> {

    private final int start;
    private int next = 0;
    private AlipayNode[] nodes;

    public NodeIterator(int keyStart,AlipayNode[] n) {
      start = keyStart;
      next = start;
      nodes = n;
      computeNext();
      assert next >= 0 || nodes.length == 1 : "Starting sequence at " + start
          + " of " + nodes.length + " next is " + next;
    }

    public boolean hasNext() {
      return next >= 0;
    }

    private void computeNext() {
      if (++next >= nodes.length) {
        next = 0;
      }
      if (next == start) {
        next = -1;
      }
    }

    public AlipayNode next() {
      try {
        return nodes[next];
      } finally {
        computeNext();
      }
    }

    public void remove() {
      throw new UnsupportedOperationException("Can't remove a node");
    }
  }
