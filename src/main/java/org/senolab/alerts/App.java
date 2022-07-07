package org.senolab.alerts;

import org.senolab.alerts.service.CommandLineProcessor;
import org.senolab.alerts.util.InstructionsUtil;

public class App {

    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println(InstructionsUtil.printInstructions());
            System.exit(0);
        }
        CommandLineProcessor commandLineProcessor = new CommandLineProcessor(args);
        commandLineProcessor.execute();
    }
}
