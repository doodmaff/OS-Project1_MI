package src;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MemoryManager {

    //Memory Allocation Methods

    public static void firstFit(int[] blockSizes, int[] processSizes) {
        System.out.println("\n--------------------------------------------------");
        System.out.println("  First-Fit Memory Allocation");
        System.out.println("--------------------------------------------------");
        allocate(blockSizes, processSizes, "First-Fit");
    }

    public static void bestFit(int[] blockSizes, int[] processSizes) {
        System.out.println("\n--------------------------------------------------");
        System.out.println("  Best-Fit Memory Allocation");
        System.out.println("--------------------------------------------------");
        allocate(blockSizes, processSizes, "Best-Fit");
    }

    public static void worstFit(int[] blockSizes, int[] processSizes) {
        System.out.println("\n--------------------------------------------------");
        System.out.println("  Worst-Fit Memory Allocation");
        System.out.println("--------------------------------------------------");
        allocate(blockSizes, processSizes, "Worst-Fit");
    }

    private static void allocate(int[] blockSizes, int[] processSizes, String method) {
        int[] blocks = blockSizes.clone();
        int[] allocation = new int[processSizes.length];
        for (int i = 0; i < allocation.length; i++)
            allocation[i] = -1;

        for (int i = 0; i < processSizes.length; i++) {
            int chosen = -1;

            for (int j = 0; j < blocks.length; j++) {
                if (blocks[j] < processSizes[i])
                    continue;

                if (method.equals("First-Fit")) {
                    chosen = j;
                    break; // first one that fits, stop
                } else if (method.equals("Best-Fit")) {
                    if (chosen == -1 || blocks[j] < blocks[chosen])
                        chosen = j;
                } else if (method.equals("Worst-Fit")) {
                    if (chosen == -1 || blocks[j] > blocks[chosen])
                        chosen = j;
                }
            }

            if (chosen != -1) {
                allocation[i] = chosen;
                blocks[chosen] -= processSizes[i];
            }
        }

        System.out.printf("  %-12s %-14s %-15s%n",
                "Process", "Size (KB)", "Block Assigned");
        System.out.println("  " + "-".repeat(42));

        for (int i = 0; i < processSizes.length; i++) {
            String blockLabel = allocation[i] != -1 ? "Block " + (allocation[i] + 1) : "Not Allocated";
            System.out.printf("  %-12d %-14d %-15s%n",
                    (i + 1), processSizes[i], blockLabel);
        }

    }

    // ------- PAGE REPLACEMENT -------

    // FIFO - oldest page gets evicted first
    public static void fifoPageReplacement(int[] pages, int frameCount) {
        System.out.println("\n--------------------------------------------------");
        System.out.println("  FIFO Page Replacement");
        System.out.println("--------------------------------------------------");
        System.out.println("  Frames available : " + frameCount);
        System.out.println("  Reference string : " + arrayToString(pages));
        System.out.println();

        LinkedList<Integer> frames = new LinkedList<>();
        int pageFaults = 0;
        int pageHits = 0;

        System.out.printf("  %-8s %-20s %-8s%n", "Page", "Frames", "Result");
        System.out.println("  " + "-".repeat(40));

        for (int page : pages) {
            if (frames.contains(page)) {
                pageHits++;
                System.out.printf("  %-8d %-20s %-8s%n", page, frames.toString(), "HIT");
            } else {
                pageFaults++;
                if (frames.size() == frameCount)
                    frames.removeFirst();
                frames.add(page);
                System.out.printf("  %-8d %-20s %-8s%n", page, frames.toString(), "FAULT");
            }
        }

        System.out.println("\n  FIFO Summary:");
        System.out.println("  Total Page Faults : " + pageFaults);
        System.out.println("  Total Page Hits   : " + pageHits);
    }

    // LRU - track order manually, index 0 is least recently used
    public static void lruPageReplacement(int[] pages, int frameCount) {
        System.out.println("\n--------------------------------------------------");
        System.out.println("  LRU Page Replacement");
        System.out.println("--------------------------------------------------");
        System.out.println("  Frames available : " + frameCount);
        System.out.println("  Reference string : " + arrayToString(pages));
        System.out.println();

        List<Integer> frames = new ArrayList<>();
        int pageFaults = 0;
        int pageHits = 0;

        System.out.printf("  %-8s %-20s %-8s%n", "Page", "Frames", "Result");
        System.out.println("  " + "-".repeat(40));

        for (int page : pages) {
            if (frames.contains(page)) {
                pageHits++;
                frames.remove(Integer.valueOf(page));
                frames.add(page); // move to back = most recently used
                System.out.printf("  %-8d %-20s %-8s%n", page, frames.toString(), "HIT");
            } else {
                pageFaults++;
                if (frames.size() == frameCount)
                    frames.remove(0); // evict LRU
                frames.add(page);
                System.out.printf("  %-8d %-20s %-8s%n", page, frames.toString(), "FAULT");
            }
        }

        System.out.println("\n  LRU Summary:");
        System.out.println("  Total Page Faults : " + pageFaults);
        System.out.println("  Total Page Hits   : " + pageHits);
    }

    private static String arrayToString(int[] arr) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1)
                sb.append(", ");
        }
        return sb.append("]").toString();
    }
}