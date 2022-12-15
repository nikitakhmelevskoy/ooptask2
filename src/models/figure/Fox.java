package models.figure;

import field.FieldHelp;
import models.Point;
import models.Step;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class Fox extends AbstractFigure {
    public Fox(@NotNull Point position) {
        super(position);
        updatePossibleSteps();
    }

    @Override
    public @NotNull AbstractFigure moveFigure(Point point) {
        return new Fox(point);
    }

    @Override
    protected void updatePossibleSteps() {
        super.updatePossibleSteps();

        final List<Point> potentialPoints = new ArrayList<>();

        potentialPoints.add(new Point(currPosition.x() + 1, currPosition.y()));
        potentialPoints.add(new Point(currPosition.x() + 1, currPosition.y() + 1));
        potentialPoints.add(new Point(currPosition.x() + 1, currPosition.y() - 1));
        potentialPoints.add(new Point(currPosition.x(), currPosition.y() - 1));
        potentialPoints.add(new Point(currPosition.x(), currPosition.y() + 1));
        potentialPoints.add(new Point(currPosition.x() - 1, currPosition.y() - 1));
        potentialPoints.add(new Point(currPosition.x() - 1, currPosition.y()));
        potentialPoints.add(new Point(currPosition.x() - 1, currPosition.y() + 1));

        for (var point : potentialPoints) {
            if (FieldHelp.canPointBeInField(point)) {
                possibleSteps.add(new Step(currPosition, point));
            }
        }

    }
}