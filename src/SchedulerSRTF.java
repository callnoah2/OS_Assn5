import java.util.PriorityQueue;
import java.util.Comparator;

public class SchedulerSRTF extends Scheduler {
    private PriorityQueue<Process> queue;

    public SchedulerSRTF(Platform platform) {
        // Initialize the priority queue with a custom comparator for shortest remaining time
        queue = new PriorityQueue<>(new Comparator<Process>() {
            @Override
            public int compare(Process p1, Process p2) {
                if (p1.getBurstTime() < p2.getBurstTime()) {
                    return -1;
                } else if (p1.getBurstTime() > p2.getBurstTime()) {
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
    }

    @Override
    public Process update(Process currentProcess, int cpu) {
        // If no current process, get the next process in the queue
        if (currentProcess == null || currentProcess.getBurstTime() == 0) {
            contextSwitches++;
            return queue.poll();
        }

        // Check if the queue has a shorter process than the current one
        Process nextProcess = queue.peek();
        if (nextProcess != null && nextProcess.getBurstTime() < currentProcess.getBurstTime()) {
            contextSwitches++;
            queue.add(currentProcess); // Re-add the current process to the queue
            return queue.poll();       // Schedule the process with the shortest remaining time
        }

        return currentProcess; // Continue with the current process if no shorter process exists
    }
}