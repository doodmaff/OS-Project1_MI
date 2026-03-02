package src;
public class Process {

    // fields
    private int pid;
    private int arrivalTime;
    private int burstTime;
    private int priority;
    private int remainingBurstTime;
    private int waitingTime;
    private int turnaroundTime;

    public Process(int pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid; // process ID
        this.arrivalTime = arrivalTime; // time when process arrives in the ready queue
        this.burstTime = burstTime; // total time required by the process for execution
        this.priority = priority; // priority level of the process (lower value means higher priority)
        this.remainingBurstTime = burstTime; // time left for the process to complete execution
        this.waitingTime = 0; // time the process has been waiting in the ready queue (initially 0)
        this.turnaroundTime = 0; // total time taken from arrival to completion (initially 0)
    }

    public Process(Process other) {
        this.pid = other.pid;
        this.arrivalTime = other.arrivalTime;
        this.burstTime = other.burstTime;
        this.priority = other.priority;
        this.remainingBurstTime = other.remainingBurstTime;
        this.waitingTime = other.waitingTime;
        this.turnaroundTime = other.turnaroundTime;
    }

    // getters
    public int getPid() {
        return pid;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getPriority() {
        return priority;
    }

    public int getRemainingBurstTime() {
        return remainingBurstTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    // Setters

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setRemainingBurstTime(int remainingBurstTime) {
        this.remainingBurstTime = remainingBurstTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    @Override
    public String toString() {
        return String.format("Process{PID=%d, Arrival=%d, Burst=%d, Priority=%d}",
                pid, arrivalTime, burstTime, priority);
    }
}