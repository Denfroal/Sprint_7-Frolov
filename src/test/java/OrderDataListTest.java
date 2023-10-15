import org.example.CheckOther;
import org.example.CheckResponseForOrder;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.example.BaseURLHandlesAndWarnings.ORDERS_HANDLE;
import static org.example.OtherRequests.*;
import static java.net.HttpURLConnection.HTTP_OK;
public class OrderDataListTest {
    @Test
    @DisplayName("Check order list")
    @Description("Test for getting a general list of orders without CourierId for /api/v1/orders")
    public void checkOrderListWithCourierIdNull() {

        var requestOrderList = getResponseViaGet(ORDERS_HANDLE);

        CheckOther.checkForStatusCode(requestOrderList, HTTP_OK);
        CheckResponseForOrder.checkOrdersIdNotNull(requestOrderList);
    }
}
