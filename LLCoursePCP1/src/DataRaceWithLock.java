
import java.util.concurrent.locks.*;

public class DataRaceWithLock {

    public DataRaceWithLock() throws InterruptedException {
        Thread andreas = new Shopper2(100);
        Thread layla = new Shopper2(500);
        andreas.setName("Andreas");
        layla.setName("Layla");
        layla.run();
        andreas.run();
        andreas.join();
        layla.join();
        System.out.println(Shopper2.garlicCount);

    }

}

class Shopper2 extends Thread{

    public static Lock pencil = new ReentrantLock();
    public static int garlicCount = 0;
    public int waitTime;

    public Shopper2(int waitTime){
        this.waitTime = waitTime;
    }

    @Override
    public void run(){
        for(int i = 0; i < 5; i++){
            pencil.lock();
            garlicCount++; //Because static, will be upgraded and degraded

            System.out.println(this.getName() + " is adding garlic.");
            pencil.unlock();

        //as two threads access out of sync.
            try {
                Thread.sleep(waitTime);
                System.out.println(this.getName() + " is thinking.");
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
