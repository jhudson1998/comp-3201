public class CA
{
    public static void main(String[] args)
    {
        int n = Integer.parseInt(args[0]);
        int r = Integer.parseInt(args[1]);
        if(r < 0 || r > 255)
        {
            System.out.println("Rule must be between 0 and 255");
            System.exit(0);
        }
        String[] rule = new String[8];
        String[] curr = new String[n*2];
        String[] prev = new String[n*2];
        String bin = Integer.toBinaryString(r);
        int j = 7;

        while(bin.length() != 8)
        {
            bin = "0" + bin;
        }

        System.out.println(bin.charAt(1));

        for(int i=0;i<8;i++)
        {
            if(bin.charAt(j) == '1')
            {
                rule[i] = "*";
            }
            else
            {
                rule[i] = " ";
            }
            j-=1;
        }

        for(int i=0;i<n*2;i++)
        {
            if(i == n*2/2)
            {
                curr[i] = "*";
            }
            else
            {
                curr[i] = " ";
            }
        }

        for(int i=0;i<n;i++)
        {
            for(int k=0;k<n*2;k++)
            {
                System.out.print(curr[k]);
            }
            System.out.println("");
            prev = curr.clone();
            curr = nextLine(prev,rule,n);
        }
    }

    public static String[] nextLine(String[] line, String[] rule, int n)
    {
        String[]out = new String[n*2];
        out[0] = " ";
        out[(n*2)-1] = " ";
        for(int i=1;i<(n*2)-1;i++)
        {
            if(line[i-1].equals(" ") && line[i].equals(" ") && line[i+1].equals(" "))
            {
                out[i] = rule[0];
            }
            else if(line[i-1].equals(" ") && line[i].equals(" ") && line[i+1].equals("*"))
            {
                out[i] = rule[1];
            }
            else if(line[i-1].equals(" ") && line[i].equals("*") && line[i+1].equals(" "))
            {
                out[i] = rule[2];
            }
            else if(line[i-1].equals(" ") && line[i].equals("*") && line[i+1].equals("*"))
            {
                out[i] = rule[3];
            }
            else if(line[i-1].equals("*") && line[i].equals(" ") && line[i+1].equals(" "))
            {
                out[i] = rule[4];
            }
            else if(line[i-1].equals("*") && line[i].equals(" ") && line[i+1].equals("*"))
            {
                out[i] = rule[5];
            }
            else if(line[i-1].equals("*") && line[i].equals("*") && line[i+1].equals(" "))
            {
                out[i] = rule[6];
            }
            else if(line[i-1].equals("*") && line[i].equals("*") && line[i+1].equals("*"))
            {
                out[i] = rule[7];
            }
        }
        return out;
    }
}
