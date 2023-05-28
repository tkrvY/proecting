import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

public class PacmanTips extends JFrame {

    public PacmanTips() {
        setTitle("Pacman Tips");
        setSize(400, 300);
        setLocationRelativeTo(null); // центрируем окно на экране
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // закрыть только это окно, не всю программу

        JPanel tipsPanel = new JPanel();
        tipsPanel.setBackground(Color.BLACK); // задаем цвет фона
        tipsPanel.setLayout(new BorderLayout());

        JLabel title = new JLabel("PACMAN TIPS");
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 24)); // задаем стиль шрифта
        title.setForeground(Color.YELLOW); // задаем цвет текста
        title.setHorizontalAlignment(JLabel.CENTER); // выравнивание по центру
        tipsPanel.add(title, BorderLayout.NORTH);

        JLabel tips = new JLabel("<html><body>"
                + "1. Если вы соберете всю еду то выиграете<br>"
                + "2. Пакман и призраки двигаются с одинаковой скоростью. Ваше преимущество - что они двигаются по алгоритму и вы можете их обмануть.<br>"
                + "3. Старайтесь держаться подальше от призраков и не стоять в тупике когда они близко. У вас должен быть путь для отступления<br>"
                + "4. В начале игры старайтесь быстрее убежать из центра, иначе вас быстро поймают"
                + "</body></html>");
        //tips.setFont(new Font("Arial", Font.PLAIN, 14)); // задаем стиль шрифта
        tips.setFont(new Font("Comic Sans MS", Font.PLAIN, 14)); // задаем стиль шрифта
        tips.setForeground(Color.YELLOW); // задаем цвет текста

        tips.setHorizontalAlignment(JLabel.LEFT); // выравнивание по левому краю
        tips.setVerticalAlignment(JLabel.TOP); // выравнивание по верхнему краю
        tipsPanel.add(tips, BorderLayout.CENTER);

        add(tipsPanel);
        setVisible(true); // отображаем окно на экране
    }

}
