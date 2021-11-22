package net.codedesign.elevator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

// Elevators constantly send their current state
// floor, direction.
// ElevatorManager decides which elevator to choose depending on the elevator state
public class Elevator implements Runnable {

    private final AtomicBoolean doShutdown = new AtomicBoolean(false);
    private int currentFloor;
    private ElevatorDirection direction;
    private ElevatorState elevatorState;
    private int numPassengers = 0;

    public void serveExternalRequest(ExternalRequest request) {

    }

    public void serveInternalRequest(InternalRequest request) {

    }

    public void shutdown() {
        doShutdown.set(true);
    }

    private final ConcurrentMap<Integer, InternalRequest> internalRequests = new ConcurrentHashMap<>();

    @Override
    public void run() {
        while (!doShutdown.get()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            if (needsToStop()) {
                openDoor();
                processExit();
                //processEntry();
                closeDoor();
            }

            move();
        }
    }

    private boolean needsToStop() {
        return true;
    }

    private void openDoor() {
        elevatorState = ElevatorState.STOPPED;
    }

    private void processExit() {
        Set<Integer> numRequestsToProcess = new HashSet<>();
        for (InternalRequest req : internalRequests.values()) {
            if (req.getFloorId() == currentFloor) {
                numRequestsToProcess.add(req.commandId);
            }
        }

        for (int requestId : numRequestsToProcess) {
            internalRequests.remove(requestId);
        }
        numPassengers -= numRequestsToProcess.size();
    }

    private void processEntry(List<InternalRequest> requests) {

        if (elevatorState == ElevatorState.IDLE) {
            elevatorState = ElevatorState.MOVING;
        }

        for (InternalRequest request : requests) {
            if (request.getFloorId() > currentFloor && direction == ElevatorDirection.DOWN) {
                // reject
            }
        }
    }

    private void closeDoor() {
        elevatorState = (isEmpty())? ElevatorState.IDLE : ElevatorState.MOVING;
    }

    private void move() {
        if (isEmpty()) {
            elevatorState = ElevatorState.IDLE;
        } else {

        }
    }

    private boolean isEmpty() {
        return (numPassengers == 0);
    }
}
