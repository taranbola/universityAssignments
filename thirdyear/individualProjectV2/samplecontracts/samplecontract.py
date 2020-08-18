def main():
    deposit = 250
    rent_per_month = 500
    number_of_months = 6
    current_balance = 3000
    print("Checking if rent payments have been payed...")
    if ((number_of_months * rent_per_month) == current_balance):
        print("All rent payments have been payed!")
        print("Deposit of £" + str(deposit) + " has been sent back to the tenant")
        deposit = 0

main()
