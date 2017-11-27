/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DeaNiv
 */
public class Server extends Thread{
    
    public static final int MaxClient = 5; //DON'T KNOW HOW TO HANDLE MAXIMUM CLIENTS YET
    private static boolean keepAlive;
    private static ArrayList<ClientThreads> clientArray = new ArrayList<ClientThreads>();
    private static ArrayList<Thread> threadArray = new ArrayList<Thread>();
    private static String rdpClients[][];
    private static int countWinners;
    
    private ServerSocket serv;
    private int portNumber=26666;
  
    public void run() { 
        
    try {
        this.serv = new ServerSocket(portNumber);
        System.out.println("Connecting...");
        keepAlive = true;
        initializeRDPClients();
    } catch (IOException ex) {
        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        System.out.println("Couldn't connect to port:"+portNumber);
    }
    while (keepAlive)
        try{
            ClientThreads c = new ClientThreads(serv.accept());
            if (clientArray.size()>=MaxClient)
                //maximumClients();
                  System.out.println("Server has reached to maximum clients");
            else{
                System.out.println("New client has connected");
                Thread t = new Thread ((Runnable) c); 
                t.start();
                clientArray.add(c);
                threadArray.add(t);
        }
        }catch (IOException e)
        {
            System.out.println("Error. Server shutdown");
            System.exit(-1);
        }
    
    }
    
       public static void removeClient (int id)
       {
        int ans=-1;
        
           for (int i = 0; i < clientArray.size(); i++) {
               if (clientArray.get(i).getID()==id)
                   ans=i; 
           }
     
           if (ans!=-1)
           {
               System.out.println("Removing clinet. id: "+ans);
               clientArray.remove(ans);
               threadArray.remove(ans);
           }
       }

     public static ArrayList<ClientThreads> getArray(){
         return clientArray;
     }
     
     public static String getNextUser(int safeKey){
         if (safeKey == 8765344){
             return generateUser();
         }
         else{
             return "Don't try me you little zionist...";
         }
     }
     
     public static String getNextPass(int safeKey){
         if (safeKey == 8765344){
             return generatePass();
         }
         else{
             return "Don't try me you little zionist...";
         }
     }
     
     private static String generateUser(){
         return rdpClients[countWinners][0];
     }
     private static String generatePass(){
         String win = rdpClients[countWinners][1];
         countWinners++;
         return win;
     }
     
     private void initializeRDPClients(){
         countWinners = 0;
         rdpClients = new String[10][2];
         rdpClients[0][0] = "User1";
         rdpClients[0][1] = "Password1";
         rdpClients[1][0] = "User2";
         rdpClients[1][1] = "Password2";
         rdpClients[2][0] = "User3";
         rdpClients[2][1] = "Password23";
     }

        
        
    
}
    

 
  
  
    