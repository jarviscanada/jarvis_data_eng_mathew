package ca.jrvs.practice.sorting;

public class JQuickSort {

  public void quickSort(int[] values) {
    quickSort(values, 0, values.length - 1);
  }

  private void quickSort(int[] values, int start, int end) {
    int pivot = values[end];
    int j = start - 1;
    if (start < end) {
      for (int i = start; i < end; i++) {
        if (values[i] <= pivot) {
          swap(values, i, ++j);
        }
      }
      swap(values, ++j, end);
      quickSort(values, start, j - 1);
      quickSort(values, j + 1, end);
    }
  }

  private void swap(int[] arr, int index1, int index2) {
    int temp = arr[index1];
    arr[index1] = arr[index2];
    arr[index2] = temp;
  }
}
