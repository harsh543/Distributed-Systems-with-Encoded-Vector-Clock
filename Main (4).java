import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;


//import dcs2.Utils;

public class Main {

    public static Map<Long, Long> storeInitialValues;
    public static Map<Long, Long> storeTimestamps;



    @SuppressWarnings({"resource"})
    public static void main(String[] args) throws InterruptedException {
        Processor p = new Processor();
        System.out.println("****This is where the project starts*****");
        Scanner reader = new Scanner(System.in);
        System.out.print("Enter number of processes you want to create: ");
        int n = reader.nextInt();

        //Simulator simulator = new Simulator();
        Thread thread[] = new Thread[n];
        for (int i = 0; i < n; i++) {
            thread[i] = new Thread(new Processor(), "Process" + i);
        }
        for (int i = 0; i < n; i++) {
          //  long primeNo = Processor.generateNextPrime();

          //  System.out.println(Thread.currentThread().getId() + " " + primeNo);
           // storeInitialValues.put(Thread.currentThread().getId(), primeNo);
            //storeTimestamps.put(Thread.currentThread().getId(), primeNo);
           // System.out.println("Text   ");
            thread[i].start();
           // System.out.println(storeInitialValues);

            // check wat this doing
            //Processor.setSharedPrimeNoMap(storeInitialValues);
          //  System.out.println("Prime number associated with Process Id " + thread[i].getId() + "is : " + primeNo);
        }
        //REMAIN HERE
        TimeUnit.MINUTES.sleep(1);

        // now send stop signal to PrintQueue

        p.setStop(true);
    }

}

class Processor implements Runnable {

    private static volatile long currentPrime = 0;
    private volatile static long prevProcess;
    private static volatile long prevPrime = 0;
    private static volatile Map<Long, Long> sharedPrimeNoMap = new ConcurrentHashMap<>();
    private final Semaphore semaphore;
    public Integer prevPrimeNumber = 0;
    private boolean stop = false;
    private final Object lock = new Object();

    Main m ;
    public Processor() {
        semaphore = new Semaphore(1);
    }

    public static synchronized long generateNextPrime() {
        currentPrime++;
        if ( currentPrime < 2 ) {
            currentPrime = 2;
            return currentPrime;
        }
        for (int i = 2; i < currentPrime; i++) {
            if ( currentPrime % i == 0 ) {
                currentPrime++;
                i = 2;
            } else {
                continue;
            }
        }
        return currentPrime;
    }

    public long findLcm(long cId, long rID) {
        long sendProcess = m.storeTimestamps.get(cId);
        long recieveProcess = m.storeTimestamps.get(rID);
       // long lcm = (sendProcess > recieveProcess) ? sendProcess : recieveProcess;
        long x = sendProcess;
        for (int y = 0;; x += sendProcess) {
            while (y < x) {
                y += recieveProcess;
            }
            if (x == y)
                break;
        }
        return x *  m.storeInitialValues.get(rID);
        /*long gcd =1;
        for(int i = 1; i <= sendProcess && i <= recieveProcess; ++i)
        {
            // Checks if i is factor of both integers
            if(sendProcess % i == 0 && recieveProcess % i == 0)
                gcd = i;
        }
        long lcm = (sendProcess * recieveProcess) / gcd;
        long totalTimestamp = lcm * m.storeInitialValues.get(rID);
        return totalTimestamp;*/
    }

    public static boolean isItALocalTick() {
        Random rand = new Random();
        int randomNo1 = rand.nextInt(100);
        if ( randomNo1 / 2 == 0 )
            return true;
        else
            return false;
    }



    public static Map<Long, Long> getSharedPrimeNoMap() {
        return sharedPrimeNoMap;
    }



    @Override
    public void run() {
        System.out.printf("%s: Started Thread\n", Thread.currentThread().getName());


            simulate();

        System.out.printf("%s: The document has been printed \n", Thread.currentThread().getName());
    }

