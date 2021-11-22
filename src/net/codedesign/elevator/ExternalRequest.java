package net.codedesign.elevator;

public class ExternalRequest extends Command {
    private final int floorId;
    private final ElevatorDirection direction;

    public ExternalRequest(int commandId, int floorId, ElevatorDirection direction) {
        super(commandId, System.currentTimeMillis());
        this.floorId = floorId;
        this.direction = direction;
    }

    public int getFloorId() {
        return floorId;
    }

    public ElevatorDirection getDirection() {
        return direction;
    }
}
