package edu.upc.dsa.minim.Domain.Entity.VO;

import java.util.UUID;

public class RandomId {
    public static String getId() {
        return UUID.randomUUID().toString();
    }
}
