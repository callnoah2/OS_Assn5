import java.util.PriorityQueue;

public class SchedulerSJF implements Scheduler {
    private Platform platform;
    private int numberOfContextSwitches = 0;
    private PriorityQueue<Process> readyQueue;

    public SchedulerSJF(Platform platform) {
        this.platform = platform;
        // A priority queue that orders processes by burst time (ascending)
        this.readyQueue = new PriorityQueue<>((p1, p2) -> Integer.compare(p1.getBurstTime(), p2.getBurstTime()));
    }

    @Override
    public void notifyNewProcess(Process p) {
        // Add the new process to the ready queue when it's available
        readyQueue.add(p);
    }

    @Override
    public Process update(Process runningProcess, int cpu) {
        // If the current running process is finished, clear it
        if (runningProcess != null && runningProcess.isFinished()) {
            runningProcess = null;
        }

        // If no process is running and there are ready processes, assign the shortest one
        if (runningProcess == null && !readyQueue.isEmpty()) {
            runningProcess = readyQueue.poll();  // Get the process with the shortest burst time
            platform.log("CPU " + cpu + " is now running process " + runningProcess.getName());
            numberOfContextSwitches++;
        }

        // If there is a process running, log its state
        if (runningProcess != null) {
            platform.log("CPU " + cpu + " is running process " + runningProcess.getName());
        }
        
        return runningProcess;  // Return the running process (or null if none is running)
    }

    @Override
    public int getNumberOfContextSwitches() {
        return numberOfContextSwitches;
    }
}