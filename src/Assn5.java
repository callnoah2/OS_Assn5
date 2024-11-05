/* Add a file (and path) as a commandline argument to process an input file. 
 * Otherwise hardcoded default values will apply. The default values do make 
 * it easier to see how the code works. 
*/
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Assn5 {
    private static int cpuCount = 1;
    private static File inputFile;
    public static void main(String[] args) {

        // Setup a Scanner if there is a file given. Otherwise hardcoded defaults are used
        if (args.length > 0) {
            String currentDir = System.getProperty("user.dir");
            java.nio.file.Path filePath = java.nio.file.Paths.get(currentDir, args[0]);
            inputFile = new File(filePath.toString());
            
            cpuCount = Integer.parseInt(readSection("cpus").get(0)[0]);
            System.out.println("did this!");
        }

        System.out.println("---------------------------------------------------------");
        demoFCFS();
        System.out.println();

        System.out.println("---------------------------------------------------------");
        demoSJF();
        System.out.println();

        System.out.println("---------------------------------------------------------");
        demoSRTF();
        System.out.println();

        System.out.println("---------------------------------------------------------");
        demoPriority();
        System.out.println();

        System.out.println("---------------------------------------------------------");
        demoRR();
        System.out.println();
    }

    /**
     * First-Come, First Served
     */
    private static void demoFCFS() {
        Platform platform = new Platform(cpuCount);
        Queue<Process> processes = new LinkedList<>();

        if (inputFile != null) {
            // Process the FCFS section
            // Use the input from a file if it was given
            for (String[] items : readSection("fcfs")){
                String name = items[0];
                int start = Integer.parseInt(items[1]);
                int burst = Integer.parseInt(items[2]);
                int total = Integer.parseInt(items[3]);
                processes.add(new Process(name, start, burst, total));
            }
        }
        else { // Use default values if no file was given
            processes.add(new Process("P1", 0, 24, 48));
            processes.add(new Process("P2", 0, 3, 6));
            processes.add(new Process("P3", 0, 3, 6));
        }

        System.out.println("Starting First Come, First Served CPU scheduling simulation");
        Scheduler scheduler = new SchedulerFCFS(platform);
        platform.simulate(scheduler, processes);
        System.out.printf("Number of context switches: %d\n", scheduler.getNumberOfContextSwitches());
        System.out.println("FCFS CPU scheduling simulation complete");
    }

    /**
     * Shortest Job First
     */
    private static void demoSJF() {
        Platform platform = new Platform(cpuCount);
        Queue<Process> processes = new LinkedList<>();
    
        if (inputFile != null) {
            // Process the SJF Section
            for (String[] items : readSection("sjf")){
                String name = items[0];
                int start = Integer.parseInt(items[1]);
                int burst = Integer.parseInt(items[2]);
                int total = Integer.parseInt(items[3]);
                processes.add(new Process(name, start, burst, total));
            }
        } else {
            // Default values for SJF
            processes.add(new Process("P1", 0, 6, 6));
            processes.add(new Process("P2", 0, 8, 8));
            processes.add(new Process("P3", 0, 7, 7));
            processes.add(new Process("P4", 0, 3, 3));
        }
    
        System.out.println("Starting Shortest Job First CPU scheduling simulation");
        Scheduler scheduler = new SchedulerSJF(platform);
        platform.simulate(scheduler, processes);
        System.out.printf("Number of context switches: %d\n", scheduler.getNumberOfContextSwitches());
        System.out.println("SJF CPU scheduling simulation complete");
    }

    /**
     * Shortest Remaining Time First
     */
    private static void demoSRTF() {
        Platform platform = new Platform(cpuCount);
        Queue<Process> processes = new LinkedList<>();

        if (inputFile != null) {
            // Process the SRTF Section
            for (String[] items : readSection("srtf")){
                String name = items[0];
                int start = Integer.parseInt(items[1]);
                int burst = Integer.parseInt(items[2]);
                int total = Integer.parseInt(items[3]);
                processes.add(new Process(name, start, burst, total));
            }
        } else {
            processes.add(new Process("P1", 0, 8, 8));
            processes.add(new Process("P2", 1, 4, 4));
            processes.add(new Process("P3", 2, 9, 9));
            processes.add(new Process("P4", 3, 5, 5));
        }

        System.out.println("Starting Shortest Remaining Time First CPU scheduling simulation");
        Scheduler scheduler = new SchedulerSRTF(platform);
        platform.simulate(scheduler, processes);
        System.out.printf("Number of context switches: %d\n", scheduler.getNumberOfContextSwitches());
        System.out.println("SRTF CPU scheduling simulation complete");
    }

    /**
     * Priority
     */
    private static void demoPriority() {
        Platform platform = new Platform(cpuCount);
        Queue<Process> processes = new LinkedList<>();

        if (inputFile != null) {
            // Process the PRIORITY Section
            for (String[] items : readSection("priority")){
                String name = items[0];
                int start = Integer.parseInt(items[1]);
                int burst = Integer.parseInt(items[2]);
                int total = Integer.parseInt(items[3]);
                int priority = Integer.parseInt(items[4]);
                processes.add(new Process(name, start, burst, total, priority));
            }
        } else {
            processes.add(new Process("P1", 0, 10, 10, 3));
            processes.add(new Process("P2", 0, 1, 1, 1));
            processes.add(new Process("P3", 0, 2, 2, 4));
            processes.add(new Process("P4", 0, 1, 1, 5));
            processes.add(new Process("P5", 0, 5, 5, 2));
        }

        System.out.println("Starting Priority CPU scheduling simulation");
        Scheduler scheduler = new SchedulerPriority(platform);
        platform.simulate(scheduler, processes);
        System.out.printf("Number of context switches: %d\n", scheduler.getNumberOfContextSwitches());
        System.out.println("Priority CPU scheduling simulation complete");
    }

    /**
     * Round Robin
     */
    private static void demoRR() {
        Platform platform = new Platform(cpuCount);
        Queue<Process> processes = new LinkedList<>();

        if (inputFile != null) {
            // Process the RR Section
            for (String[] items : readSection("rr")){
                String name = items[0];
                    int start = Integer.parseInt(items[1]);
                    int burst = Integer.parseInt(items[2]);
                    int total = Integer.parseInt(items[3]);
                    processes.add(new Process(name, start, burst, total));
            }
        } else {
            processes.add(new Process("P1", 0, 24, 24));
            processes.add(new Process("P2", 0, 3, 3));
            processes.add(new Process("P3", 0, 3, 3));
        }

        System.out.println("Starting Round Robin CPU scheduling simulation");
        Scheduler scheduler = new SchedulerRR(platform, 4);
        platform.simulate(scheduler, processes);
        System.out.printf("Number of context switches: %d\n", scheduler.getNumberOfContextSwitches());
        System.out.println("RR CPU scheduling simulation complete");
    }

    /**
     * Process a specific section of the input file
     */
    private static ArrayList<String[]> readSection(String section) {
        ArrayList<String[]> results = new ArrayList<>();
        Scanner scanner = null;
        try {
            scanner = new Scanner(inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Process file
        while (scanner.hasNextLine()) {
            // Look for start of appropriate section
            String line = scanner.nextLine();

            if (line.compareTo("**" + section + " start**") == 0) {
                // Found start of section
                while(scanner.findInLine(Pattern.compile("\\*")) == null) {
                    results.add(scanner.nextLine().split("[ ,]+"));
                }

                break;
            }
        }

        return results;
    }
}
