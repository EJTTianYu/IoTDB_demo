package io.learn;

import java.util.Scanner;
import java.util.Set;


class Node {

  int num;
  Set<Edge> edges;
}

class Edge {

  Node src;
  Node dst;
  int weight;

  public Edge(Node src, Node dst, int weight) {
    this.src = src;
    this.dst = dst;
    this.weight = weight;
  }
}

public class Solution3 {


  static int nodeNum;


  public static void main(String[] args) {
    System.out.println(Integer.MAX_VALUE);
    Scanner in = new Scanner(System.in);
    int nodeNum = in.nextInt();
    int edgeNum = in.nextInt();
    Node[] nodes = new Node[nodeNum];
    // 生成节点
    for (int i = 0; i < nodeNum; i++) {
      nodes[i].num = i + 1;
    }
    for (int i = 0; i < edgeNum; i++) {
      Node src = nodes[in.nextInt() - 1];
      Node dst = nodes[in.nextInt() - 1];
      Edge edge = new Edge(src, dst, in.nextInt());
      src.edges.add(edge);
      dst.edges.add(edge);
    }

  }

}
