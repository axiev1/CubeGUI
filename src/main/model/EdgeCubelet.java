package model;

public class EdgeCubelet extends Cubelet {
    public EdgeCubelet(Position pos) {
        super(pos);
    }

    @SuppressWarnings("methodlength")
    @Override
    public void scrambleColors() {
        String color1;
        String color2;
        if (getTargetPos().getX() == 0) {
            color1 = getColorY();
            color2 = getColorZ();
        } else if (getTargetPos().getY() == 0) {
            color1 = getColorX();
            color2 = getColorZ();
        } else {
            color1 = getColorX();
            color2 = getColorY();
        }

        if (Math.random() > 0.5) {
            String swap = color1;
            color1 = color2;
            color2 = swap;
        }

        if (getPos().getX() == 0) {
            setColorY(color1);
            setColorZ(color2);
        } else if (getPos().getY() == 0) {
            setColorX(color1);
            setColorZ(color2);
        } else {
            setColorX(color1);
            setColorY(color2);
        }
    }
}
