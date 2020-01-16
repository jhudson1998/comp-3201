import java.awt.Color;
import java.lang.Math;

public class Life
{
    private Picture pic;
    private int x,y,n;
    private int mag;
    private int[][] curr;
    private int[][] prev;
    private char init;

    public Life(int x, int y, int n, char init)
    {
        this.mag = 10;
        this.x = x;
        this.y = y;
        this.n = n;
        this.init = init;
        pic = new Picture(x*mag,y*mag);
        curr = new int[x][y];
        prev = new int[x][y];
        setup(init,n);
    }

    private void drawCell(int i, int j)
    {
        Color col;
        if(curr[i][j] == 0)
        {
            col = Color.WHITE;
        }
        else
        {
            col = Color.BLACK;
        }

        for (int offsetX = 0; offsetX < mag; offsetX++)
        {
            for (int offsetY = 0; offsetY < mag; offsetY++)
            {
                // set() colours an individual pixel
                pic.set((i*mag)+offsetX,
                        (j*mag)+offsetY, col);
            }
        }
    }

    public void show()
    {
        pic.show();     // without calling this the pic will not show
    }

    public void setup(char init, int n)
    {
        if(init == 'R')
        {
            for (int i = 0; i < x; i++)
            {
                for (int j = 0; j < y; j++)
                {
                    double rand = Math.random();
                    if(rand < 0.1)
                    {
                        curr[i][j] = 1;
                    }
                    else
                    {
                        curr[i][j] = 0;
                    }
                    drawCell(i,j);
                }
            }
            show();
        }
        else if(init == 'G')
        {

        }
        else
        {
            System.out.println("Initialization must be either 'G' for Glider or 'R' for random");
            System.exit(0);
        }

        prev = curr;

        for(int i=0;i<n;i++)
        {

            for(int j=0;j<y;j++)
            {

                for(int k=0;k<x;k++)
                {
                    int neighbours = 0;
                    int a;
                    int b;
                    int c;
                    int d;
                    if(k-1 == -1)
                    {
                        a = x-1;
                    }
                    else
                    {
                        a = k-1;
                    }
                    if(j-1 == -1)
                    {
                        b = x-1;
                    }
                    else
                    {
                        b = j-1;
                    }
                    if(k+1 == x)
                    {
                        c = 0;
                    }
                    else
                    {
                        c = k+1;
                    }
                    if(j+1 == x)
                    {
                        d = 0;
                    }
                    else
                    {
                        d = j+1;
                    }

                    if(prev[a][b] == 1)
                    {
                        neighbours += 1;
                    }
                    if(prev[k][b] == 1)
                    {
                        neighbours += 1;
                    }
                    if(prev[c][b] == 1)
                    {
                        neighbours += 1;
                    }
                    if(prev[a][j] == 1)
                    {
                        neighbours += 1;
                    }
                    if(prev[c][j] == 1)
                    {
                        neighbours += 1;
                    }
                    if(prev[a][d] == 1)
                    {
                        neighbours += 1;
                    }
                    if(prev[k][d] == 1)
                    {
                        neighbours += 1;
                    }
                    if(prev[c][d] == 1)
                    {
                        neighbours += 1;
                    }

                    if(prev[k][j] == 1 && neighbours < 2)
                    {
                        curr[k][j] = 0;
                    }
                    else if(prev[k][j] == 1 && (neighbours == 2 || neighbours == 3))
                    {
                        curr[k][j] = 1;
                    }
                    else if(prev[k][j] == 1 && neighbours > 3)
                    {
                        curr[k][j] = 0;
                    }
                    else if(prev[k][j] == 0 && neighbours == 3)
                    {
                        curr[k][j] = 1;
                    }
                    else
                    {
                        curr[k][j] = prev[k][j];
                    }
                    drawCell(k,j);
                }
            }
            show();
            prev = curr;
            try
            {
                Thread.sleep(100);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
    {
        int x = Integer.parseInt(args[0]);
        int y = x;
        int n = Integer.parseInt(args[1]);
        char init = args[2].charAt(0);

        Life life = new Life(x,y,n,init);
    }
}
