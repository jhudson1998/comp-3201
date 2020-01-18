import java.awt.Color;
import java.lang.Math;
import java.io.*;

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
            col = Color.BLACK;
        }
        else
        {
            col = Color.YELLOW;
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

    public int checkEdge(int edge)
    {
        if(edge < 0)
        {
            return x-1;
        }
        else if(edge > (x-1))
        {
            return 0;
        }
        else
        {
            return edge;
        }
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
                    if(rand < 0.2)
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
            int j = 0;
            if(x <= 38)
            {
                System.out.println("Grid size must be at least 38 to initialize with Glider");
                System.exit(0);
            }
            try (BufferedReader br = new BufferedReader(new FileReader("gosper.txt")))
            {
                String line;
                int count = 0;
                while ((line = br.readLine()) != null) {
                    for(int i=0; i < line.length(); i++)
                    {
                        if(line.charAt(i) == '.')
                        {
                            curr[i][j] = 0;
                        }
                        else if(line.charAt(i) == 'O')
                        {
                            curr[i][j] = 1;
                        }
                        drawCell(i,j);
                    }
                    j += 1;
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            for(int i=38; i<x; i++)
            {
                for(int k=0; k<9; k++)
                {
                    curr[i][k] = 0;
                    drawCell(i,k);
                }
            }
            for(int i=0; i<x; i++)
            {
                for(int k=9; k<x; k++)
                {
                    curr[i][k] = 0;
                    drawCell(i,k);
                }
            }
            show();
        }
        else
        {
            System.out.println("Initialization must be either 'G' for Glider or 'R' for random");
            System.exit(0);
        }

        for(int i=0; i<x; i++)
        {
            for(int j=0; j<y; j++)
            {
                prev[i][j]=curr[i][j];
            }
        }

        for(int h=0;h<n;h++)
        {

            for(int i=0;i<x;i++)
            {

                for(int j=0;j<y;j++)
                {
                    int neighbours = 0;
                    int a = checkEdge(i-1);
                    int b = checkEdge(j-1);
                    int c = checkEdge(i+1);
                    int d = checkEdge(j+1);

                    if(prev[a][b] == 1)
                    {
                        neighbours += 1;
                    }
                    if(prev[a][j] == 1)
                    {
                        neighbours += 1;
                    }
                    if(prev[a][d] == 1)
                    {
                        neighbours += 1;
                    }
                    if(prev[i][b] == 1)
                    {
                        neighbours += 1;
                    }
                    if(prev[i][d] == 1)
                    {
                        neighbours += 1;
                    }
                    if(prev[c][b] == 1)
                    {
                        neighbours += 1;
                    }
                    if(prev[c][j] == 1)
                    {
                        neighbours += 1;
                    }
                    if(prev[c][d] == 1)
                    {
                        neighbours += 1;
                    }

                    if((prev[i][j] == 1) && (neighbours < 2))
                    {
                        curr[i][j] = 0;
                    }
                    else if((prev[i][j] == 1) && ((neighbours == 2) || (neighbours == 3)))
                    {
                        curr[i][j] = 1;
                    }
                    else if((prev[i][j] == 1) && (neighbours > 3))
                    {
                        curr[i][j] = 0;
                    }
                    else if((prev[i][j] == 0) && (neighbours == 3))
                    {
                        curr[i][j] = 1;
                    }
                    drawCell(i,j);
                }
            }
            show();
            for(int i=0; i<x; i++)
            {
                for(int j=0; j<y; j++)
                {
                    prev[i][j]=curr[i][j];
                }
            }
            try
            {
                Thread.sleep(30);
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
