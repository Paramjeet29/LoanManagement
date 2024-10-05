package org.loanManagement.dao;


import java.util.List;

import org.loanManagement.entity.*;
import org.loanManagement.exception.InvalidLoanException;

public interface ILoanRepository {
    void applyLoan(Loan loan);
    double calculateInterest(int loanId) throws InvalidLoanException;
    String loanStatus(int loanId) throws InvalidLoanException;
    double calculateEMI(int loanId) throws InvalidLoanException;
    void loanRepayment(int loanId, double amount) throws InvalidLoanException;
    List<Loan> getAllLoan();
    Loan getLoanById(int loanId) throws InvalidLoanException;
}
