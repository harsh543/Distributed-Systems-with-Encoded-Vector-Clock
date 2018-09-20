# Distributed-Systems-with-Encoded-Vector-Clock
Implementation of a research paper : https://www.cs.uic.edu/~ajayk/ext/icdcn18_paper19.pdf


It's a paper by a Professor at my college.In his class he asked to do the implementation :(






Vector clock is a fundamental tool to define causality in a distributed systems.In this project we implement  the Encoded vector clock which assigns each process with a prime number and then updates them with the rules, depending on whether the event that occurred is internal event or communication event.With these we perform the operations on global states of EVC.


Hence, in this we observed that the computation was time consuming.It also introduced errors due to precision not only because it involved floating values as we used log and antilogs but we also performed round off as we restricted mantissa(m) bit and log base (b).The log  base ‘b’ does’nt seem to have much effect on false positive and false negative as we perform it for several m and n.Therefore, we can conclude that Error rate decreases as we increase processes.So it’s a good sign that the EVC is efficient.Efficient not only terms of space but also in terms of time complexity of operations in computing timestamps.However, the EVC overflow very fast so we cannot compute error rate very precisely.The ratio of shared and local events were found to be 1:1 approximately on an average over the mantissa bits.The local events were less than shared events.Results plotted and noted above.

