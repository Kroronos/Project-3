/*
 * Name: Jesse Martinez
 * Class Section: 6909
 * Instructor: Kyla McMullen
 * Due Date: October 25, 2016
 * Brief Description: Allows user to deposit and withdraw various denominations of currency.
 * Converting currency from USD incurs a transaction fee.
 * User must withdraw their remaining balance upon exiting the program.
 * Regrade Changelog: 
 * Change 1: Moves lines 287-288 to precede the call for method convertCurrency in Witdraw Method
 *    if (currencyType != 1)
            amount *= 1.005;
            -> before
       double converted = convertCurrency( amount, currencyType, isConvertToUS);
       Issue was that the withdrawal fee would fail to apply to the amount taken from balance because balance did not take into account variable amount but rather used a new variable set to convertCurrency
   
   Change 2: Moved code block from Main to Deposit and Withdraw Methods   
   The following code block was placed in lines 290-291 & 265-266  
    if (currencyType != 1)
                       isConvertToUS = true
    Unit Testing did not go through main method meaning that a determination of isConvertToUs could not be made for the purposes of calling convertCurrency
    This caused convertCurrency to fail with the unit test either returning null or simply skipping the method call leading to improper output
 *  Number of Conceptual Changes = 2
 * 
 *  */
import java.util.Scanner;

public class CurrencyExchange {
    //Create global variables
    static int mainSelect;
    private static double balance = 0;
    private static double transaction;
    private static boolean validDeposit = true;
    private static boolean validWithdraw = true;
    public static boolean isConvertToUS = false;
 
   public static void main(String[] args) {
        //Create Scanner 
        Scanner in = new Scanner(System.in);
        
        //Declare variables for main method
        //boolean isConvertToUS = false;
        int currencyType = 1;
        
        //Call printConversionTable to print welcome message and conversion table
        printConversionTable();
        
        //Make while loop that only exits when user selects to exit from program 
        while(mainSelect != 4) {
            
            //Call mainMenuOptionSelector to print main menu and take input from user, returns selection
            mainMenuOptionSelector(in);
            
            //Displays users balance
            if (mainSelect == 1)
                System.out.print("Your current balance is: " + getBalance());
            
            //User chooses to withdraw or deposit 
            if ((mainSelect == 2) || (mainSelect ==3)) {
                //Takes input from console, prints currency selection menu
               currencyType = currencyMenuOptionSelector(in);
               //Ask user for amount they wish to deposit/withdraw
               if (mainSelect == 2)
                   System.out.println("Please enter the deposit amount:");
               if (mainSelect == 3)
                   System.out.println("Please enter the withdraw amount:");
               
               double amount = in.nextDouble();
              
               //Set isConvertToUS parameters for method calls
                if (currencyType != 1)
                       isConvertToUS = true;
               
               
               //Makes variable called transaction to reflect converted amount based on convertCurrency method
               transaction = convertCurrency( amount, currencyType, isConvertToUS);
               
               //Deposit Route
               if ( (isDeposit(mainSelect) == true)) {
                   //Checks to see if amount is valid deposit
                   validDeposit = deposit(amount, currencyType);
                   //Prints success or failure message
                   System.out.println(logTransaction(amount, currencyType, isDeposit(mainSelect)));
               }
               //Withdraw Route
               if ( (isDeposit(mainSelect) == false)) {
                   //Checks to see if amount is valid withdrawal    
                   validWithdraw = withdraw(amount, currencyType);
                   //Prints success or failure message
                   if ( (isDeposit(mainSelect) == false) && (validWithdraw == false) && (mainSelect != 4) ) {
                       if (transaction > 0) {
                           System.out.println("Error: Insufficient funds.");
                       }
                   }
                   System.out.println(logTransaction(amount, currencyType, isDeposit(mainSelect)));
                 
               }
            }
            
            //User selects to exit program and withdraw all money from account
            if (mainSelect == 4) {
                //Prints closing message
                System.out.println(logTransaction(transaction, currencyType, isDeposit(mainSelect)));
                System.out.println("Goodbye");
                }
            }
      
        }
        
    
    //Prints welcome message, and currency conversion rates
    public static void printConversionTable() {
        System.out.println("Welcome to Currency Exchange 2.0");
        System.out.println("");
        System.out.println("Current rates are as follows:");
        System.out.println("");
        System.out.println("1 - U.S. Dollar - 1.00");
        System.out.println("2 - Euro - 0.89");
        System.out.println("3 - British Pound - 0.78");
        System.out.println("4 - Indian Rupee - 66.53");
        System.out.println("5 - Australian Dollar - 1.31");
        System.out.println("6 - Canadian Dollar - 1.31");
        System.out.println("7 - Singapore Dollar - 1.37");
        System.out.println("8 - Swiss Franc - 0.97");
        System.out.println("9 - Malaysian Ringgit - 4.12");
        System.out.println("10 - Japanese Yen - 101.64");
        System.out.println("11 - Chinese Yuan Renminbi - 6.67");

        }
    
