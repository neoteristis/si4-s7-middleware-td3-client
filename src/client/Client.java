package client;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Client extends UnicastRemoteObject implements ClientInterface {
    private Scanner scanner;
    private String studentID;

    public Client() throws RemoteException {
        super();
        this.scanner = new Scanner(System.in);
        this.studentID = null;
    }

    public Client(int port) throws RemoteException {
        super(port);
    }

    @Override
    public String getStudentID() {
        return studentID;
    }

    @Override
    public void setStudentID(String studentID) throws RemoteException {
        this.studentID = studentID;
    }
}
