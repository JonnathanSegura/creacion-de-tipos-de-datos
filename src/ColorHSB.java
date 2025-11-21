/**
 * ColorHSB: immutable data type that represents a color in HSB format.
 * Public API (as required by Coursera autograder):
 *  - public ColorHSB(int h, int s, int b)
 *  - public String toString()
 *  - public boolean isGrayscale()
 *  - public int distanceSquaredTo(ColorHSB that)
 *  - public static void main(String[] args)
 *
 * Behavior:
 *  - Validate ranges: 0<=h<=359, 0<=s<=100, 0<=b<=100; otherwise IllegalArgumentException.
 *  - distanceSquaredTo throws IllegalArgumentException if argument is null.
 *  - Distance: min((h1-h2)^2, (360-|h1-h2|)^2) + (s1-s2)^2 + (b1-b2)^2.
 *  - isGrayscale if s==0 or b==0 (or both).
 *  - main: receives h s b as command-line integers; reads predefined colors from StdIn:
 *          lines of: <name> <h> <s> <b>; prints closest color name and its "(h, s, b)".
 */
public class ColorHSB {
    private final int h;
    private final int s;
    private final int b;

    // construct a color with hue h, saturation s, and brightness b
    public ColorHSB(int h, int s, int b) {
        if (h < 0 || h > 359) throw new IllegalArgumentException("h out of range");
        if (s < 0 || s > 100) throw new IllegalArgumentException("s out of range");
        if (b < 0 || b > 100) throw new IllegalArgumentException("b out of range");
        this.h = h;
        this.s = s;
        this.b = b;
    }

    // return string representation in the format "(h, s, b)"
    public String toString() {
        return "(" + h + ", " + s + ", " + b + ")";
    }

    // is this color a shade of gray?
    public boolean isGrayscale() {
        return (s == 0) || (b == 0);
    }

    // return squared distance between this color and that color
    public int distanceSquaredTo(ColorHSB that) {
        if (that == null) throw new IllegalArgumentException("null color");
        int dh = Math.abs(this.h - that.h);
        int dhWrap = 360 - dh;
        int dh2 = Math.min(dh * dh, dhWrap * dhWrap);
        int ds2 = (this.s - that.s) * (this.s - that.s);
        int db2 = (this.b - that.b) * (this.b - that.b);
        return dh2 + ds2 + db2;
    }

    // client: reads target HSB from args; reads color list from StdIn;
    // prints closest color name and its HSB tuple.
    public static void main(String[] args) {
        if (args.length != 3) return;
        int h = Integer.parseInt(args[0]);
        int s = Integer.parseInt(args[1]);
        int b = Integer.parseInt(args[2]);
        ColorHSB target = new ColorHSB(h, s, b);

        // exercise public methods at least once
        target.isGrayscale();

        String bestName = null;
        ColorHSB bestColor = null;
        int bestDist = Integer.MAX_VALUE;

        // Read until StdIn is empty (the autograder provides StdIn/StdOut)
        while (!StdIn.isEmpty()) {
            String name = StdIn.readString();
            int hh = StdIn.readInt();
            int ss = StdIn.readInt();
            int bb = StdIn.readInt();
            ColorHSB candidate;
            try {
                candidate = new ColorHSB(hh, ss, bb);
            } catch (IllegalArgumentException e) {
                continue; // ignore malformed lines
            }
            int d = target.distanceSquaredTo(candidate);
            if (d < bestDist) {
                bestDist = d;
                bestName = name;
                bestColor = candidate;
            }
        }

        if (bestName != null && bestColor != null) {
            StdOut.println(bestName + " " + bestColor.toString());
        }
    }
}