    //Prints main menu, takes input, returns input as integer
    public static int mainMenuOptionSelector(Scanner in) {
        //Asks user to select from main menu, taking their input as an integer
        System.out.println("");
        System.out.println("Please select an option from the list below:");
        System.out.println("1. Check the balance of your account");
        System.out.println("2. Make a deposit");
        System.out.println("3. Withdraw an amount in a specific currency");
        System.out.println("4. End your session (and withdraw all remaining currency in U.S. Dollars)");
        mainSelect = in.nextInt();
        
        //Enters loop until valid input is achieved
        while ( (mainSelect > 4) || (mainSelect < 1)) {
            System.out.print("Input failed validation. Please try again.");
            System.out.println("");
            System.out.println("Please select an option from the list below:");
            System.out.println("1. Check the balance of your account");
            System.out.println("2. Make a deposit");
            System.out.println("3. Withdraw an amount in a specific currency");
            System.out.println("4. End your session (and withdraw all remaining currency in U.S. Dollars)");
            mainSelect = in.nextInt();
        }
        //returns user selection in main menu as an integer
        return mainSelect;
    }
    
    //Prints currency menu, takes input, returns input as integer
    public static int currencyMenuOptionSelector (Scanner in) {
        int input;
        //Prints currency selection menu
        System.out.println("Please select the currency type:");

       
        System.out.println("1. U.S. Dollars");
        System.out.println("2. Euros");
        System.out.println("3. British Pounds");
        System.out.println("4. Indian Rupees");
        System.out.println("5. Australian Dollars");
        System.out.println("6. Canadian Dollars");
        System.out.println("7. Singapore Dollars");
        System.out.println("8. Swiss Francs");
        System.out.println("9. Malaysian Ringgits");
        System.out.println("10. Japanese Yen");
        System.out.println("11. Chinese Yuan Renminbi");
        input = in.nextInt();
        
            //if user input fails validation, enter loop until correct validation can be achieved
            while ( (input > 11) || (input < 1)) {
                System.out.println("Input failed validation. Please try again.");
                System.out.println("Please select the currency type: ");
                System.out.println("1. U.S. Dollars");
                System.out.println("2. Euros");
                System.out.println("3. British Pounds");
                System.out.println("4. Indian Rupees");
                System.out.println("5. Australian Dollars");
                System.out.println("6. Canadian Dollars");
                System.out.println("7. Singapore Dollars");
                System.out.println("8. Swiss Francs");
                System.out.println("9. Malaysian Ringgits");
                System.out.println("10. Japanese Yen");
                System.out.println("11. Chinese Yuan Renminbi");
                input = in.nextInt();
           }
        //Returns user selection as an integer, reflects currency type selected by user
        return input;   
    }
    
    public static double convertCurrency( double amount, int currencyType, boolean isConvertToUSD) {
        //Declare conversion which will be returned at end of method
        double conversion = 0;
        //Switch to select correct formula when converting to USD
        if (isConvertToUSD){
            switch (currencyType) {
                case 1 : conversion = amount/ 1;
                    break;
                case 2 : conversion = amount/ .89;
                    break;
                case 3 : conversion = amount/ .78;
                    break;
                case 4 : conversion = amount/ 66.53;
                    break;
                case 5 : conversion = amount/ 1.31;
                    break;
                case 6 : conversion = amount/ 1.31;
                    break;
                case 7 : conversion = amount/ 1.37;
                    break;
                case 8 : conversion = amount/ .97;
                    break;
                case 9 : conversion = amount/ 4.12 ;
                    break;
                case 10: conversion = amount/ 101.64;
                    break;
                case 11: conversion = amount/ 6.67;
                    break;
            }
        }
        //Switch to select correct formula when converting from USD
        else {
            switch (currencyType) {
                case 1 : conversion = amount * 1;
                    break;
                case 2 : conversion = amount * .89;
                    break;
                case 3 : conversion = amount * .78;
                    break;
                case 4 : conversion = amount * 66.53;
                    break;
                case 5 : conversion = amount * 1.31;
                    break;
                case 6 : conversion = amount * 1.31;
                    break;
                case 7 : conversion = amount * 1.37;
                    break;
                case 8 : conversion = amount * .97;
                    break;
                case 9 : conversion = amount * 4.12;
                    break;
                case 10 : conversion = amount * 101.64;
                    break;
                case 11: conversion = amount * 6.67;
                    break;
                
            }
        }
        //Returns changed integer reflecting conversion selected by user
        return conversion;   
    }
    
