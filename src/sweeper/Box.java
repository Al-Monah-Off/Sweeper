package sweeper;

public enum Box
{
    ZERO,
    NUM1,
    NUM2,
    NUM3,
    NUM4,
    NUM5,
    NUM6,
    NUM7,
    NUM8,
    BOMB,//бомба
    OPENED,//откр
    CLOSED,//закр
    FLAGED,//флаг
    BOMBED,//взорван
    NOBOMB;//нету бомб

    public Object image;//для хранения картинки

    //увеличить текущее значении Box на 1
    Box getNextNumberBox()
    {
        return Box.values()[this.ordinal() + 1];
    }

    public int getNumber()
    {
        return this.ordinal();
    }
}