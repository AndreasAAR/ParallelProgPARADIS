public class DaemonDemo {

    public DaemonDemo() throws InterruptedException{
        Thread demo = new Daemon();
        demo.setDaemon(true);
        demo.start();

        System.out.println("Banishing demon");
        Thread.sleep(600);
        System.out.println("Banishing demon");
        Thread.sleep(600);
        System.out.println("Banishing demon");
        Thread.sleep(600);
        System.out.println("Banishing demon");
        Thread.sleep(600);
        System.out.println("Done with demon");
    }

}

class Daemon extends Thread{
    public void run() {
        while (true) {
            System.out.println("Demon is mischevious");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}