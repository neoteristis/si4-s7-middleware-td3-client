package client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
    public String getStudentID() throws RemoteException;
    public void setStudentID(String studentID) throws RemoteException;
}
