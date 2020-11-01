import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {



        public ReentrantLockDemo() throws InterruptedException {
            Thread andreas = new Shopper4(100);
            Thread layla = new Shopper4(500);
            andreas.setName("Andreas");
            layla.setName("Layla");
            layla.run();
            andreas.run();
            andreas.join();
            layla.join();
            System.out.println("we should by num garlic: " + Shopper4.garlicCount
            + "And numpotato: " + Shopper4.potatoCount);

        }

    }



    class Shopper4 extends Thread{

        public static Lock pencil = new ReentrantLock();
        public static int garlicCount, potatoCount = 0;
        public int waitTime;

        private void addGarlic(){
            pencil.lock();
            garlicCount++;
            pencil.unlock();
        }
        private void addPotato(){
            pencil.lock();
            potatoCount++;
            addGarlic();
            pencil.unlock();
        }


        public Shopper4(int waitTime){
            this.waitTime = waitTime;
        }

        public static synchronized void incrementGarlic(){
            garlicCount++;
        }

        @Override
        public void run(){
            for(int i = 0; i < 5; i++){
                addGarlic(); //Because static, will be upgraded and degraded
                addPotato();
                System.out.println(this.getName() + " is adding garlic and potatoes.");
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
