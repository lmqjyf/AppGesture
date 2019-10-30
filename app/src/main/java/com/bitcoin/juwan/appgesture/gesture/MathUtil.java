package com.bitcoin.juwan.appgesture.gesture;

/**
 * FileName：MathUtil
 * Create By：liumengqiang
 * Description：TODO
 */
public class MathUtil {
    public static double getDistancePoints(float x1, float y1, float x2, float y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    /**
     * 点的投影是否在线段上
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param pointX
     * @param pointY
     * @return
     */
    public static boolean isOfLine(float x1, float y1, float x2, float y2, float pointX, float pointY) {
        double AB = getDistancePoints(x1, y1, x2, y2);
        double BC = getDistancePoints(pointX, pointY, x2, y2);
        double AC = getDistancePoints(x1, y1, pointX, pointY);

        if( AB * AB >= (BC * BC + AC * AC)) {
            return true;
        } else {
            return false;
        }
    }

    public static double getPointToTargetLineOfHeight(double distance1, double distance2, double targetDistance) {
        double q = (distance1 + distance2 + targetDistance) /2;
        double s = Math.sqrt(q * (q - distance1) * (q - distance2) * (targetDistance));
        return s * 2 / targetDistance;
    }
}
