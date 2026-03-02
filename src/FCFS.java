package src;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// FCFS scheduling - runs processes in arrival order
public class FCFS {

    public static void run(List<Process> original) {
        System.out.println("\n--------------------------------------------------");
        System.out.println("  FCFS (First-Come, First-Served Scheduling)");
        System.out.println("--------------------------------------------------");

        List<Process> processes = copyList(original);

        // sort by arrival time, use pid to break ties
        processes.sort(Comparator.comparingInt(Process::getArrivalTime)
                .thenComparingInt(Process::getPid));

        List<String> ganttLabels = new ArrayList<>();
        List<Integer> ganttTimes = new ArrayList<>();
        int currentTime = 0;

        for (Process p : processes) {
            if (currentTime < p.getArrivalTime())
                currentTime = p.getArrivalTime();

            ganttLabels.add("P" + p.getPid());
            ganttTimes.add(currentTime);

            p.setWaitingTime(currentTime - p.getArrivalTime());
            currentTime += p.getBurstTime();
            p.setTurnaroundTime(p.getWaitingTime() + p.getBurstTime());
        }
        ganttTimes.add(currentTime);

        printGanttChart(ganttLabels, ganttTimes);
        printResults(processes, ganttTimes);
    }

    private static List<Process> copyList(List<Process> original) {
        List<Process> copy = new ArrayList<>();
        for (Process p : original)
            copy.add(new Process(p));
        return copy;
    }

    // prints the gantt chart with timestamps below each block
    private static void printGanttChart(List<String> labels, List<Integer> times) {
        System.out.println("\n  Gantt Chart:");

        StringBuilder bar = new StringBuilder("|");
        StringBuilder timeline = new StringBuilder();

        for (int i = 0; i < labels.size(); i++) {
            String cell = " " + labels.get(i) + " ";
            bar.append(cell).append("|");
        }

        timeline.append(times.get(0));
        for (int i = 1; i < times.size(); i++) {
            String prevCell = " " + labels.get(i - 1) + " ";
            int pad = prevCell.length() + 1 - String.valueOf(times.get(i)).length();
            for (int s = 0; s < pad; s++)
                timeline.append(" ");
            timeline.append(times.get(i));
        }

        System.out.println("  " + bar);
        System.out.println("  " + timeline);
    }

    private static void printResults(List<Process> processes, List<Integer> ganttTimes) {
        System.out.printf("%n  %-5s %-14s %-12s %-10s %-14s %-15s%n",
                "PID", "Arrival_Time", "Burst_Time", "Priority",
                "Waiting_Time", "Turnaround_Time");
        System.out.println("  " + "-".repeat(72));

        double totalWT = 0, totalTAT = 0;
        for (Process p : processes) {
            System.out.printf("  %-5d %-14d %-12d %-10d %-14d %-15d%n",
                    p.getPid(), p.getArrivalTime(), p.getBurstTime(),
                    p.getPriority(), p.getWaitingTime(), p.getTurnaroundTime());
            totalWT += p.getWaitingTime();
            totalTAT += p.getTurnaroundTime();
        }

        int n = processes.size();
        System.out.printf("%n  Average Waiting Time    : %.2f%n", totalWT / n);
        System.out.printf("  Average Turnaround Time : %.2f%n", totalTAT / n);

        //using the gantt chart to calculate the elapsed time
        int totalBurst = processes.stream().mapToInt(Process::getBurstTime).sum();
        int elapsed = ganttTimes.get(ganttTimes.size() - 1) - ganttTimes.get(0);
        double utilization = elapsed > 0 ? (double) totalBurst / elapsed * 100 : 100;
        System.out.printf("  CPU Utilization         : %.1f%%%n", utilization);
    }
}