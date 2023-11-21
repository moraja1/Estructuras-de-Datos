package chatServer;

import chatProtocol.*;

import java.io.IOException;

public class Worker {
    Server srv;
    ObjectSocket os;
    IService service;
    User user;

    public Worker(Server srv, ObjectSocket os, User user, IService service) {
        this.srv=srv;
        this.os=os;
        this.user=user;
        this.service=service;
    }

    boolean continuar;    
    public void start(){
        try {
            System.out.println("Worker atendiendo peticiones...");
            Thread t = new Thread(new Runnable(){
                public void run(){
                    listen();
                }
            });
            continuar = true;
            t.start();
        } catch (Exception ex) {  
        }
    }
    
    public void stop(){
        continuar=false;
        System.out.println("Conexion cerrada...");
    }
    
    public void listen(){
        int method;
        while (continuar) {
            try {
                method = os.in.readInt();
                System.out.println("Operacion: "+method);
                switch(method){
                //case Protocol.LOGIN: done on accept
                case Protocol.LOGOUT:
                    try {
                        srv.remove(user);
                        //service.logout(user); //nothing to do
                    } catch (Exception ex) {}
                    stop();
                    break;                 
                case Protocol.POST:
                    Message message=null;
                    try {
                        message = (Message)os.in.readObject();
                        message.setSender(user);
                        srv.deliver(message);
                        //service.post(message); // if wants to save messages, ex. receiver no logged in
                        System.out.println(user.getNombre()+": "+message.getMessage());
                    } catch (ClassNotFoundException ex) {}
                    break;                     
                }
            } catch (IOException  ex) {
                System.out.println(ex);
                continuar = false;
            }                        
        }
    }
    
    public void deliver(Message message){
        try {
            os.out.writeInt(Protocol.DELIVER);
            os.out.writeObject(message);
            os.out.flush();
        } catch (IOException ex) {
        }
    }
}
