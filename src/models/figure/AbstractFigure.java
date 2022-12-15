package models.figure;


import models.Point;
import models.Step;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFigure implements Character {
    protected @NotNull Point currPosition;
    protected List<Step> possibleSteps;

    public AbstractFigure(@NotNull Point position) {
        this.currPosition = position;
        updatePossibleSteps();
    }

    @Override
    public Point getCurrentPosition() {
        return currPosition;
    }

    public abstract @NotNull AbstractFigure moveFigure(Point point);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractFigure that = (AbstractFigure) o;
        return currPosition.equals(that.currPosition) && possibleSteps.equals(that.possibleSteps);
    }

    @Override
    public List<Step> getPossibleSteps() {
        return possibleSteps;
    }

    protected void updatePossibleSteps() {
        possibleSteps = new ArrayList<>();
    }
}