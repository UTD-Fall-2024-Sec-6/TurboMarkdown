// Aaron Jacob
// AXJ210111

import java.io.*;
import java.util.Scanner;

public class Main {
    public static double calculateOrderCost(char drinkSize, String drinkType, double pricePerSquareInch, int numDrinks) {
        // All costs in USD.
        final double sodaPerOunceCost = 0.20;
        final double teaPerOunceCost = 0.12;
        final double punchPerOunceCost = 0.15;

        // All surface area calculations in square inches.
        // Surface area formula: pi * cup diameter * cup height
        final double smallSurfaceArea = Math.PI * 4 * 4.5;
        final double mediumSurfaceArea = Math.PI * 4.5 * 5.75;
        final double largeSurfaceArea = Math.PI * 5.5 * 7;

        // All volumes in fluid ounces.
        final double smallVolume = 12;
        final double mediumVolume = 20;
        final double largeVolume = 32;

        double orderCost = 0;

        // Order cost formula: Number of drinks to buy * (drink per-ounce cost * drink volume + graphic price per square
        // inch * drink surface area)
        if (drinkSize == 'S' && drinkType.equals("soda")) {
            orderCost = numDrinks * (sodaPerOunceCost * smallVolume + pricePerSquareInch * smallSurfaceArea);
        }
        else if (drinkSize == 'M' && drinkType.equals("soda")) {
            orderCost = numDrinks * (sodaPerOunceCost * mediumVolume + pricePerSquareInch * mediumSurfaceArea);
        }
        else if (drinkSize == 'L' && drinkType.equals("soda")) {
            orderCost = numDrinks * (sodaPerOunceCost * largeVolume + pricePerSquareInch * largeSurfaceArea);
        }
        else if (drinkSize == 'S' && drinkType.equals("tea")) {
            orderCost = numDrinks * (teaPerOunceCost * smallVolume + pricePerSquareInch * smallSurfaceArea);
        }
        else if (drinkSize == 'M' && drinkType.equals("tea")) {
            orderCost = numDrinks * (teaPerOunceCost * mediumVolume + pricePerSquareInch * mediumSurfaceArea);
        }
        else if (drinkSize == 'L' && drinkType.equals("tea")) {
            orderCost = numDrinks * (teaPerOunceCost * largeVolume + pricePerSquareInch * largeSurfaceArea);
        }
        else if (drinkSize == 'S' && drinkType.equals("punch")) {
            orderCost = numDrinks * (punchPerOunceCost * smallVolume + pricePerSquareInch * smallSurfaceArea);
        }
        else if (drinkSize == 'M' && drinkType.equals("punch")) {
            orderCost = numDrinks * (punchPerOunceCost * mediumVolume + pricePerSquareInch * mediumSurfaceArea);
        }
        else if (drinkSize == 'L' && drinkType.equals("punch")) {
            orderCost = numDrinks * (punchPerOunceCost * largeVolume + pricePerSquareInch * largeSurfaceArea);
        }

        return orderCost;
    }

    public static Customer[] addPreferredCustomerToPreferredCustomerArray(Customer preferredCustomer,
                                                                          Customer[] originalArray) {
        Customer[] newArray;

        if (originalArray != null) { // If the original preferred customer array already exists
            newArray = new Customer[originalArray.length + 1];

            for (int i = 0; i < originalArray.length; i++) {
                newArray[i] = originalArray[i];
            }
        }
        else { // If the preferred customer array hasn't been created (because the input file didn't exist or was empty)
            newArray = new Customer[1];
        }

        newArray[newArray.length - 1] = preferredCustomer;

        return newArray;
    }

    // Your typical basic array resizing algorithm.
    public static Customer[] removeRegularCustomerFromRegularCustomerArray(int index, Customer[] originalArray) {
        for (int i = index; i < originalArray.length - 1; i++) {
            originalArray[i] = originalArray[i + 1];
        }

        Customer[] newArray = new Customer[originalArray.length - 1];

        for (int i = 0; i < newArray.length; i++) {
            newArray[i] = originalArray[i];
        }

        return newArray;
    }

