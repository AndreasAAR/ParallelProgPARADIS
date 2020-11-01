import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionVariableSlowCookerEfficient {
    public static int numPeople = 5;

    public ConditionVariableSlowCookerEfficient(){
        for(int i = 0; i < numPeople; i ++){
            new HungryPerson2(i,numPeople).start();
        }
    }

}

class HungryPerson2 extends Thread{

    private int personID;
    private static Lock slowCookerLid = new ReentrantLock();
    private static int servings = 11;
    private static Condition soupTaken = slowCookerLid.newCondition();

    int numPeople;
    public HungryPerson2(int personID, int numPeople){
        this.numPeople = numPeople;
        this.personID = personID;
    }
    public void run(){
        while(servings > 0 ){
            slowCookerLid.lock();
            try{
                while((personID != servings % numPeople) && servings > 0 ) {
                    System.out.format("Person %d checked the cooker for the other taken their share, then put the lid back. \n",personID);
                    soupTaken.await();
                }  //Check for your turn or wait!
                 if(servings > 0){
                    servings--;
                    System.out.format("Person %d, took some soup! Servings left: " +
                            "%d\n", personID, servings);
                    soupTaken.signalAll(); //Now waiting threads can be started again!
                        //Awaiting threads are good as they do not actually run in the loop
                // Until they are checked with a signal!
                     //Issue here is latest also checks.
                 }
                 } catch (Exception e){
                e.printStackTrace();
            }finally {
                slowCookerLid.unlock();
            }
        }
    }

}

