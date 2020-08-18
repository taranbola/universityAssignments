def main():
    deposit = 250
    rent_per_month = 500
    number_of_months = 6
    current_balance = 2850
    print("Checking if rent payments have been payed...")
    if ((number_of_months * rent_per_month) > current_balance):
        difference = (number_of_months * rent_per_month) - current_balance
        print("Missing £" + str(difference) + " in Rent")
        deposit = deposit - difference
        if (deposit >= 0):
            print("Deposit of £" + str(deposit) + " has been sent back to the tenant")
            print("£" + str(difference) + " has been taken out for missing rent")
            deposit = 0

main()
