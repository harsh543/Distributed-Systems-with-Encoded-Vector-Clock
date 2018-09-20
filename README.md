# Distributed-Systems-with-Encoded-Vector-Clock
Implementation of a research paper : https://www.cs.uic.edu/~ajayk/ext/icdcn18_paper19.pdf


It's a paper by a Professor at my college.In his class he asked to do the implementation :(






Vector clock is a fundamental tool to define causality in a distributed systems.In this project we implement  the Encoded vector clock which assigns each process with a prime number and then updates them with the rules, depending on whether the event that occurred is internal event or communication event.With these we perform the operations on global states of EVC.

For part 1:
Initialize the Processes with a unique prime number and store the mapping in Hashmap(as initial value of EVC).So that each process knows it’s own prime number.
Each of these processes were added to Queue for responding to events.
Simulator function is taken control of by a particular thread randomly based on dequeue from queue of threads.Then depending on randomly generated events:-
         i)For a internal event it multiplies it’s own prime number
        ii)For a communication event it becomes the sender and increments it’s own timestamp                                               
            and then randomly selects a process and that process becomes a receiver, takes         
           control of simulator function and then it performs it operation of performing LCM and
     4.  Then all computed values are stored in a Hashmap with the thread id and also keeping  
           track of event number.The computed values determine value of EVC till this event.
     5.   Finally, we perform a system wide when the EVC overflows checking for 32*n or 64*n.
For part 2:
Initialize the Processes with a unique prime number and store the mapping in Hashmap(as initial value of EVC).Here EVC value is in Big Decimal form.
Each of these processes were added to Queue for responding to events.
For total of v*n we have threads picking up simulator function randomly
Simulator function is taken control of by a particular thread randomly based on dequeue.Then depending on randomly generated events:-
          i) For a internal event this thread performs internal communication
          ii) For a external event a random process from queue is taken and made receiver
              Then it perform LCM,log and antilog as in  research paper
              (Encoded Vector Clock:Using Primes to Characterize Causality in Distributed Systems.     
                ICDCN’18)
       5.In Simulator function, the bits of EVC(mantissa) and logarithm base can be adjusted.
       6. Finally, we check if Error rate i.e. False Positive/False Negative increases above a                                         
           threshold depending on number of process we set a threshold   then we stop and 
           perform System.exit.(The error rate is computed based on all pair of events and I check        
          The causality of the events comparing with actual vector clock and logarithm EVC.From
           here we can get False positive or False negative).          

Hence, in this we observed that the computation was time consuming.It also introduced errors due to precision not only because it involved floating values as we used log and antilogs but we also performed round off as we restricted mantissa(m) bit and log base (b).The log  base ‘b’ does’nt seem to have much effect on false positive and false negative as we perform it for several m and n.Therefore, we can conclude that Error rate decreases as we increase processes.So it’s a good sign that the EVC is efficient.Efficient not only terms of space but also in terms of time complexity of operations in computing timestamps.However, the EVC overflow very fast so we cannot compute error rate very precisely.The ratio of shared and local events were found to be 1:1 approximately on an average over the mantissa bits.The local events were less than shared events.Results plotted and noted above.

