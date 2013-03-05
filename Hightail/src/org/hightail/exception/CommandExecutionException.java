package org.hightail.exception;

public class CommandExecutionException extends Exception {

    protected String command;

    /**
     * Get the value of command
     *
     * @return the value of command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Constructs an instance of <code>CommandExecutionException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public CommandExecutionException(String msg, String command) {
        super(msg);
        this.command = command;
    }
}
