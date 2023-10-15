import org.example.CheckOther;
import org.example.CheckResponseForCourier;
import org.example.CourierData;
import org.example.RequestsForCourier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.example.BaseURLHandlesAndWarnings.ALREADY_IN_USE;
import static org.example.BaseURLHandlesAndWarnings.NOT_ENOUGH_DATA;
import static java.net.HttpURLConnection.*;
public class CreateCourierTest {
    CourierData courierData;
    @Before
    public void createNewCourierData() {
        courierData = RequestsForCourier.genericCourier();
    }
    @Test
    @DisplayName("Check creating a courier with random name")
    @Description("Basic test for /api/v1/courier")
    public void createCourierWithRandomName() {
        var courier = RequestsForCourier.createCourier(courierData);

        CheckOther.checkForStatusCode(courier, HTTP_CREATED);
        CheckResponseForCourier.checkCreatedWithOkTrue(courier);
    }
    @Test
    @DisplayName("Check creating a courier with a duplicate name")
    @Description("Attempt to create a courier of a previously used name for /api/v1/courier")
    public void creatingCourierWithDuplicateName() {
        var courier = RequestsForCourier.createCourier(courierData);

        CheckOther.checkForStatusCode(courier, HTTP_CREATED);
        CheckResponseForCourier.checkCreatedWithOkTrue(courier);

        var courier2 = RequestsForCourier.createCourier(courierData);

        CheckOther.checkForStatusCode(courier2, HTTP_CONFLICT);
        CheckOther.checkParamWithValue(courier2, "message", ALREADY_IN_USE);
    }
    @Test
    @DisplayName("Check creating a courier without name")
    @Description("Attempt to create a courier without login name for /api/v1/courier")
    public void creatingCourierWithoutName() {
        courierData.setLogin(null);
        var courier = RequestsForCourier.createCourier(courierData);

        CheckOther.checkForStatusCode(courier, HTTP_BAD_REQUEST);
        CheckOther.checkParamWithValue(courier, "message", NOT_ENOUGH_DATA);
    }
    @Test
    @DisplayName("Check creating a courier without name")
    @Description("Attempt to create a courier without login name for /api/v1/courier")
    public void creatingCourierWithBlankName() {
        courierData.setLogin("");
        var courier = RequestsForCourier.createCourier(courierData);

        CheckOther.checkForStatusCode(courier, HTTP_BAD_REQUEST);
        CheckOther.checkParamWithValue(courier, "message", NOT_ENOUGH_DATA);
    }
    @Test
    @DisplayName("Check creating a courier without password")
    @Description("Attempt to create a courier without password for /api/v1/courier")
    public void creatingCourierWithoutPassword() {
        courierData.setPassword(null);
        var courier = RequestsForCourier.createCourier(courierData);

        CheckOther.checkForStatusCode(courier, HTTP_BAD_REQUEST);
        CheckOther.checkParamWithValue(courier, "message", NOT_ENOUGH_DATA);
    }
    @Test
    @DisplayName("Check creating a courier without password")
    @Description("Attempt to create a courier without password for /api/v1/courier")
    public void creatingCourierWithBlankPassword() {
        courierData.setPassword("");
        var courier = RequestsForCourier.createCourier(courierData);

        CheckOther.checkForStatusCode(courier, HTTP_BAD_REQUEST);
        CheckOther.checkParamWithValue(courier, "message", NOT_ENOUGH_DATA);
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
