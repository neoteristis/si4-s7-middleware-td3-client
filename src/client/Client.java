package client;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Client extends UnicastRemoteObject implements ClientInterface {

    public Client() throws RemoteException {
        super();
    }

    public Client(int port) throws RemoteException {
        super(port);
    }

    @Override
    public void voter(Vote vote) throws RemoteException {
        System.out.println(vote);
    }
}
