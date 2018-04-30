*MPJ program to test the latency between workers and master*

To run, ensure you have MPJ installed and setup, and then run (for multicore mode):
 - mpjrun -np *numberOfWorkers* MPJPing *PayloadSize* *numberOfTestsPerWorker*

For cluster mode (with multiple pc's/servers):
 - mpjrun -np 5 -dev niodev MPJPing 10 10

For example;
 - mpjrun -np 10 MPJPing 10000 10

Payload size is 32 bits (max integer value) * specified number. (If anyone wants to improve this bad method of changing the payload size please do) For example a value of 100000 is 400KB.