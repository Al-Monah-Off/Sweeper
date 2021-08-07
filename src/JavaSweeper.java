import sweeper.*;
import sweeper.Box;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JavaSweeper extends JFrame {
    public static JavaSweeper sweeper;
    public static Bot bot;
    private static Game game;
    public static JPanel panel;//панель для отрисовки
    public static JLabel label;
    private int COLS = 15;
    private int ROWS = 15;
    private int BOMBS = 10;
    private final int IMAGE_SIZE = 50;

    public static void main(String[] args) {
        sweeper = new JavaSweeper(10, 10, 10);
    }

    public JavaSweeper(int COLS, int ROWS, int BOMBS) {
        this.BOMBS = BOMBS;
        this.COLS = COLS;
        this.ROWS = ROWS;
        game = new Game(COLS, ROWS, BOMBS);
        game.start();
        bot = new Bot();
        setImages();
        initLabel();
        initMenu();
        initPanel();
        initFrame();
    }

    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Меню");
        JMenu levelMenu = new JMenu("Уровень");
        JMenuItem hardLevel = new JMenuItem("Сложный");
        JMenuItem middleLevel = new JMenuItem("Средний");
        JMenuItem easyLevel = new JMenuItem("Легкий");
        JMenuItem botMenu = new JMenuItem("Бот");

        levelMenu.add(easyLevel);
        levelMenu.add(middleLevel);
        levelMenu.add(hardLevel);
        menu.add(levelMenu);
        menu.add(botMenu);
        menuBar.add(menu);

        easyLevel.addActionListener(e -> {
            sweeper.dispose();
            sweeper = new JavaSweeper(10, 10, 10);
        });
        middleLevel.addActionListener(e -> {
            sweeper.dispose();
            sweeper = new JavaSweeper(12, 12, 20);
        });
        hardLevel.addActionListener(e -> {
            sweeper.dispose();
            sweeper = new JavaSweeper(15, 15, 50);
        });

        botMenu.addActionListener(e -> {
            bot.Alg1();
            Game.checkWinner();
            if (Game.state == GameState.PLAYED) bot.Alg2();
            else return;
            panel.repaint();
        });



        this.setJMenuBar(menuBar);
    }

    private void initPanel() {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g)//функция вызывается каждый раз для отрисовки
            {
                super.paintComponent(g);
                for (Coord coord : Ranges.getAllCoords())//перебираем весть список координат
                    g.drawImage((Image) game.getBox(coord).image,
                            coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this);
            }
        };//create


        //обработка мышки
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                int x = e.getX() / IMAGE_SIZE;//смотрим координаты(куда нажали)
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x, y);
                if (e.getButton() == MouseEvent.BUTTON1)
                    game.pressLeftButton(coord);
                if (e.getButton() == MouseEvent.BUTTON3)
                    game.pressRightButton(coord);
                if (e.getButton() == MouseEvent.BUTTON2)//рестарт
                    game.start();
                label.setText(getMassage());
                panel.repaint();
            }
        });

        panel.setPreferredSize(new Dimension(
                Ranges.getSize().x * IMAGE_SIZE,
                Ranges.getSize().y * IMAGE_SIZE));//set size
        add(panel);// from JFrame
    }

    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//что бы не висела в трее
        setTitle("JavaSweeper");//заголовок
        setResizable(false);//запрет на изменение размера
        setVisible(true);//показать
        setIconImage(getImage("icon"));
        pack();//устанавливает размер окна достаточный для отображения всех компонентов
        setLocationRelativeTo(null);//окно по центру
    }

    public static String getMassage() {
        switch (game.getState()) {
            case PLAYED:
                return "Осторожно бомбы!";
            case BOMBED:
                return "Не повезло :(";
            case WINNER:
                return "Это победа!";
            default:
                return "Welcome!";
        }
    }

    private void initLabel() {
        label = new JLabel("Welcome!");
        Font font = new Font("Verdana", Font.BOLD, 12);
        label.setFont(font);
        add(label, BorderLayout.SOUTH);
    }

    private void setImages() {
        for (sweeper.Box box : Box.values())//для всех элементов перечисления Box
            box.image = getImage(box.name().toLowerCase());//объекту image присваиваем картинку
    }

    private Image getImage(String name) {
        String fileName = "img/" + name + ".png";//путь
        ImageIcon icon = new ImageIcon(getClass().getResource(fileName));//используем "папку с ресурсами"
        return icon.getImage();
    }
}
