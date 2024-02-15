package model;

public class CornerCubelet extends Cubelet {
    public CornerCubelet(Position pos) {
        super(pos);
    }

    public CornerCubelet(Cubelet c) {
        super(c);
    }

    @Override
    public void scrambleColors() {
        // Clockwise XYZ: (1, -1, 1) (-1, 1, 1) (1, 1, -1) (-1, -1, -1)
        // Clockwise XZY: (-1, -1, 1), (1, 1, 1), (1, -1, -1), (-1, 1, -1)
        boolean targetXYZ = ((getTargetPos().getX() == 1) ^ (getTargetPos().getY() == 1))
                == (getTargetPos().getZ() == 1);
        boolean currentXYZ = ((getPos().getX() == 1) ^ (getPos().getY() == 1)) == (getPos().getZ() == 1);

        String[] scramble;
        int shift = (int)(Math.random() * 3);
        if (currentXYZ != targetXYZ) {
            String swap = getColorX();
            setColorX(getColorY());
            setColorY(swap);
        }
        if (targetXYZ) {
            scramble = new String[] {getColorX(), getColorY(), getColorZ()};
            setColorX(scramble[shift]);
            setColorY(scramble[(shift + 1) % 3]);
            setColorZ(scramble[(shift + 2) % 3]);
        } else {
            scramble = new String[] {getColorX(), getColorZ(), getColorY()};
            setColorX(scramble[shift]);
            setColorZ(scramble[(shift + 1) % 3]);
            setColorY(scramble[(shift + 2) % 3]);
        }
    }

    public int getClockwiseTurns() {
        boolean xyz = ((getPos().getX() == 1) ^ (getPos().getY() == 1)) == (getPos().getZ() == 1);
        if (xyz) {
            if (getColorX().equals("W") || getColorX().equals("Y")) {
                return 2;
            } else if (getColorY().equals("W") || getColorY().equals("Y")) {
                return 1;
            }
        } else {
            if (getColorX().equals("W") || getColorX().equals("Y")) {
                return 1;
            } else if (getColorY().equals("W") || getColorY().equals("Y")) {
                return 2;
            }
        }
        return 0;
    }
}
