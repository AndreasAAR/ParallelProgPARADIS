import com.sun.source.util.SourcePositions;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * We have a queue of soup,
 * and consumer. The producer fills the queue
 * the consumer drinks the soup.
 * We use FIFO in this case (not to waste soup that gets cold lets say)
 * We need mutual exclusion of the producer and consumer
 * We want to avoid consumption attempts from empty queues.
 * Threadsafety can be implemented with mutex and conditionals.
 * Sometimes you have an external streamer that cant be slowed down,
 * then you have to handle not consuming or have buffer-overflow.
 * Can we lose data or not?
 * Unbounded queues are linked lists with unlimited capacity.
 * Still limited by memory in computer(depending on program type)
 * The average rate of production and consumption matters for this
 * Sometimes you want to pipeline the process
 * With producer consumer pairs, with a buffer
 */

public class ProducerConsumerSoupDemo {
    public String terminatingMessage = "No soup for you!";
    public ProducerConsumerSoupDemo(){
        //Arrayblocking queue is concrete, bounded,
        //Uses an array under the hood with finite elements.
        //Since it implements blocking interface its threadsafe,
        //Does not allow dataraces, no locks needed.
        BlockingQueue servingLine = new ArrayBlockingQueue(5);
        new SoupDrinker(servingLine).start();
        new SoupDrinker(servingLine).start(); //Two needed, if not enough, we will have a error
        new SoupProducer(servingLine).start();
        //In the initial example we only have one drinker/consumer, and get queue full error
    }

    class SoupProducer extends Thread{
        private BlockingQueue servingLine;
        public SoupProducer(BlockingQueue servingLine){
            this.servingLine = servingLine;
        }
        public void run(){
            try{
                for(int i = 0; i < 20; i++){
                    servingLine.add("Bowl #"+ i);
                    //There is no safety check here, if there is a full buffer we just fail
                    System.out.printf("Served soup bowl #%d - remaining queue capacity: " +
                            "%d\n", i, servingLine.remainingCapacity());
                    Thread.sleep(200); //Time to serve again
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            //We have to tell both soupdrinkers we dont have more soup.
            servingLine.add(terminatingMessage);
            servingLine.add(terminatingMessage);
        }
    }

    class SoupDrinker extends  Thread{
        BlockingQueue servingLine;
        public SoupDrinker(BlockingQueue servingLine){
            this.servingLine = servingLine;
        }
        public void run(){
            while (true){
                try{
                    String bowl = (String) servingLine.take();//Take is a queue function
                    if(bowl.equals(terminatingMessage)){
                        System.out.println("No more soup!");
                        break; //We
                    }
                    System.out.format("Ate %s\n", bowl);
                    Thread.sleep(300);
                }catch (Exception e){e.printStackTrace();}

            }
        }
    }

}



