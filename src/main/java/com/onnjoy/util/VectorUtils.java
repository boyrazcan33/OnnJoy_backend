package com.onnjoy.util;

import java.util.List;

public class VectorUtils {

    public static double cosineSimilarity(List<Double> v1, List<Double> v2) {
        if (v1.size() != v2.size()) throw new IllegalArgumentException("Vector size mismatch");

        double dot = 0.0, normV1 = 0.0, normV2 = 0.0;
        for (int i = 0; i < v1.size(); i++) {
            double a = v1.get(i);
            double b = v2.get(i);
            dot += a * b;
            normV1 += a * a;
            normV2 += b * b;
        }

        return dot / (Math.sqrt(normV1) * Math.sqrt(normV2));
    }
}
