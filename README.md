# Expense Tracker CLI

## Introduction

Expense Tracker CLI is a command-line application that allows users to manage their expenses efficiently. Users can add, list, delete, update, and summarize their expenses, along with managing expense categories.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [Features](#features)
- [Commands](#commands)
- [Dependencies](#dependencies)
- [Configuration](#configuration)
- [URL](#Url)

## Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/yourusername/expense-tracker-cli.git

   ```


2. Navigate to the project directory:
```sh
cd expense-tracker-cli
 ```
3. Compile the Java files
```sh
javac -d bin -sourcepath src src/org.example/main.java
 ```
4. Run the application:
```sh
java -cp bin main.java
 ```

# Usage
Start the CLI by running:

```sh
java -cp bin main.java
 ```

Once running, you can use the following commands:

# Features
- **Add Expenses:** Add an expense with a description, amount, and category.
- **List Expenses:** View all expenses or filter them by category.
- **Delete Expenses:** Remove an expense using its ID.
- **Update Expenses:** Modify an expense amount using its ID.
- **Summarize Expenses:** Get the total expense amount, including monthly summaries.
- **Manage Categories:** View available expense categories.

# Commands
**1. List categories**
```sh
category

<!-- Result -->

  Id: 1  -> Public Services
  Id: 2  -> Food
  Id: 3  -> Entertainment
  Id: 4  -> Rent
  Id: 5  -> other
  ```
**2. Add an Expense**
  ```sh
  add --description "Lunch" --amount 15.50 --category 2
  ```
* **--description:** Description of the expense (must be in quotes).
* **--amount:** The amount spent.
* **--category:** The category ID.

**3. List Expenses**

List all expenses
```sh
list
```
list expenses by category
```sh
list --category 2
```
**4. Delete an Expense**
```sh
delete --id 5
```
* **--id:** The expense ID to delete

**5. Update an Expense**
```sh
update --id 5 --amount 20.00
```
* **--id:** The expense ID.
* **--amount:** The new amoun

**6. Show Expense Summary**

Show total expenses
```sh
summary
```

Show monthly expenses
```sh
delete --month 3
```
* **--month:** The month number (1-12).

**7. Exit**
```sh
exit
```

# Dependencies

* Java Development Kit (JDK 8+)
* Gson Library

# Configuration
* Expense and category data are managed through ExpenseDAO and CategoryDAO.
* Expenses are stored with ExpenseVO, and categories are handled using CategoryVO.

# URL

https://roadmap.sh/projects/expense-tracker