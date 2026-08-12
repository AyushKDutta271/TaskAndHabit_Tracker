package userInterface;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentFileReader {

    public static void readFile(String file)
    {

        try(BufferedReader br = new BufferedReader(new FileReader(file)))
        {
           String line;
           while((line=br.readLine())!=null)
           {
            Thread.sleep(4000);
            System.out.println(line);
           }
        }catch(FileNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        catch(InterruptedException ex)
        {
            ex.printStackTrace();
        }
    }
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(2);

        String file1="src\\file.txt";
        String file2="src\\file1.txt";

        String[] files = {file1,file2};

        for(String file: files)
        {
            es.execute(()->readFile(file));
        }
        
        es.shutdown();
       
    }
}
