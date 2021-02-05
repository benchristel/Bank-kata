package bank;

import bank.core.Clock;

import java.time.LocalDate;

public class StoppedClock extends Clock {
    private LocalDate today;

    public StoppedClock(LocalDate today) {
        this.today = today;
    }

    public LocalDate today() {
        return today;
    }
}
