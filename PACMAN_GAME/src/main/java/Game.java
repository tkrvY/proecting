import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Game extends JFrame implements KeyListener,ActionListener {
    private JPanel panel;
    private Timer timer;
    private Pacman pacman;
    private int GHOST_COUNT;
    private Ghost[] ghost;
    private static final int BOARD_WIDTH = 20;
    private static final int BOARD_HEIGHT = 20;
    private static final int CELL_SIZE = 20; // размер ячейки в пикселях
    private static String[][] cells = new String[BOARD_WIDTH][BOARD_HEIGHT];
    private int SCORE;
    boolean GAME_OVER = false;
    boolean GAME_WIN = false;
    private JButton exitButton;

    private Image
            wall_img, ghost_img,
            pacman_img, pacman_eat,
            pacman_img_R,pacman_img_L,
            pacman_img_U,pacman_img_D;
    private boolean eat = true;
    private int eat_count = 0;
    public Game(int GHOST_COUNT) {

        super("Game"); // название окна

        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        exitButton.setEnabled(false); // кнопка неактивна
        exitButton.setVisible(false); // кнопка невидима

        this.GHOST_COUNT = GHOST_COUNT;
        ghost = new Ghost[GHOST_COUNT];
        loadImage();
        SCORE = 0;
        setSize(BOARD_WIDTH*CELL_SIZE+10, BOARD_HEIGHT*CELL_SIZE+100);
        setLocationRelativeTo(null); // центрируем окно на экране
        setVisible(true);
        //setExtendedState(MAXIMIZED_BOTH);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // освобождаем ресурсы, связанные с окном Game
                dispose();
            }
        });
        setVisible(true);
        SCORE = 0;
        pacman = new Pacman(150, 150, "LEFT", false); // начальная позиция Пакмана в пикселях


        ArrayList<Integer> GH_W = new ArrayList<Integer>();
        Collections.addAll(GH_W, 18, 2, 18, 1);

        ArrayList<Integer> GH_H = new ArrayList<Integer>();
        Collections.addAll(GH_H, 1, 1, 17, 15);

        for (int i = 0; i < GHOST_COUNT; i++) {
            ghost[i] = new Ghost((BOARD_WIDTH - GH_W.get(i)) * CELL_SIZE - CELL_SIZE / 2, (BOARD_HEIGHT - GH_H.get(i)) * CELL_SIZE - CELL_SIZE / 2, "STOP", pacman, cells, true);
        }

        for (int i = 0; i < GHOST_COUNT; i++) ghost[i].setDirection("STOP");



        cells = new String[][]
                {
                        {"WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL"},
                        {"WALL", "WALL", "FOOD", "FOOD", "FOOD", "FOOD", "FOOD", "FOOD", "FOOD", "WALL", "FOOD", "WALL", "WALL", "WALL", "FOOD", "FOOD", "FOOD", "FOOD", "FOOD", "WALL"},
                        {"WALL", "FOOD", "FOOD", "WALL", "WALL", "WALL", "WALL", "WALL", "FOOD", "FOOD", "FOOD", "FOOD", "FOOD", "FOOD", "FOOD", "WALL", "WALL", "FOOD", "FOOD", "WALL"},
                        {"WALL", "FOOD", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "FOOD", "FOOD", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "FOOD", "WALL"},
                        {"WALL", "FOOD", "FOOD", "FOOD", "FOOD", "FOOD", "WALL", "WALL", "FOOD", "WALL", "WALL", "WALL", "FOOD", "FOOD", "FOOD", "FOOD", "FOOD", "WALL", "FOOD", "WALL"},
                        {"WALL", "WALL", "WALL", "WALL", "WALL", "FOOD", "FOOD", "FOOD", "FOOD", "FOOD", "FOOD", "FOOD", "FOOD", "WALL", "WALL", "WALL", "WALL", "WALL", "FOOD", "WALL"},
                        {"WALL", "WALL", "FOOD", "WALL", "WALL", "FOOD", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "FOOD", "WALL", "WALL", "FOOD", "FOOD", "FOOD", "FOOD", "WALL"},
                        {"WALL", "WALL", "FOOD", "WALL", "WALL", "FOOD", "WALL", "FOOD", "FOOD", "FOOD", "FOOD", "WALL", "FOOD", "FOOD", "FOOD", "FOOD", "WALL", "WALL", "FOOD", "WALL"},
                        {"WALL", "WALL", "FOOD", "FOOD", "FOOD", "FOOD", "FOOD", "FOOD", "FOOD", "FOOD", "WALL", "WALL", "FOOD", "WALL", "WALL", "FOOD", "WALL", "FOOD", "FOOD", "WALL"},
                        {"WALL", "WALL", "FOOD", "WALL", "WALL", "WALL", "FOOD", "WALL", "FOOD", "FOOD", "WALL", "WALL", "FOOD", "WALL", "WALL", "FOOD", "WALL", "WALL", "FOOD", "WALL"},
                        {"WALL", "FOOD", "FOOD", "FOOD", "WALL", "FOOD", "FOOD", "WALL", "FOOD", "WALL", "WALL", "WALL", "FOOD", "WALL", "WALL", "FOOD", "FOOD", "FOOD", "FOOD", "WALL"},
                        {"WALL", "FOOD", "WALL", "FOOD", "WALL", "FOOD", "FOOD", "WALL", "FOOD", "FOOD", "FOOD", "FOOD", "FOOD", "WALL", "WALL", "FOOD", "WALL", "FOOD", "WALL", "WALL"},
                        {"WALL", "FOOD", "WALL", "FOOD", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "FOOD", "WALL", "WALL", "FOOD", "WALL", "FOOD", "FOOD", "WALL"},
                        {"WALL", "FOOD", "WALL", "FOOD", "FOOD", "FOOD", "FOOD", "FOOD", "FOOD", "WALL", "WALL", "WALL", "FOOD", "FOOD", "FOOD", "FOOD", "WALL", "WALL", "FOOD", "WALL"},
                        {"WALL", "FOOD", "WALL", "WALL", "FOOD", "WALL", "WALL", "WALL", "FOOD", "FOOD", "FOOD", "WALL", "WALL", "WALL", "WALL", "FOOD", "FOOD", "FOOD", "FOOD", "WALL"},
                        {"WALL", "FOOD", "FOOD", "WALL", "FOOD", "FOOD", "FOOD", "FOOD", "WALL", "WALL", "FOOD", "WALL", "FOOD", "FOOD", "FOOD", "FOOD", "WALL", "FOOD", "WALL", "WALL"},
                        {"WALL", "WALL", "FOOD", "WALL", "WALL", "FOOD", "WALL", "FOOD", "FOOD", "WALL", "FOOD", "FOOD", "FOOD", "WALL", "WALL", "WALL", "WALL", "FOOD", "FOOD", "WALL"},
                        {"WALL", "WALL", "FOOD", "WALL", "WALL", "FOOD", "WALL", "WALL", "FOOD", "WALL", "WALL", "WALL", "FOOD", "WALL", "WALL", "WALL", "WALL", "WALL", "FOOD", "WALL"},
                        {"WALL", "FOOD", "FOOD", "FOOD", "FOOD", "FOOD", "WALL", "WALL", "FOOD", "FOOD", "FOOD", "FOOD", "FOOD", "FOOD", "FOOD", "FOOD", "FOOD", "FOOD", "FOOD", "WALL"},
                        {"WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL", "WALL"}
                };

