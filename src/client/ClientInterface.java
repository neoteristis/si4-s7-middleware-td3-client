package client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
    public void voter(Vote vote) throws RemoteException;
    public void setStudentID(String studentID) throws RemoteException;
}
