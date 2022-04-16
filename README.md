Unit 6: Group Milestone 2
===
## Progress
<img src="" width=800><br>


# Budget Tracker

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
1. [Schema](#Schema)

## Overview
### Description
Tracks how much users spend on a weekly or monthly basis and what categories their expenses fall into. 

### App Evaluation
- **Category:** Personal Finance
- **Mobile:** This app would be primarily developed for mobile. The mobile experience is unique because it would be more convenient for users to track their spending every time they receive income or spend money using their mobile phones.
- **Story:** Tracks and analyzes users' spending habit, and helps them monitor their spending habit by presenting a visualization of how much of their spending goes to non-essentials versus essentials.
- **Market:** Any individual could choose to use this app, but the app is targeted specifically to college students.
- **Habit:** This app could be used as often or unoften as the user want, depending on when they want to insert their spending/income - either immediately at the time a transaction takes place or later) and the frequency transactions take place.
- **Scope:** First we would start with just a budget tracker for college students, then perhaps this could evolve into an app that supports splitting mutual spending for groups of people to broaden its usage. Large potential for use with banking apps.

## Product Spec
### 1. User Stories (Required and Optional)

**Required Must-have Stories**

- [x] Users can login to their accounts or sign up
- [ ] Users can enter their expenses 
- [ ] Users can track their expenses 
- [ ] Users can see a visualization that analyzes how they’re spending money
- [ ] Users can categorize their expenses into essential/non-essential and other categories. i.e. Decide which is essential and which isn’t.

**Optional Nice-to-have Stories**

- [ ] Users can take a picture with their camera or upload a picture from their phone.
- [ ] The picture of the receipt can be scanned using the ocr api.
- [ ] The expenses extracted from the photo can be tracked and analyzed.

### 2. Screen Archetypes

* Login/Sign up
* Summary Screen 
   * Allows user to see a summary of their expenses and incomes on a weekly/monthly basis.
* Insert Screen
   * Allows user to be insert and categorize the amount of their expense/income (dollars) into essential/non-essential.
* Profile Screen
   * Lets people see their balance, their total expense and income.
   * Lets people see a visualization that analyzes how they're spending money.

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Summary
* Insert
* Profile

Optional:
* Scan (a screen to scan receipts)

**Flow Navigation** (Screen to Screen)
* Forced Log-in -> Account creation if no log in is available
* Insert -> Text field to be modified, radio buttons for categories
* Profile -> Text field to be modified. 

## Wireframes
<img src="https://imgur.com/zhYXw0J.jpg" width=800><br>

## Schema
### Models

**Transaction**

| Properties (json key)  | Type            | Description |
| :--------------------- |:--------------- | :---------- |
| objectId               | String          | User identifier for transaction |
| name                   | String          | Name of transaction |
| isIncome               | Boolean         | Is income or not (expense) |
| cost                   | Number          | Total amount of a transaction |
| date                   | Date            | Date when transaction was made. |
| user                   | Number          | Identifier of the user |
| itemCount              | Number          | Number of items purchased (optional) |
| category               | Number          | categoryId from Category Model |
| isEssential            | Boolean         | Is essential or not (nonessential) |
| receipt                | File            | Scanned image of the receipt (optional) |

**Category**

| Properties (json key)  | Type            | Description |
| :--------------------- |:--------------- | :---------- |
| id                     | Number          | Unique identifier for category |
| name                   | String          | Name of category

**User**

| Properties (json key)  | Type            | Description |
| :--------------------- |:--------------- | :---------- |
| objectId               | String          | Unique identifier for user |
| username               | String          | Username |
| password               | String          | Password |
| createdAt              | Date            | Date when account was created |
| avatar                 | File            | Avatar of user (optional)|
| balance                | Number          | Current balance in user’s account |
| totExp                 | Number          | Total spending of user |
| totInc                 | Number          | Total income of user |

### Networking

**List of network requests by screen**

* Log In/Sign Up Screen
   * (Read/GET) Query logged in user object
   * (Create/POST) Create new user object
* Summary Screen
   * (Read/GET) Query user’s transaction history
   * (Update/PUT) Modify a transaction
* Scan Screen
   * (Create/POST) Create a new Transaction object (with receipt field not null)
* Insert Screen
   * (Create/POST) Create a new Transaction object
   * (Update/PUT) Modify User’s totExp/totInc and balance
* Profile Screen
   * (Read/GET) Display user’s name
   * (Read/GET) Display user’s balance, totInc and totExp
   * (Read/GET) Display user’s spending summary visualization
