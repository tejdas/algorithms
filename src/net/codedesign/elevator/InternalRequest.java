package net.codedesign.elevator;

public class InternalRequest extends Command {
    private final int floorId;

    public InternalRequest(int commandId, int floorId) {
        super(commandId, System.currentTimeMillis());
        this.floorId = floorId;
    }

    public int getFloorId() {
        return floorId;
    }
}
