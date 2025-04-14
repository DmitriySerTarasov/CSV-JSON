import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class Product {
    private UUID id;
    private String name;
    private double price;
    private String manufacturer;

    public Product(String name, double price, String manufacturer) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
        this.manufacturer = manufacturer;
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

    // Открытость/закрытость

    static class Cart {
        private List<Product> products = new ArrayList<>();

        public void addProduct(Product product) {
            products.add(product);
        }
        public void removeProduct(Product product) {
            products.remove(product);
        }

        public  List<Product> getProducts() {
            return products;
        }
    }

    //Принцип подстановки Лисков

    abstract class Order {
        protected Cart cart;
        protected String status;

        public Order(Cart cart) {
            this.cart = cart;
            this.status = "Pending";
        }

        public abstract void processOrder();
    }

    //разделение интерфейса

    interface OrderProcessing {
        void processOrder();
    }

    class OnlineOrder extends  Order implements OrderProcessing {
        public OnlineOrder(Cart cart) {
            super(cart);
        }
        @Override
        public void processOrder() {
            //Логика обработки онлайн заказа
            status = "Processed";
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
    }

    //Сервис для работы с заказами

    class OrderService {
        private List<Order> orders = new ArrayList<>();

        public void placeOrder(Order order) {
            order.processOrder();
            orders.add(order);
        }
    }
    //Основной класс для взаимодействия с пользователем

    public class Shop {
        public static void main(String[] args) {
            ProductService productService = new ProductService();
            productService.addProduct(new Product("object1", 1200.00, "level 1" ));
            productService.addProduct(new Product("object2", 800.00, "level 2"));
            Cart cart = new Cart();
            cart.addProduct(productService.filterProducts("object1").get(0));

            OrderService orderService = new OrderService();
            orderService.placeOrder(new OnlineOrder(cart));

            System.out.println("Order placed successfully!");
        }
    }

}