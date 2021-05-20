//Selling product in a website like Amazon, you want to filter product based on some criteria
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

enum Color {
    RED, GREEN, BLUE;
}

enum Size {
    SMALL, MEDIUM, LARGE, HUGE
}

class Product{
    public String name;
    public Color color;
    public Size size;

    public Product(String name, Color color, Size size) {
        this.name = name;
        this.color = color;
        this.size = size;
    }
}

interface Specification<T>{
    boolean isSatisfied(T item);
}

class ColorSpecification implements Specification<Product>{
    Color color;

    public ColorSpecification(Color color) {
        this.color = color;
    }

    public boolean isSatisfied(Product item) {
        return color == item.color;
    }
}

class SizeSpecification implements Specification<Product>{
    Size size;

    public SizeSpecification(Size size) {
        this.size = size;
    }

    public boolean isSatisfied(Product item) {
        return size == item.size;
    }
}

class Specifications implements Specification<Product>{
    List<Specification<Product>> specifications;

    public Specifications(List<Specification<Product>> specifications) {
        this.specifications = specifications;
    }

    public boolean isSatisfied(Product item) {
        return specifications.stream().allMatch(specification -> specification.isSatisfied(item));
    }
}

class ProductFilter{
    public Stream<Product> filter(List<Product> items, Specification<Product> specification){
       return items.stream().filter(item -> specification.isSatisfied(item));
    }
}

class OpenCloseAndSpecification{
    public void demo(){
        Product apple = new Product("Apple",Color.GREEN,Size.SMALL);
        Product tree = new Product("Tree",Color.GREEN,Size.LARGE);
        Product house = new Product("House",Color.BLUE,Size.HUGE);

        List<Product> products = new ArrayList();
        products.add(apple);
        products.add(tree);
        products.add(house);

        ProductFilter productFilter = new ProductFilter();
        List<Specification<Product>> list = new ArrayList<>();

        list.add(new ColorSpecification(Color.GREEN));
        list.add(new SizeSpecification(Size.SMALL));

        Specification<Product> specification = new Specifications(list);
        productFilter.filter(products,specification).forEach(item -> System.out.println(item.name));
    }
}