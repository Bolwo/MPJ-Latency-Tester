
import java.io.PrintWriter;
import java.util.concurrent.ThreadLocalRandom;

import mpi.*;

public class MPJPing {

    public static void main(String args[]) throws Exception {
        PrintWriter writer = new PrintWriter("N:\\output.txt", "UTF-8");
        MPI.Init(args);
        System.out.println("Initialising");

        int me = MPI.COMM_WORLD.Rank();
        int P = MPI.COMM_WORLD.Size();

        long sendTime = 0;
        long recieveTime = 0;
        int payloadSize = Integer.parseInt(args[3]);
        int totalTests = Integer.parseInt(args[4]);
        System.out.println("Payload size: " + payloadSize);
        System.out.println("Tests per worker: " + totalTests);
        System.out.println(me + " Initialised");

        //int count = 0;
        //while (count < totalTests) {
            //count++;

            //Send back to master/recieve by master
            if (me == 0) {
                System.out.println("sending to all workers");
                long[] sendBuf = new long[payloadSize];
                for (int i = 1; i < payloadSize; i++) {
                    System.out.println(i);
                    sendBuf[i] = Integer.MAX_VALUE;
                }
                for(int i = 1; i<P; i++){
                    sendBuf[0] = System.nanoTime();
                    MPI.COMM_WORLD.Send(sendBuf, 0, payloadSize, MPI.LONG, i, 0); //fix this
                }
                System.out.println("sent to all workers");
            } else {
                System.out.println(me + " waiting to recieve");
                    long[] recvBuf = new long[payloadSize];
                    MPI.COMM_WORLD.Recv(recvBuf, 0, payloadSize, MPI.LONG, 0, 0);

                    recieveTime = System.nanoTime();
                    sendTime = recvBuf[0];
                    
                System.out.println(me + " RECIEVED");
                
                //send back now
            }

            if (me > 0) {
                    System.out.println(me + ": Send Time: " + sendTime
                            + " Recieved Time: " + recieveTime
                            + "Total Latency: " + (recieveTime - sendTime) + " Nanoseconds");

                    writer.println(Long.toString((recieveTime - sendTime)));
            }
        //} 

        MPI.Finalize();
        writer.close();
    }
}
