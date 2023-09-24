import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class SortingTask extends RecursiveAction {

    // the maximum size for which the method doesn't fork
    public static int PARALLEL_LIMIT = 55_000;

    float[] data;
    int lo;
    int hi;

    public SortingTask(float[] data, int lo, int hi) {
        this.data = data;
        this.lo = lo; // the lowest index to consider in the subarray
        this.hi = hi; // the highest index to consider in the subarray
    }

    @Override
    protected void compute() {
        // checking if we have reached the minimum array size to use non-parallel sorting
        if (hi - lo < PARALLEL_LIMIT) {
            Arrays.sort(data, lo, hi+1);
        }
        else {
            // parallel quick sort on two sides of the array based on the pivot
            int pivot = partition(data, lo, hi); // acquiring the pivot
            SortingTask st1 = new SortingTask(data, lo, pivot - 1);
            SortingTask st2 = new SortingTask(data, pivot + 1, hi);
            st1.fork();
            st2.compute();
            st1.join();
        }
    }

    private int partition(float[] data, int lo, int hi) {
        // partition procedure with a single pivot
        float pivot = data[hi];
        int i = (lo - 1); // index of smaller element
        int j = lo;
        for (; j < hi; j++) {
            // if current element is smaller than or equal to pivot
            if (data[j] <= pivot)
            { // swap data[i] and data[j]
                i++;
                float temp = data[i];
                data[i] = data[j];
                data[j] = temp;
            }
        }
        // swap data[i+1] and data[high] (or pivot)
        float temp = data[i+1];
        data[i+1] = data[hi];
        data[hi] = temp;

        return i + 1;
    }

}
