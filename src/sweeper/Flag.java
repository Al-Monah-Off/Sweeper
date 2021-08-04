package sweeper;

public class Flag
{
    public static Matrix flagMap;//"верхняя" матрица
    private int countOfClosedBoxes;//количество закрытых клеток

    void start()
    {
        flagMap=new Matrix(Box.CLOSED);//изначально все ячейки закрыты
        countOfClosedBoxes=Ranges.getSize().x*Ranges.getSize().y;
    }
    public Box get(Coord coord)
    {
        return flagMap.get(coord);
    }

    void setOpenedToBox(Coord coord)
    {
        flagMap.set(coord,Box.OPENED);
        countOfClosedBoxes--;
    }

    public void setFlagedToBox(Coord coord)
    {
        flagMap.set(coord,Box.FLAGED);
    }

    public void toggleFlagedToBox(Coord coord)
    {
        switch (flagMap.get(coord))
        {
            case FLAGED:setClosedToBox(coord);break;
            case CLOSED:setFlagedToBox(coord);break;
        }
    }

    private void setClosedToBox(Coord coord)
    {
        flagMap.set(coord,Box.CLOSED);
    }

    int getCountOfClosedBoxes()
    {
        return countOfClosedBoxes;
    }

    void setBombedToBox(Coord coord)
    {
        flagMap.set(coord,Box.BOMBED);
    }

    void setOpenedToClosedBox(Coord coord)
    {
         if (flagMap.get(coord)==Box.CLOSED)
             flagMap.set(coord,Box.OPENED);
    }

    void setNoBombToFlagedSafeBox(Coord coord)
    {
        if(flagMap.get(coord)==Box.FLAGED)
            flagMap.set(coord,Box.NOBOMB);
    }


    int getCountOfFlagedBoxesAround(Coord coord)
    {
        int count=0;
        for (Coord around:Ranges.getCoordsAround(coord))
            if (flagMap.get(around)==Box.FLAGED)
                count++;
            return count;
    }
}
