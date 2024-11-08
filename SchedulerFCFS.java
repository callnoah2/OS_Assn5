import java.util.LinkedList;
import java.util.Queue;

public class SchedulerFCFS extends Scheduler {
    private Queue<Process> readyQueue = new LinkedList<>();
    private Platform platform;

    public SchedulerFCFS(Platform platform) {
        this.platform = platform;
    }

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
                platform.log("Context switch to process: " + currentProcess.getName());
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