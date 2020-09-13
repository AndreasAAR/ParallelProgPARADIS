import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;




import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class FailedTryLockDemo {

    public FailedTryLockDemo() throws InterruptedException {
        Thread andreas = new Shopper6(100);
        Thread layla = new Shopper6(100);
        andreas.setName("Andreas");
        layla.setName("Layla");
        long start = System.currentTimeMillis();
        layla.start();
        andreas.start();
        andreas.join();
        layla.join();
        long finish = System.currentTimeMillis();
        System.out.println("Worktime:   " +(float)(finish-start)/100 + "seconds" );

    }

}



class Shopper6 extends Thread{

    private int itemsToadd = 0;
    public static Lock pencil = new ReentrantLock();
    public static int itemsOnNotePad = 0;
    public int waitTime;

    public Shopper6(int waitTime){
        this.waitTime = waitTime;
    }


    @Override
    public void run(){
        while(itemsOnNotePad <= 20){
            if(itemsToadd > 0){

                pencil.lock();
                itemsOnNotePad += itemsToadd;
                System.out.println(this.getName() + "Added " + itemsToadd
                        + " items to notepad");
                itemsToadd = 0;
                try {
                    Thread.sleep(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    pencil.unlock();
                }
            }else{
                try{
                    Thread.sleep(waitTime);
                    itemsToadd++;
                    System.out.println(this.getName() + " found something to add ");
                }catch (InterruptedException ie){
                    ie.printStackTrace();
                }
            }


        }

    }

}
