/**
 * This interface defines the behaviors required of any scheduler.
 */
public abstract class Scheduler {
    protected int contextSwitches = 0; // Keep track of the number of context switches

    /**
     * A scheduler must track the number of context switches performed during the simulation.
     * This method returns that count.
     * @return The number of context switches that occurred during the simulation
     */
    public int getNumberOfContextSwitches() { return this.contextSwitches; }

    /**
     * Used to notify the scheduler a new process has just entered the ready state.
     */
    abstract void notifyNewProcess(Process process);

    /**
     * Update the scheduling algorithm for a single CPU.
     * @param process The current process on the cpu
     * @param cpu The int representation of the cpu that is updating
     * @return Reference to the process that is executing on the CPU; result might be null
     *         if no process available for scheduling.
     */
    abstract Process update(Process process, int cpu);
}
