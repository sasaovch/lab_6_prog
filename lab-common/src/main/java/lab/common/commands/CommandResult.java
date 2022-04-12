package lab.common.commands;

import java.io.Serializable;

public class CommandResult implements Serializable {
    private Serializable data;
    private Boolean result;

    public CommandResult(Serializable data, Boolean result) {
        this.setResult(result);
        this.setData(data);
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public Serializable getData() {
        return data;
    }

    public void setData(Serializable data) {
        this.data = data;
    }
}
