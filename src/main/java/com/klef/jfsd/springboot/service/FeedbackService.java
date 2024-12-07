package com.klef.jfsd.springboot.service;

import com.klef.jfsd.springboot.model.Feedback;

import java.util.List;

public interface FeedbackService {
    void saveFeedback(Feedback feedback);
    List<Feedback> getAllFeedback();
}
