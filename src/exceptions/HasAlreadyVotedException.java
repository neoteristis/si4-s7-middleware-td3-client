package exceptions;

public class HasAlreadyVotedException extends Throwable {
    public HasAlreadyVotedException() {
        super("You have already voted!");
    }
}
