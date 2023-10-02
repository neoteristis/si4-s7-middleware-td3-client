package remote;

import client.Vote;

import java.io.Serializable;
import java.util.List;

public class Result implements Serializable {

    private List<Vote> clientVotes;
    public Result(List<Vote> clientVotes) {
        this.clientVotes = clientVotes;
    }

}
