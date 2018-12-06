import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) throws Exception {

        if (args.length != 1) {
            System.out.println("Please insert number of txt files...");
            return;
        }

        final String dir = System.getProperty("user.dir");
        File fileCSV = Paths.get(dir, "csvFiles", "statFile.csv").toFile();
        PrintWriter pw = new PrintWriter(fileCSV);
        pw.print("RngSeed,DeathRate,ImmigrantChanceCooperateWithDifferent,ImmigrantChanceCooperateWithSame,ImmigrantsPerDay,InitialPtr,MutationRate,PlotResolution,SmartChoice,SpaceSize,TickDelay,CC,CD,DC,DD,CC-end,CD-end,DC-end,DD-end\n");
        for (int i = 1; i <= Integer.valueOf(args[0]); i++) {
            File fileTXT = Paths.get(dir, "txtFiles", "statFile" + i + ".txt").toFile();
            readWriteToFile(fileTXT, pw);
        }
        pw.close();
    }

    public static void readWriteToFile(File fileTXT, PrintWriter pw) throws IOException {
        FileInputStream fis = new FileInputStream(fileTXT);

        //Construct BufferedReader from InputStreamReader
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        String line = null;
        String beforeLine = null;
        int i = 0;

        while ((line = br.readLine()) != null) {
            if (i > 0 && i < 12) {
                String d[] = line.split(": ");
                if(i == 9){
                    if(d[1].equals("false")){
                        pw.print("0,");
                    }else{
                        pw.print("1,");
                    }
                }else{
                    pw.print(d[1] + ",");
                }
            }

            if (i == 14) {
                System.out.println(line);
                String d[] = line.split(", ");
                pw.print(String.join(",", Arrays.copyOfRange(d, 1, 5)));
                pw.print(',');
            }

            beforeLine = line;
            i++;
        }
        System.out.println(beforeLine);
        String d[] = beforeLine.split(", ");
        pw.println(String.join(",", Arrays.copyOfRange(d, 1, 5)));
        br.close();
    }
}
