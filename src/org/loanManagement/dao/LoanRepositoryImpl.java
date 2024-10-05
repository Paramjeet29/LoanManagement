
package org.loanManagement.dao;

import org.loanManagement.entity.Customer;
import org.loanManagement.entity.Loan;
import org.loanManagement.exception.InvalidLoanException;
import org.loanManagement.util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanRepositoryImpl implements ILoanRepository {

    private Connection getConnection() {
        return DBConnUtil.getConnection(); 
    }


    @Override
    public void applyLoan(Loan loan) {
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(
                 "INSERT INTO Loan (customer_id, principal_amount, interest_rate, loan_term, loan_type, loan_status) VALUES (?, ?, ?, ?, ?, ?)",
                 Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, loan.getCustomer().getCustomerId());
            pstmt.setDouble(2, loan.getPrincipalAmount());
            pstmt.setDouble(3, loan.getInterestRate());
            pstmt.setInt(4, loan.getLoanTerm());
            pstmt.setString(5, loan.getLoanType());
            pstmt.setString(6, "Pending");

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating loan failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    loan.setLoanId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating loan failed, no ID obtained.");
                }
            }

            System.out.println("Loan application submitted successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to apply loan.");
        }
    }
    @Override
    public double calculateInterest(int loanId) throws InvalidLoanException {
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement("SELECT principal_amount, interest_rate, loan_term FROM Loan WHERE loan_id = ?")) { // Updated table name

            pstmt.setInt(1, loanId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                double principalAmount = rs.getDouble("principal_amount");
                double interestRate = rs.getDouble("interest_rate");
                int loanTerm = rs.getInt("loan_term");

                return (principalAmount * interestRate * loanTerm) / 100;
            } else {
                throw new InvalidLoanException("Loan not found with ID: " + loanId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidLoanException("Error calculating interest for loan ID: " + loanId);
        }
    }

    @Override
    public String loanStatus(int loanId) throws InvalidLoanException {
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement("SELECT loan_status FROM Loan WHERE loan_id = ?")) {

            pstmt.setInt(1, loanId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("loan_status");
            } else {
                throw new InvalidLoanException("Loan not found with ID: " + loanId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidLoanException("Error retrieving loan status for loan ID: " + loanId);
        }
    }

    @Override
    public double calculateEMI(int loanId) throws InvalidLoanException {
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement("SELECT principal_amount, interest_rate, loan_term FROM Loan WHERE loan_id = ?")) { 

            pstmt.setInt(1, loanId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                double principal = rs.getDouble("principal_amount");
                double annualInterestRate = rs.getDouble("interest_rate");
                int loanTermInMonths = rs.getInt("loan_term");

                double monthlyInterestRate = annualInterestRate / (12 * 100); 
                return (principal * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, loanTermInMonths)) /
                        (Math.pow(1 + monthlyInterestRate, loanTermInMonths) - 1);
            } else {
                throw new InvalidLoanException("Loan not found with ID: " + loanId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidLoanException("Error calculating EMI for loan ID: " + loanId);
        }
    }

    @Override
    public void loanRepayment(int loanId, double amount) throws InvalidLoanException {
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement("UPDATE Loan SET principal_amount = principal_amount - ? WHERE loan_id = ?")) { // Updated table name

            pstmt.setDouble(1, amount);
            pstmt.setInt(2, loanId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new InvalidLoanException("Loan not found with ID: " + loanId);
            }

            System.out.println("Loan repayment processed successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidLoanException("Error processing loan repayment for loan ID: " + loanId);
        }
    }


    @Override
    public List<Loan> getAllLoan() {
        List<Loan> loans = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(
                 "SELECT l.*, c.customer_id FROM Loan l JOIN Customer c ON l.customer_id = c.customer_id");
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Loan loan = new Loan();
                loan.setLoanId(rs.getInt("loan_id"));
                
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("customer_id"));
                loan.setCustomer(customer);
                
                loan.setPrincipalAmount(rs.getDouble("principal_amount"));
                loan.setInterestRate(rs.getDouble("interest_rate"));
                loan.setLoanTerm(rs.getInt("loan_term"));
                loan.setLoanType(rs.getString("loan_type"));
                loan.setLoanStatus(rs.getString("loan_status"));
                loans.add(loan);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loans;
    }

    @Override
    public Loan getLoanById(int loanId) throws InvalidLoanException {
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(
                 "SELECT l.*, c.customer_id FROM Loan l JOIN Customer c ON l.customer_id = c.customer_id WHERE l.loan_id = ?")) {

            pstmt.setInt(1, loanId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Loan loan = new Loan();
                loan.setLoanId(rs.getInt("loan_id"));
                
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("customer_id"));
                loan.setCustomer(customer);
                
                loan.setPrincipalAmount(rs.getDouble("principal_amount"));
                loan.setInterestRate(rs.getDouble("interest_rate"));
                loan.setLoanTerm(rs.getInt("loan_term"));
                loan.setLoanType(rs.getString("loan_type"));
                loan.setLoanStatus(rs.getString("loan_status"));
                return loan;
            } else {
                throw new InvalidLoanException("Loan not found with ID: " + loanId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidLoanException("Error retrieving loan by ID: " + loanId);
        }
    }
}