import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadLocalRandom;

public class Sorting {
    // replace with your code
    public static final String TEAM_NAME = "CANJ";
    public static final int POOL_SIZE = Runtime.getRuntime().availableProcessors();

    /**
     * Sorts an array of doubles in increasing order. This method is a
     * single-threaded baseline implementation.
     *
     * @param data the array of doubles to be sorted
     */
    public static void baselineSort(float[] data) {
        Arrays.sort(data, 0, data.length);
    }

    /**
     * Sorts an array of doubles in increasing order. This method is a
     * multi-threaded optimized sorting algorithm. For large arrays (e.g., arrays of size at least 1 million) it should be significantly faster than baselineSort.
     *
     * @param data the array of doubles to be sorted
     */
    public static void parallelSort(float[] data) {

        int n = data.length; // getting length of array

        ForkJoinPool pool = new ForkJoinPool(POOL_SIZE); //creating a fork-join pool
        SortingTask st = new SortingTask(data, 0, n-1); //creating a sorting task on the full array of data
        pool.invoke(st);

    }

    /**
     * Determines if an array of doubles is sorted in increasing order.
     *
     * @param data the array to check for sortedness
     * @return `true` if the array is sorted, and `false` otherwise
     */
    public static boolean isSorted(float[] data) {
        double prev = data[0];

        for (int i = 1; i < data.length; ++i) {
            if (data[i] < prev) {
                return false;
            }

            prev = data[i];
        }

        return true;
    }
}
