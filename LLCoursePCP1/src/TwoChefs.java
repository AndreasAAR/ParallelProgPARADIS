class ChefAndreas{


}

class ChefLayla extends Thread{
   public void run(){
       System.out.println("Layla is waiting for Cofee");
       try{
       Thread.sleep(3000);
       }catch(InterruptedException e){
           e.printStackTrace();
       }
       System.out.println("Coffee is done!");
   }

}
public class TwoChefs {

    public TwoChefs()  {
        System.out.println("Andreas is asking layla for coffee");
        Thread layla = new ChefLayla();
        System.out.println("Andreas tells layla to start");
        layla.start();
        System.out.println("Andreas keep doing his thing");
        try{
            Thread.sleep(5000);
            layla.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("Both Andreas and Layla are done");


    }

}