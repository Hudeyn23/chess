package labs.nsu.game.model;

public class Check {
    private final Cell attackCell;
    private final int[] direction;

    public int[] getDirection() {
        return direction;
    }

    public Cell getAttackCell() {
        return attackCell;
    }

    public Check(Cell attackCell, int[] direction) {
        this.attackCell = attackCell;
        this.direction = direction.clone();
    }
}
