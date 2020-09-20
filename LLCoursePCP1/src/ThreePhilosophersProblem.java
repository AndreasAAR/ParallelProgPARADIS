import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreePhilosophersProblem {
    Lock chopStickA =  new ReentrantLock();
    Lock chopStickB =  new ReentrantLock();
    Lock chopStickC =  new ReentrantLock();

    /**
     *  There is an important order here.
     *   If Andreas has the logical order of C, and THEN A in his chopS priority,
     *   Then there is a risk that Jordathan takes A, Layla takes B,
     *   Andreas takes C, and ALL threads are stuck.
     *   As long as Jordathan and Andreas both compete for the lock A as prio
     *   there is no issue. Both Layla and Andreas wants C, but then its simply
     *   first come, first served.
     *
     *   Another issue if you three chopsticks is that in this case
     *   layla is the only one competing heavily for chopstick B
     *   To average this, you would perhaps want all of them to compete for the same sticks
     *    However, if you have a ton of threads, you might starve some threads.
     *    If those are requests, youd want all ot process.
     *    This workload mangament not covered in this course
     */

    public ThreePhilosophersProblem(){
        new Philosopher("Jordathan",chopStickA, chopStickB).start();
        new Philosopher("Layla",chopStickA,chopStickB).start();
        new Philosopher("Andreas",chopStickA, chopStickB).start();
     //  new Philosopher("Jordathan",chopStickA, chopStickB).start();
     //   new Philosopher("Layla",chopStickB,chopStickC).start();
     //   new Philosopher("Andreas",chopStickA, chopStickC).start();
    }
}

 class Philosopher extends Thread{
    static int  sushiCount = 40000;
    Lock prioChopStick;
    Lock secPrioChopStick;
    public Philosopher(String name, Lock prioChopStick,
                       Lock secPrioChopStick){
        super(name);
        this.prioChopStick = prioChopStick;
        this.secPrioChopStick = secPrioChopStick;
    }

    @Override
    public void run(){


        int sushiEaten = 0;

        while(sushiCount > 0) {
            prioChopStick.lock();
            if (!secPrioChopStick.tryLock()) {
                System.out.println(this.getName() + " Released its lock as there was a deadlock!");
                prioChopStick.unlock();
            }else {
                secPrioChopStick.lock();
            //In case of exception
            //The thread will always release even if terminating abruptly!
            try {
                if (sushiCount > 0) {
                    sushiCount--;
                    sushiEaten++;
                 //   System.out.println(this.getName() + " took a sushi" + ", sushi remaining: " + sushiCount);
                }
            }finally {
                secPrioChopStick.unlock();
                prioChopStick.unlock();
            }
         }
        }
        System.out.println(this.getName() + " got to eat" + sushiEaten + " pieces");
    }

}