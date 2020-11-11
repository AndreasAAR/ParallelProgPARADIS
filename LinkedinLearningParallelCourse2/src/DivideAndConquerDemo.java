import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class DivideAndConquerDemo {
    /*
    Typical for merge sort etc, where
    you can solve independent subproblems.
    compare and move in order for example,
    for sorting a list, can be done with smaller sets of comparisons,
    were you merge already sorted lists,
    if the smallest value in a set is larger than the largest in a set to merge,
    you can merge immediately, so the sets can benefit from being sorted independently.
     */
    public DivideAndConquerDemo(){
        ForkJoinPool pool = ForkJoinPool.commonPool();
        Long total = pool.invoke(new RecursiveSum(0,1_00_00_000));
        System.out.println("Sum of all numbers between 0 and 1 0 000 000 is: "
                + total);
    }

}


class RecursiveSum extends RecursiveTask<Long> {
    private long lo, hi;
    public RecursiveSum(long lo, long hi){
        this.lo = lo;
        this.hi = hi;
    }

    @Override
    protected Long compute() {

        if(hi-lo  <= 100_000){
            long total = 0;
            for(long i = lo; i <= hi;i++){
                total += i;
                return total;
            }
        }else{
            long mid = (hi+lo)/2;
            RecursiveSum left = new RecursiveSum(lo,mid);
            RecursiveSum right = new RecursiveSum(mid+1,hi);
            left.fork();
            return right.compute() + left.join(); //We wait for lefts result.
        }
        return null;
   }
}