    public static void main(String[] args) throws IOException {
        Scanner scnr = new Scanner(System.in);

        // Open regular customer file.
        String regularCustomerFileName = "";
        FileInputStream inputFileStream;
        Scanner inputFileScanner = null;

        boolean isFileNotFound = true;

        while (isFileNotFound) {
            System.out.print("Enter the regular customer file name: ");
            regularCustomerFileName = scnr.nextLine();

            try {
                inputFileStream = new FileInputStream(regularCustomerFileName);
                inputFileScanner = new Scanner(inputFileStream);
                isFileNotFound = false;
            } catch (FileNotFoundException exception) {
                /* Do nothing here. Will loop again until file is found. */
            }
        }

        int numRegularCustomers = 0;

        while (inputFileScanner.hasNextLine()) {
            inputFileScanner.nextLine();
            numRegularCustomers++;
        }

        Customer[] regularCustomerArray = new Customer[numRegularCustomers];

        inputFileScanner.close();
        inputFileStream = new FileInputStream(regularCustomerFileName);
        inputFileScanner = new Scanner(inputFileStream);

        // Read in regular customers from the file into the regular customer array.
        String guestId;
        String firstName;
        String lastName;
        double amountSpent;

        for (int i = 0; i < numRegularCustomers; i++) {
            guestId = inputFileScanner.next();
            firstName = inputFileScanner.next();
            lastName = inputFileScanner.next();
            amountSpent = inputFileScanner.nextDouble();

            regularCustomerArray[i] = new Customer(firstName, lastName, guestId, amountSpent);
        }

        // TODO: Remove debug statements
        for (Customer customer : regularCustomerArray) {
            System.out.println(customer);
        }

        inputFileScanner.close();

        // Open the preferred customer file (if there is one) and count the number of preferred customers in the file.
        System.out.print("Enter the preferred customer file name: ");
        String preferredCustomerFileName = scnr.nextLine();

        int numPreferredCustomers = 0;

        try {
            inputFileStream = new FileInputStream(preferredCustomerFileName);
            inputFileScanner = new Scanner(inputFileStream);

            while (inputFileScanner.hasNextLine()) {
                inputFileScanner.nextLine();
                numPreferredCustomers++;
            }

            inputFileScanner.close();
        }
        catch (FileNotFoundException exception) { /* Do nothing when a FileNotFoundException is thrown. */ }

        Customer[] preferredCustomerArray = null; // No array is created until a preferred customer is found.

        if (numPreferredCustomers > 0) { // If there are preferred customers
            preferredCustomerArray = new Customer[numPreferredCustomers]; // Read in preferred customers into an array.

            inputFileStream = new FileInputStream(preferredCustomerFileName);
            inputFileScanner = new Scanner(inputFileStream);

            int discountPercentage;
            int bonusBucks;
            String discountPercentageOrBonusBucks;

            for (int i = 0; i < numPreferredCustomers; i++) {
                guestId = inputFileScanner.next();
                firstName = inputFileScanner.next();
                lastName = inputFileScanner.next();
                amountSpent = inputFileScanner.nextDouble();
                discountPercentageOrBonusBucks = inputFileScanner.next();

                if (discountPercentageOrBonusBucks.contains("%")) { // If the preferred customer is a gold customer
                    discountPercentage = Integer.parseInt(discountPercentageOrBonusBucks.substring(0,
                            discountPercentageOrBonusBucks.indexOf("%")));

                    preferredCustomerArray[i] = new Gold(firstName, lastName, guestId, amountSpent,
                            discountPercentage);
                }
                else { // If the customer is platinum
                    bonusBucks = Integer.parseInt(discountPercentageOrBonusBucks);

                    preferredCustomerArray[i] = new Platinum(firstName, lastName, guestId, amountSpent,
                            bonusBucks);
                }
            }

            inputFileScanner.close();

            // TODO: Remove debug statements
            for (Customer customer : preferredCustomerArray) {
                System.out.println(customer);
            }
        }

        // Open order file
        String orderFileName;

        isFileNotFound = true;

        while (isFileNotFound) {
            System.out.print("Enter the order file name: ");
            orderFileName = scnr.nextLine();

            try {
                inputFileStream = new FileInputStream(orderFileName);
                inputFileScanner = new Scanner(inputFileStream);
                isFileNotFound = false;
            }
            catch (FileNotFoundException exception) {
                /* Do nothing here. Will loop again until file is found. */
            }
        }

        // FOR EACH LINE IN THE ORDER FILE *****************************************************************************
        // *************************************************************************************************************
        while (inputFileScanner.hasNextLine()) {
            String orderLine = inputFileScanner.nextLine();

            Customer customer = null;
            String orderGuestId;
            String guestStatus = "";
            int guestArrayIndex = -1;
            String drinkSizeString;
            char drinkSize;
            String drinkType;
            double pricePerSquareInch;
            int numDrinks;

            // Order line input validation #1: Line has correct number of fields.
            int spaceCount = 0;
            for (int i = 0; i < orderLine.length(); i++) {
                if (orderLine.charAt(i) == ' ') {
                    spaceCount++;
                }
            }

            if (spaceCount != 4) { // If there are more or less than 5 fields (4 spaces) in the line
                // Continue to the next order line (ignore current one).
                continue;
            }

            Scanner orderLineScanner = new Scanner(orderLine);

            // TODO: For core implementation, assume all orders in the order file are valid.
            orderGuestId = orderLineScanner.next();

            // Order line input validation #2: Line guest ID matches a customer in either customer array.
            boolean guestIdMatchesOrder = false;
            for (int i = 0; i < regularCustomerArray.length; i++) { // Check regular customer array first.
                if (regularCustomerArray[i].getGuestId().equals(orderGuestId)) {
                    // TODO: Guest ID matches order.
                    guestIdMatchesOrder = true;
                    customer = regularCustomerArray[i]; // Get the corresponding customer.
                    guestStatus = "regular";
                    guestArrayIndex = i;
                    break;
                }
            }

            if (!guestIdMatchesOrder) { // If not in regular customer array, check preferred customer array.
                if (preferredCustomerArray != null) {
                    for (int i = 0; i < preferredCustomerArray.length; i++) {
                        if (preferredCustomerArray[i].getGuestId().equals(orderGuestId)) {
                            // TODO: Guest ID matches order.
                            guestIdMatchesOrder = true;
                            customer = preferredCustomerArray[i];
                            double guestAmountSpent = preferredCustomerArray[i].getAmountSpent();

                            if (guestAmountSpent >= 50 && guestAmountSpent < 200) {
                                guestStatus = "gold";
                            } else {
                                guestStatus = "platinum";
                            }

                            guestArrayIndex = i;
                            break;
                        }
                    }
                }
            }

            if (!guestIdMatchesOrder) { // If not in either array
                orderLineScanner.close();
                continue; // Continue to the next line.
            }

            drinkSizeString = orderLineScanner.next();

            // Order line input validation #3: Line drink size must be "S", "M", or "L".
            if (drinkSizeString.length() > 1) { // If drink size is more than one character
                orderLineScanner.close();
                continue; // Continue to the next line.
            }
            // If drink size is not S, M, or L
            else if ( !(drinkSizeString.equals("S") || drinkSizeString.equals("M") || drinkSizeString.equals("L")) ) {
                orderLineScanner.close();
                continue; // Continue to the next line.
            }
            else {
                drinkSize = drinkSizeString.charAt(0);
            }

            drinkType = orderLineScanner.next();

            // Order line input validation #4: Line drink type must be "soda", "tea", or "punch".
            if (!drinkType.equals("soda") && !drinkType.equals("tea") && !drinkType.equals("punch")) {
                orderLineScanner.close();
                continue; // Continue to the next line.
            }

            // Order line input validation #5: Line graphic price per square inch contains no garbage characters.
            try {
                pricePerSquareInch = orderLineScanner.nextDouble();
            }
            catch (java.util.InputMismatchException exception) {
                orderLineScanner.close();
                continue; // Continue to the next line.
            }

            // Order line input validation #6: Line number of drinks contains no garbage characters.
            try {
                numDrinks = orderLineScanner.nextInt();
            }
            catch (java.util.InputMismatchException exception) {
                orderLineScanner.close();
                continue; // Continue to the next line.
            }

            // Calculate raw order cost.
            double orderCost = calculateOrderCost(drinkSize, drinkType, pricePerSquareInch, numDrinks);

            // TODO: Remove debug statement
            System.out.println(orderCost);

            if (customer.getAmountSpent() < 200) { // If the customer is already regular or gold
                double newAmountSpentBeforeAddedDiscounts = customer.getAmountSpent() + orderCost;

                // Determine if the gold discounts apply after applying the raw order cost and apply those discounts.
                if (newAmountSpentBeforeAddedDiscounts >= 150) {
                    customer.setAmountSpent(customer.getAmountSpent() + 0.85 * orderCost);
                }
                else if (newAmountSpentBeforeAddedDiscounts >= 100) {
                    customer.setAmountSpent(customer.getAmountSpent() + 0.90 * orderCost);
                }
                else if (newAmountSpentBeforeAddedDiscounts >= 50) {
                    customer.setAmountSpent(customer.getAmountSpent() + 0.95 * orderCost);
                }
                else {
                    customer.setAmountSpent(newAmountSpentBeforeAddedDiscounts);
                }

                if (customer.getAmountSpent() >= 200) { // If new amount spent makes regular or gold into platinum
                    Customer upgradedCustomer = new Platinum(customer.getFirstName(),
                            customer.getLastName(), customer.getGuestId(), customer.getAmountSpent(),
                            (int) ((customer.getAmountSpent() - 200) / 5)); // Formula for calculating initial bonus

                    if (guestStatus.equals("regular")) { // If originally regular, move them to preferred array.
                        preferredCustomerArray = addPreferredCustomerToPreferredCustomerArray(upgradedCustomer,
                                preferredCustomerArray);
                        regularCustomerArray = removeRegularCustomerFromRegularCustomerArray(guestArrayIndex,
                                regularCustomerArray);
                    }
                    else {
                        preferredCustomerArray[guestArrayIndex] = upgradedCustomer; // Otherwise, replace gold with platinum.
                    }
                }
                else if (customer.getAmountSpent() >= 50 && guestStatus.equals("regular")) { // If orig. reg. -> gold
                    int newDiscountPercentage = 0;

                    if (customer.getAmountSpent() >= 150) {
                        newDiscountPercentage = 15;
                    }
                    else if (customer.getAmountSpent() >= 100) {
                        newDiscountPercentage = 10;
                    }
                    else if (customer.getAmountSpent() >= 50) {
                        newDiscountPercentage = 5;
                    }

                    Customer upgradedCustomer = new Gold(customer.getFirstName(), customer.getLastName(),
                            customer.getGuestId(), customer.getAmountSpent(), newDiscountPercentage);

                    preferredCustomerArray = addPreferredCustomerToPreferredCustomerArray(upgradedCustomer,
                            preferredCustomerArray);
                    regularCustomerArray = removeRegularCustomerFromRegularCustomerArray(guestArrayIndex,
                            regularCustomerArray);
                }
                else if (customer.getAmountSpent() >= 50 && guestStatus.equals("gold")) { // If gold remains gold
                    if (customer.getAmountSpent() >= 150) {
                        ((Gold) customer).setDiscountPercentage(15);
                    }
                    else if (customer.getAmountSpent() >= 100) {
                        ((Gold) customer).setDiscountPercentage(10);
                    }
                    else {
                        ((Gold) customer).setDiscountPercentage(5);
                    }
                }
            }
            else { // If customer is already a platinum customer
                if (orderCost >= ((Platinum) customer).getBonusBucks()) {
                    orderCost -= ((Platinum) customer).getBonusBucks(); // Apply bonus bucks discount
                    ((Platinum) customer).setBonusBucks((int) (orderCost / 5)); // Calculate bonus earned (1 b.b./$5)
                }
                else {
                    ((Platinum) customer).setBonusBucks(
                            ((Platinum) customer).getBonusBucks() - (int) Math.ceil(orderCost) // Order cost is fully covered by bonus bucks
                    );

                    orderCost = 0;
                }

                customer.setAmountSpent(customer.getAmountSpent() + orderCost);
            }

            // TODO: Remove debug statements
            System.out.println("Regular ---");
            for (Customer c : regularCustomerArray) {
                System.out.println(c);
            }

            System.out.println("Preferred ---");
            for (Customer c : preferredCustomerArray) {
                System.out.println(c);
            }
        }

        FileOutputStream outputFileStream = new FileOutputStream("customer.dat");
        PrintWriter outputFileWriter = new PrintWriter(outputFileStream);

        for (Customer c : regularCustomerArray) {
            outputFileWriter.print(c.getGuestId() + " ");
            outputFileWriter.print(c.getFirstName() + " ");
            outputFileWriter.print(c.getLastName() + " ");
            outputFileWriter.printf("%.2f\n", Math.round(c.getAmountSpent() * 100) / 100.0);
        }

        outputFileWriter.close();

        outputFileStream = new FileOutputStream("preferred.dat");
        outputFileWriter = new PrintWriter(outputFileStream);

        for (Customer c : preferredCustomerArray) {
            outputFileWriter.print(c.getGuestId() + " ");
            outputFileWriter.print(c.getFirstName() + " ");
            outputFileWriter.print(c.getLastName() + " ");
            outputFileWriter.printf("%.2f ", Math.round(c.getAmountSpent() * 100) / 100.0);

            if (c.getAmountSpent() >= 200) {
                outputFileWriter.print(((Platinum) c).getBonusBucks() + "\n");
            }
            else {
                outputFileWriter.print(((Gold) c).getDiscountPercentage() + "%\n");
            }
        }

        outputFileWriter.close();
    }
}