package org.learning.designpatterns.builder.classhierarchy;

import java.util.Objects;
import java.util.StringJoiner;

public class NyPizza extends Pizza {
    public enum Size {SMALL, MEDIUM, LARGE}

    private final Size size;

    public static class Builder extends Pizza.Builder<Builder> {
        private final Size size;

        public Builder(Size size) {
            this.size = Objects.requireNonNull(size);
        }

        // Here we modify the return type to subtype "NyPizza" instead of the base type "Pizza"
        // This is called "Covariant return typing"
        // This allows the client to use the build method without requiring to cast to the subtype
        @Override
        NyPizza build() {
            return new NyPizza(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    NyPizza(Builder builder) {
        super(builder);
        size = builder.size;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NyPizza.class.getSimpleName() + "[", "]")
                .add("size=" + size)
                .add("toppings=" + toppings)
                .toString();
    }
}
