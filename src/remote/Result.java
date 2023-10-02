package remote;

import java.io.Serializable;

public class Result implements Serializable {

    private String infoCB;
    public Result(String infoCB) {
        this.infoCB = infoCB;
    }

    @Override
    public String toString() {
        return "infoCB : "+this.infoCB;
    }

    public String getInfoCB() {
        return infoCB;
    }

    public void setInfoCB(String infoCB) {
        this.infoCB = infoCB;
    }
}
