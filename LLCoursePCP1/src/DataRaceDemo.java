public class DataRaceDemo {

    public DataRaceDemo() throws InterruptedException {
     Thread andreas = new Shopper();
     Thread layla = new Shopper();
     andreas.run();
     layla.run();
     andreas.join();
     layla.join();
      System.out.println(Shopper.garlicCount);

    }

}

class Shopper extends Thread{

   public static int garlicCount = 0;

    public void run(){
        for(int i = 0; i < 1000; i++)
            garlicCount++; //Because static, will be upgraded and degraded
            //as two threads access out of sync.
    }

}
