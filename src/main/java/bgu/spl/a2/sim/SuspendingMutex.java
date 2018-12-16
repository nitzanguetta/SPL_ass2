package bgu.spl.a2.sim;

import bgu.spl.a2.Promise;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * this class is related to {@link Computer}
 * it indicates if a computer is free or not
 * <p>
 * Note: this class can be implemented without any synchronization.
 * However, using synchronization will be accepted as long as the implementation is blocking free.
 */
public class SuspendingMutex {

    private Computer computer;
    private volatile AtomicBoolean computerLocked = new AtomicBoolean(false);
    private ConcurrentLinkedQueue<Promise> promisesToBeSolved = new ConcurrentLinkedQueue<>();

    /**
     * Constructor
     *
     * @param computer
     */
    public SuspendingMutex(Computer computer) {
        this.computer = computer;
        computer.setSuspendingMutex(this);
    }

    /**
     * Computer acquisition procedure
     * Note that this procedure is non-blocking and should return immediatly
     *
     * @return a promise for the requested computer
     */
    public Promise<Computer> down() {
        Promise<Computer> promise = new Promise<>();
        if (!computerLocked.compareAndSet(false, true)) { //if wasn't free - could not locked it ->want to add promise
            promisesToBeSolved.add(promise);
            return promise;
        } else {
            promise.resolve(computer);
            return promise; //locked the Mutex successfully
        }
    }

    /**
     * Computer return procedure
     * releases a computer which becomes available in the warehouse upon completion
     */
    public void up() { //want to resolve the promise
        if (!promisesToBeSolved.isEmpty()) { // if i got a promise - resolves it
            promisesToBeSolved.remove().resolve(computer);
        } else {
            computerLocked.compareAndSet(false, true);
        }
    }

    /**
     * @return computer field
     */
    public Computer getComputer() {
        return computer;
    }
}
