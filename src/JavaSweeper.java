import sweeper.*;
import sweeper.Box;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JavaSweeper extends JFrame
{
    public static JavaSweeper sweeper;
    public static Bot bot;
    private static Game game;
    public static JPanel panel;//панель для отрисовки
    public static JLabel label;
    private int COLS = 15;
    private int ROWS = 15;
    private int BOMBS = 10;
    private final int IMAGE_SIZE = 50;

    public static void main(String[] args)
    {
        sweeper = new JavaSweeper(10,10,10);
    }

    public JavaSweeper(int COLS,int ROWS,int BOMBS ) {
        this.BOMBS = BOMBS;
        this.COLS = COLS;
        this.ROWS = ROWS;
        game = new Game(COLS,ROWS,BOMBS);
        game.start();
        bot = new Bot();
        setImages();
        initLabel();
        initMenu();
        initPanel();
        initFrame();
    }

    private void initMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Меню");
        ///подменю
        JMenu levelMenu = new JMenu("Уровень");
        JMenuItem hardLevel = new JMenuItem("Сложный");
        JMenuItem midLevel = new JMenuItem("Средний");
        JMenuItem easyLevel = new JMenuItem("Легкий");

        levelMenu.add(easyLevel);
        levelMenu.add(midLevel);
        levelMenu.add(hardLevel);

        easyLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sweeper.dispose();
                sweeper = new JavaSweeper(10,10,10);
            }
        });
        midLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sweeper.dispose();
                sweeper = new JavaSweeper(12,12,20);
            }
        });

        hardLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sweeper.dispose();
                sweeper = new JavaSweeper(15,15,50);
            }
        });


        menu.add(levelMenu);
        menuBar.add(menu);
        this.setJMenuBar(menuBar);
    }

    //настройка панели и отрисовка игры
    private void initPanel() {
        panel=new JPanel()//создание
        {
            @Override
            protected void paintComponent(Graphics g)//функция вызывается каждый раз для отрисовки
            {
                super.paintComponent(g);
                for (Coord coord: Ranges.getAllCoords())//перебираем весть список координат
                    g.drawImage((Image) game.getBox(coord).image,
                            coord.x*IMAGE_SIZE, coord.y*IMAGE_SIZE, this);
            }
        };//create


        //обработка мышки
        panel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)//если нажана
            {
                int x=e.getX() / IMAGE_SIZE;//смотрим координаты(куда нажали)
                int y=e.getY() / IMAGE_SIZE;
                Coord coord=new Coord(x,y);
                if (e.getButton()==MouseEvent.BUTTON1)
                    game.pressLeftButton(coord);
                if(e.getButton()==MouseEvent.BUTTON3)
                    game.pressRightButton(coord);
                if(e.getButton()==MouseEvent.BUTTON2)//рестарт
                    game.start();
                label.setText(getMassage());
                panel.repaint();
            }
        });

        panel.setPreferredSize(new Dimension(
                Ranges.getSize().x*IMAGE_SIZE,
                Ranges.getSize().y*IMAGE_SIZE));//set size
        add(panel);// from JFrame
    }

    //настройка окна
    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//что бы не висела в трее
        setTitle("JavaSweeper");//заголовок
        setResizable(false);//запрет на изменение размера
        setVisible(true);//показать
        setIconImage(getImage("icon"));
        pack();//устанавливает размер окна достаточный для отображения всех компонентов
        setLocationRelativeTo(null);//окно по центру
    }

    public static String getMassage()
    {
        switch (game.getState())
        {
            case PLAYED: return "Осторожно бомбы!";
            case BOMBED: return "Не повезло :(";
            case WINNER:return "Это победа!";
            default: return "Welcome!";
        }
    }
    private void initLabel() {
        label=new JLabel("Welcome!");
        Font font= new Font("Verdana",Font.BOLD, 12);
        label.setFont(font);
        add(label,BorderLayout.SOUTH);
    }

    /*private void initButton()
    {
        botButton=new JButton("Бот");
        botButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                   bot.Alg1();
                   Game.checkWinner();
                   if (Game.state == GameState.PLAYED) bot.Alg2();
                   else return;
                   panel.repaint();
            }
        });
        botButton.setSize(20,40);
        add(botButton,BorderLayout.BEFORE_FIRST_LINE);
    }*/

    private void setImages () {
        for(sweeper.Box box : Box.values())//для всех элементов перечисления Box
            box.image=getImage(box.name().toLowerCase());//объекту image присваиваем картинку
    }
    //вернуть указанное изображение
    private Image getImage(String name) {
        String filename ="img/" +name + ".png";//путь
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));//используем "папку с ресурсами"
        return icon.getImage();
    }
}
