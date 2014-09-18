package test;

import main.java.screens.MainScreen;
import main.java.screens.SearchNotebookScreen;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class OnlinerTest extends Preparations {

    private static final List<String> MANUFACTURERS = Arrays.asList("Sony", "Samsung", "Lenovo", "HP");
    private static final List<String> INCHES = Arrays.asList("12.0", "13.4");
    private static final List<String> PRICES = Arrays.asList("1000", "1500");
    private static final String CATALOG_ITEM = "Ноутбуки";

    @Test
    public void SearchTest() {
        SearchNotebookScreen searchNotebookScreen = factory.createPage(MainScreen.class).clickByCatalog().
                clickByCatalogItem(CATALOG_ITEM).initThisPageAs(SearchNotebookScreen.class).setFewManufacturers(MANUFACTURERS).
                setPrice(PRICES.get(0), PRICES.get(1)).setInches(INCHES.get(0), INCHES.get(1)).clickSearch();
        searchNotebookScreen.getCountSearchElements();
        String notebook = searchNotebookScreen.sortByPrice().getNameElementByPosition(1);
        Assert.assertTrue(searchNotebookScreen.sortByPrice().goToLastSearchPage().isNotebookPresent(notebook),
                String.format("Искомый ноутбук %s не найден в конце списка", notebook));

    }

}
