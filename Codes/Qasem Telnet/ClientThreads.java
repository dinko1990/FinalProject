/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author dinko190
 */
public class ClientThreads implements Runnable{
    
    
    private static int newID = 1571;

    private int coutLimit;
    private int id;
    private Socket soc;
    private PrintWriter pw;
    private BufferedReader br;
    private boolean keepAlive = true;

    
    public ClientThreads(Socket s)
            
    {
        this.soc = s;
        this.id = createID();
        this.coutLimit = 0;
        try {
            pw  =  new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())), true) ;
            br = new BufferedReader(new InputStreamReader(soc.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(ClientThreads.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
    
    public final void sendHello()
    {
           pw.println("Hello Qasem S. Please enter indentification code");
           pw.println("DD.MM.YY");
           System.out.println("Finished writing hello message");
    }


    private void handleMsg(String msg){
        System.out.println("Handling message: "+msg+" Thread: "+this.id);
        if (msg.equals("11.03.57")){
            String userName = Server.getNextUser(8765344);
            String password = Server.getNextPass(8765344);
            pw.println("\nYour RDP Details are:");
            pw.println("Username: "+userName);
            pw.println("Password: "+password);
            ClientDisconnect();
        }
        else{
            pw.println("Wrong code. Please try again.");
        }
        
    }
    @Override
    public void run() {
          sendHello();
          while (keepAlive){
            System.out.println("Waiting for input. Thread: "+this.id);
            if (coutLimit>=3){
                limitPassed();
                break;
            }
             try {
                System.out.println("Trying to read string... Thread: "+this.id);
                String msg = br.readLine();
                if (br!=null){
                     System.out.println("Finished readLine(). message: "+msg);
                     coutLimit++;
                     handleMsg(msg);
                }
            }
             catch (IOException ex) {
                System.out.println("IOException. Client: "+this.id);
                keepAlive = false;
                ClientDisconnect();
            }
        }
    }
    
    
     private int createID(){
         return ++newID;
     }
     
     public int getID(){
         return this.id;
     }
    
     
     private void ClientDisconnect(){
        try {
            Server.removeClient(id);      
            pw.close();
            br.close();
            soc.close();
            keepAlive = false;
        } catch (IOException ex) {
            Logger.getLogger(ClientThreads.class.getName()).log(Level.SEVERE, null, ex);
        }
         
     }
     
     private void limitPassed(){
          pw.println("\nToo many tries. Please try again later.");
          System.out.println("Too many tries.");
          ClientDisconnect();
     }
     
     
     
    
    
    
    
    
    
    
}
