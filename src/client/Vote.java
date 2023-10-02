package client;

import java.io.Serializable;

public class Vote implements Serializable {
    int rank;
    int value;

    public Vote(int rank, int value) {
        this.rank = rank;
        this.value = value;
    }

    @Override
    public String toString() {
        return "( "+this.rank+", "+this.value+" )";
    }
}
