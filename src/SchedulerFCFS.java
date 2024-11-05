import java.util.LinkedList;
import java.util.Queue;

public class SchedulerFCFS extends Scheduler {
    private Queue<Process> readyQueue = new LinkedList<>();

    @Override
    public void notifyNewProcess(Process process) {
        readyQueue.add(process);
    }

    @Override
    public Process update(Process currentProcess, int cpu) {
        // Check if there's a process to run
        if (currentProcess == null) {
            // Get the next process from the queue
            currentProcess = readyQueue.poll();
            if (currentProcess != null) {
                // Increment context switch count
                contextSwitches++;
            }
        }

        // Update current process if not finished
        if (currentProcess != null) {
            currentProcess.update();
            if (currentProcess.isBurstComplete()) {
                // If the burst is complete, remove the process
                currentProcess = null;
            }
        }

        return currentProcess;
    }
}
