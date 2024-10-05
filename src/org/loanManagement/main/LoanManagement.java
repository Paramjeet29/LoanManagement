
package org.loanManagement.main;

import org.loanManagement.dao.LoanRepositoryImpl;
import org.loanManagement.entity.Customer;
import org.loanManagement.entity.Loan;
import org.loanManagement.exception.InvalidLoanException;

import java.util.List;
import java.util.Scanner;

public class LoanManagement {

    public static void main(String[] args) {
        LoanRepositoryImpl loanRepository = new LoanRepositoryImpl();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to the Loan Management System");
            System.out.println("1. Apply for a Loan");
            System.out.println("2. Calculate Interest");
            System.out.println("3. Check Loan Status");
            System.out.println("4. Calculate EMI");
            System.out.println("5. Make Loan Repayment");
            System.out.println("6. View All Loans");
            System.out.println("7. view loan by id");
            System.out.println("8. exit");
            System.out.print("Select an option: ");

            int option = scanner.nextInt();

            switch (option) {
                case 1: // Apply for a Loan
                    System.out.print("Enter Customer ID: ");
                    int customerId = scanner.nextInt();
                    System.out.print("Enter Principal Amount: ");
                    double principalAmount = scanner.nextDouble();
                    System.out.print("Enter Interest Rate: ");
                    double interestRate = scanner.nextDouble();
                    System.out.print("Enter Loan Term (in months): ");
                    int loanTerm = scanner.nextInt();
                    System.out.print("Enter Loan Type: ");
                    String loanType = scanner.next();

                   
                    Customer customer = new Customer(); 
                    customer.setCustomerId(customerId); 

                    Loan loan = new Loan();
                    loan.setCustomer(customer);
                    loan.setPrincipalAmount(principalAmount);
                    loan.setInterestRate(interestRate);
                    loan.setLoanTerm(loanTerm);
                    loan.setLoanType(loanType);

                    loanRepository.applyLoan(loan);
                    break;

                case 2: // Calculate Interest
                    System.out.print("Enter Loan ID: ");
                    int loanIdForInterest = scanner.nextInt();
                    try {
                        double interest = loanRepository.calculateInterest(loanIdForInterest);
                        System.out.println("Calculated Interest: " + interest);
                    } catch (InvalidLoanException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3: // Check Loan Status
                    System.out.print("Enter Loan ID: ");
                    int loanIdForStatus = scanner.nextInt();
                    try {
                        String status = loanRepository.loanStatus(loanIdForStatus);
                        System.out.println("Loan Status: " + status);
                    } catch (InvalidLoanException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4: // Calculate EMI
                    System.out.print("Enter Loan ID: ");
                    int loanIdForEMI = scanner.nextInt();
                    try {
                        double emi = loanRepository.calculateEMI(loanIdForEMI);
                        System.out.println("Calculated EMI: " + emi);
                    } catch (InvalidLoanException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 5:
                    System.out.print("Enter Loan ID: ");
                    int loanIdForRepayment = scanner.nextInt();
                    System.out.print("Enter Repayment Amount: ");
                    double repaymentAmount = scanner.nextDouble();
                    try {
                        loanRepository.loanRepayment(loanIdForRepayment, repaymentAmount);
                    } catch (InvalidLoanException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 6: // View All Loans
                    List<Loan> loans = loanRepository.getAllLoan();
                    System.out.println("All Loans:");
                    for (Loan l : loans) {
                        System.out.println(l); 
                    }
                    break;
                case 7: // View All Loans
                	System.out.print("Enter Loan ID: ");
                    int loanId = scanner.nextInt();
                    try {
                        Loan loan1 = loanRepository.getLoanById(loanId); // Call to getLoanById
                        System.out.println("Loan Details: " + loan1);
                    } catch (InvalidLoanException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 8: // Exit
                    System.out.println("Exiting the Loan Management System.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
