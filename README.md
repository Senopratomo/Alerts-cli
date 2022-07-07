<h3>Alert CLI</h3>
<hr>
<p>
This is a simple CLI to manage Akamai Control Center alerts - https://techdocs.akamai.com/alerts-app/docs/welcome-alerts 
which include creating, update, delete, and bulk delete.
</p>

<h5>Prerequisite</h5>
<ul>
    <li>Java 17 or above installed in the local machine</li>
    <li>Maven installed in local (if build from source)</li>
</ul>
<br>
<h5>Setup (build from source)</h5>
<ul>
    <li>Clone this project</li>
    <li>Go to that root dir of the project</li>
    <li>Run: mvn clean install</li>
    <li>It will produce file Alerts.jar inside "target" folder</li>
</ul>
<h5>Getting started</h5>
<ul>
    <li>Download the latest .jar from the release section of this repo 
    https://github.com/Senopratomo//releases</li>
    <li>Run: <br>
    <code>
    java -jar Alerts.jar
    </code>
    </li>
</ul>
<br>
<h5>How to use the CLI</h5>
<p>Alerts CLI It takes 2 - 4 parameters depends on the command specified.</p>
<p>List of command supported:</p>
<ol>
   <li>listAllAlerts<br>
       This command will list all alerts that your API client can view (depend on your API client group access).<br>
       It will output the HTTP method call, URL endpoint, Response header, and JSON body containing the alerts definition.<br>
       It has one required parameter which is the full path to edgerc file (file which contain all the tokens + secret).<br>
       It has one optional parameter "json-only" which will return only the JSON body containing the alerts definition.<br>
       Sample command:<br>
       a) List all alerts, but only the JSON<br>
          $java -jar Alerts.jar listAllAlerts /home/user/.edgerc json-only<br>
       b) List all alerts with verbose<br>
          $java -jar Alerts.jar listAllAlerts /home/user/.edgerc<br>
   </li><br>
   <li>getAnAlert<br>
       This command take an id (alert id) and will output the JSON body containing the alert definiion associated with <br>
       the specified id.<br>
       It has two required parameter.<br>
       First is the full path to edgerc file (file which contain all the tokens + secret).<br>
       Second is the id of the alert which you want to pull.<br>
       It has one optional parameter "json-only" which will return only the JSON body containing the alerts definition.<br>
       <br>
       Sample command:<br>
       a) Get an alert with id s@1234, but only the JSON<br>
       $java -jar Alerts.jar getAnAlert /home/user/.edgerc s@1234 json-only<br>
       b) Get an alert with id s@1234<br>
       $java -jar Alerts.jar getAnAlert /home/user/.edgerc s@1234<br>
   </li><br>
   <li>createanalert<br>
       This command create an alert based on a JSON file containing the definition of alert which pass on as parameter to this CLI.<br>
       It has two required parameter.<br>
       First is the full path to edgerc file (file which contain all the tokens + secret).<br>
       Second is the full path to the JSON file.<br>
       It has one optional parameter "json-only" which will return only the JSON body containing the alerts definition.<br>
       <br>
       Sample command:<br>
       a) Create an alert and output only the JSON<br>
       $java -jar Alerts.jar createanalert /home/user/.edgerc /home/user/alert-body.json json-only<br>
       b) Create an alert <br>
       $java -jar Alerts.jar createanalert /home/user/.edgerc /home/user/alert-body.json<br>
   </li><br>
   
   <li>deleteanalert<br>
       This command delete an alert based on alert definition id specified in the parameter<br>
       It takes two required parameters.<br>
       First is the full path to edgerc file (file which contain all the tokens + secret).<br>
       Second is the id of the alert which you want to delete.<br>
       <br>
       Sample command:<br>
       Delete an alert with id s@1234<br>
       $java -jar Alerts.jar deleteAnAlert /home/user/.edgerc s@1234<br>
   </li><br>
   <li>deleteAlerts<br>
       This command delete multiple alerts based on list of definition id specified in a text file.<br>   
       It takes two required parameters.<br>
       First is the full path to edgerc file (file which contain all the tokens + secret).<br>
       Second is the full path to the text file which contain list of alert definition id (one alert id per line)<br>
       <br>
       Sample command:<br>
       Delete all alerts which id specified within file /home/users/oldStuff/oldAlerts.txt<br>
       $java -jar Alerts.jar deleteAlerts /home/user/.edgerc /home/users/oldStuff/oldAlerts.txt<br>
   </li>
</ol>
<br>
