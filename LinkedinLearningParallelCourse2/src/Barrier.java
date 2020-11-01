

import java.sql.SQLOutput;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.*;
/*
Barrier, prevents progression until
enough threads finished.
//Parties are demanded threads to finish untill program gets to proceed.
//Order does not matter here, we care about finishing up all the treads, thats it.
*/
public class Barrier {

      public Barrier() throws InterruptedException {
          Shopper[] shoppers = new Shopper[10];
          for(int i = 0; i < shoppers.length/2; i++){
              shoppers[2*i] = new Shopper("Andreas-"+i);
              shoppers[2*i+1] = new Shopper("Layla-"+i);
          }
          for(Shopper s : shoppers ){
              s.start();
          }
          for(Shopper s : shoppers ){
              s.join();
          }
          System.out.println("We will buy " + Shopper.bagsOfChips + " Bags of Chips");
      }
}

class Shopper extends Thread {
    public static int bagsOfChips = 1;
    private static Lock pencil = new ReentrantLock();
     private static CyclicBarrier fistBump = new CyclicBarrier(10);
    public Shopper(String name){
        setName(name);
    }
    public void run(){
         if(this.getName().contains("Andreas")){
             pencil.lock();
             try{
                 bagsOfChips +=3;
                 System.out.println(this.getName() + "ADDED three bags of chips.");
             }finally{
                 pencil.unlock();
             }try{
                 fistBump.await();
             }catch(InterruptedException | BrokenBarrierException e){
                 e.printStackTrace();
             }
         }else{ //Presume "Layla"
             //Here all the layla threads need to show up and start,
             //THEN we can continue the execution of their run.
             //Other cool uncitons a
             try{
                 fistBump.await();
             }catch(InterruptedException | BrokenBarrierException e){
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
