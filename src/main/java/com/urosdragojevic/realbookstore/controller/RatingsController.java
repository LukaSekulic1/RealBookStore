package com.urosdragojevic.realbookstore.controller;

import com.urosdragojevic.realbookstore.audit.AuditLogger;
import com.urosdragojevic.realbookstore.domain.Rating;
import com.urosdragojevic.realbookstore.repository.RatingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RatingsController {
    private static final Logger LOG = LoggerFactory.getLogger(RatingsController.class);
    private static final AuditLogger auditLogger = AuditLogger.getAuditLogger(RatingsController.class);

    private RatingRepository ratingRepository;

    public RatingsController(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @PostMapping(value = "/ratings")
    @PreAuthorize("hasAuthority('RATE_BOOK')")
    public String createOrUpdateRating(@ModelAttribute Rating rating) {
        rating.setUserId(1);
        ratingRepository.createOrUpdate(rating);

        auditLogger.audit("Create or update rating on book with id: " + rating.getBookId() + " and rating: " + rating.getRating());

        return "redirect:/books/" + rating.getBookId();
    }
}
