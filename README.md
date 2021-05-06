# Project Net Worth

## Project Description
Project Net Worth a is desktop application that **tracks users' personal finances**. The application will be able to
take user inputs to keep track of their monthly income and expenses and net worth. Additionally, the application should 
be able to summarize their financial data so that users can make informed decisions based on their historical financial
data. 

This application is for **individuals, couples, families, and even small businesses who are looking to better manage
and track their finances**. I hope that users will become more financially literate and learn to manage their finances 
in a more structured manner by using this application. This project is of particular interest to me because 
**I am a huge proponent for financial literacy and closely manage and invest my finances to reach short-term and 
long-term goals myself.** For example: this application should be able to help many people who want to reach 
*F.I.R.E.* (financial independence, retire early).

## User Case Stories
Below are a list of user case stories that I would like to bring to reality for this application:
- As a user, I want to be able to create a new account and add it to my list of current accounts
- As a user, I want to be able to delete my own personal account
- As a user, I want to be able to update financial income and expense information on a specific month and account  
- As a user, I want to be able to update my net worth (assets and liabilities) on a specific account
- As a user, I want to be able to view my balance sheet (net worth) and monthly financial summary
- As a user, I want to be able to save all created user accounts (username and password required for log in)
- As a user, I want to be able to save my latest balance sheet and income statement list for each respective user
- As a user, I want to be able to sign in with user accounts created in the past 
- As a user, I want to be able to reload all the balance sheet and income statement list for each respective user

## Phase 4: Task 2
I have selected to test and design a class in my model package that is robust by having at least one method that throws 
a checked exception. The class that has the robust method is AccountList and the name of the method is called
getAccountName() that checks for IndexOutOfBoundsException.

## Phase 4: Task 3
If I had more time with the project, I would spend more time refactoring the MonthlyStatement, MonthlySummary,
BalanceSheetStatement, ConfirmMessage, and PopUpMessage ui classes because they have a lot of similar methods found
between them that are very repetitive. One way would be to create an interface or abstract class to abstract and 
streamline the duplicate similar methods found between the classes. 