    public void simulate() {
        m = new Main();
        m.storeInitialValues = new ConcurrentHashMap<>();
        m.storeTimestamps = new ConcurrentHashMap<>();
        // long prevPrimeNoOfCurrThread = 0;
        //mistake should pick from prev
        // long primeNoOfThread = generateNextPrime();
        // System.out.println(getSharedPrimeNoMap());
        long primeNo = Processor.generateNextPrime();

        System.out.println(Thread.currentThread().getId() + " " + primeNo);
        m.storeInitialValues.put(Thread.currentThread().getId(), primeNo);
        m.storeTimestamps.put(Thread.currentThread().getId(), primeNo);
        System.out.println("Text   ");
        while (!stop) {
            synchronized(lock) {
                try {

                    //current timestamp for duration Math.random remove
                    //int duration = (int) (Math.random() * 1000);
                    long currProcess = Thread.currentThread().getId();
               //     semaphore.acquire();
                    // -- check this to choose a new process id randomly
                    long recieveProcessId;


                    Map<Long, Long> currentSharedMap = getSharedPrimeNoMap();

                    if ( isItALocalTick() ) {

                        System.out.println("This is Local Tick");
                  /*  if ( Main.storeTimestamps.get(currProcess) == null ) {
                        Main.storeTimestamps.put(currProcess, Main.storeInitialValues.get(currProcess));
                      //  Utils.setSharedPrimeNoMap(currentSharedMap);
                      //  Utils.setPrevPrime(primeNoOfThread);
                        System.out.println(
                                "Prime Number Associated with Process " + currProcess + " is: " + Main.storeTimestamps.get(currProcess));
                    } else { */
                        //   prevPrimeNoOfCurrThread = currentSharedMap.get(currProcessId);
                        m.storeTimestamps.put(currProcess, m.storeTimestamps.get(currProcess) * m.storeInitialValues.get(currProcess));
                        //   Utils.setSharedPrimeNoMap(currentSharedMap);
                        //   Utils.setPrevPrime(currentSharedMap.get(currProcessId));
                        System.out.println("Local: Prime number associated with Process : " + currProcess + " is: " + m.storeTimestamps.get(currProcess));
                        //  }
                    } else {
                        System.out.println("This is Shared Tick");
                        //change
                  /*  if ( Main.storeTimestamps.get(currProcess) == null) {
                        Main.storeTimestamps.put(currProcess, Main.storeInitialValues.get(currProcess));
                     //   Utils.setSharedPrimeNoMap(currentSharedMap);
                        System.out.println("Error");
                        //change to duration/timestamp associated
                        System.out.println("Prime number associated with Process : " + currProcessId + " is: "
                                + currentSharedMap.get(currProcessId)); // random no.
                    } else { */

                        recieveProcessId = pickupRandomProcess(currProcess);
                          long lcm = findLcm(currProcess, recieveProcessId);

                         // Thread.sleep(10000);
                          m.storeTimestamps.put(recieveProcessId, lcm);
                        //      Utils.setSharedPrimeNoMap(currentSharedMap);
                        System.out.println("Shared: Prime number associated with Process Send: " + currProcess + "Recieve " + recieveProcessId + " is: "
                                + m.storeTimestamps.get(recieveProcessId));
                    /*	if(duration==437634653)
                            setStop(true);*/
                    }

                    // duration and thread id
                    System.out.printf("Resumed %s: ", Thread.currentThread().getId());

                    System.out.printf("Going to Run in Background %s\n", Thread.currentThread().getId());

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                //    semaphore.release();
                }
            }
        }
    }

    // when 32 bit reached by timesatmp
    public void setStop(boolean stop) {

        this.stop = stop;
    }

    /**
     * To check if integer has crossed its limit.
     *
     * @param val
     * @return
     */

    public long pickupRandomProcess(long currProcess) {
        long nextProcessId = 0;
        boolean checkProcessId = false;
        List<Map.Entry<Long, Long>> entries = new ArrayList<Map.Entry<Long, Long>>(m.storeTimestamps.entrySet());
        while (!checkProcessId) {
            Collections.shuffle(entries);
            nextProcessId = entries.get(0).getKey();
            if ( nextProcessId != currProcess ) {
                checkProcessId = true;
            }
        }

        return nextProcessId;

    }
}
