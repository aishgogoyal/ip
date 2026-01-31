import java.time.LocalDate;

public class Deadline extends Task {
    protected LocalDate by;

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }
    
    public LocalDate getBy() {
        return by;
    }

    @Override
    public String toString() {
        return "[D][" + status() + "] " + description + " (by: " + by + ")";
    }
}

