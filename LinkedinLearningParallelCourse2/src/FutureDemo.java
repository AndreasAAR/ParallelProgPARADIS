import java.sql.SQLOutput;
import java.util.concurrent.*;

class HowManyVegatables implements Callable{
    public Integer call() throws Exception{
        System.out.println("Layla counts veggies");
        Thread.sleep(3000);
        return 42;
    }
}

public class FutureDemo {
    /*
    Future is a asyncronus execution.
    Similar to Apex Future execution too.
     */
    /*
     The callable interface is used to get the result back
     from a asyncronus process.
     The Callable has callable, and can throw an exepction.
     */
    public FutureDemo() throws ExecutionException, InterruptedException {
        System.out.println("Andreas checks with layla");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future result = executor.submit(new HowManyVegatables());
        System.out.println("Andreas does some other stuff....");
        System.out.println("Layla says we had " + result.get() + " Vegetables");
    }
}
