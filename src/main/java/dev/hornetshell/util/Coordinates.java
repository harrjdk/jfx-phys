package dev.hornetshell.util;

public class Coordinates {

    private Coordinates() {
        // no-op
    }

    public static double[] cartToSphere(double[] xyz) {
        final double p = Math.sqrt(Math.pow(xyz[0], 2) + Math.pow(xyz[1], 2) + Math.pow(xyz[2], 2));
        final double theta = Math.atan2(xyz[1], xyz[0]);
        final double phi = Math.acos(xyz[2]/p);
        System.out.println("Sphere: "+p+", "+theta+", "+phi);
        return new double[] {p, theta!=theta?0:theta, phi!=phi?0:phi};
    }

    public static double[] sphereToCart(double[] ptp) {
        final double x = ptp[0] * Math.sin(ptp[2]) * Math.cos(ptp[1]);
        final double y = ptp[0] * Math.sin(ptp[2]) * Math.sin(ptp[1]);
        final double z = ptp[0] * Math.cos(ptp[2]);
        System.out.println("Cart:"+x+", "+y+", "+z);
        return new double[] {x, y, z};
    }
}
