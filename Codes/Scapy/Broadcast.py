#!/usr/bin/python
import time
from scapy.all import *

sleepingTime = 4.0
while (1) :
	time.sleep(sleepingTime)
	bcastPckt = Ether()/IP(dst=Net("google.com/30"))/ICMP() 
	#bcastPckt = Ether(src="00:00:00:00:00:00",dst="ff:ff:ff:ff:ff:ff")/IP(src="0.0.0.0",dst="255.255.255.255")/UDP()/("Qasem Soleimani's PC "*10+" The IP is: 10.0.0.10"+"Qasem Soleimani's PC "*10))
	send(bcastPckt)
	if sleepingTime>0.3:
		sleepingTime=sleepingTime-0.01
	print("Sleeping time: %f" %sleepingTime)
