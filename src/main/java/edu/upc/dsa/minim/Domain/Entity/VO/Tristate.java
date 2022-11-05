package edu.upc.dsa.minim.Domain.Entity.VO;

import java.util.Objects;

final public class Tristate {
    public static final Tristate TRUE = new Tristate("True");
    public static final Tristate FALSE = new Tristate("False");
    public static final Tristate NEITHER = new Tristate("Neither");

    public transient String text;

    public Tristate() {}

    public Tristate(String value) {
        this.text = value;
    }

    public static Tristate fromBoolean(Boolean value) {
        return((value) ? TRUE : FALSE);
    }

    public Boolean isTrue(){
        return (Objects.equals(this.text, "True"));
    }

    public Boolean isFalse(){
        return (Objects.equals(this.text, "False"));
    }

    public Boolean isNeither(){
        return (Objects.equals(this.text, "Neither"));
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
