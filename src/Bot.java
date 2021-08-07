import sweeper.*;

public  class Bot
{
    public static int WinCount=0;

    void setFlag(Coord coord)
    {
        if(Ranges.inRange(coord))
        for (int x = coord.x-1; x<= coord.x+1;x++)
            for (int y = coord.y-1; y<= coord.y+1;y++)
            {
                if(Game.flag.get(coord)!=Box.CLOSED)
                if(Game.flag.get(new Coord(x,y))==Box.CLOSED)
                Flag.flagMap.set(new Coord(x,y), Box.FLAGED);
            }
    }


    int getCountFlagedTileAround(Coord coord)
    {
        int count=0;


        for (int x = coord.x-1; x<= coord.x+1;x++)
            for (int y = coord.y-1; y<= coord.y+1;y++)
                 if (Game.flag.get(new Coord(x, y)) == Box.FLAGED)
                     count++;
                 return count;
    }

    int getCountClosedTileAround(Coord coord)
    {
        int count=0;
        for (int x = coord.x-1; x<= coord.x+1;x++)
            for (int y = coord.y-1; y<= coord.y+1;y++) {
                if (Game.flag.get(new Coord(x, y)) == Box.CLOSED ||
                        Game.flag.get(new Coord(x, y)) == Box.FLAGED)
                    count++;
            }
            return count;
    }



    public void Alg1()
    {

        for(Coord coord:Ranges.getAllCoords())
            if(Game.bomb.get(coord).ordinal() == getCountClosedTileAround(coord))
                    setFlag(coord);
    }

    public void Alg2()
    {
        //if(Ranges.inRange(coord))

        for(Coord coord:Ranges.getAllCoords())
        {
            if(Game.state==GameState.WINNER)
            {
                break;
            }
            for (Coord around : Ranges.getCoordsAround(coord))
                if (Game.flag.get(coord) != Box.FLAGED &&
                        Game.flag.get(coord) != Box.CLOSED)
                    if (getCountFlagedTileAround(coord) == Game.bomb.get(coord).ordinal())
                        Game.pressLeftButton(around);
                        Game.checkWinner();
                        if(Game.state==GameState.WINNER)WinCount++;
                        JavaSweeper.label.setText(JavaSweeper.getMassage()+" Бот выйграл: "+ WinCount);
                        JavaSweeper.panel.repaint();

        }
    }
}
