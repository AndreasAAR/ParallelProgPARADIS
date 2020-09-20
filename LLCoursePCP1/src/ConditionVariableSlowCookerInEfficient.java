import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionVariableSlowCookerInEfficient {
public static int numPeople = 2;

     public ConditionVariableSlowCookerInEfficient(){
         for(int i = 0; i < numPeople; i ++){
             new HungryPerson(i,numPeople).start();
         }
     }

}

class HungryPerson extends Thread{

    private int personID;
    private static Lock slowCookerLid = new ReentrantLock();
    private static int servings = 11;
    int numPeople;
    public HungryPerson(int personID, int numPeople){
        this.numPeople = numPeople;
        this.personID = personID;
    }
    public void run(){
        while(servings > 0 ){
            slowCookerLid.lock();
            try{
                if((personID == servings % numPeople) //If 4 servings are left, if Im number 0,
                                             //then I may take, basically
                                             //0 takes even, 1 odd.
                        && servings > 0 ){
                    servings--;
                    System.out.format("Person %d, took some soup! Servings left: " +
                            "%d\n", personID, servings);
                }else {
                    System.out.format("Person %d checked the cooker for the other taken their share, then put the lid back. \n",personID);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                slowCookerLid.unlock();
            }
        }
    }

 }

