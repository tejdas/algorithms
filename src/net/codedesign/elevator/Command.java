package net.codedesign.elevator;

public abstract class Command {
    int commandId;
    long submittedTime;

    public Command(int commandId, long submittedTime) {
        this.commandId = commandId;
        this.submittedTime = submittedTime;
    }
}
