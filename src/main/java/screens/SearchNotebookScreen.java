package main.java.screens;

import org.apache.log4j.Level;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchNotebookScreen extends SearchScreen {

    private int amountManufacturer = 0;
    private static final String SEARCH_ELEMENT = "//td[@class='textmain']//td[@class='pimage']/following-sibling::td[@class='pdescr']";
    private static final String NAME_SEARCH_ELEMENT = SEARCH_ELEMENT + "//strong[contains(@class,'pname')]//a";
    private static final String SEARCH_ELEMENT_BY_NAME = NAME_SEARCH_ELEMENT + "[contains(.,'%s')]";
    private static final String MANUFACTURER_SELECT = FILTER_FORM + "//select[@id='lmfr%d']";
    private static final String MANUFACTURER_ADD = FILTER_FORM + "//tr[contains(@id,'more_mfrs')]//a";
    private static final String MIN_PRICE_INPUT = FILTER_FORM + "//input[contains(@id,'smth2')]";
    private static final String MAX_PRICE_INPUT = FILTER_FORM + "//input[contains(@id,'smth3')]";
    private static final String MIN_INCHES_SELECT = FILTER_FORM + "//select[contains(@id,'ldiagonalnb')]";
    private static final String MAX_INCHES_SELECT = FILTER_FORM + "//select[contains(@id,'ldiagonalnb_2')]";
    private static final String SEARCH_BUTTON = "//input[@name='search']";
    private static final String SORT_PRICE_BUTTON = "//td[contains(.,'Сортировать по')]//a[contains(.,'Цене')]";
    private static final String SEARCH_PAGE = "//a[contains(@title,' Страница %d ')]";
    private static final String SEARCH_PAGES = "//table[@class='phed']//strong[contains(.,'Страницы')]/a";

    public SearchNotebookScreen setManufacturer(String nameManufacturer) {
        log.log(Level.INFO, String.format("Выбираем производителя '%s' (xpath='%s')", nameManufacturer,
                String.format(MANUFACTURER_SELECT, amountManufacturer)));
        driver.selectOptionByText(String.format(MANUFACTURER_SELECT, amountManufacturer), nameManufacturer);
        return this;
    }

    public SearchNotebookScreen addManufacturer() {
        log.log(Level.INFO, String.format("Добавляем нового производителя (xpath='%s')", MANUFACTURER_ADD));
        driver.clickByXpath(MANUFACTURER_ADD);
        amountManufacturer++;
        return this;
    }

    public SearchNotebookScreen setFewManufacturers(List<String> manufacturers) {
        for (int i = 0; i < manufacturers.size(); i++) {
            setManufacturer(manufacturers.get(i));
            if (i != manufacturers.size() - 1) {
                addManufacturer();
            }
        }
        return this;
    }

    public SearchNotebookScreen setPrice(String minPrice, String maxPrice) {
        log.log(Level.INFO, String.format("Выбираем минимальную цену '%s' (xpath='%s')", minPrice, MIN_PRICE_INPUT));
        driver.typeByXpath(MIN_PRICE_INPUT, minPrice);
        log.log(Level.INFO, String.format("Выбираем максимальную цену '%s' (xpath='%s')", maxPrice, MAX_PRICE_INPUT));
        driver.typeByXpath(MAX_PRICE_INPUT, maxPrice);
        return this;
    }

    public SearchNotebookScreen setInches(String minInches, String maxInches) {
        log.log(Level.INFO, String.format("Выбираем минимальные дюймы '%s' (xpath='%s')", minInches, MIN_INCHES_SELECT));
        driver.selectOptionByText(MIN_INCHES_SELECT, minInches + "\"");
        log.log(Level.INFO, String.format("Выбираем максимальные дюймы '%s' (xpath='%s')", maxInches, MAX_INCHES_SELECT));
        driver.selectOptionByText(MAX_INCHES_SELECT, maxInches + "\"");
        return this;
    }

    public SearchNotebookScreen clickSearch() {
        log.log(Level.INFO, String.format("Нажимаем кнопку 'Подобрать' (xpath='%s')", SEARCH_BUTTON));
        driver.clickByXpath(SEARCH_BUTTON);
        return this;
    }

    public SearchNotebookScreen sortByPrice() {
        log.log(Level.INFO, String.format("Сортируем по цене (xpath='%s')", SORT_PRICE_BUTTON));
        driver.clickByXpath(SORT_PRICE_BUTTON);
        return this;
    }

    public String getNameElementByPosition(int position) {
        log.log(Level.INFO, String.format("Выбираем название ноутбука по позиции %d в списке (xpath='%s')", position, NAME_SEARCH_ELEMENT));
        List<WebElement> searchElements = driver.findElements(By.xpath(NAME_SEARCH_ELEMENT));
        String result = searchElements.get(position - 1).getText();
        log.log(Level.INFO, String.format("Результат: %s", result));
        return result;
    }

    public int getCountSearchElements() {
        log.log(Level.INFO, String.format("Определяем количество ноутбуков в списке (xpath='%s')", SEARCH_ELEMENT));
        int result = driver.getElementsCount(SEARCH_ELEMENT);
        log.log(Level.INFO, String.format("Количество ноутбуков: %d", result));
        return result;
    }

    public SearchNotebookScreen goToSearchPage(int number) {
        log.log(Level.INFO, String.format("Переходим на %d страницу поиска (xpath='%s')", number, SEARCH_PAGE));
        driver.clickByXpath(String.format(SEARCH_PAGE, number));
        return this;
    }

    public SearchNotebookScreen goToLastSearchPage() {
        log.log(Level.INFO, String.format("Переходим на последнюю страницу поиска (xpath='%s')", SEARCH_PAGES));
        List<WebElement> searchPages = driver.findElements(By.xpath(SEARCH_PAGES));
        searchPages.get(searchPages.size() - 1).click();
        return this;
    }

    public boolean isNotebookPresent(String name) {
        log.log(Level.INFO, String.format("Проверяем наличие ноутбука %s в списке (xpath='%s')", name,
                String.format(SEARCH_ELEMENT_BY_NAME, name)));
        boolean result = driver.isElementPresentAndVisible(String.format(SEARCH_ELEMENT_BY_NAME, name));
        log.log(Level.INFO, String.format("Результат: %s", result));
        return result;
    }

}
