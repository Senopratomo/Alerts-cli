package org.senolab.alerts.util;

public class InstructionsUtil {
    public static String printInstructions() {
        return """
            Alerts CLI v0.1.0
            
            This is a simple CLI to manage Akamai Control Center alerts - https://techdocs.akamai.com/alerts-app/docs/welcome-alerts
            
            It takes 2 - 4 parameters depends on the command specified.    
            List of command supported:
            1. listAllAlerts
               This command will list all alerts that your API client can view (depend on your API client group access).
               It will output the HTTP method call, URL endpoint, Response header, and JSON body containing the alerts definition.
               It has one required parameter which is the full path to edgerc file (file which contain all the tokens + secret).
               It has one optional parameter "json-only" which will return only the JSON body containing the alerts definition.
               
               Sample command:
               a) List all alerts, but only the JSON
               $java -jar Alerts.jar listAllAlerts /home/user/.edgerc json-only
               
               b) List all alerts with verbose
               $java -jar Alerts.jar listAllAlerts /home/user/.edgerc
            
            2. getAnAlert
               This command take an id (alert id) and will output the JSON body containing the alert definiion associated with
               the specified id.
               It has two required parameter.
               First is the full path to edgerc file (file which contain all the tokens + secret).
               Second is the id of the alert which you want to pull.
               It has one optional parameter "json-only" which will return only the JSON body containing the alerts definition.
               
               Sample command
               a) Get an alert with id s@1234, but only the JSON
               $java -jar Alerts.jar getAnAlert /home/user/.edgerc s@1234 json-only
               
               b) Get an alert with id s@1234
               $java -jar Alerts.jar getAnAlert /home/user/.edgerc s@1234
               
            3. createanalert
               This command create an alert based on a JSON file containing the definition of alert which pass on as parameter to this CLI.
               It has two required parameter.
               First is the full path to edgerc file (file which contain all the tokens + secret).
               Second is the full path to the JSON file.
               It has one optional parameter "json-only" which will return only the JSON body containing the alerts definition.
               
               Sample command:
               a) Create an alert and output only the JSON
               $java -jar Alerts.jar createanalert /home/user/.edgerc /home/user/alert-body.json json-only
               
               b) Create an alert 
               $java -jar Alerts.jar createanalert /home/user/.edgerc /home/user/alert-body.json
            
            4. deleteanalert
               This command delete an alert based on alert definition id specified in the parameter
               It takes two required parameters.
               First is the full path to edgerc file (file which contain all the tokens + secret).
               Second is the id of the alert which you want to delete.
               
               Sample command:
               Delete an alert with id s@1234
               $java -jar Alerts.jar deleteAnAlert /home/user/.edgerc s@1234
               
            5. deleteAlerts
               This command delete multiple alerts based on list of definition id specified in a text file.   
               It takes two required parameters.
               First is the full path to edgerc file (file which contain all the tokens + secret).
               Second is the full path to the text file which contain list of alert definition id (one alert id per line)
               
               Sample command:
               Delete all alerts which id specified within file /home/users/oldStuff/oldAlerts.txt
               $java -jar Alerts.jar deleteAlerts /home/user/.edgerc /home/users/oldStuff/oldAlerts.txt
               
            Any issues or questions, please submit an "Issues" within the Github repo
            """;
    }

    public static String printIncorrectParameterMessage() {
        return """
           You specified incorrect command and/or parameters.
           Please run the following command to see all available command and parameter: 
           java -jar Alerts.jar     
           """;
    }

    public static String printListAllAlertsInstruction() {
        return """
          command "listAllAlerts"
          
          This command will list all alerts that your API client can view (depend on your API client group access).
          It will output the HTTP method call, URL endpoint, Response header, and JSON body containing the alerts definition.
          it has one optional parameter "json-only" which will return only the JSON body containing the alerts definition.
               
          sample command:
          a) List all alerts, but only the JSON output
             $java -jar Alerts.jar listAllAlerts json-only
               
          b) List all alerts with verbose
             $java -jar Alerts.jar listAllAlerts
          
          """;
    }

    public static String printGetAnAlertInstruction() {
        return """
          command "getAnAlert"
          
          This command take an id (alert id) and will output the JSON body containing the alert definiion associated with
          the specified id.
          It has two required parameter.
          First is the full path to edgerc file (file which contain all the tokens + secret).
          Second is the id of the alert which you want to pull.
          It has one optional parameter "json-only" which will return only the JSON body containing the alerts definition.
              
          Sample command
          a) Get an alert with id s@1234, but only the JSON
             $java -jar Alerts.jar getAnAlert /home/user/.edgerc s@1234 json-only
               
          b) Get an alert with id s@1234
             $java -jar Alerts.jar getAnAlert /home/user/.edgerc s@1234
          
          """;
    }

    public static String printCreateAnAlertInstruction() {
        return """
          command "createanalert"
          
          This command create an alert based on a JSON file containing the definition of alert which pass on as parameter to this CLI.
          It has two required parameter.
          First is the full path to edgerc file (file which contain all the tokens + secret).
          Second is the full path to the JSON file.
          It has one optional parameter "json-only" which will return only the JSON body containing the alerts definition.
               
          Sample command:
          a) Create an alert and output only the JSON
          $java =jar Alerts.jar createanalert /home/user/.edgerc /home/user/alert-body.json json-only
               
          b) Create an alert 
          $java =jar Alerts.jar createanalert /home/user/.edgerc /home/user/alert-body.json      
          """;
    }

    public static String printDeleteAnAlertInstruction() {
        return """
          command "deleteanalert"
          
          This command delete an alert based on alert definition id specified in the parameter
          It has two required parameter.
          First is the full path to edgerc file (file which contain all the tokens + secret).
          Second is the id of the alert which you want to delete.
          It has one optional parameter "json-only" which will return only the JSON body containing the alerts definition.
               
          Sample command:
          Delete an alert with id s@1234
          $java -jar Alerts.jar deleteAnAlert /home/user/.edgerc s@1234
          """;
    }

    public static String printDeleteAlertsInstruction() {
        return """
          command "deletealerts"
          
          This command delete multiple alerts based on list of definition id specified in a text file.   
          It takes two required parameters.
          First is the full path to edgerc file (file which contain all the tokens + secret).
          Second is the full path to the text file which contain list of alert definition id (one alert id per line)
               
          Sample command:
          Delete all alerts which id specified within file /home/users/oldStuff/oldAlerts.txt
          $java -jar Alerts.jar deleteAlerts /home/user/.edgerc /home/users/oldStuff/oldAlerts.txt
                
          """;
    }
}
