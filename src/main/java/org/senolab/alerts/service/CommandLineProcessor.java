package org.senolab.alerts.service;

import org.senolab.alerts.model.AlertDefinition;
import org.senolab.alerts.util.InstructionsUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CommandLineProcessor {

    AlertDefinition alertDefinition;
    String command;

    public CommandLineProcessor(String[] args) {
        command = args[0].toLowerCase();
        switch (command) {
            case "listallalerts" -> processListAllAlerts(args);
            case "getanalert" -> processGetAnAlert(args);
            case "createanalert" -> processCreateAnAlert(args);
            case "deleteanalert" -> processDeleteAnAlert(args);
            case "deletealerts" -> processDeleteAlerts(args);
            default -> {
                System.out.println(InstructionsUtil.printIncorrectParameterMessage());
                System.exit(0);
            }
        }
    }

    private void processDeleteAlerts(String[] params) {
        if(params.length == 3) {
            alertDefinition = alertDefinition = new AlertDefinition(params[1], false, null);
        } else if(params.length == 4) {
            String[] options = params[3].split(",");
            if(options.length == 1) {
                switch (options[0].toLowerCase().substring(0,4)) {
                    case "json" -> alertDefinition = new AlertDefinition(params[1], true, null);
                    case "acco" -> {
                        alertDefinition = new AlertDefinition(params[1], false, options[0].split("=")[1]);
                    }
                    default -> {
                        System.out.println("You specify incorrect options for 'getanalert' command. Please refer below for the correct command.");
                        System.out.println(InstructionsUtil.printDeleteAlertsInstruction());
                        System.exit(0);
                    }
                }
            } else if(options.length == 2) {
                alertDefinition = new AlertDefinition(params[1], true, options[1].split("=")[1]);
            }
        }
        try {
            List<String> alertIDs = Files.readAllLines(Paths.get(params[2]));
            for (String alertID : alertIDs) {
                alertDefinition.deleteAnAlert(alertID);
                alertDefinition.execute();
                Thread.sleep(2000);
                alertDefinition.resetEndpoint();
            }
        } catch (IOException e) {
            System.out.println(this.getClass().getSimpleName()+": "+e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println(this.getClass().getSimpleName()+": "+e.getMessage());
            e.printStackTrace();
        }
    }

    private void processDeleteAnAlert(String[] params) {
        if(params.length == 3) {
            alertDefinition = alertDefinition = new AlertDefinition(params[1], false, null);
        } else if(params.length == 4) {
            String[] options = params[3].split(",");
            if(options.length == 1) {
                switch (options[0].toLowerCase().substring(0,4)) {
                    case "json" -> alertDefinition = new AlertDefinition(params[1], true, null);
                    case "acco" -> {
                        alertDefinition = new AlertDefinition(params[1], false, options[0].split("=")[1]);
                    }
                    default -> {
                        System.out.println("You specify incorrect options for 'getanalert' command. Please refer below for the correct command.");
                        System.out.println(InstructionsUtil.printDeleteAnAlertInstruction());
                        System.exit(0);
                    }
                }
            } else if(options.length == 2) {
                alertDefinition = new AlertDefinition(params[1], true, options[1].split("=")[1]);
            }
        }
        alertDefinition.deleteAnAlert(params[2]);
    }

    private void processCreateAnAlert(String[] params) {
        if(params.length == 3) {
            alertDefinition = new AlertDefinition(params[1], false, null);
        } else if(params.length == 4) {
            String[] options = params[3].split(",");
            if(options.length == 1) {
                switch (options[0].toLowerCase().substring(0,4)) {
                    case "json" -> alertDefinition = new AlertDefinition(params[1], true, null);
                    case "acco" -> {
                        alertDefinition = new AlertDefinition(params[1], false, options[0].split("=")[1]);
                    }
                    default -> {
                        System.out.println("You specify incorrect options for 'createanalert' command. Please refer below for the correct command.");
                        System.out.println(InstructionsUtil.printCreateAnAlertInstruction());
                        System.exit(0);
                    }
                }
            } else if(options.length == 2) {
                alertDefinition = new AlertDefinition(params[1], true, options[1].split("=")[1]);
            }
        }
        alertDefinition.createAnAlert(params[2]);
    }

    private void processListAllAlerts(String[] params) {
        if(params.length == 2) {
            alertDefinition = new AlertDefinition(params[1], false, null);
        } else if (params.length == 3) {
            String[] options = params[2].split(",");
            if(options.length == 1) {
                switch (options[0].toLowerCase().substring(0,4)) {
                    case "json" -> alertDefinition = new AlertDefinition(params[1], true, null);
                    case "acco" -> {
                        alertDefinition = new AlertDefinition(params[1], false, options[0].split("=")[1]);
                    }
                    default -> {
                        System.out.println("You specify incorrect options for listAllAlerts command. Please refer below for the correct command.");
                        System.out.println(InstructionsUtil.printListAllAlertsInstruction());
                        System.exit(0);
                    }
                }
            } else if(options.length == 2) {
                alertDefinition = new AlertDefinition(params[1], true, options[1].split("=")[1]);
            }
        }
        alertDefinition.listAllAlerts();
    }

    private void processGetAnAlert(String[] params) {
        if(params.length == 3) {
            alertDefinition = alertDefinition = new AlertDefinition(params[1], false, null);
        } else if(params.length == 4) {
            String[] options = params[3].split(",");
            if(options.length == 1) {
                switch (options[0].toLowerCase().substring(0,4)) {
                    case "json" -> alertDefinition = new AlertDefinition(params[1], true, null);
                    case "acco" -> {
                        alertDefinition = new AlertDefinition(params[1], false, options[0].split("=")[1]);
                    }
                    default -> {
                        System.out.println("You specify incorrect options for 'getanalert' command. Please refer below for the correct command.");
                        System.out.println(InstructionsUtil.printGetAnAlertInstruction());
                        System.exit(0);
                    }
                }
            } else if(options.length == 2) {
                alertDefinition = new AlertDefinition(params[1], true, options[1].split("=")[1]);
            }
        }
        alertDefinition.getAnAlert(params[2]);
    }

    public void execute() {
        if (command.equalsIgnoreCase("deletealerts")) return;
        alertDefinition.execute();
    }
}
