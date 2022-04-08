package lab.common.commands;

import java.io.Serializable;

public class CommandResult implements Serializable {
    private String message;
    private Boolean result;

    public CommandResult(String message, Boolean result) {
        this.setMessage(message);
        this.setResult(result);
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
