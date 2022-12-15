package field;

import models.Point;

public class FieldHelp {
    public static boolean canPointBeInField(Point point) {
        if (point.y() >= 1 && point.y() <= 2) {
            return point.x() >= 3 && point.x() <= 5;
        } else {
            if (point.y() >= 3 && point.y() <= 5) {
                return point.x() >= 1 && point.x() <= 7;
            } else {
                if (point.y() >= 6 && point.y() <= 7) {
                    return point.x() >= 3 && point.x() <= 5;
                }
            }

        }
        return false;
    }
}