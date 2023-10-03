package remote;

import client.ClientInterface;
import client.Vote;
import exceptions.HasAlreadyVotedException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Service extends Remote {

    List<String> getCandidates() throws RemoteException;

    String getResults() throws RemoteException;

    void sendVotes(List<Vote> votes, ClientInterface client) throws RemoteException;

    String getUserOTP(String studentID) throws RemoteException, HasAlreadyVotedException;

    String updateUserOTP(String studentID, String oldOTP) throws RemoteException;

    boolean authenticate(String studentID, String otp) throws RemoteException;

    boolean isStudentIDValid(String studentID) throws RemoteException;

    boolean authenticateAccount(String studentID, String password) throws RemoteException;
}
