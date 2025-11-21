/**
 * Clock: mutable data type that represents the time on a 24-hour clock.
 * Public API (as required by Coursera autograder):
 *  - public Clock(int h, int m)
 *  - public Clock(String s)           // format "HH:MM"
 *  - public String toString()         // "HH:MM"
 *  - public boolean isEarlierThan(Clock that)
 *  - public void tic()                // +1 minute
 *  - public void toc(int delta)       // +delta minutes; IllegalArgumentException if delta < 0
 *  - public static void main(String[] args)
 */
public class Clock {
    private static final int HOURS_PER_DAY = 24;
    private static final int MINUTES_PER_HOUR = 60;
    private static final int MAX_HOUR = HOURS_PER_DAY - 1;
    private static final int MAX_MINUTE = MINUTES_PER_HOUR - 1;

    private int h; // 0..23
    private int m; // 0..59

    // create a clock whose initial time is h:m
    public Clock(int h, int m) {
        if (h < 0 || h > MAX_HOUR) throw new IllegalArgumentException("hour out of range");
        if (m < 0 || m > MAX_MINUTE) throw new IllegalArgumentException("minute out of range");
        this.h = h;
        this.m = m;
    }

    // create a clock whose initial time is specified as a string, using the format HH:MM
    public Clock(String s) {
        if (s == null) throw new IllegalArgumentException("null string");
        if (s.length() != 5 || s.charAt(2) != ':')
            throw new IllegalArgumentException("bad format");
        String sh = s.substring(0, 2);
        String sm = s.substring(3, 5);
        try {
            int hh = Integer.parseInt(sh);
            int mm = Integer.parseInt(sm);
            if (hh < 0 || hh > MAX_HOUR) throw new IllegalArgumentException("hour out of range");
            if (mm < 0 || mm > MAX_MINUTE) throw new IllegalArgumentException("minute out of range");
            this.h = hh;
            this.m = mm;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("bad format", e); // preserve stack trace
        }
    }

    // return string representation in the format HH:MM
    public String toString() {
        String hh = (h < 10 ? "0" : "") + h;
        String mm = (m < 10 ? "0" : "") + m;
        return hh + ":" + mm;
    }

    // is this time strictly earlier than that time?
    public boolean isEarlierThan(Clock that) {
        if (that == null) throw new IllegalArgumentException("null clock");
        if (this.h != that.h) return this.h < that.h;
        return this.m < that.m;
    }

    // add 1 minute to the time on this clock
    public void tic() {
        m++;
        if (m >= MINUTES_PER_HOUR) {
            m = 0;
            h++;
            if (h >= HOURS_PER_DAY) h = 0;
        }
    }

    // add delta minutes to the time on this clock
    public void toc(int delta) {
        if (delta < 0) throw new IllegalArgumentException("negative delta");
        int total = h * MINUTES_PER_HOUR + m;
        total = (total + delta) % (HOURS_PER_DAY * MINUTES_PER_HOUR);
        h = total / MINUTES_PER_HOUR;
        m = total % MINUTES_PER_HOUR;
    }

    // simple client that exercises the API without SpotBugs warnings
    public static void main(String[] args) {
        Clock a = new Clock(0, 0);
        Clock b = new Clock("23:59");
        System.out.println(a.toString());
        System.out.println(b.toString());
        System.out.println(a.isEarlierThan(b));
        a.tic();
        System.out.println(a.toString());
        a.toc(120);
        System.out.println(a.toString());
    }
}
