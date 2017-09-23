package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandExecutor {

    private static CommandExecutor commander = new CommandExecutor();

    private CommandExecutor() {
    }

    public static CommandExecutor getInstance(){
        return commander;
    }

    public void exec(String command, String... parameters){
        try {
            String s = null;
            command = command + " " + Stream.of(parameters).collect(Collectors.joining(" "));
            System.out.println(command);
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(process.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(process.getErrorStream()));

            System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
            System.out.println();

            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.err.println(s);
            }
            System.out.println();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
