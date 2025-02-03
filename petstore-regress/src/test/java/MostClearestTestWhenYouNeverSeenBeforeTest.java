import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import lombok.SneakyThrows;
import org.example.db.PetstoreDBQueries;
import org.example.config.PetstoreConfiguration;
import org.example.db.model.CategoryModel;
import org.openapitools.client.api.CategoryControllerApi;
import org.openapitools.client.model.CategoryResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.qameta.allure.Allure.step;

@Feature("Pet")
@ContextConfiguration(classes = PetstoreConfiguration.class)
@EnableConfigurationProperties(PetstoreDBQueries.class)

public class MostClearestTestWhenYouNeverSeenBeforeTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private CategoryControllerApi controllerApi;
    @Autowired
    private PetstoreDBQueries petstoreDBQueries;

    CategoryModel categoryModel = new CategoryModel()
            .id(111)
            .name("SHIIT");

    @SneakyThrows
    @Story("GET /pet")
    @Test(description = "Метод GET /pet должен вернуть модель Pet")
    public void getPetIdShouldReturnPetTest() {
        petstoreDBQueries.insertCategory(categoryModel);

        CategoryResponseDto response = step("Отправка запроса GET /category/all", () -> controllerApi.getAllCategory()
                .stream()
                .filter(category -> category.getName().equals(categoryModel.name()))
                .findFirst()
                .get());


        SoftAssert softAssert = new SoftAssert();
        step("Сравнение отправленных данных в sql insert и полученных в GET /category/all", () -> {
            step("Сравнение поля id", () ->
                    softAssert.assertEquals(response.getId().intValue(), categoryModel.id(),
                            "поле id не совпадает с ожидаемым"));
            step("Сравнение поля name", () ->
                    softAssert.assertEquals(response.getName(), categoryModel.name(),
                            "поле name не совпадает с ожидаемым"));
            softAssert.assertAll();
        });
    }

    @AfterMethod(alwaysRun = true)
    private void cleanUp() {
        petstoreDBQueries.deleteCategoryById(categoryModel.id());
    }
}
