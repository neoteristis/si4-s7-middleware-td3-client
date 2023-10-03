package exceptions;

import java.rmi.RemoteException;

public class HasAlreadyVotedException extends RemoteException {
    public HasAlreadyVotedException() {
        super("You have already voted!");
    }
}
