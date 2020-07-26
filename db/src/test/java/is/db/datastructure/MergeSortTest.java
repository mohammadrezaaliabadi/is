package is.db.datastructure;

import org.junit.jupiter.api.Test;


class MergeSortTest {

    @Test
    void sort() {
        Integer[] items =
                {
                        8, 10, 13, 5, 14, 11, 7, 12, 1, 4, 17, 20, 18, 6, 9, 15, 19, 2, 16
                };

        /* Create a new instance of the mergesort Algorithm and sort the array of integers */
        MergeSort<Integer> mergeSort = new MergeSort<>(items);
        mergeSort.sort();

        /* Printing the array to check whether they are in sorted order */
        System.out.println("Printing Sorted Items: ");
        for (Integer x : mergeSort.getSortedItems()) {
            System.out.print(x + " ");
        }
    }

}