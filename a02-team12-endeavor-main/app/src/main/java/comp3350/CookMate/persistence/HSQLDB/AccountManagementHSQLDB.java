package comp3350.CookMate.persistence.HSQLDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import comp3350.CookMate.objects.Cook;
import comp3350.CookMate.objects.DietaryRestriction;
import comp3350.CookMate.persistence.AccountManagement;
public class AccountManagementHSQLDB implements AccountManagement
{
        private final String dbPath;
        public AccountManagementHSQLDB(final String dbPath)
        {
                this.dbPath = dbPath;
        }
        private Connection connection() throws SQLException {
                return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
        }

        public List<Cook> getAllCook(){
                final List<Cook> accounts = new ArrayList<>();

                try (final Connection c = connection()) {
                        final Statement st = c.createStatement();
                        final ResultSet rs = st.executeQuery("SELECT * FROM COOK");
                        printResultSet(rs);
                        while (rs.next()) {
                                final String userID = rs.getString("USERID");
                                final String username = rs.getString("USERNAME");
                                final String password = rs.getString("PASSWORD");
                                final String preferredName = rs.getString("PREFERREDNAME");
                                final String dietaryRestrictions = rs.getString("DIETARYRESTRICTIONS");
                                final Cook currentCook = new Cook(userID, username, password, preferredName, null);
                                accounts.add(currentCook);
                        }
                        rs.close();
                        st.close();

                        return accounts;
                } catch (final SQLException e) {
                        throw new AccountManagementException(e);
                }
        }
        public Cook getCook(String userID) {
                try (Connection c = connection()) {
                        PreparedStatement ps = c.prepareStatement("SELECT * FROM COOK WHERE USERID = ?");
                        ps.setString(1, userID);
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                                String username = rs.getString("USERNAME");
                                String password = rs.getString("PASSWORD");
                                String preferredName = rs.getString("PREFERREDNAME");
                                String dietaryRestrictions = rs.getString("DIETARYRESTRICTIONS");
                                List<DietaryRestriction> dietaryRestrictionList = parseDietaryRestrictions(convertDietaryRestrictions(dietaryRestrictions));
                                return new Cook(userID, username, password, preferredName, dietaryRestrictionList);
                        } else {
                                return null;
                        }
                } catch (SQLException e) {
                        throw new AccountManagementException(e);
                }
        }
        public boolean verifyAccount(String userID, String password) {
                try (final Connection c = connection()) {
                        final PreparedStatement ps = c.prepareStatement("SELECT * FROM COOK WHERE USERID = ? AND PASSWORD = ?");
                        ps.setString(1, userID);
                        ps.setString(2, password);
                        final ResultSet rs = ps.executeQuery();

                        if (rs.next()) {
                                // Account found with matching user ID and password
                                rs.close();
                                ps.close();
                                return true;
                        }

                        // Account not found with matching user ID and password
                        rs.close();
                        ps.close();
                        return false;
                } catch (final SQLException e) {
                        throw new AccountManagementException(e);
                }
        }
        public Cook addAccount(Cook newAccount) {
                try (Connection c = connection()) {
                        PreparedStatement ps = c.prepareStatement("INSERT INTO COOK (USERID, USERNAME, PASSWORD, PREFERREDNAME, DIETARYRESTRICTIONS) VALUES (?, ?, ?, ?, ?)");
                        ps.setString(1, newAccount.getUserID());
                        ps.setString(2, newAccount.getUserName());
                        ps.setString(3, newAccount.getPassword());
                        ps.setString(4, newAccount.getPreferredName());
                        ps.setString(5, newAccount.getDietaryRestrictionString());
                        ps.executeUpdate();
                        ps.close();
                } catch (SQLException e) {
                        throw new AccountManagementException(e);
                }
                return newAccount;
        }
        public void deleteAccount(Cook newCook) {
                try (Connection c = connection()) {
                        PreparedStatement ps = c.prepareStatement("DELETE FROM COOK WHERE USERID = ?");
                        ps.setString(1, newCook.getUserID());
                        ps.executeUpdate();
                        ps.close();
                } catch (SQLException e) {
                        throw new AccountManagementException(e);
                }
        }

        // I am not sure if we will created a new class for updating
        // If so please comment the functions below.

        public void updatePassword(Cook account, String newPassword) {
                try (Connection c = connection()) {
                        PreparedStatement ps = c.prepareStatement("UPDATE COOK SET PASSWORD = ? WHERE USERID = ?");
                        ps.setString(1, newPassword);
                        ps.setString(2, account.getUserID());
                        ps.executeUpdate();
                        ps.close();
                } catch (SQLException e) {
                        throw new AccountManagementException(e);
                }
                account.setPassword(newPassword);
        }
        public void updateUserName(Cook account, String newUserName) {
        try (Connection c = connection()) {
                PreparedStatement ps = c.prepareStatement("UPDATE COOK SET USERNAME = ? WHERE USERNAME = ?");
                ps.setString(1, newUserName);
                ps.setString(2, account.getUserName());
                ps.executeUpdate();
                ps.close();
        } catch (SQLException e) {
                throw new AccountManagementException(e);
        }
        account.setUserName(newUserName);
}

        public void updateDietaryRestrictions(Cook account, List<DietaryRestriction> newDietaryRestrictions) {
                try (Connection c = connection()) {
                        PreparedStatement ps = c.prepareStatement("UPDATE COOK SET DIETARYRESTRICTIONS = ? WHERE USERID = ?");
                        List<String> dietaryRestrictionsStringList = ToparseDietaryRestrictions(newDietaryRestrictions);
                        ps.setString(1, String.join(",", dietaryRestrictionsStringList));                        ps.setString(2, account.getUserID());
                        ps.executeUpdate();
                        ps.close();
                        account.setDietaryRestrictions(newDietaryRestrictions);
                } catch (SQLException e) {
                        throw new AccountManagementException(e);
                }
        }

        //void updatePreferredName(Cook account);
        //void addSavedRecipe(Cook account, Recipe savedRecipe);
        //void deleteSavedRecipe(Cook account, Recipe deletedRecipe);

        public static void printResultSet(ResultSet rs) throws SQLException {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                // Print column headers
                for (int i = 1; i <= columnCount; i++) {
                        System.out.printf("%-20s", metaData.getColumnName(i));
                }
                System.out.println();

                // Print rows
                while (rs.next()) {
                        for (int i = 1; i <= columnCount; i++) {
                                System.out.printf("%-20s", rs.getString(i));
                        }
                        System.out.println();
                }
        }

        public static List<String> convertDietaryRestrictions(String dietaryRestrictionsString) {
                List<String> dietaryRestrictions = new LinkedList<>();
                String[] dietaryRestrictionsArray = dietaryRestrictionsString.split(",");
                for (String restriction : dietaryRestrictionsArray) {
                        dietaryRestrictions.add(restriction.trim());
                }
                return dietaryRestrictions;
        }


        public List<DietaryRestriction> parseDietaryRestrictions(List<String> dietaryRestrictions) {
                List<DietaryRestriction> restrictions = new ArrayList<>();
                for (String restriction : dietaryRestrictions) {
                        String[] restrictionNames = restriction.split(",");
                        for (String restrictionName : restrictionNames) {
                                restrictions.add(new DietaryRestriction(restrictionName.trim()));
                        }
                }
                return restrictions;
        }

        private List<String> ToparseDietaryRestrictions(List<DietaryRestriction> dietaryRestrictions) {
                List<String> dietaryRestrictionsStringList = new ArrayList<>();
                for (DietaryRestriction dr : dietaryRestrictions) {
                        dietaryRestrictionsStringList.add(dr.getName());
                }
                return dietaryRestrictionsStringList;
        }




}

