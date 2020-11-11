/**
 * Challenge: Multiply two matrices
 */

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Random;
import java.util.concurrent.*;

/* sequential implementation of matrix multiplication */
class SequentialMatrixMultiplier {

    private int[][] A, B;
    private int numRowsA, numColsA, numRowsB, numColsB;

    public SequentialMatrixMultiplier(int[][] A, int[][] B) {
        this.A = A;
        this.B = B;
        this.numRowsA = A.length;
        this.numColsA = A[0].length;
        this.numRowsB = B.length;
        this.numColsB = B[0].length;
        if (numColsA != numRowsB)
            throw new Error(String.format("Invalid dimensions; Cannot multiply %dx%d*%dx%d\n", numRowsA, numRowsB, numColsA, numColsB));
    }

    /* returns matrix product C = AB */
    public int[][] computeProduct() {
        int[][] C = new int[numRowsA][numColsB];
        for (int i=0; i<numRowsA; i++) {

            for (int k=0; k<numColsB; k++) {
                int sum = 0;
                for (int j=0; j<numColsA; j++) {
                    sum += A[i][j] * B[j][k];
                }
                C[i][k] = sum;
            }

        }
        return C;
    }
}




/* parallel implementation of matrix multiplication */
class ParallelMatrixMultiplier {

    private class ParallelWorker implements Callable{
      private int rowStartC, rowEndC;
      public ParallelWorker(int rowStartC, int rowEndC){
          this.rowStartC = rowStartC;
          this.rowEndC = rowEndC;
      }
      public int[][] call(){
          // Creates its own 2d array for its partial result.
          int[][] partialC = new int [rowEndC - rowStartC][numColsB];
         for(int i = 0; i < rowEndC-rowStartC; i ++){ //iterating rows
          for(int k = 0; k < numColsB; k++){ //iterating B cols
              int sum = 0;
              for(int j = 0; j < numColsA; j++){ // iterating cols A
                  sum += A[i+rowStartC][j]*B[j][k];
              }
              partialC[i][k]= sum;
          }
          }
         return partialC;
      }

    }



    private int[][] A, B;
    private int numRowsA, numColsA, numRowsB, numColsB;

    public ParallelMatrixMultiplier(int[][] A, int[][] B) {
        this.A = A;
        this.B = B;
        this.numRowsA = A.length;
        this.numColsA = A[0].length;
        this.numRowsB = B.length;
        this.numColsB = B[0].length;
        if (numColsA != numRowsB)
            throw new Error(String.format("Invalid dimensions; Cannot multiply %dx%d*%dx%d\n", numRowsA, numRowsB, numColsA, numColsB));
    }

    /* returns matrix product C = AB */
    public int[][] computeProduct() {
        /*
         This splits the array into the maximum amount of
         threads we can use effectively w. our existing processors.
         */
        int numWorkers = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(numWorkers);


        int chunkSize = (int) Math.ceil((double) numRowsA/numWorkers);
        Future<int[][]>[] futures = new Future[numWorkers];

        for(int w=0; w< numWorkers; w++){ //All the workers available to us is numworkers
            int start = Math.min(w*chunkSize, numRowsA);  //We jumb a step of w to assign to all our workers
            int end = Math.min((w+1)*chunkSize,numRowsA);  //Chunksize is the size of the task per worker
            futures[w] = pool.submit(new ParallelWorker(start, end));
        }
        int [][] c = new int[numRowsA][numColsB]; //An array the same size of A and B chunks we sent to our futures
        try{
            //Collecting the results of our futures.
            for(int w = 0; w < numWorkers; w++ ){
                 int [][] partialC = futures[w].get();
                 for(int i = 0; i< partialC.length; i++) {
                     for(int j = 0; j< numColsB; j++){
                      c[i + (w*chunkSize)][j] = partialC[i][j];
                     }
                 }
            }
        }catch (ConcurrentModificationException ce){
            ce.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return c;
    }
}

public class MatrixMultiplyChallenge {

    /* helper function to generate MxN matrix of random integers */
    public static int[][] generateRandomMatrix(int M, int N) {
        System.out.format("Generating random %d x %d matrix...\n", M, N);
        Random rand = new Random();
        int[][] output = new int[M][N];
        for (int i=0; i<M; i++)
            for (int j=0; j<N; j++)
                output[i][j] = rand.nextInt(100);
        return output;
    }

    /* evaluate performance of sequential and parallel implementations */
    public static void main(String args[]) {
        final int NUM_EVAL_RUNS = 5;
        //Runnin the test on smaller sizes will not give as good result as larger sizes.
        //Already at 200 we have improvement
        // But at
        final int[][] A = generateRandomMatrix(200,200);
        final int[][] B = generateRandomMatrix(200,200);

        System.out.println("Evaluating Sequential Implementation...");
        SequentialMatrixMultiplier smm = new SequentialMatrixMultiplier(A,B);
        int[][] sequentialResult = smm.computeProduct();
        double sequentialTime = 0;
        for(int i=0; i<NUM_EVAL_RUNS; i++) {
            long start = System.currentTimeMillis();
            smm.computeProduct();
            sequentialTime += System.currentTimeMillis() - start;
        }
        sequentialTime /= NUM_EVAL_RUNS;

        System.out.println("Evaluating Parallel Implementation...");
        ParallelMatrixMultiplier pmm = new ParallelMatrixMultiplier(A,B);
        int[][] parallelResult = pmm.computeProduct();
        double parallelTime = 0;
        for(int i=0; i<NUM_EVAL_RUNS; i++) {
            long start = System.currentTimeMillis();
            pmm.computeProduct();
            parallelTime += System.currentTimeMillis() - start;
        }
        parallelTime /= NUM_EVAL_RUNS;

        // display sequential and parallel results for comparison
        if (!Arrays.deepEquals(sequentialResult, parallelResult))
            throw new Error("ERROR: sequentialResult and parallelResult do not match!");
        System.out.format("Average Sequential Time: %.1f ms\n", sequentialTime);
        System.out.format("Average Parallel Time: %.1f ms\n", parallelTime);
        System.out.format("Speedup: %.2f \n", sequentialTime/parallelTime);
        System.out.format("Efficiency: %.2f%%\n", 100*(sequentialTime/parallelTime)/Runtime.getRuntime().availableProcessors());
    }
}