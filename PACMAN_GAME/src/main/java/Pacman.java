public class Pacman {
    private boolean GAME_OVER;
    private int x;
    private int y;
    private String direction;
    final static int speed = 2;
    private static final int CELL_SIZE = 20; // размер ячейки в пикселях

    public Pacman(int x, int y, String direction, boolean GAME_OVER) {
        this.x = x;
        this.y = y;
        this.direction = "RIGHT";
        this.GAME_OVER = GAME_OVER;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public String getDirection() {
        return direction;
    }
    public void setDirection(String direction) {
        this.direction = direction;
    }
    public void setGAME_OVER(boolean GAME_OVER) {
        this.GAME_OVER = GAME_OVER;
    }
    public void move() {
        switch (direction) {
            case "LEFT" -> x -= speed;
            case "RIGHT" -> x += speed;
            case "UP" -> y -= speed;
            case "DOWN" -> y += speed;
            case "STOP" -> {}
        }
    }
}