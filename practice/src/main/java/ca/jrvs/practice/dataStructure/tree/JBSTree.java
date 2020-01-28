package ca.jrvs.practice.dataStructure.tree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class JBSTree<T> implements JTree<T> {

  private Comparator<T> comparator;
  private TreeNode<T> root = null;
  private int size = 0;

  public JBSTree(Comparator<T> comparator) {
    if (comparator == null) {
      throw new IllegalArgumentException("Comparator cannot be null");
    }
    this.comparator = comparator;
  }

  @Override
  public T insert(T obj) {
    TreeNode<T> current = root;
    TreeNode<T> newNode = new TreeNode<>(obj, null, null, null);
    if (root == null) {
      root = newNode;
      size++;
    } else {
      while (current != null) {
        if (comparator.compare(current.element, obj) > 0) {
          if (current.left == null) {
            current.left = newNode;
            newNode.parent = current;
            size++;
            return obj;
          }
          current = current.left;
        } else if (comparator.compare(current.element, obj) < 0) {
          if (current.right == null) {
            current.right = newNode;
            newNode.parent = current;
            size++;
            return obj;
          }
          current = current.right;
        } else {
          throw new IllegalArgumentException("Element already in BST");
        }
      }
    }
    return obj;
  }

  @Override
  public T search(T obj) {
    TreeNode<T> current = root;
    while (current != null) {
      if (comparator.compare(current.element, obj) > 0) {
        current = current.left;
      } else if (comparator.compare(current.element, obj) < 0) {
        current = current.right;
      } else {
        return obj;
      }
    }
    return null;
  }

  @Override
  public T remove(T obj) {
    return remove(obj, root);
  }

  private T remove(T obj, TreeNode<T> node) {
    while (node != null) {
      if (comparator.compare(node.element, obj) > 0) {
        node = node.left;
      } else if (comparator.compare(node.element, obj) < 0) {
        node = node.right;
      } else {
        TreeNode<T> successor;

        // Node to remove has a right child
        if (node.right != null) {
          successor = node.right;
          while (successor.left
              != null) { // find leftmost child in right branch. is leaf or has right branch
            successor = successor.left;
          }
          if (compareToParent(successor) > 0) {
            successor.parent.left = successor.right;
          } else if (compareToParent(successor) < 0) {
            successor.parent.right = successor.right;
          }
          node.element = successor.element;
          successor.parent = null;
          successor.right = null;
          return obj;

          // Node to remove has a left child, but not a right
        } else if (node.left != null) {
          successor = node.left;
          while (successor.right
              != null) { // find the rightmost child in left branch. is leaf or has left branch
            successor = successor.right;
          }
          if (compareToParent(successor) > 0) {
            successor.parent.left = successor.left;
          } else if (compareToParent(successor) < 0) {
            successor.parent.right = successor.left;
          }
          node.element = successor.element;
          successor.parent = null;
          successor.left = null;
          return obj;

          // Node to remove is a leaf
        } else {
          if (node.parent != null) {
            if (compareToParent(node) > 0) {
              node.parent.left = null;
              node.parent = null;
              return obj;
            } else if (compareToParent(node) < 0) {
              node.parent.right = null;
              node.parent = null;
              return obj;
            }
          } else {
            root = null;
            return obj;
          }
        }
      }
    }
    throw new IllegalArgumentException("Element does not exist");
  }

  private int compareToParent(TreeNode<T> node) {
    return comparator.compare(node.parent.element, node.element);
  }

  @Override
  @SuppressWarnings("unchecked")
  public T[] preOrder() {
    return (T[]) preOrder(root).toArray();
  }

  private List<T> preOrder(TreeNode<T> node) {
    List<T> elems = new ArrayList<>();
    elems.add(node.element);
    if (node.left != null) {
      elems.addAll(preOrder(node.left));
    }
    if (node.right != null) {
      elems.addAll(preOrder(node.right));
    }
    return elems;
  }

  @Override
  @SuppressWarnings("unchecked")
  public T[] inOrder() {
    return (T[]) inOrder(root).toArray();
  }

  private List<T> inOrder(TreeNode<T> node) {
    List<T> elems = new ArrayList<>();
    if (node.left != null) {
      elems.addAll(inOrder(node.left));
    }
    elems.add(node.element);
    if (node.right != null) {
      elems.addAll(inOrder(node.right));
    }
    return elems;
  }

  @Override
  @SuppressWarnings("unchecked")
  public T[] postOrder() {
    return (T[]) postOrder(root).toArray();
  }

  private List<T> postOrder(TreeNode<T> node) {
    List<T> elems = new ArrayList<>();
    if (node.left != null) {
      elems.addAll(postOrder(node.left));
    }
    if (node.right != null) {
      elems.addAll(postOrder(node.right));
    }
    elems.add(node.element);
    return elems;
  }

  @Override
  public int findHeight() {
    return findHeight(root);
  }

  private int findHeight(TreeNode<T> node) {
    if (node == null) {
      return 0;
    } else {
      return Math.max(findHeight(node.left), findHeight(node.right)) + 1;
    }
  }

  static class TreeNode<T> {

    T element;
    TreeNode<T> left;
    TreeNode<T> right;
    TreeNode<T> parent;

    TreeNode(T elem, TreeNode<T> left, TreeNode<T> right, TreeNode<T> parent) {
      element = elem;
      this.left = left;
      this.right = right;
      this.parent = parent;
    }
  }
}
