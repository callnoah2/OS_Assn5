import java.util.PriorityQueue;
import java.util.Comparator;

public class SchedulerPriority extends Scheduler {
    private PriorityQueue<Process> queue;
    private Platform platform;

    public SchedulerPriority(Platform platform) {
        this.platform = platform;
        // Initialize the priority queue with a custom comparator for process priority
        queue = new PriorityQueue<>(new Comparator<Process>() {
            @Override
            public int compare(Process p1, Process p2) {
                if (p1.getPriority() < p2.getPriority()) {
                    return -1;
                } else if (p1.getPriority() > p2.getPriority()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
    }

    @Override
    public void notifyNewProcess(Process process) {
        queue.add(process);
        platform.log("Added process with priority: " + process.getPriority());
    }

    @Override
    public Process update(Process currentProcess, int cpu) {
        // If there's no current process, or it has completed, switch to the next process
        if (currentProcess == null || currentProcess.getBurstTime() == 0) {
            if (currentProcess != null && currentProcess.getBurstTime() == 0) {
                platform.log("Process " + currentProcess.getName() + " has completed.");
            }
            contextSwitches++;
            return queue.poll();
        }

        // Check if there's a higher-priority process in the queue
        Process nextProcess = queue.peek();
        if (nextProcess != null && nextProcess.getPriority() < currentProcess.getPriority()) {
            platform.log("Preempting process " + currentProcess.getName() +
                    " with priority " + currentProcess.getPriority() +
                    " for process " + nextProcess.getName() +
                    " with priority " + nextProcess.getName());
            contextSwitches++;
            queue.add(currentProcess); 
            return queue.poll();
        }

        // Continue with the current process if no higher-priority process is ready
        return currentProcess;
    }
}