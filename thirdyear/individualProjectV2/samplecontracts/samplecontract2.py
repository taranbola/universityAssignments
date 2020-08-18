def main():
    month_list = ["January","February","March","April","May","June"]
    balance = 3005
    monthly_rent = 500
    print("Current Balance is £" + str(balance))
    for each in month_list:
        if ((balance - monthly_rent ) >= 0):
            balance = balance - monthly_rent
            print("Rent Paid for " + each + " is £" + str(monthly_rent))

    print("Current Balance is £" + str(balance))

main()
