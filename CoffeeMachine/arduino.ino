#include <Wire.h>
#include <Servo.h>
#include <OneWire.h>
#include <DallasTemperature.h>

// Senzorul de temp la pin 3
#define ONE_WIRE_BUS 3
#define sensorPower 7
#define sensorPin A0
const int RELEU_HEAT = 2;
const int RELEU_WATER = 4;

String receivedCommand = "";
bool stringComplete = false;
String coffeeStatus = "STATUS:WAITING\n";
Servo servomotor;
int val = 0;

OneWire oneWire(ONE_WIRE_BUS);
DallasTemperature sensors(&oneWire);

void setup() {
  servomotor.attach(A3);
  servomotor.write(30);
  delay(500);
  servomotor.write(0);
  pinMode(LED_BUILTIN, OUTPUT);
  Serial.begin(9600);
  Wire.begin(9);
  Wire.onReceive(receiveEvent);
  Wire.onRequest(requestEvent);
  pinMode(RELEU_HEAT, OUTPUT);
  pinMode(RELEU_WATER, OUTPUT);
  digitalWrite(RELEU_WATER, HIGH);
  sensors.begin();
  pinMode(sensorPower, OUTPUT);
  pinMode(sensorPin, INPUT);
	digitalWrite(sensorPower, LOW);
}

void loop() {

  if(stringComplete){
    Serial.println(receivedCommand);

    if(receivedCommand.startsWith("COFFEE:")){
      String coffeeType = receivedCommand.substring(7);
      coffeeStatus = "STATUS:PREPARING\n";
      Serial.println("Preparing your " + coffeeType);
      processCoffee(coffeeType);
      coffeeStatus = "STATUS:READY\n";
      Serial.println("Your coffee is ready");
      delay(1000);
      coffeeStatus = "STATUS:WAITING\n";
    }

    receivedCommand = "";
    stringComplete = false;
  }

}

void processCoffee(String coffee){
  if(coffee.startsWith("ESPRESSO")){
    //40 ml pt espresso, 20 grams
    prepareCoffee(20, 40);
  } else if(coffee.startsWith("DOUBLE")){
    //60 ml pt double espresso, 20 grams
    prepareCoffee(20, 60);
  } else if(coffee.startsWith("AMERICANO")){
    //100 ml pt americano, 20 grams
    prepareCoffee(20, 100);
  }
}

void prepareCoffee(int grams, int ml){
  while(readWaterLevel() - ml < 0){
    Serial.println("Low water level.");
    delay(100);
  }

  //se pune cafeaua 
  servomotor.write(30);
  delay(grams * 100);
  servomotor.write(0);

  // se incalzeste apa
  while(readTemperature()<80){
    digitalWrite(RELEU_HEAT, HIGH);
    Serial.print("Heating water...");
  }
  digitalWrite(RELEU_HEAT, LOW);

  //se porneste pompa cu verificarea de pe senzorul de nivel
  int levelWater = 0;
  if (ml > 50)
    levelWater = readWaterLevel() - ml + 50;
  else 
    levelWater = readWaterLevel() - ml + 20;
  digitalWrite(RELEU_WATER, LOW);
  digitalWrite(LED_BUILTIN, HIGH);
  while(readWaterLevel() > levelWater){
    delay(500);
  }
  digitalWrite(RELEU_WATER, HIGH);
  digitalWrite(LED_BUILTIN, LOW);
}

float readTemperature(){
  Serial.print("Requesting temperatures...");
  sensors.requestTemperatures();
  Serial.println("DONE");
  float tempC = sensors.getTempCByIndex(0);
  if(tempC != DEVICE_DISCONNECTED_C) {
    Serial.print("Temperature is: ");
    Serial.println(tempC);
  } else{
    Serial.println("Error: Could not read temperature data");
  }
  return tempC;
}

int readWaterLevel(){
  Serial.print("Requesting water level...");
  digitalWrite(sensorPower, HIGH);
	delay(100);
  val = analogRead(sensorPin);
  digitalWrite(sensorPower, LOW);
  //int result = map(val, 0, 1023, 0, 500);
  Serial.print("Water level: ");
  Serial.println(val);
  return val;
}

void receiveEvent(int numBytes) {
  while (Wire.available()) {
    char inChar = (char)Wire.read();
    if (inChar != '\n')
      receivedCommand += inChar;
    if (inChar == '\n') {
      stringComplete = true;
    }
 }  
}

void requestEvent(){
  Wire.write(coffeeStatus.c_str(), coffeeStatus.length());
}


