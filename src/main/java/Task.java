public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markDone() {
        isDone = true;
    }

    public void markNotDone() {
        isDone = false;
    }

    public String status() {
        return isDone ? "X" : " ";
    }
    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return isDone;
    }


    @Override
    public String toString() {
        return "[ ] " + description;
    }
}

