package client;

import java.io.Serializable;

public class Vote implements Serializable {
    int rank;
    int value;

    public Vote(int rank, int value) {
        this.rank = rank;
        this.value = value;
    }

    public int getRank() {
        return rank;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "( "+this.rank+", "+this.value+" )";
    }

}
