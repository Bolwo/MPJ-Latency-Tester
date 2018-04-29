*MPJ program to test the latency between workers and master*

To run, ensure you have MPJ installed and setup, and then run:
 - mpjrun -np *numberOfWorkers* MPJPing *PayloadSize* *numberOfTestsPerWorker*

For example;
 - mpjrun -np 10 MPJPing 10000 10

Payload size is 32 bits (max integer value) * specified number. (If anyone wants to improve this bad method of changing the payload size please do) For example a value of 100000 is 400KB.