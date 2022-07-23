# New-Bank
 SE2 Coursework 1: Group 2

## Setup IntelliJ
Some setup is needed for IntelliJ to run code and unit tests.

1. the directories `NewBank/src/main/java` and `NewBank/src/test/java` must be marked as "Source Root"
   ![](images/source-root-1.png)
   ![](images/source-root-2.png)
2.  The `lib` folder must be add to libraries in IntelliJ. To do this
* Click File / Project Structure
* Select "Libraries" on left and click the plus sign
* Select "Java" and find the "lib" folder
![](images/add-libs-1.png)
![](images/add-libs-2.png)
![](images/add-libs-3.png)
![](images/add-libs-4.png)
![](images/add-libs-5.png)
![](images/add-libs-6.png)

3. The compiler output path might need to be configured, this can be set to "/path/to/code/NewBank/build"
![](images/configure-output-path.png)
4. To run the tests using IntelliJ, right click file `src/test/java/NewBankTest` and click `Run NewBankTest`.
This will open new window at bottom and show the test result
![](images/run-tests-1.png)![](images/run-tests-2.png)
