import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTest {
    /*
    The threadpool handles the threads we have available,
    and schedules the threads as widely as possible.
    However threadpools need to shut down explicitly.
     */
    public ThreadPoolTest(){
        int numProcs = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(numProcs);
        for(int i = 0; i < 100; i++){
            pool.submit(new VegetableChopper());
        }
        pool.shutdown();
    }
}

class VegetableChopper extends  Thread{
    public void run(){
        System.out.println(Thread.currentThread().getName() + "Chopped a veggie");
    }
}