/*
        // fill cells with empty cells
        for (int x = 0; x < BOARD_WIDTH; x++)
            for (int y = 0; y < BOARD_HEIGHT; y++)
                cells[x][y] = "EMPTY";


 */
        /* СЛУЧАЙНАЯ КАРТА
        // add walls
        for (int x = 0; x < BOARD_WIDTH; x++) {
            cells[x][0] = "WALL";
            cells[x][BOARD_HEIGHT - 1] = "WALL";
        }
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            cells[0][y] = "WALL";
            cells[BOARD_WIDTH - 1][y] = "WALL";
        }

        // add OBSTACLE and FOOD
        for (int x = 1; x < BOARD_WIDTH - 1; x++)
            for (int y = 1; y < BOARD_HEIGHT - 1; y++) {
                int rand = (int) (Math.random() * 100);
                if ((0 <= rand) && (rand <= 14)) {
                    if (!((x > BOARD_WIDTH - 8) && (y > BOARD_HEIGHT - 8)))
                        cells[x][y] = "OBSTACLE";
                } else if ((19 <= rand) && (rand <= 23))
                    cells[x][y] = "FOOD";
            }

         */

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // включаем сглаживание для более качественной отрисовки
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // рисуем фон
                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                if (GAME_OVER) {
                    Font currentFont = g.getFont();

                    Font newFont = currentFont.deriveFont(currentFont.getSize() * 3F);
                    g2d.setFont(newFont);
                    g2d.setColor(Color.BLUE);
                    g2d.drawString("GAME OVER", 95, 350);
                }
                else if (GAME_WIN){
                    Font currentFont = g.getFont();
                    Font newFont = currentFont.deriveFont(currentFont.getSize() * 3F);
                    g2d.setFont(newFont);
                    g2d.setColor(Color.PINK);
                    g2d.drawString("!!! YOU WIN !!!", 95, 350);
                }
                else {


                    // рисуем Пакмана
                    if(eat)
                        pacman_img = pacman_eat;
                    else
                        switch (pacman.getDirection()) {
                            case "RIGHT" ->
                                    pacman_img = pacman_img_R;
                            case "UP" ->
                                    pacman_img = pacman_img_U;
                            case "LEFT" ->
                                    pacman_img = pacman_img_L;
                            case "DOWN" ->
                                    pacman_img = pacman_img_D;
                        }
                    g2d.drawImage(pacman_img, pacman.getX() - 10, pacman.getY() - 10, CELL_SIZE, CELL_SIZE, null);



                    // рисуем Призраков
                    g2d.setColor(Color.RED);
                    for (int i = 0; i < GHOST_COUNT; i++)
                        g2d.drawImage(ghost_img, ghost[i].getX() - 10, ghost[i].getY() - 10, CELL_SIZE, CELL_SIZE, null);

                    //рисуем карту
                    drawMap(g2d);
                }
            }
        };
        panel.setPreferredSize(new Dimension(200, 100));
        add(panel, BorderLayout.CENTER);
        //add(panel); // добавляем панель на фрейм
        addKeyListener(this); // добавляем слушатель клавиатуры
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        timer = new Timer(10, this); // создаем таймер для обновления положения квадрата
        timer.start(); // запускаем таймер
    }

    @Override
    public void actionPerformed(ActionEvent e){

        // анимация, закрываем рот пакмана
        eat_count +=1;
        if (eat_count==15){
            eat = !eat;
        }
        if (eat_count==30){
            eat = !eat;
            eat_count=0;
        }

        int pacmanX = pacman.getX();
        int pacmanY = pacman.getY();

        int radius = (int) (Math.sqrt(Math.pow(10, 2) / 2));
        int aura = 1;

        // габариты пакмана (нужны так, как мы не можем принять пакмана за материальную точку)
        int[][] pacmanDimensions = new int[2][3];
        switch (pacman.getDirection()) { // куда повернут
            case "LEFT" -> {
                pacmanDimensions[0][0] = pacmanX - radius;
                pacmanDimensions[0][1] = pacmanX - CELL_SIZE / 2 - aura - aura;
                pacmanDimensions[0][2] = pacmanX - radius;

                pacmanDimensions[1][0] = pacmanY + radius;
                pacmanDimensions[1][1] = pacmanY;
                pacmanDimensions[1][2] = pacmanY - radius;
            }
            case "RIGHT" -> {
                pacmanDimensions[0][0] = pacmanX + radius;
                pacmanDimensions[0][1] = pacmanX + CELL_SIZE / 2 + aura + aura;
                pacmanDimensions[0][2] = pacmanX + radius;

                pacmanDimensions[1][0] = pacmanY + radius;
                pacmanDimensions[1][1] = pacmanY;
                pacmanDimensions[1][2] = pacmanY - radius;
            }
            case "UP" -> {
                pacmanDimensions[0][0] = pacmanX - radius;
                pacmanDimensions[0][1] = pacmanX;
                pacmanDimensions[0][2] = pacmanX + radius;

                pacmanDimensions[1][0] = pacmanY - radius;
                pacmanDimensions[1][1] = pacmanY - CELL_SIZE / 2 - aura - aura;
                pacmanDimensions[1][2] = pacmanY - radius;
            }
            case "DOWN" -> {
                pacmanDimensions[0][0] = pacmanX - radius;
                pacmanDimensions[0][1] = pacmanX;
                pacmanDimensions[0][2] = pacmanX + radius;

                pacmanDimensions[1][0] = pacmanY + radius;
                pacmanDimensions[1][1] = pacmanY + CELL_SIZE / 2 + aura + aura;
                pacmanDimensions[1][2] = pacmanY + radius;
            }
            case "STOP" -> {
            }
        }
        //  ЛВ В ПВ  0 1 2
        //  Л  _  П  3 4 5
        //  ЛН Н ПН  6 7 8

        //  UP    :  0,1,2
        //  LEFT  :  0,3,6
        //  RIGHT :  2,5,8
        //  DOWN  :  6,7,8
        //////////////////////
        int[][] pacmanDimensions_inCells = new int[2][3];
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 3; j++)
                pacmanDimensions_inCells[i][j] = (int) pacmanDimensions[i][j] / CELL_SIZE;

        //взаимодействие пакамана со стенами и едой
        for (int i = 0; i < 3; i++) {
            if (((cells[pacmanDimensions_inCells[0][i]][pacmanDimensions_inCells[1][i]]).equals("OBSTACLE"))
                    ||
                    ((cells[pacmanDimensions_inCells[0][i]][pacmanDimensions_inCells[1][i]]).equals("WALL")))
                pacman.setDirection("STOP");
            else if ((cells[pacmanDimensions_inCells[0][i]][pacmanDimensions_inCells[1][i]]).equals("FOOD")) {
                SCORE++;
                cells[pacmanDimensions_inCells[0][i]][pacmanDimensions_inCells[1][i]] = "EMPTY";
            }
        }

        pacman.move(); // перемещаем Пакмана

        ///////////search path

        for (int i = 0; i < GHOST_COUNT; i++) {
            if ((ghost[i].getDirection().equals("STOP")) && (!GAME_OVER) && (!GAME_WIN)) ghost[i].setDirection("UP");

            switch (ghost[i].getDirection()) {
                case "LEFT" -> {
                    if (!isFreePath(ghost[i].getDirection(), ghost[i].getX(), ghost[i].getY())) {
                        boolean G1 = isFreePath("DOWN", ghost[i].getX(), ghost[i].getY());
                        boolean G2 = isFreePath("UP", ghost[i].getX(), ghost[i].getY());

                        if (G1 && G2) {
                            //выбираем куда идти
                            int dx1 = Math.abs(ghost[i].getX() + 10 - pacman.getX());
                            int dx2 = Math.abs(ghost[i].getX() - 10 - pacman.getX());
                            if (dx1 < dx2)
                                ghost[i].setDirection("DOWN");
                            else
                                ghost[i].setDirection("UP");
                        } else if (G1)
                            ghost[i].setDirection("DOWN");//

                        else if (G2)
                            ghost[i].setDirection("UP");//

                        else ghost[i].setDirection("RIGHT");
                    }
                }
                case "RIGHT" -> {
                    if (!isFreePath(ghost[i].getDirection(), ghost[i].getX(), ghost[i].getY())) {
                        boolean G1 = isFreePath("UP", ghost[i].getX(), ghost[i].getY());
                        boolean G2 = isFreePath("DOWN", ghost[i].getX(), ghost[i].getY());

                        if (G1 && G2) {
                            //выбираем куда идти
                            int dx1 = Math.abs(ghost[i].getX() + 10 - pacman.getX());
                            int dx2 = Math.abs(ghost[i].getX() - 10 - pacman.getX());
                            if (dx1 < dx2)
                                ghost[i].setDirection("UP");
                            else
                                ghost[i].setDirection("DOWN");
                        } else if (G1)
                            ghost[i].setDirection("UP");//

                        else if (G2)
                            ghost[i].setDirection("DOWN");//

                        else ghost[i].setDirection("LEFT");
                    }
                }
                case "UP" -> {
                    if (!isFreePath(ghost[i].getDirection(), ghost[i].getX(), ghost[i].getY())) {
                        boolean G1 = isFreePath("LEFT", ghost[i].getX(), ghost[i].getY());
                        boolean G2 = isFreePath("RIGHT", ghost[i].getX(), ghost[i].getY());

                        if (G1 && G2) {
                            //выбираем куда идти
                            int dx1 = Math.abs(ghost[i].getX() + 10 - pacman.getX());
                            int dx2 = Math.abs(ghost[i].getX() - 10 - pacman.getX());
                            if (dx1 < dx2)
                                ghost[i].setDirection("LEFT");
                            else
                                ghost[i].setDirection("RIGHT");
                        } else if (G1)
                            ghost[i].setDirection("LEFT");//

                        else if (G2)
                            ghost[i].setDirection("RIGHT");//

                        else ghost[i].setDirection("DOWN");
                    }
                }
                case "DOWN" -> {
                    if (!isFreePath(ghost[i].getDirection(), ghost[i].getX(), ghost[i].getY())) {
                        boolean G1 = isFreePath("RIGHT", ghost[i].getX(), ghost[i].getY());
                        boolean G2 = isFreePath("LEFT", ghost[i].getX(), ghost[i].getY());

                        if (G1 && G2) {
                            //выбираем куда идти
                            int dx1 = Math.abs(ghost[i].getX() + 10 - pacman.getX());
                            int dx2 = Math.abs(ghost[i].getX() - 10 - pacman.getX());
                            if (dx1 < dx2)
                                ghost[i].setDirection("RIGHT");
                            else
                                ghost[i].setDirection("LEFT");
                        } else if (G1)
                            ghost[i].setDirection("RIGHT");//

                        else if (G2)
                            ghost[i].setDirection("LEFT");//

                        else ghost[i].setDirection("UP");
                    }
                }
            }
        }

        /////////

        for (int i = 0; i < GHOST_COUNT; i++) {
            ghost[i].move();
            if (ghostEatPac(ghost[i], pacman))
                gameOver();
        } // перемещаем приведение и смотрим, поймало ли оно пакмана

        if(SCORE==177){
            gameWin();
            WinWriter();
            SCORE++;
        }

        panel.repaint();         // перерисовываем игровое поле
        System.out.println();
    }



    public boolean ghostEatPac(Ghost ghost, Pacman pacman) {
        return ((ghost.getX() / CELL_SIZE) == (pacman.getX() / CELL_SIZE)) &&
                ((ghost.getY() / CELL_SIZE) == (pacman.getY() / CELL_SIZE));
    }

    public void gameWin(){
        GAME_WIN = true;
        for (int i = 0; i < GHOST_COUNT; i++) {
            pacman.setDirection("STOP");
            for (int j = 0; j < GHOST_COUNT; j++)
                ghost[j].setDirection("STOP");

        }


        System.out.println("YOU WIN");
    }

    public void gameOver() {
        GAME_OVER = true;

        for (int i = 0; i < GHOST_COUNT; i++) {
            pacman.setDirection("STOP");
            for (int j = 0; j < GHOST_COUNT; j++)
                ghost[j].setDirection("STOP");

        }
        System.out.println("GAME OVER");
    }

    public boolean pacChangDir(String oldDir, String newDir) {
        return !oldDir.equals(newDir);
    }

    public boolean isFreePath(String direction, int X, int Y) {

        int radius = (int) (Math.sqrt(Math.pow(10, 2) / 2));
        int aura = 1;

        // габариты объекта (нужны так, как мы не можем принять объект за материальную точку)
        int[][] ghostDimensions = new int[2][3];

        switch (direction) { // куда повернут
            case "LEFT" -> {
                ghostDimensions[0][0] = X - radius;
                ghostDimensions[0][1] = X - CELL_SIZE / 2 - aura - aura;
                ghostDimensions[0][2] = X - radius;

                ghostDimensions[1][0] = Y + radius;
                ghostDimensions[1][1] = Y;
                ghostDimensions[1][2] = Y - radius;
            }
            case "RIGHT" -> {
                ghostDimensions[0][0] = X + radius;
                ghostDimensions[0][1] = X + CELL_SIZE / 2 + aura + aura;
                ghostDimensions[0][2] = X + radius;

                ghostDimensions[1][0] = Y + radius;
                ghostDimensions[1][1] = Y;
                ghostDimensions[1][2] = Y - radius;
            }
            case "UP" -> {
                ghostDimensions[0][0] = X - radius;
                ghostDimensions[0][1] = X;
                ghostDimensions[0][2] = X + radius;

                ghostDimensions[1][0] = Y - radius;
                ghostDimensions[1][1] = Y - CELL_SIZE / 2 - aura - aura;
                ghostDimensions[1][2] = Y - radius;
            }
            case "DOWN" -> {
                ghostDimensions[0][0] = X - radius;
                ghostDimensions[0][1] = X;
                ghostDimensions[0][2] = X + radius;

                ghostDimensions[1][0] = Y + radius;
                ghostDimensions[1][1] = Y + CELL_SIZE / 2 + aura + aura;
                ghostDimensions[1][2] = Y + radius;
            }
            case "STOP" -> {
            }
        }

        //  ЛВ В ПВ  0 1 2
        //  Л  _  П  3 4 5
        //  ЛН Н ПН  6 7 8

        //  UP    :  0,1,2
        //  LEFT  :  0,3,6
        //  RIGHT :  2,5,8
        //  DOWN  :  6,7,8
        //////////////////////

        int[][] Dimensions_inCells = new int[2][3];
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 3; j++)
                Dimensions_inCells[i][j] = (int) ghostDimensions[i][j] / CELL_SIZE;

        for (int i = 0; i < 3; i++)
            if (
                    ((cells[Dimensions_inCells[0][i]][Dimensions_inCells[1][i]]).equals("OBSTACLE"))
                            ||
                            ((cells[Dimensions_inCells[0][i]][Dimensions_inCells[1][i]]).equals("WALL")))
                return false;
        return true;
    }

    public void pause(boolean ONorOFF) {
        pacman.setGAME_OVER(ONorOFF);
        for (int i = 0; i < GHOST_COUNT; i++)
            ghost[i].setGAME_OVER(ONorOFF);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!GAME_OVER)
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> pacman.setDirection("LEFT");
                case KeyEvent.VK_RIGHT -> pacman.setDirection("RIGHT");
                case KeyEvent.VK_UP -> pacman.setDirection("UP");
                case KeyEvent.VK_DOWN -> pacman.setDirection("DOWN");

                case KeyEvent.VK_SPACE -> pause(true);
            }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void drawMap(Graphics2D g2d) {
        // текстуры
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[0].length; y++) {
                switch (cells[x][y]) {
                    case "EMPTY":
                        break;
                    case "FOOD":
                        g2d.setColor(Color.PINK);
                        g2d.fillOval(x * CELL_SIZE + CELL_SIZE / 4 + CELL_SIZE / 6, y * CELL_SIZE + CELL_SIZE / 4 + CELL_SIZE / 6, CELL_SIZE / 4, CELL_SIZE / 4);
                        break;
                    case "WALL":
                        //g2d.setColor(Color.BLUE);
                        //g2d.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);

                        ////
                        g2d.drawImage(wall_img, x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
                        break;
                    case "OBSTACLE":
                        //g2d.setColor(Color.CYAN);
                        //g2d.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);

                        ////
                        g2d.drawImage(wall_img, x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
                        break;
                }
            }
        }
        g2d.drawString("Passed: " + SCORE*100/177+"%", CELL_SIZE*BOARD_WIDTH/2-25, CELL_SIZE*BOARD_HEIGHT+25);
    }
    private void loadImage() {

        // Load images

        ImageIcon ii = new ImageIcon(getClass().getResource("/wall.jpg"));
        wall_img = ii.getImage();

        ii = new ImageIcon(getClass().getResource("/pacman.png"));
        pacman_img_R = ii.getImage();
        pacman_img_D = rotateImage(pacman_img_R,90);
        pacman_img_L = rotateImage(pacman_img_D,90);
        pacman_img_U = rotateImage(pacman_img_L,90);

        pacman_eat = new ImageIcon(getClass().getResource("/pacman_eat.png")).getImage();

        ghost_img = new ImageIcon(getClass().getResource("/ghost.png")).getImage();



    }
    public static Image rotateImage(Image image, double angle) {
        // Преобразуем исходное изображение в BufferedImage
        BufferedImage bimg = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = bimg.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        // Создаем AffineTransform и поворачиваем изображение на заданный угол
        AffineTransform at = new AffineTransform();
        at.rotate(Math.toRadians(angle), bimg.getWidth() / 2, bimg.getHeight() / 2);

        // Создаем новый BufferedImage и возвращаем его в виде Image
        BufferedImage rotatedImg = new BufferedImage(bimg.getWidth(), bimg.getHeight(), bimg.getType());
        Graphics2D g2 = rotatedImg.createGraphics();
        g2.setTransform(at);
        g2.drawImage(bimg, 0, 0, null);
        g2.dispose();

        return rotatedImg;
    }
    public void WinWriter(){
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter((Objects.requireNonNull(PacmanMenu.class.getResource("/players.txt"))).getPath(), true)))) {
            out.print(" "+GHOST_COUNT + ",");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/*
    public static void main(String[] args) {
        Game game = new Game();
        game.setVisible(true);
    }


 */
}