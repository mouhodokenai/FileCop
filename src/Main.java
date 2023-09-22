import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main{

    public static void main(String[] args) {
        String File1 = "src/filee1";
        String copyFile1 = "src/filee1copy";
        String File2 = "src/filee2";
        String copyFile2 = "src/filee2copy";

        long startTime = System.currentTimeMillis();
        CopySequential(File1, copyFile1);
        CopySequential(File2, copyFile2);
        long endTime = System.currentTimeMillis();
        long sequentialTime = endTime - startTime;

        startTime = System.currentTimeMillis();
        CopyParallel(File1, copyFile1);
        CopyParallel(File2, copyFile2);
        endTime = System.currentTimeMillis();
        long parallelTime = endTime - startTime;

        System.out.println("\nВремя последовательной копии " + sequentialTime + " ms");

        System.out.println("\nВремя парралельной копии " + parallelTime + " ms");
    }

    public static void CopySequential(String sourcePath, String destinationPath) {
        try (FileReader reader = new FileReader(sourcePath);
             FileWriter writer = new FileWriter(destinationPath)) {
            int character;
            while ((character = reader.read()) != -1) {
                writer.write(character);
            }
            System.out.println("\nПоследовательная копия завершена ");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void CopyParallel(String sourcePath, String destinationPath) {
        Thread copyThread = new Thread(() -> {
            try (FileReader reader = new FileReader(sourcePath);
                 FileWriter writer = new FileWriter(destinationPath)) {
                int character;
                while ((character = reader.read()) != -1) {
                    writer.write(character);
                }
                System.out.println("\nПараллельная копия завершена ");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });

        copyThread.start();
        try {
            copyThread.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }


}