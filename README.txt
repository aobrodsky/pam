To run Pam on the unix server:

0. Be sure your CS id is activated.  If it is not, you can go to the
   CS Help Desk or look here: 

     https://csid.cs.dal.ca

1. Log into the Faculty unix server.  
     bluenose.cs.dal.ca
   OR
     csci2110.cs.dal.ca (the CSCI 2110 Unix VM)

   You will need to use an ssh client such as ssh (Mac)  or 
   Putty (Windows).

   Note: You will need your CS id and password to log into these servers.

   If you do not know how to do this, there are plenty of tutorials on
   the web on how to ssh into a unix server.

2. Create a csci2110 directory and an assn2 subdirectory on the unix server

      mkdir -p csci2110/assn2

3. Change directories into assn2

      cd csci2110/assn2

4. Copy (upload) the Pam.zip file to csci2110/assn2 on the Faculty unix 
   server.

   Again, if you do not know how to do this, there are plenty of tutorials 
   on the web on how to copy files to a unix server.

5. On the unix server, unzip the zip file.

     unzip Pam.zip

6. Run the script file to run the tests.

     ./run.sh | tee data.csv

7. Download data.csv file to your own computer and open it with Excel.

8. Copy and paste the data into the provided Results.xlsx.

Notes:
a.  If you are using a Mac, you have a unix system already.  You run 
    Terminal.app and do everything locally instead of using a remote
    unix server.

b.  You can run the tests inside your IDE, but this will take you a 
    very long time.
