public class Ghost {
    private boolean GAME_OVER;
    private static final int CELL_SIZE = 20; // размер ячейки в пикселях
    private int x;
    private int y;
    private String direction;
    private String[][] cells;
    private Pacman pacman;
    final static int speed = 2;
    public Ghost(int x, int y, String direction, Pacman pacman, String[][] cells, boolean GAME_OVER) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.pacman = pacman;
        this.cells = cells;
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
            //case "STOP" -> locator();
            case "LEFT" -> x -= speed;
            case "RIGHT" -> x += speed;
            case "UP" -> y -= speed;
            case "DOWN" -> y += speed;
        }
    }
    public void locator(){
        int dx = Math.abs(pacman.getX() / CELL_SIZE - x / CELL_SIZE);
        int dy = Math.abs(pacman.getY() / CELL_SIZE - y / CELL_SIZE);
        if (dx > dy)// лево/право
            if (dx > (x + CELL_SIZE) / CELL_SIZE)
                direction = "RIGHT";
            else
                direction = "LEFT";
        else if (dy > (y + CELL_SIZE) / CELL_SIZE)// верх/низ
            direction = "DOWN";
        else
            direction = "UP";
    }
}