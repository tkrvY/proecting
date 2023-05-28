import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PacmanMenu extends JFrame implements ActionListener {

    private JButton newGameButton;
    private JButton tipsButton;
    private JButton exitButton;
    private JLabel usersLabel;

    private ImageIcon icon_ghost;
    public PacmanMenu() throws IOException {
        loadImages();
        setTitle("Pacman");
        setSize(400, 300);
        setLocationRelativeTo(null); // центрируем окно на экране
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(Color.BLACK); // задаем цвет фона
        menuPanel.setLayout(new BorderLayout());

        JLabel title = new JLabel("PACMAN");
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 36)); // задаем стиль шрифта
        title.setForeground(Color.YELLOW); // задаем цвет текста
        title.setHorizontalAlignment(JLabel.CENTER); // выравнивание по центру
        menuPanel.add(title, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.BLACK); // задаем цвет фона
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(this);
        tipsButton = new JButton("Tips");
        tipsButton.addActionListener(this);
        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        buttonsPanel.add(newGameButton);
        buttonsPanel.add(tipsButton);
        buttonsPanel.add(exitButton);
        menuPanel.add(buttonsPanel, BorderLayout.CENTER);

        usersLabel = new JLabel("");
        usersLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14)); // задаем стиль шрифта
        usersLabel.setForeground(Color.YELLOW); // задаем цвет текста
        usersLabel.setHorizontalAlignment(JLabel.CENTER); // выравнивание по центру

        update_usersLabel();
        menuPanel.add(usersLabel, BorderLayout.SOUTH);


        add(menuPanel);
        setVisible(true); // отображаем окно на экране
        String cur_user = getUserName(icon_ghost);
        cur_user+=":";
        update_usersLabel();
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("players.txt", true)))) {
            out.print(cur_user);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void update_usersLabel() throws IOException {
        List<String> usersList = readUserNamesToList();
        StringBuilder sb = new StringBuilder();
        sb.append("<html>Доска достижений:<br>");
        for (String element : usersList) {
            sb.append(element).append("<br>");
        }
        sb.append("</html>");
        String html = sb.toString();

        usersLabel.setText(html);
    }
    public static List<String> readUserNamesToList() throws IOException {
        List<String> lines = new ArrayList<>();

        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(PacmanMenu.class.getResourceAsStream("/players.txt"))))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameButton) {
            int level = chooseLevel(icon_ghost);
            new Game(level);       // открыть окно новой игры
        } else
        if (e.getSource() == tipsButton) {
            new PacmanTips(); // создание объекта и отображение окна с советами
        } else
        if (e.getSource() == exitButton) {
            spaceWriter();
            System.exit(0); // передайте значение 0 в качестве параметра для успешного завершения программы

        }
    }
    public void spaceWriter(){
        deleteLastCharFromFile();
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter((PacmanMenu.class.getResource("/players.txt")).getPath(), true)))) {
            out.print("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void deleteLastCharFromFile() {
        File file = new File((Objects.requireNonNull(PacmanMenu.class.getResource("/players.txt"))).getPath());

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = reader.readLine();
            }

            reader.close();
            sb.deleteCharAt(sb.length() - 1);

            FileWriter writer = new FileWriter(file);
            writer.write(sb.toString());
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static int chooseLevel(ImageIcon icon_ghost) {
        String[] options = {"1", "2", "3", "4"};
        //ImageIcon icon = new ImageIcon("icon_ghost.png");
        int ch = JOptionPane.showOptionDialog(
                null,
                "Выберите уровень сложности",
                "Уровень сложности",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                icon_ghost,
                options,
                options[0]
        );
        // Возвращаем выбранный вариант плюс 1, чтобы получить число от 1 до 4
        return ch + 1;
    }
    public static String getUserName(ImageIcon icon_ghost) {
        return JOptionPane.showInputDialog(
                null,
                "Введите ваш ник:",
                "pacpacpac",
                JOptionPane.QUESTION_MESSAGE,
                icon_ghost,
                null,
                "pacpacpac"
        ).toString();
    }
    public void loadImages(){
        icon_ghost = new ImageIcon(getClass().getResource("/icon_ghost.png"));
    }

    public static void main(String[] args) throws IOException {
        new PacmanMenu();
    }
}