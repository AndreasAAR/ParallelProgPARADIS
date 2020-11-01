
import java.util.concurrent.locks.*;

public class DataRaceWithSync {

    public DataRaceWithSync() throws InterruptedException {
        Thread andreas = new Shopper3(100);
        Thread layla = new Shopper3(500);
        andreas.setName("Andreas");
        layla.setName("Layla");
        layla.run();
        andreas.run();
        andreas.join();
        layla.join();
        System.out.println(Shopper3.garlicCount);

    }

}

class Shopper3 extends Thread{

    public static Lock pencil = new ReentrantLock();
    public static Integer garlicCount = 0;
    public int waitTime;

    public Shopper3(int waitTime){
        this.waitTime = waitTime;
    }

    public static synchronized void incrementGarlic(){
        garlicCount++;
    }

    @Override
    public void run(){
        for(int i = 0; i < 5; i++){
            incrementGarlic(); //Because static, will be upgraded and degraded
            System.out.println(this.getName() + " is adding garlic.");
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
