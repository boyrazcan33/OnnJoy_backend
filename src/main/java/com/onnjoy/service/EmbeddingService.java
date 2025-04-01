package com.onnjoy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.Embedding;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmbeddingService {

    private final EmbeddingModel embeddingModel;

    public List<Double> getEmbeddingVector(String inputText) {
        EmbeddingRequest request = new EmbeddingRequest(List.of(inputText), null);
        List<Embedding> embeddings = embeddingModel.call(request).getResults();

        float[] vectorArray = embeddings.get(0).getOutput();
        List<Double> vector = new ArrayList<>();
        for (float v : vectorArray) {
            vector.add((double) v);
        }

        return vector;
    }
}
