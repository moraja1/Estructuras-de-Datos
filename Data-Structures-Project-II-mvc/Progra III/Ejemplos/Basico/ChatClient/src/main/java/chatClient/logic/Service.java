package chatClient.logic;

import chatClient.presentation.Controller;
import chatProtocol.*;

import java.io.IOException;
import java.net.Socket;
import javax.swing.SwingUtilities;

public class Service implements IService, IListener{  // PROXY
    private static Service theInstance;
    public static Service instance(){
        if (theInstance==null){ 
            theInstance=new Service();
        }
        return theInstance;
    }

    public static IListener instanceListener(){
        if (theInstance==null){
            theInstance=new Service();
        }
        return theInstance;
    }

    ObjectSocket os = null;
    ITarget target;

    public Service() {
    }

    private void connect() throws Exception{
        Socket skt;
        skt = new Socket(Protocol.SERVER,Protocol.PORT);
        os = new ObjectSocket(skt);
    }

    private void disconnect() throws Exception{
        os.skt.shutdownOutput();
        os.skt.close();
    }
    
    public User login(User u) throws Exception{
        connect();
        try {
            os.out.writeInt(Protocol.LOGIN);
            os.out.writeObject(u);
            os.out.flush();
            int response = os.in.readInt();
            if (response==Protocol.ERROR_NO_ERROR){
                User u1=(User) os.in.readObject();
                this.startListening();
                return u1;
            }
            else {
                disconnect();
                throw new Exception("No remote user");
            }            
        } catch (IOException | ClassNotFoundException ex) {
            return null;
        }
    }
    
    public void logout(User u) throws Exception{
        os.out.writeInt(Protocol.LOGOUT);
        os.out.writeObject(u);
        os.out.flush();
        this.stopListening();
        this.disconnect();
    }
    
    public void post(Message message){
        try {
            os.out.writeInt(Protocol.POST);
            os.out.writeObject(message);
            os.out.flush();
        } catch (IOException ex) {
            
        }   
    }  

    // LISTENING FUNCTIONS
   boolean continuar = true;    
   public void startListening(){
        Thread t = new Thread(new Runnable(){
            public void run(){
                listen();
            }
        });
        continuar = true;
        t.start();
    }

   public void stopListening(){
        continuar=false;
    }
    
   public void listen(){
        int method;
        while (continuar) {
            try {
                method = os.in.readInt();
                switch(method){
                case Protocol.DELIVER:
                    try {
                        Message message=(Message)os.in.readObject();
                        deliver(message);
                    } catch (ClassNotFoundException ex) {}
                    break;
                }
                os.out.flush();
            } catch (IOException  ex) {
                continuar = false;
            }                        
        }
    }
    
   private void deliver( final Message message ){
      SwingUtilities.invokeLater(new Runnable(){
            public void run(){
               target.deliver(message);
            }
         }
      );
   }

    @Override
    public void addTarget(ITarget target) {
        this.target=target;
    }
}
