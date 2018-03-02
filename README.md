# WirelessThroughputCalculator
802.11 frame exchange for TCP and UDP, using OFDM of 802.11a/g/n/ac

COMP40660 - Advances in Wireless Networking


I have built a java ThroughputCalculator which performs 802.11 frame exchange for TCP and UDP, using OFDM of 802.11a/g/n/ac. 
This model approximates the actual throughput of the model.

My program uses JOptionPane's for user input, which is then validated to ensure the input has been entered correctly. 


Weaknesses of the model:
Since my model works in a simulated environment, it  does not factor in the major variables that affect the performance of wireless signalling such as:
	Physical objects - which interfere with the signal path.
	Radio frequency interference - devices that share the 2.4GHz channel often cause interference with technologies such as 802.11g. 
	Environmental factors - Weather conditions such as lightening can cause major electrical interference with wireless signals. 

My model also does not account for any contention on the network. 


There is a major difference between the actual throughput and the advertised data rate because of the amount of overhead that needs to be transmitted along with the data frame to ensure integrity and security. The advertised data rate is the absolute maximum amount of data that can be sent over a channel. This is not achieved due to overhead, latency and interference. 