    //Determines whether a deposit is valid 
    public static boolean deposit (double amount, int currencyType) {
        //Save balance so that if update balance returns false, users balance is preserved
        double tempBalance = getBalance();
        //Lets move this and see what happens
        if (currencyType != 1)
            isConvertToUS = true;
    
        //Convert amount
        double converted = convertCurrency( amount, currencyType, isConvertToUS);
        //Checks to see if balance will be valid after deposit, while ensuring that the user hasn't entered a negative input
        if ( (updateBalanceNoRound(balance + converted) == true) && (converted > 0) ) {
            updateBalance(tempBalance + converted);
            return true;
        }
        else 
            balance = tempBalance;
            return false;
    }
    
    //Determines whether a withdrawal is valid 
    public static boolean withdraw (double amount, int currencyType) {
        
        //Save balance so that if update balance returns false, user's balance is preserved
        double tempBalance = getBalance();
        
        //Apply conversion fee if currency is not USD
        if (currencyType != 1)
            amount *= 1.005; 
        //Lets move this and see what happens
        if (currencyType != 1)
            isConvertToUS = true;
    
        //Convert Amount
        double converted = convertCurrency( amount, currencyType, isConvertToUS);
        
        //Checks to see if user has inputed valid, positive number
        //Checks to see if there will be a negative balance and updates balance to reflect change if valid transaction
        if ( (updateBalanceNoRound(balance - converted) == true) && (converted > 0) ) {
            updateBalance(tempBalance - converted);
            //Return that transaction was valid
            return true;
            }
        //Returns false because balance would be negative, corrects any changes made by updateBalance
        else {
            balance = tempBalance;
            return false; 
                }
        
    }
    
    
    //Prints a log of the transaction in cases of success and failure
    public static String logTransaction (double amount, int currencyType, boolean isDeposit) {
        //Declare strings. 
        String currency = "";
        String output = "";
        
        
        //Switch to create strings User's currency selection, strings used to print success messages
        switch (currencyType) {
            case 1: currency = " U.S. Dollars";
                break;
            case 2: currency = " Euros";
                break;
            case 3:currency =  " British Pounds";
                break;
            case 4: currency = " Indian Rupees";
                break;
            case 5: currency = " Australian Dollars";
                break;
            case 6: currency = " Canadian Dollars";
                break;
            case 7: currency = " Singapore Dollars";
                break;
            case 8: currency = " Swiss Francs";
                break;
            case 9: currency = " Malaysian Ringgits";
                break;
            case 10: currency =  " Japanese Yen";
                break;
            case 11: currency = " Chinese Yuan Renminbi";
                break;
        }
        
        //Prints success messages for logs. 
        if ( (isDeposit == true) && (validDeposit == true) && (mainSelect != 4) )
            output = "You successfully deposited " + amount + currency ;
        
        if ( (isDeposit == false) && (validWithdraw == true) && (mainSelect != 4) )
            output = "You successfully withdrew " + amount + currency ;
        
        //This if block prints error messages for cases where withdrawal or deposit amounts are invalid
        if ( (isDeposit == true) && (validDeposit == false) && (mainSelect != 4) ) {
            output = "Logging Error";
        }
        if ( (isDeposit == false) && (validWithdraw == false) && (mainSelect != 4) ) {
            output = "Logging Error";
        }
        
        //Return two closing messages dependent on remaining balance
        if (mainSelect == 4) {
            if (getBalance() == 0) {
                output = "Your remaining balance is " + getBalance() + " U.S. dollars";
            }
            
            else {
                output = "You successfully withdrew " + getBalance() + " U.S. Dollars";
            }
        }
        
        return output;
    } 
    
    //Test to determine whether user is depositing, used for creating a clean boolean for if/else statements
    public static boolean isDeposit (int a) {
        boolean isDeposit;
        if (a == 2)
            isDeposit = true; 
       
        else
            isDeposit = false;
        
        return isDeposit;
    }
    
    
     
    // Get the current balance of the account
    public static double getBalance() {
        return balance;
    }

     
     /* Update the balance of current account, will do a roundup to 2 significant digits
      If update would succeed, will return true. If failed, return false
      This method updates global balance regardless of success or failure, store a temporary balance when called
     */
    private static boolean updateBalance(double newBalance) {

        balance = Math.round(newBalance * 100) / 100.0;
        if (balance >= 0) {
            return true;
        } else
            return false;
    }
    
    //Verifies that transaction would occur without rounding, used to reduce repetitive code in withdraw and deposit methods
    private static boolean updateBalanceNoRound(double newBalance) {

        balance = newBalance;
        if (balance >= 0) {
            return true;
        } else
            return false;
    }
    
    
    
    
    
    
    
    
    
}


