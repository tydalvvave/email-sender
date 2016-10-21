# email-sender  
### Cornell College
### CSC 317: Computer Networks
#### Created October 2016

***

##### EmailSender
EmailSender is a Java program that runs from a console window. The program does not require any input. It simply sends me an e-mail with a pre-determined message using a Java socket connection. This connection is based upon a local mail server, using one of the mail exchanger addresses as a parameter. 

There is also a version of the program with an implemented GUI that can be run by an IDE where the user can input the local mail server, the source and destination e-mail addresses, the subject, and the message.

##### Find your local mail server:
1. Open a terminal window
2. Enter the following command: **nslookup -type=mx (yourWebsiteName)**
3. Replace **(yourWebsiteName)** with the name of a website with an internal mail server
4. Find one of the mail exchanger addresses to use for the program

##### Source Code
The initial code for the EmailSender program was provided by Professor Leon Tabak on a local system.

The initial code for the EmailSender_GUI program was created by Jussi Kangasharju via the link below.
http://www.csc.villanova.edu/~schragge/CSC8560/mailagentprogram.html

***

##### Contributors
This program has 3 contributors:
* Tyler Wians
* Jeremy Novak
* Kyle Drum
