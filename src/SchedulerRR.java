import java.util.LinkedList;
import java.util.Queue;

public class SchedulerRR extends Scheduler {
    private Queue<Process> queue;
    private Platform platform;
    private int timeQuantum;
    private int timeSliceRemaining;

    public SchedulerRR(Platform platform, int timeQuantum) {
        this.platform = platform;
        this.timeQuantum = timeQuantum;
        this.queue = new LinkedList<>();
        this.timeSliceRemaining = timeQuantum;
    }

    @Override
    public void notifyNewProcess(Process process) {
        queue.add(process);
        platform.log("Added process with ID: " + process.getName());
    }

    @Override
    public Process update(Process currentProcess, int cpu) {
        // If there is no current process or the current process has finished
        if (currentProcess == null || currentProcess.getBurstTime() == 0) {
            if (currentProcess != null && currentProcess.getBurstTime() == 0) {
                platform.log("Process " + currentProcess.getName() + " has completed.");
            }
            contextSwitches++;
            timeSliceRemaining = timeQuantum; // Reset the time slice for the next process
            return queue.poll();              // Get the next process in the queue
        }

        timeSliceRemaining--;

        // Check if the time slice has expired
        if (timeSliceRemaining == 0) {
            platform.log("Time slice expired for process " + currentProcess.getName());
            contextSwitches++;
            timeSliceRemaining = timeQuantum; // Reset the time slice for the next process

            // Re-queue the current process if it hasn't finished
            if (currentProcess.getBurstTime() > 0) {
                queue.add(currentProcess);
            }

            return queue.poll(); // Get the next process in the queue
        }

        // Continue with the current process if it hasn't completed and the time slice remains
        return currentProcess;
    }
}