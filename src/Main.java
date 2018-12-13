import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) throws Exception {

        final String dir = System.getProperty("user.dir");

        File folderTxt =  Paths.get(dir, "txtFiles").toFile();
        File[] listOfFiles = folderTxt.listFiles();

        File fileCSV = new File(dir +"/csvFiles/statFile.csv");

        Boolean firstTime = false;

        // if file doesnt exists, then create it
        if (!fileCSV.exists()) {
            fileCSV.createNewFile();
            firstTime = true;
        }

        // true = append file
        FileWriter fw = new FileWriter(fileCSV.getAbsoluteFile(), true);
        BufferedWriter bw = new BufferedWriter(fw);

        if(firstTime) {
            bw.write("RngSeed,DeathRate,ImmigrantChanceCooperateWithDifferent,ImmigrantChanceCooperateWithSame,ImmigrantsPerDay,InitialPtr,MutationRate,PlotResolution,SmartChoice,SpaceSize,TickDelay,CC,CD,DC,DD,CC-end,CD-end,DC-end,DD-end\n");
        }

        for (File file : listOfFiles) {
            if (file.isFile()) {
                System.out.println(file.getName());
                readWriteToFile(file, bw);
                bw.write('\n');
            }
        }

        if (bw != null)
            bw.close();

        if (fw != null)
            fw.close();
    }

    public static void readWriteToFile(File fileTXT, BufferedWriter bw) throws IOException {
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
                        bw.write("0,");
                    }else{
                        bw.write("1,");
                    }
                }else{
                    bw.write(d[1] + ",");
                }
            }

            if (i == 14) {
                System.out.println(line);
                String d[] = line.split(", ");
                bw.write(String.join(",", Arrays.copyOfRange(d, 1, 5)));
                bw.write(',');
            }

            beforeLine = line;
            i++;
        }
        System.out.println(beforeLine);
        String d[] = beforeLine.split(", ");
        bw.write(String.join(",", Arrays.copyOfRange(d, 1, 5)));
        br.close();
    }
}
