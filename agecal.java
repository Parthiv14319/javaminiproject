public class agecal {

    public static void main(String[] args) {
        // Ensure four command line arguments are provided
        if (args.length != 4) {
            System.out.println("Error: Please provide all 4 arguments: DOB/AGE, Reference Date, Date Format, Delimiter");
            return;
        }

        String dobOrAge = args[0];  // DOB=YYYY-MM-DD or AGE=XX-YY-ZZ
        String refDate = args[1];   // Reference date like 27-02-2024
        String dateFormat = args[2]; // Format: YYYY-MM-DD, DD-MM-YYYY, MM-DD-YYYY
        String delimiter = args[3]; // Delimiter: "-", "/", "."

        String[] refDateParts = refDate.split(delimiter);
        int refDay = 0, refMonth = 0, refYear = 0;

        // Determine reference date components based on the date format provided using if-else
        if (dateFormat.equals("YYYY-MM-DD")) {
            refYear = Integer.parseInt(refDateParts[0]);
            refMonth = Integer.parseInt(refDateParts[1]);
            refDay = Integer.parseInt(refDateParts[2]);
        } else if (dateFormat.equals("DD-MM-YYYY")) {
            refDay = Integer.parseInt(refDateParts[0]);
            refMonth = Integer.parseInt(refDateParts[1]);
            refYear = Integer.parseInt(refDateParts[2]);
        } else if (dateFormat.equals("MM-DD-YYYY")) {
            refMonth = Integer.parseInt(refDateParts[0]);
            refDay = Integer.parseInt(refDateParts[1]);
            refYear = Integer.parseInt(refDateParts[2]);
        } else {
            System.out.println("Error: Unsupported date format");
            return;
        }

        if (!isValidDate(refDay, refMonth, refYear)) {
            System.out.println("Error: Invalid reference date");
            return;
        }

        // Determine if input is DOB or AGE and split accordingly
        if (dobOrAge.startsWith("DOB=")) {
            String dob = dobOrAge.split("=")[1];
            String[] dobParts = dob.split(delimiter);

            int day = 0, month = 0, year = 0;
            if (dateFormat.equals("YYYY-MM-DD")) {
                year = Integer.parseInt(dobParts[0]);
                month = Integer.parseInt(dobParts[1]);
                day = Integer.parseInt(dobParts[2]);
            } else if (dateFormat.equals("DD-MM-YYYY")) {
                day = Integer.parseInt(dobParts[0]);
                month = Integer.parseInt(dobParts[1]);
                year = Integer.parseInt(dobParts[2]);
            } else if (dateFormat.equals("MM-DD-YYYY")) {
                month = Integer.parseInt(dobParts[0]);
                day = Integer.parseInt(dobParts[1]);
                year = Integer.parseInt(dobParts[2]);
            } else {
                System.out.println("Error: Unsupported date format");
                return;
            }

            if (!isValidDate(day, month, year)) {
                System.out.println("Error: Invalid DOB");
                return;
            }

            calculateAge(day, month, year, refDay, refMonth, refYear);

        } else if (dobOrAge.startsWith("AGE=")) {
            String age = dobOrAge.split("=")[1];
            String[] ageParts = age.split(delimiter);

            int ageYears = Integer.parseInt(ageParts[0]);
            int ageMonths = Integer.parseInt(ageParts[1]);
            int ageDays = Integer.parseInt(ageParts[2]);

            calculateDOB(ageYears, ageMonths, ageDays, refDay, refMonth, refYear);
        } else {
            System.out.println("Error: Invalid input format. Use DOB= or AGE=");
        }
    }

    // Validate if the date is correct and handle leap years
    public static boolean isValidDate(int day, int month, int year) {
        int[] daysInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

        // Handle leap year for February
        if (month == 2 && isLeapYear(year)) {
            return day > 0 && day <= 29;
        } else if (month < 1 || month > 12) {
            return false;
        }

        return day > 0 && day <= daysInMonth[month - 1];
    }

    // Check if a given year is a leap year
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    // Calculate age based on DOB and reference date
    public static void calculateAge(int day, int month, int year, int refDay, int refMonth, int refYear) {
        int ageYears = refYear - year;
        int ageMonths = refMonth - month;
        int ageDays = refDay - day;

        // Adjust months and days if negative
        if (ageDays < 0) {
            ageDays += 30;  // Adding 30 days as a rough approximation
            ageMonths--;
        }
        if (ageMonths < 0) {
            ageMonths += 12;
            ageYears--;
        }

        // Check if the birth month has passed in the reference year
        if (refMonth == month && refDay == day) {
            System.out.println("Age is: " + (ageYears + 1) + " years, " + ageMonths + " months, " + ageDays + " days");
        } else {
            System.out.println("Age is: " + ageYears + " years, " + ageMonths + " months, " + ageDays + " days");
        }
    }

    // Calculate DOB based on age and reference date
    public static void calculateDOB(int ageYears, int ageMonths, int ageDays, int refDay, int refMonth, int refYear) {
        int dobYear = refYear - ageYears;
        int dobMonth = refMonth - ageMonths;
        int dobDay = refDay - ageDays;

        // Adjust month and day if negative
        if (dobDay <= 0) {
            dobDay += 30;
            dobMonth--;
        }
        if (dobMonth <= 0) {
            dobMonth += 12;
            dobYear--;
        }

        System.out.println("DOB is: " + dobDay + "-" + dobMonth + "-" + dobYear);
    }
}

