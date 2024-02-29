# Hackathon Project

This project has been made as a part of training done under SDET domain in cognizant's training program for GenCs. The requirements for this projects are listed below

## Display Bookshelves

### Suggested Site

![Urban Ladder](https://www.urbanladder.com/deadpul-public/assets/images/branding/animated-logo.b3951.gif)

[Urban Ladder](https://www.urbanladder.com)

> however you are free to use any other legitimate site

### Problem Statement : Display Bookshelves

Display the name, price of

1. Bookshelves below Rs. 15000 
2. Including out of stock
3. Storage type should be open.
4. Take first 3 study chair details with highest recommendation

If more than one item with same price displayed, include that details as well.

### Detailed Description: Hackathon Ideas

1. Display the name & price of first 3 Bookshelves below Rs. 15000, with Storage type as open & exclude out of stock
2. From The Home Page, retrieve all sub-menu items under Top Deals and store in a List; Display the same
3. Gift cards - choose "Birthday/Anniversay"; fill customize the gift card; fill from to details with any one input invalid (example: email)


### Key Automation Scope

- Handling alert, drag & drop, search option
- Extract menu items & store in collections
- Navigating back to home page
- Scrolling down in web page
- Filling form (in different objects in web page)
- Capture warning message

## Project Implementation

| Module      | Test Scenario                          | Test Case                                           |
|-------------|----------------------------------------|-----------------------------------------------------|
| Bookshelves | Basic Navigation                       | basic navigation from the home page to bookshelves  |
|             | Application of price filter            | setting price filter using slider functional or not |
|             | Application of Storage Type Filter     | storage type filter being applied or not            |
|             | Exclude out of stock                   | exclude out of stock is being applied or not        |
| Gift Cards  | Basic Navigation                       | basic navigation from the home page                 |
|             | Filling the Customise form             | positive amount test                                |
|             |                                        | negative amount test                                |
|             |                                        | positive amount test using buttons                  |
|             |                                        | negative amount test using buttons                  |
|             |                                        | positive date test                                  |
|             |                                        | negative date test                                  |
|             | Delivery to all valid zipcodes         | positive zip code test                              |
|             | Filling Customer and Recipient Details | positive customer and recipient details test        |
|             |                                        | negative customer and recipient details test        |
| Home Page   | basic navigation test                  | launching home page on browser                      |
|             | Top Deals Sub Menu Item                | Sub menu item count test                            |
|             |                                        | Check for emptiness in Sub Menue Items              |

