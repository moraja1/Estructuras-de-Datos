package chatProtocol;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ObjectSocket {
    public Socket skt;
    public ObjectInputStream in;
    public ObjectOutputStream out;
    public String sid; // Session Id

    public ObjectSocket(Socket skt) {
        this.skt = skt;
        this.sid="";
        try {
            this.out = new ObjectOutputStream(skt.getOutputStream() );
            this.in =new ObjectInputStream(skt.getInputStream());
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
