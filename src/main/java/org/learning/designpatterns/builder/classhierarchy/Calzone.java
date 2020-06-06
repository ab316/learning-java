package org.learning.designpatterns.builder.classhierarchy;

import java.util.StringJoiner;

public class Calzone extends Pizza {
    private final boolean sauceInside;

    public static class Builder extends Pizza.Builder<Builder> {
        private boolean sauceInside = false;

        public Builder sauceInside() {
            this.sauceInside = true;
            return this;
        }

        // Here we modify the return type to subtype "Calzone" instead of the base type "Pizza"
        // This is called "Covariant return typing"
        // This allows the client to use the build method without requiring to cast to the subtype
        @Override
        Calzone build() {
            return new Calzone(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    Calzone(Builder builder) {
        super(builder);
        sauceInside = builder.sauceInside;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Calzone.class.getSimpleName() + "[", "]")
                .add("sauceInside=" + sauceInside)
                .add("toppings=" + toppings)
                .toString();
    }
}
