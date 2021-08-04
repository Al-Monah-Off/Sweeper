package sweeper;

 public class Matrix
{
    private Box [][] matrix;

    Matrix(Box defaultBox)
    {
        matrix=new Box[Ranges.getSize().x][Ranges.getSize().y];

        for(Coord coord: Ranges.getAllCoords())//
            matrix [coord.x][coord.y] =defaultBox;
    }

    public Box get(Coord coord)
    {
        if(Ranges.inRange(coord))//проверка границ
        return matrix [coord.x][coord.y];
        return null;
    }
    public void set (Coord coord,Box box)
    {
        if(Ranges.inRange(coord))//проверка границ
        matrix [coord.x][coord.y]=box;
    }
}
