package org.learning.designpatterns.builder.classhierarchy;

public class Run {
    // This exam shows using the builder pattern with class hierarchies with an abstract parent class
    // Abstract classes have abstract builders and concrete classes have concrete builders
    public static void main(String[] args) {
        NyPizza pizza = new NyPizza.Builder(NyPizza.Size.SMALL)
                .addTopping(Pizza.Topping.MUSHROOM)
                .addTopping(Pizza.Topping.ONION)
                .build();

        Calzone calzone = new Calzone.Builder()
                .addTopping(Pizza.Topping.PEPPER)
                .addTopping(Pizza.Topping.PEPPER)
                .sauceInside()
                .build();

        System.out.println(pizza);
        System.out.println(calzone);
    }
}
