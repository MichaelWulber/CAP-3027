public class ColorUtil {
    public static int addARGB(int a, int r, int g, int b) {
        // snap to closest color value in case of negative or large color values

        if (a > 255)
            a = 255;
        else if (a < 0)
            a = 0;

        if (r > 255)
            r = 255;
        else if (r < 0)
            r = 0;

        if (g > 255)
            g = 255;
        else if (g < 0)
            g = 0;

        if (b > 255)
            b = 255;
        else if (b < 0)
            b = 0;

        return (a << 24) + (r << 16) + (g << 8) + b;
    }

    public static int getAlphaComponent(int c) {
        return ((c >>> 24) & 0x000000FF);
    }

    public static int getRedComponent(int c) {
        return ((c >>> 16) & 0x000000FF);
    }

    public static int getGreenComponent(int c) {
        return ((c >>> 8) & 0x000000FF);
    }

    public static int getBlueComponent(int c) {
        return (c & 0x000000FF);
    }

    public static int[] getLinearGradient(int c1, int c2, int num) {
        // c1 color component values
        double a1 = (double) getAlphaComponent(c1);
        double r1 = (double) getRedComponent(c1);
        double g1 = (double) getGreenComponent(c1);
        double b1 = (double) getBlueComponent(c1);

        // c2 color component values
        double a2 = (double) getAlphaComponent(c1);
        double r2 = (double) getRedComponent(c2);
        double g2 = (double) getGreenComponent(c2);
        double b2 = (double) getBlueComponent(c2);

        // delta values
        double dr = -(r1 - r2) / ((double) num - 1);
        double dg = -(g1 - g2) / ((double) num - 1);
        double db = -(b1 - b2) / ((double) num - 1);

        // array of interpolated colors between c1 and c2
        int[] lg = new int[num];

        // initial color
        lg[0] = addARGB((int) a1, (int) r1, (int) g1, (int) b1);

        // interpolate iteratively
        for (int i = 1; i < lg.length; i++) {
            r1 += dr;
            g1 += dg;
            b1 += db;

            lg[i] = addARGB((int) a1, (int) r1, (int) g1, (int) b1);
        }

        return lg;
    }
}