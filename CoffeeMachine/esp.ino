#include <WiFi.h>
#include <WiFiAP.h>
#include <WiFiClient.h>
#include <Wire.h>

const String SETUP_INIT = "SETUP: Initializing ESP32 dev board";
const String SETUP_ERROR = "!!ERROR!! SETUP: Unable to start SoftAP mode";
const String SETUP_SERVER_START = "SETUP: HTTP server started --> IP addr: ";
const String SETUP_SERVER_PORT = " on port: ";
const String INFO_NEW_CLIENT = "New client connected";
const String INFO_DISCONNECT_CLIENT = "Client disconnected";

const String HTTP_HEADER = "HTTP/1.1 200 OK\r\nContent-type:text/html\r\n\r\n";

const char *SSID = "ana's coffee machine";
const char *PASS = "12345678";

const int HTTP_PORT_NO = 80;
WiFiServer HttpServer(HTTP_PORT_NO);
IPAddress accessPointIP;

String coffeeStatus = "STATUS:WAITING";
String coffeeType = "Espresso";

void setup() {
  Serial.begin(9600);
  Wire.begin();
  if (!WiFi.softAP(SSID, PASS)) {
    Serial.println(SETUP_ERROR);
    while (1)
      ;
  }
  accessPointIP = WiFi.softAPIP();
  const String webServerInfoMessage = SETUP_SERVER_START + accessPointIP.toString()
                                      + SETUP_SERVER_PORT + HTTP_PORT_NO;
  HttpServer.begin();
  Serial.println(webServerInfoMessage);
}

void loop() {
  WiFiClient client = HttpServer.available();  

  if(Wire.requestFrom(9, 32)){
    String status = "";
    while(Wire.available()){
      char c = Wire.read();
      if (c != '\n') {  
        status += c;
      } else {
        break;
      }
    }
    status.trim();
    if (status.length() > 0) {
      coffeeStatus = status;
      Serial.println("Status from Mega: " + coffeeStatus);
    }
  }

  if (client) {                                
    Serial.println(INFO_NEW_CLIENT);           
    String currentRequest = "";                   
    while (client.connected()) {               
      if (client.available()) {                
        const char c = client.read();          
        Serial.write(c);                       
        if (c == '\n') {
          if (currentRequest.length() == 0) {
            if(coffeeStatus.startsWith("STATUS:WAITING")){
              printWelcomePage(client); 
            } 
            break;
          } else currentRequest = "";
        } else if (c != '\r') {
          currentRequest += c;
        }

        if (currentRequest.endsWith("GET /ESPRESSO")) {
          Serial.println("Coffee Order: ESPRESSO");
          coffeeType = "Espresso";
          printPrepareCoffee(client, coffeeType);
          sendMessagetoArduino(coffeeType);
          coffeeStatus = "STATUS:PREPARING";
        } else if (currentRequest.endsWith("GET /DUBLU_ESPRESSO")) {
          Serial.println("Coffee Order: DUBLU ESPRESSO");
          coffeeType = "Double Espresso";
          printPrepareCoffee(client, coffeeType);
          sendMessagetoArduino(coffeeType);
          coffeeStatus = "STATUS:PREPARING";
        } else if (currentRequest.endsWith("GET /AMERICANO")) {
          Serial.println("Coffee Order: AMERICANO");
          coffeeType = "Americano";
          printPrepareCoffee(client, coffeeType);
          sendMessagetoArduino(coffeeType);
          coffeeStatus = "STATUS:PREPARING";
        }
      }
    }

    client.stop();
    Serial.println(INFO_DISCONNECT_CLIENT);
    Serial.println();
  }

}

void sendMessagetoArduino(String coffee){
  Wire.beginTransmission(9);

  if(coffee.startsWith("Espresso")){
    Wire.write((uint8_t*)"COFFEE:ESPRESSO\n", strlen("COFFEE:ESPRESSO\n"));
    Serial.println("Sent to Arduino Mega: COFFEE:ESPRESSO");
  } else if(coffee.startsWith("Double Espresso")){
    Wire.write((uint8_t*)"COFFEE:DOUBLE\n", strlen("COFFEE:DOUBLE\n"));
    Serial.println("Sent to Arduino Mega: COFFEE:DOUBLE");
  } else if(coffee.startsWith("Americano")){
    Wire.write((uint8_t*)"COFFEE:AMERICANO\n", strlen("COFFEE:AMERICANO\n"));
    Serial.println("Sent to Arduino Mega: COFFEE:AMERICANO");
  }

  Wire.endTransmission();
}

