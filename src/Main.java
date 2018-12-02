import java.io.*;


public class Main {
    public static void main(String[] args) throws Exception {

        if(args.length < 1) {
            System.out.println("Please insert number of txt files...");
            return;
        }

        final String dir = System.getProperty("user.dir");

        for(int i = 1; i <= Integer.valueOf(args[0]); i++) {
        File fileTXT = new File(dir +"/txtFiles" +"/statFile" + i + ".txt");
        File fileCSV =  new File(dir + "/csvFiles"+"/statFile" + i + ".csv");
        readWriteToFile(fileTXT, fileCSV);
        }
    }

    public  static void readWriteToFile(File fileTXT, File fileCSV) throws IOException {
        FileInputStream fis = new FileInputStream(fileTXT);
        OutputStream os = new FileOutputStream(fileCSV);

        //Construct BufferedReader from InputStreamReader
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        String line = null;
        String beforeLine = null;
        int i = 0;

        while ((line = br.readLine()) != null) {

            if (i > 0 && i < 12) {
                String d[] = line.split(": ");
                String data = d[0] +","+d[1] + "\n";
                os.write(data.getBytes(), 0, data.length());
            }

            if (i == 14) {
                String d[] = line.split(", ");
                String data = d[1]+","+d[2]+","+d[3]+","+d[4] + "\n";
                os.write(data.getBytes(), 0, data.length());
            }

            beforeLine = line;
            i++;
        }

        String d[] = beforeLine.split(", ");
        String data = d[1]+","+d[2]+","+d[3]+","+d[4] + "\n";
        os.write(data.getBytes(), 0, data.length());
        br.close();
    }
}
