package web.utils;

//import com.sun.org.apache.xml.internal.security.utils.Base64;
import model.Account;
import model.Administrator;
import model.Driver;
import model.Employee;
import model.Manager;

/**
 *
 */
public class AccountUtils {

    public static boolean isAdministrator(Account account) {
        return (account instanceof Administrator);
    }

    public static boolean isEmployee(Account account) {
        return (account instanceof Employee);
    }

    public static boolean isManager(Account account) {
        return (account instanceof Manager);
    }

    public static boolean isDriver(Account account) {
        return (account instanceof Driver);
    }

    /**
     * Przepisuje do przekazanej encji dane z formularza edycji konta.
     * Uwzględnione są klasy rozszerzające Konto (Administrator, Employee,
     * Manager), przy czym tylko dane występujące na formularzu sa przepisywane.
     * Pomijane są: login, hasło, id, wersja.
     *
     * @param source encja zawierająca dane z formularza edycji
     * @param account encja doaccountowa
     */
    public static void rewriteAccountData(Account source, Account account) {

        if (null == source || null == account) {
            return;
        }

        account.setEmail(source.getEmail());
        account.setName(source.getName());
        account.setSurname(source.getSurname());
        account.setPhone(source.getPhone());

        if (isAdministrator(source) && isAdministrator(account)) {
            Administrator sourceAdministrator = (Administrator) source;
            Administrator accountrAdministrator = (Administrator) account;
        }

        if (isEmployee(source) && isEmployee(account)) {
            Employee sourceEmployee = (Employee) source;
            Employee accountEmployee = (Employee) account;
            accountEmployee.setPosition(sourceEmployee.getPosition());
            accountEmployee.setManager(sourceEmployee.getManager());
        }

        if (isManager(source) && isManager(account)) {
            Manager sourceManager = (Manager) source;
            Manager accountManager = (Manager) account;
            accountManager.setDepartment(sourceManager.getDepartment());
            accountManager.setEmployees(sourceManager.getEmployees());

        }
        if (isDriver(source) && isDriver(account)) {
            Driver sourceDriver = (Driver) source;
            Driver accountDriver = (Driver) account;
            accountDriver.setCars(sourceDriver.getCars());
            accountDriver.setLocationId(sourceDriver.getLocationId());

        }
    }
}
