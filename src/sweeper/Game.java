package sweeper;

public class Game
{
    public static Bomb bomb;//поле бомб     --bot_fail
    public static Flag flag;//внешне поле   --bot_fail
    public static GameState state;  //-bot
//к-тор принимает размеры окна и кол-во бомб
    public Game(int cols,int rows, int bombs)
    {
        Ranges.setSize(new Coord(cols,rows));//устаналиваем размер игрового поля
        bomb=new Bomb(bombs);
        flag=new Flag();
    }

    public static void start()
    {
        bomb.start();
        flag.start();
        state=GameState.PLAYED;
    }

    //возвращает значение Box в указанных координатах
    //что ресуем в определенном месте экрана?
    public Box getBox(Coord coord)
    {
        if(flag.get(coord)==Box.OPENED)//если верхнее поле открыто
            return bomb.get(coord);//смотрим что под ним
        else
            return  flag.get(coord);
    }

    public static void pressLeftButton(Coord coord)
    {
        if(gameOver())return;
        openBox(coord);
        checkWinner();
    }

    private static boolean gameOver() {
        if(state==GameState.PLAYED)
            return false;
        start();
        return true;
    }

    static void openBox(Coord coord)
    {
        switch (flag.get(coord))
        {
            case OPENED:setOpenedToClosedBoxesAroundNumber(coord);return;
            case FLAGED:return;
            case CLOSED:
                switch (bomb.get(coord)) {
                    case ZERO:
                        openBoxesAround(coord);
                        return;
                    case BOMB:
                        openBombs(coord);
                        return;
                    default:
                        flag.setOpenedToBox(coord);
                        return;
                }

        }
    }
    static void setOpenedToClosedBoxesAroundNumber(Coord coord)
    {
        if(bomb.get(coord)!=Box.BOMB)
        if(flag.getCountOfFlagedBoxesAround(coord)== bomb.get(coord).getNumber())
            for (Coord around:Ranges.getCoordsAround(coord))
                if (flag.get(around)==Box.CLOSED)
                    openBox(around);
    }

    static void openBombs(Coord bombed)
    {
        state=GameState.BOMBED;
        flag.setBombedToBox(bombed);
        for (Coord coord: Ranges.getAllCoords())
            if(bomb.get(coord)==Box.BOMB)
                flag.setOpenedToClosedBox(coord);
            else
                flag.setNoBombToFlagedSafeBox(coord);
    }

    //рекурсивное открытие пустых клеток
    static void openBoxesAround(Coord coord)
    {
        flag.setOpenedToBox(coord);
        for (Coord around:Ranges.getCoordsAround(coord))
            openBox(around);
    }

    public void pressRightButton(Coord coord)
    {
        if(gameOver())return;
        flag.toggleFlagedToBox(coord);
    }

    public GameState getState()
    {
        return state;
    }
    public static void checkWinner()
    {
        if(state==GameState.PLAYED)
            if(flag.getCountOfClosedBoxes()==bomb.getTotalBombs()) {
                state = GameState.WINNER;

            }
    }
}
