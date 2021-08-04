package sweeper;

public class Bomb
{
    private Matrix bombMap;//МАТРИЦА(массив) С БОМБАМИ
    private int totalBombs;//кОЛ-ВО БОМБ

    Bomb (int totalBombs)
    {
        this.totalBombs=totalBombs;
    }



    void start()
    {
        bombMap= new Matrix(Box.ZERO);
        for(int j=0; j<totalBombs;j++)//вызывает метод placeBomb() заданное кол-во раз
        placeBomb();
    }
    //ВОЗВРАЩАЕТ ЧТО НАХОДИТСЯ В КООРДИНАТЕ МАТРИЦЫ
    public Box get(Coord coord)
    {
        return bombMap.get(coord);
    }

    //проверка на допустимое значение кол-ва бомб
    private void fixBombsCount()
    {
        int maxBombs=Ranges.getSize().x * Ranges.getSize().y / 2;
        if (totalBombs > maxBombs) totalBombs= maxBombs;
    }

    //создает одну бомбу на поле со случайной координатой
    private void placeBomb()
    {
        while (true)
        {
            Coord coord = Ranges.getRandomCoord();//генерируем случайную координату
            if (Box.BOMB==bombMap.get(coord))//если там уже есть бомба
                continue;//новая попытка
            bombMap.set(coord,Box.BOMB);//нет бомбы? - записываем,
            incNumbersAroundBomb(coord);//увеличиваем цифры вокруг новой бомбы,
            break;//выходим
        }

    }

    private void incNumbersAroundBomb(Coord coord)
    {
        for (Coord around : Ranges.getCoordsAround(coord))
            if(Box.BOMB!= bombMap.get(around))//если в текущей координате around нет бомбы
                bombMap.set(around, bombMap.get(around).getNextNumberBox());//записываем новое число
    }

    int getTotalBombs()
    {
        return totalBombs;
    }
}
