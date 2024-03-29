package bookOfCook;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import BookOfCook.Category;
import BookOfCook.Recipe;

public class RecipeTest {
    private Recipe pizza;
    private Category italiensk;
    private HashMap<String, String> ost, melk;

    @BeforeEach
    public void setup() {
        HashMap<String, String> ost = new HashMap<String, String>() {{
            put("name", "ost");
            put("amount", "1.0");
            put("unit", "kg");
        }};
        HashMap<String, String> melk = new HashMap<String, String>() {{
            put("name", "melk");
            put("amount", "2.0");
            put("unit", "L");
        }};

        italiensk = new Category("italiensk");
        pizza = new Recipe("Pizza", 3, "Pizza er godt", "45 minutter", new ArrayList<HashMap<String, String>>(Arrays.asList(ost, melk)), new ArrayList<Category>(Arrays.asList(italiensk)), new ArrayList<String>(Arrays.asList("Rull deig", "schtek pizza")));
    }

    @Test
    @DisplayName("Test pizza name")
    public void checkpizzaName() {
        assertTrue(pizza.getName().equals("Pizza".toUpperCase()));
        assertThrows(IllegalArgumentException.class, () -> new Recipe("Fransk123", 3, "Pizza er godt", "45 minutter", new ArrayList<HashMap<String, String>>(Arrays.asList(ost, melk)), new ArrayList<Category>(Arrays.asList(italiensk)), new ArrayList<String>(Arrays.asList("Rull deig", "schtek pizza"))));
        assertThrows(IllegalArgumentException.class, () -> new Recipe("Fransk ", 3, "Pizza er godt", "45 minutter", new ArrayList<HashMap<String, String>>(Arrays.asList(ost, melk)), new ArrayList<Category>(Arrays.asList(italiensk)), new ArrayList<String>(Arrays.asList("Rull deig", "schtek pizza"))));
        assertThrows(IllegalArgumentException.class, () -> new Recipe(" Fransk", 3, "Pizza er godt", "45 minutter", new ArrayList<HashMap<String, String>>(Arrays.asList(ost, melk)), new ArrayList<Category>(Arrays.asList(italiensk)), new ArrayList<String>(Arrays.asList("Rull deig", "schtek pizza"))));
        assertThrows(IllegalArgumentException.class, () -> new Recipe("Fransk#€%&!", 3, "Pizza er godt", "45 minutter", new ArrayList<HashMap<String, String>>(Arrays.asList(ost, melk)), new ArrayList<Category>(Arrays.asList(italiensk)), new ArrayList<String>(Arrays.asList("Rull deig", "schtek pizza"))));
        assertFalse(pizza.getName().equals("Fransk #@%&!".toUpperCase()));
    }

    @Test
    @DisplayName("Test pizza servings")
    public void checkpizzaServings() {
        assertTrue(pizza.getServings() == 3);
        assertThrows(IllegalArgumentException.class, () -> new Recipe("Pizza", 0, "Pizza er godt", "45 minutter", new ArrayList<HashMap<String, String>>(Arrays.asList(ost, melk)), new ArrayList<Category>(Arrays.asList(italiensk)), new ArrayList<String>(Arrays.asList("Rull deig", "schtek pizza"))));
        assertThrows(IllegalArgumentException.class, () -> new Recipe("Pizza", -1, "Pizza er godt", "45 minutter", new ArrayList<HashMap<String, String>>(Arrays.asList(ost, melk)), new ArrayList<Category>(Arrays.asList(italiensk)), new ArrayList<String>(Arrays.asList("Rull deig", "schtek pizza"))));
    }

    @Test
    @DisplayName("Test pizza description")
    public void checkpizzaDescription() {
        assertTrue(pizza.getDescription() == "Pizza er godt");
    }

    @Test
    @DisplayName("Test Calorie methods")
    public void checkCalorieMethods() {
        assertTrue(pizza.getCal() == 0);
    }

    @Test
    @DisplayName("Test pizza ingredients")
    public void checkpizzaIngredients() {
        pizza.addIngredient("Tomat", "2", "l");
        assertTrue(pizza.getIngredients().size() == 3);
        pizza.addIngredient("Sugar", "1", "tsk");
        assertTrue(pizza.getIngredients().size() == 4);
        assertThrows(IllegalArgumentException.class, () -> pizza.addIngredient("Milk", "-1", "l"));
        assertThrows(IllegalArgumentException.class, () -> pizza.addIngredient("Milk", "0", "l"));
        assertThrows(IllegalArgumentException.class, () -> pizza.addIngredient("Milk", "2", ""));
        assertThrows(IllegalArgumentException.class, () -> pizza.addIngredient("Milk123", "2", "l"));
        assertThrows(IllegalArgumentException.class, () -> pizza.addIngredient("Milk", "2", "l123"));
        assertThrows(IllegalArgumentException.class, () -> pizza.addIngredient("Milk!#¤%", "2", "l"));
        assertThrows(IllegalArgumentException.class, () -> pizza.addIngredient("Milk", "2", "l#¤%&"));
        assertThrows(IllegalArgumentException.class, () -> pizza.addIngredient("Tomat", "2", "l"));
        assertTrue(pizza.getIngredients().size() == 4);
    }
    
    @Test
    @DisplayName("Test Category methods for pizza")
    public void checkCategoryMethods() {
        Category category = new Category("italiensk");
        pizza.addCategory(category);
        assertTrue(category.getRecipes().contains(pizza));
        assertTrue(pizza.getCategories().contains(category));
    }

    @Test
    @DisplayName("Test pizza instructions")
    public void checkpizzaInstructions() {
        assertTrue(pizza.getSteps().contains("Rull deig"));
        assertFalse(pizza.getSteps().contains("Mix"));
        pizza.addStep("Mix");
        assertTrue(pizza.getSteps().contains("Mix"));
        pizza.addStep("Bake");
        assertTrue(pizza.getSteps().contains("Bake"));
        assertThrows(IllegalArgumentException.class, () -> pizza.addStep(""));
        assertThrows(NullPointerException.class, () -> pizza.addStep(null));
    }
}
