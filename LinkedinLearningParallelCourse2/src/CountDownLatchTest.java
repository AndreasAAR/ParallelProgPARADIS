import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
CyclicBarrier holds a certain threads and then releases,
the countdownlatch simply releases when count value reaches zero.
 */
public class CountDownLatchTest {
    public CountDownLatchTest() throws InterruptedException {
        Shopper2[] shoppers = new Shopper2[10];
        for(int i = 0; i < shoppers.length/2; i++){
            shoppers[2*i] = new Shopper2("Andreas-"+i);
            shoppers[2*i+1] = new Shopper2("Layla-"+i);
        }
        for(Shopper2 s : shoppers ){
            s.start();
        }
        for(Shopper2 s : shoppers ){
            s.join();
        }
        System.out.println("We will buy " + Shopper.bagsOfChips + " Bags of Chips");
    }
}
class Shopper2 extends Thread {
    public static int bagsOfChips = 1;
    private static Lock pencil = new ReentrantLock();
    private static CountDownLatch fistBump = new CountDownLatch(5);

    public Shopper2(String name){
        setName(name);
    }
    public void run(){
        if(this.getName().contains("Andreas")){
            pencil.lock();
            try{
                bagsOfChips +=3;
                System.out.println(this.getName() + "ADDED three bags of chips.");
            }finally {
                pencil.unlock();
            }
            //Instead of reaching waiting threads, its a countdown
            //That can be run at any time.
            //An issue here is if we have missed our correct amounts of threads to work with.
            fistBump.countDown();
        }else{ //Presume "Layla"

            try{
                fistBump.await();
            }catch(InterruptedException  e){
                e.printStackTrace();
            }
            pencil.lock();
            try{
                bagsOfChips *=2;
                System.out.println(this.getName() + "Doubled the bags of chips.");
            }finally{
                pencil.unlock();
            }
        }
    }

}
