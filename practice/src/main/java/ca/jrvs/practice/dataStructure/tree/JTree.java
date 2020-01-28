package ca.jrvs.practice.dataStructure.tree;

public interface JTree<T> {

  T insert(T obj);

  T search(T obj);

  T remove(T obj);

  T[] preOrder();

  T[] inOrder();

  T[] postOrder();

  int findHeight();
}
