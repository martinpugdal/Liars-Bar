package dk.martinersej.liarsbar.utils;

import org.bukkit.util.EulerAngle;

public class MathUtils {

    public static double degreesToRadians(double degrees) {
        return (degrees % 360) * (Math.PI / 180);
    }
    public static double radiansToDegrees(double radians) {
        return (radians / Math.PI) * 180;
    }

    public static EulerAngle updateEulerAngleCoord(EulerAngle eulerAngle, String coord, double value) {
        switch (coord) {
            case "X":
                return eulerAngle.setX(value);
            case "Y":
                return eulerAngle.setY(value);
            case "Z":
                return eulerAngle.setZ(value);
            default:
                return eulerAngle;
        }
    }
}
