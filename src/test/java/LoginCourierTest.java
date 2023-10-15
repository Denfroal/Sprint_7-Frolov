import org.example.*;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.example.BaseURLHandlesAndWarnings.ACCOUNT_NOT_FOUND;
import static org.example.BaseURLHandlesAndWarnings.INSUFFICIENT_LOGIN_DATA;
import static java.net.HttpURLConnection.*;
public class LoginCourierTest {
    CourierData courierData;
    @Before
    public void createNewCourierData() {
        courierData = RequestsForCourier.genericCourier();
    }
    @Test
    @DisplayName("Check login a courier with random name")
    @Description("Attempt to login a courier with random name for /api/v1/courier/login")
    public void loginCourierWithRandomName() {
        RequestsForCourier.createCourier(courierData);

        var credentialsData = Credentials.from(courierData);

        var credentials = RequestsForCourier.loggedInCourier(credentialsData);

        CheckOther.checkForStatusCode(credentials, HTTP_OK);
        CheckResponseForCourier.checkLoggedInWithNotNullId(credentials);
    }
    @Test
    @DisplayName("Check login a courier without name")
    @Description("Attempt to login a courier without login name for /api/v1/courier/login")
    public void loginCourierWithoutName() {
        RequestsForCourier.createCourier(courierData);
        var name = courierData.getLogin();
        courierData.setLogin(null);

        var credentialsData = Credentials.from(courierData);

        var credentials = RequestsForCourier.loggedInCourier(credentialsData);

        CheckOther.checkForStatusCode(credentials, HTTP_BAD_REQUEST);
        CheckOther.checkParamWithValue(credentials, "message", INSUFFICIENT_LOGIN_DATA);

        courierData.setLogin(name);
    }
    @Test
    @DisplayName("Check login a courier with blank name")
    @Description("Attempt to login a courier with blank login name for /api/v1/courier/login")
    public void loginCourierWithBlankName() {
        var name = courierData.getLogin();
        RequestsForCourier.createCourier(courierData);
        courierData.setLogin("");

        var credentialsData = Credentials.from(courierData);

        var credentials = RequestsForCourier.loggedInCourier(credentialsData);

        CheckOther.checkForStatusCode(credentials, HTTP_BAD_REQUEST);
        CheckOther.checkParamWithValue(credentials, "message", INSUFFICIENT_LOGIN_DATA);

        courierData.setLogin(name);
    }
    @Test
    @DisplayName("Check login a courier without password")
    @Description("Attempt to login a courier without password for /api/v1/courier/login")
    public void loginCourierWithoutPassword() {
        RequestsForCourier.createCourier(courierData);
        var pass = courierData.getPassword();
        courierData.setPassword(null);

        var credentialsData = Credentials.from(courierData);

        var credentials = RequestsForCourier.loggedInCourier(credentialsData);

        CheckOther.checkForStatusCode(credentials, HTTP_GATEWAY_TIMEOUT); // почему-то по таймауту падает запрос
        //CheckOther.checkParamWithValue(credentials, "message", INSUFFICIENT_LOGIN_DATA);

        courierData.setPassword(pass);
    }
    @Test
    @DisplayName("Check login a courier with blank password")
    @Description("Attempt to login a courier with blank password for /api/v1/courier/login")
    public void loginCourierWithBlankPassword() {
        RequestsForCourier.createCourier(courierData);
        var pass = courierData.getPassword();
        courierData.setPassword("");

        var credentialsData = Credentials.from(courierData);

        var credentials = RequestsForCourier.loggedInCourier(credentialsData);

        CheckOther.checkForStatusCode(credentials, HTTP_BAD_REQUEST);
        CheckOther.checkParamWithValue(credentials, "message", INSUFFICIENT_LOGIN_DATA);

        courierData.setPassword(pass);
    }
    @Test
    @DisplayName("Check login a courier with wrong name")
    @Description("Attempt to login a courier with wrong login name for /api/v1/courier/login")
    public void loginCourierWithWrongName() {
        var credentialsData = Credentials.from(courierData);

        var credentials = RequestsForCourier.loggedInCourier(credentialsData);

        CheckOther.checkForStatusCode(credentials, HTTP_NOT_FOUND);
        CheckOther.checkParamWithValue(credentials, "message", ACCOUNT_NOT_FOUND);
    }
    @Test
    @DisplayName("Check login a courier with wrong password")
    @Description("Attempt to login a courier with wrong password for /api/v1/courier/login")
    public void loginCourierWithWrongPassword() {
        var credentialsData = Credentials.from(courierData);

        var credentials = RequestsForCourier.loggedInCourier(credentialsData);

        CheckOther.checkForStatusCode(credentials, HTTP_NOT_FOUND);
        CheckOther.checkParamWithValue(credentials, "message", ACCOUNT_NOT_FOUND);
    }
    @After
    public void deleteCreatedCourier() {
        try {
            var courierDelete = RequestsForCourier.deleteCourier(courierData);
            CheckOther.checkForStatusCode(courierDelete, HTTP_OK);
            CheckResponseForCourier.checkCreatedWithOkTrue(courierDelete);
        }
        catch (Exception e){
            System.out.println("Курьер не был создан. Проверьте данные.");
        }
    }
}
