
package chatServer;

import chatProtocol.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Server {
    ServerSocket srv;
    List<Worker> workers;

    public Server() {
        try {
            srv = new ServerSocket(Protocol.PORT);
            workers = Collections.synchronizedList(new ArrayList<Worker>());
            System.out.println("Servidor iniciado...");
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public void run() {
        IService service = new Service();

        boolean continuar = true;
        ObjectSocket os = null;
        Socket skt = null;
        while (continuar) {
            try {
                skt = srv.accept();
                os = new ObjectSocket(skt);
                System.out.println("Conexion Establecida...");
                User user = this.login(os, service);
                Worker worker = new Worker(this, os, user, service);
                workers.add(worker);
                System.out.println("Quedan: " + workers.size());
                worker.start();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    private User login(ObjectSocket os, IService service) throws Exception {
        int method = os.in.readInt();
        if (method != Protocol.LOGIN) throw new Exception("Should log in first");
        User user = (User) os.in.readObject();
        try {
            user = service.login(user);
            os.out.writeInt(Protocol.ERROR_NO_ERROR);
            os.out.writeObject(user);
            os.out.flush();
            return user;
        } catch (Exception e) {
            os.out.writeInt(Protocol.ERROR_ERROR);
            os.out.flush();
            throw new Exception("User does not exist");
        }
    }

    public void deliver(Message message) {
        for (Worker wk : workers) {
            wk.deliver(message);
        }
    }

    public void remove(User u) {
        for (Worker wk : workers)
            if (wk.user.equals(u)) {
                workers.remove(wk);
                break;
            }
        System.out.println("Quedan: " + workers.size());
    }

}