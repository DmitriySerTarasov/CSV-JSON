import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// Класс Product
class Product {
    private UUID id;
    private String name;
    private double price;
    private String manufacturer;
    private String country;

    public Product(String name, double price, String manufacturer, String country) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
        this.manufacturer = manufacturer;
        this.country = country;
    }

    // Геттеры
    public UUID getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public String getManufacturer() {
        return manufacturer;
    }
    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return String.format("Product{id='%s', name='%s', price=%.2f, manufacturer='%s', country='%s'}", id, name, price, manufacturer, country);
    }
}

// Класс Cart
class Cart {
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public List<Product> getProducts() {
        return products;
    }
}

// Абстрактный класс Order
abstract class Order {
    protected Cart cart;
    protected String status;

    public Order(Cart cart) {
        this.cart = cart;
        this.status = "Pending";
    }

    public abstract void processOrder();
}

// Интерфейс OrderProcessing
interface OrderProcessing {
    void processOrder();
}

// Класс OnlineOrder
class OnlineOrder extends Order implements OrderProcessing {
    public OnlineOrder(Cart cart) {
        super(cart);
    }

    @Override
    public void processOrder() {
        // Логика обработки онлайн-заказа
        status = "Processed";
        System.out.println("Order status: " + status);
    }
}

// Сервис для работы с продуктами
class ProductService {
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> filterProducts(String keyword) {
        List<Product> filtered = new ArrayList<>();
        for (Product product : products) {
            if (product.getName().contains(keyword)) {
                filtered.add(product);
            }
        }
        return filtered;
    }

    public List<Product> getProducts() {
        return products;
    }
}

// Сервис для работы с заказами
class OrderService {
    private List<Order> orders = new ArrayList<>();

    public void placeOrder(Order order) {
        order.processOrder();
        orders.add(order);
    }
}

// Основной класс для взаимодействия с пользователем
public class Shop {
    public static void main(String[] args) {
        ProductService productService = new ProductService();
        productService.addProduct(new Product("Laptop", 1200.00, "Brand A", "USA"));
        productService.addProduct(new Product("Smartphone", 800.00, "Brand B", "China"));

        Cart cart = new Cart();
        List<Product> filteredProducts = productService.filterProducts("Laptop");
        if (!filteredProducts.isEmpty()) {
            cart.addProduct(filteredProducts.get(0));
        }

        OrderService orderService = new OrderService();
        orderService.placeOrder(new OnlineOrder(cart));

        System.out.println("Order placed successfully!");
    }
}
