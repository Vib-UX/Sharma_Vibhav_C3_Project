import org.junit.jupiter.api.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantServiceTest {

    RestaurantService service = new RestaurantService();
    Restaurant restaurant;

    @BeforeEach
    public void setup() {
        LocalTime openingTime = LocalTime.parse("09:30:00");
        LocalTime closingTime = LocalTime.parse("21:30:00");
        restaurant = service.addRestaurant("Amelie's cafe", "New Jersey", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("Sizzling brownie", 319);
    }

    // >>>>>>>>>>>>>>>>>>>>>>SEARCHING<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void searching_for_existing_restaurant_should_return_expected_restaurant_object()
            throws restaurantNotFoundException {
        Restaurant restaurant1 = service.findRestaurantByName("Amelie's cafe");
        assertEquals(restaurant.getName(), restaurant1.getName());
    }

    // You may watch the video by Muthukumaran on how to write exceptions in Course
    // 3: Testing and Version control: Optional content
    @Test
    public void searching_for_non_existing_restaurant_should_throw_exception() throws restaurantNotFoundException {
        assertThrows(restaurantNotFoundException.class,
                () -> service.findRestaurantByName("Haldiram"));
    }
    // <<<<<<<<<<<<<<<<<<<<SEARCHING>>>>>>>>>>>>>>>>>>>>>>>>>>

    // >>>>>>>>>>>>>>>>>>>>>>ADMIN: ADDING & REMOVING
    // RESTAURANTS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException {
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.removeRestaurant("Amelie's cafe");
        assertEquals(initialNumberOfRestaurants - 1, service.getRestaurants().size());
    }

    @Test
    public void removing_restaurant_that_does_not_exist_should_throw_exception() throws restaurantNotFoundException {
        assertThrows(restaurantNotFoundException.class, () -> service.removeRestaurant("Pantry d'or"));
    }

    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1() {
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.addRestaurant("Pumpkin Tales", "Chennai", LocalTime.parse("12:00:00"), LocalTime.parse("23:00:00"));
        assertEquals(initialNumberOfRestaurants + 1, service.getRestaurants().size());
    }
    // <<<<<<<<<<<<<<<<<<<<ADMIN: ADDING & REMOVING

    // RESTAURANTS>>>>>>>>>>>>>>>>>>>>>>>>>>
    // <<<<<<<<<<<<<<<<<<<<CALCULATE ORDER>>>>>>>>>>>>>>>>>>>>>>>>>>//
    @Test
    public void adding_items_should_calculate_correct_order_value() {
        List<String> itemsToOrder = new ArrayList<>(Arrays.asList("Coffe", "Vegetable lasagne"));
        int orderValue = 0;
        try {
            orderValue = service.calculateOrder(restaurant.getName(), itemsToOrder);
        } catch (restaurantNotFoundException e) {
            e.printStackTrace();
        }
        assertEquals(orderValue, 269);
    }
    // <<<<<<<<<<<<<<<<<<<<CALCULATE ORDER>>>>>>>>>>>>>>>>>>>>>>>>>>//
}
