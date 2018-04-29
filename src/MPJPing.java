
import java.io.PrintWriter;

import mpi.*;

public class MPJPing {

    public static void main(String args[]) throws Exception {
        PrintWriter writer = new PrintWriter("N:\\output.txt", "UTF-8");
        MPI.Init(args);
        System.out.println("Initialising");

        int me = MPI.COMM_WORLD.Rank();
        int P = MPI.COMM_WORLD.Size();

        long[] sendTimes = new long[P];
        long[] recieveTimes = new long[P];
        int payloadSize = Integer.parseInt(args[3]);
        int totalTests = Integer.parseInt(args[4]);
        System.out.println("Payload size: " + payloadSize);
        System.out.println("Tests per worker: " + totalTests);
        System.out.println(me + " Initialised");

        int count = 0;
        while (count < totalTests) {
            count++;

            //Send back to master/recieve by master
            if (me > 0) {

                System.out.println(me + " Sending back to master");
                long[] sendBuf = new long[payloadSize];
                for (int i = 1; i < payloadSize; i++) {
                    sendBuf[i] = Integer.MAX_VALUE;
                }
                sendBuf[0] = System.nanoTime();
                MPI.COMM_WORLD.Send(sendBuf, 0, payloadSize, MPI.LONG, 0, 0);

            } else {
                for (int i = 1; i < P; i++) {
                    long[] recvBuf = new long[payloadSize];
                    MPI.COMM_WORLD.Recv(recvBuf, 0, payloadSize, MPI.LONG, i, 0);
                    sendTimes[i] = recvBuf[0];
                    recieveTimes[i] = System.nanoTime();
                }
                System.out.println("Recieved all by master");
            }

            if (me == 0) {
                for (int i = 1; i < P; i++) {
                    System.out.println(i + ": Send Time: " + sendTimes[i]
                            + " Recieved Time: " + recieveTimes[i]
                            + "Total Latency: " + (recieveTimes[i] - sendTimes[i]) + " Nanoseconds");

                    writer.println(Long.toString((recieveTimes[i] - sendTimes[i])));

                }
            }
        }

        MPI.Finalize();
        writer.close();
    }
}
