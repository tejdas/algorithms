package net.codedesign.elevator;

public class EmergencyRequest extends Command {
    int emergencyCode;

    public EmergencyRequest(int commandId, int emergencyCode) {
        super(commandId, System.currentTimeMillis());
        this.emergencyCode = emergencyCode;
    }
}
