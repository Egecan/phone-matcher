Phone Matcher - How to build, test & run
====================================================

## 1) From terminal, go to the main project folder.

## 2) Type the following to build the project:
``./gradlew clean build``

## 3) Type the following to run the tests:
``./gradlew test``

## 4) Running the application:

- To run the application with console inputs and default dictionary: (when finished type q to quit)
``java -jar build/libs/phone-matcher-1.0.0.jar``

- To specify your own dictionary while running, you can use -Ddictionary like below: (change dictionary path to specify yours)
``java -Ddictionary=/Users/eugene/Desktop/dictionary.txt -jar build/libs/phone-matcher-1.0.0.jar``

- To run the application using your own files as inputs: (change arguments below to yours, and you can specify as many files as you want)
``java -jar build/libs/phone-matcher-1.0.0.jar src/main/resources/sample_input.txt /Users/eugene/Desktop/other_sample_input.txt``

- To run the application using both your own files as inputs and your own dictionary (change sample paths below to yours):
``java -Ddictionary=/Users/eugene/Desktop/dictionary.txt -jar build/libs/phone-matcher-1.0.0.jar src/main/resources/sample_input.txt``