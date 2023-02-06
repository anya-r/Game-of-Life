import java.awt.*;

public class LifeCell
{
    /** the current state (alive or dead) of a particular cell */
	private boolean aliveNow;
    private Color color = Color.BLUE;
	/** the next state / generation (alive or dead) of a particular cell */
    private boolean aliveNext;



    public LifeCell()
    {
        color = Color.BLUE;
        this.aliveNow = false;
        this.aliveNext = false;
    }
    public LifeCell(Boolean aliveNow, Boolean aliveNext)
    {
        color = Color.BLUE;
        this.aliveNow = aliveNow;
        this.aliveNext = aliveNext;
    }

    public boolean isAliveNow() { return aliveNow; }

    public void    setAliveNow(boolean a) { aliveNow = a; }

    public boolean isAliveNext() { return aliveNext; }

    public void    setAliveNext(boolean a) { aliveNext = a; }

    public void setColor(Color c)
    {
        color = c;
    }

    public Color getColor()
    {
        return this.color;
    }

    public Boolean getAliveNextValue()
    {
        return this.aliveNext;
    }

    public Boolean getAliveNowValue()
    {
        return this.aliveNow;
    }
}