void printWelcomePage(WiFiClient client) {
  client.println(HTTP_HEADER);
  client.println(R"rawliteral(
    <!DOCTYPE html>
    <html>
    <head>
      <title>Coffee Machine</title>
      <style>
        body {
          font-family: 'Arial', sans-serif;
          background-color: #ffe6f2; /* Roz pastel deschis */
          color: #333;
          text-align: center;
          margin: 0;
          padding: 0;
        }
        h1 {
          margin-top: 50px;
          font-size: 3em;
          color: #d63384; /* Roz închis */
          text-shadow: 2px 2px 5px #ffb3c6; /* Umbră roz deschis */
        }
        h2 {
          color: #e85d9f; /* Roz mediu */
          font-size: 1.5em;
          margin-bottom: 20px;
        }
        button {
          background-color: #f78fb3; /* Roz vibrant */
          border: none;
          color: white;
          padding: 15px 32px;
          text-align: center;
          font-size: 16px;
          margin: 10px;
          cursor: pointer;
          border-radius: 5px;
          width: 200px;
          transition: background-color 0.3s, transform 0.2s;
        }
        button:hover {
          background-color: #d63384; /* Roz închis */
          transform: scale(1.05);
        }
      </style>
    </head>
    <body>
      <h1>Welcome to Ana's Coffee Machine!</h1>
      <h2>Your perfect cup of coffee is just one click away</h2>

      <form method="get" action="/ESPRESSO">
        <button type="submit">Espresso</button>
      </form>

      <form method="get" action="/DUBLU_ESPRESSO">
        <button type="submit">Double Espresso</button>
      </form>

      <form method="get" action="/AMERICANO">
        <button type="submit">Americano</button>
      </form>
    </body>
    </html>
  )rawliteral");
}

void printPrepareCoffee(WiFiClient client, String coffeeType) {
  client.println(HTTP_HEADER);
  client.println(R"rawliteral(
    <!DOCTYPE html>
    <html>
    <head>
      <title>Preparing Coffee</title>
      <style>
        body {
          font-family: 'Arial', sans-serif;
          background-color: #ffe6f2;
          color: #333;
          text-align: center;
          margin-top: 50px;
        }
        h1 {
          font-size: 2.5em;
          color: #d63384;
          text-shadow: 1px 1px 3px #ffb3c6;
        }
        .loader {
          border: 8px solid #ffe6f2;
          border-top: 8px solid #d63384;
          border-radius: 50%;
          width: 50px;
          height: 50px;
          animation: spin 2s linear infinite;
          margin: 20px auto;
        }
        @keyframes spin {
          0% { transform: rotate(0deg); }
          100% { transform: rotate(360deg); }
        }
        p {
          font-size: 1.5em;
          color: #e85d9f;
        }
      </style>
    </head>
    <body>
      <h1>Preparing Your )rawliteral" + coffeeType + R"rawliteral( ...</h1>
      <div class="loader"></div>
      <p>Please wait while we brew your perfect )rawliteral" + coffeeType + R"rawliteral(.</p>
    </body>
    </html>
  )rawliteral");
  client.println();
}

void printCoffeeReadyPage(WiFiClient client) {
  client.println("HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n");
  client.println(R"rawliteral(
    <!DOCTYPE html>
    <html>
    <head>
      <title>Coffee Ready</title>
      <style>
        body {
          font-family: 'Arial', sans-serif;
          background-color: #ffe6f2; /* Roz pastel */
          color: #333;
          text-align: center;
          margin: 0;
          padding: 0;
        }
        h1 {
          font-size: 2.5em;
          color: #d63384;
          text-shadow: 1px 1px 3px #ffb3c6;
          margin-top: 50px;
        }
        button {
          background-color: #f78fb3;
          border: none;
          color: white;
          padding: 15px 32px;
          text-align: center;
          font-size: 16px;
          margin-top: 20px;
          cursor: pointer;
          border-radius: 5px;
          transition: background-color 0.3s, transform 0.2s;
        }
        button:hover {
          background-color: #d63384;
          transform: scale(1.05);
        }
        a {
          text-decoration: none;
          color: white;
        }
      </style>
    </head>
    <body>
      <h1>Your coffee is ready. Enjoy!</h1>
    </body>
    </html>
  )rawliteral");
}


