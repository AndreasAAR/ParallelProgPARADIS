class UnsyncedThread extends Thread {
    /**
     * Thread for testing Thread scheduling
     */
    public int jobCounts = 0;
    public static boolean working = true;

    public UnsyncedThread(String name){
        this.setName(name);
    }
    public void run(){
        while(working){
            System.out.println(this.getName() + "did a job!");
            jobCounts++;
        }
    }
}

public class UnsyncedThreadTester{

    public UnsyncedThreadTester() throws InterruptedException {
        UnsyncedThread theodor = new UnsyncedThread("Theodor");
        UnsyncedThread uma= new UnsyncedThread("Uma");
        theodor.start();
        uma.start();
        Thread.sleep(1000);
        UnsyncedThread.working = false;
        uma.join();
        theodor.join();
        System.out.println(theodor.getName() + " worked" + theodor.jobCounts + " jobs");
        System.out.println(uma.getName() + " worked" + uma.jobCounts + " jobs");
        }

}
