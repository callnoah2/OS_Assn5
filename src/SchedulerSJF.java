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
        readyQueue.add(p);
    }

    @Override
    public Process update(Process runningProcess, int cpu) {
        if (runningProcess != null && runningProcess.isFinished()) {
            runningProcess = null;
        }
    
        if (runningProcess == null && !readyQueue.isEmpty()) {
            runningProcess = readyQueue.poll();
            platform.log("CPU " + cpu + " is now running process " + runningProcess.getName());
            numberOfContextSwitches++;
        }
    
        if (runningProcess != null) {
            platform.log("CPU " + cpu + " is running process " + runningProcess.getName());
        }
    
        return runningProcess;
    }

    public int getNumberOfContextSwitches() {
        return numberOfContextSwitches;
    }
}