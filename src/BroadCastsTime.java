import java.util.*;

class BroadcastsTime implements Comparable<BroadcastsTime> {
    private byte hour;
    private byte minutes;

    public BroadcastsTime(byte hour, byte minutes) {
        this.hour = hour;
        this.minutes = minutes;
    }

    public byte getHour() {
        return hour;
    }

    public byte getMinutes() {
        return minutes;
    }

    public boolean after(BroadcastsTime t) {
        if (this.hour > t.hour) {
            return true;
        } else if (this.hour == t.hour && this.minutes > t.minutes) {
            return true;
        }
        return false;
    }

    public boolean before(BroadcastsTime t) {
        if (this.hour < t.hour) {
            return true;
        } else if (this.hour == t.hour && this.minutes < t.minutes) {
            return true;
        }
        return false;
    }

    public boolean between(BroadcastsTime t1, BroadcastsTime t2) {
        return this.after(t1) && this.before(t2);
    }

    @Override
    public int compareTo(BroadcastsTime t) {
        if (this.hour != t.hour) {
            return this.hour - t.hour;
        }
        return this.minutes - t.minutes;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d", hour, minutes);
    }
}

