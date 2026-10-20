package com.seatify.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter
@Setter

public class Event {
    @ID
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category_id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue_id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length =  500)
    private String description;

    @Column(nullable = false)
    private LocalDateTime event_date;

    @Column(nullable = false)
    private LocalDateTime start_time;
    private LocalDateTime end_time;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.String)
    @Column(nullable  = false)
    private Event.Eventuates status = Eventuates.UPCOMING;

    public enum Eventuates {
        UPCOMING,
        ONGOING,
        COMPLETED,
        CANCELLED
    }

    @Column(name = "image_url", length = 300)
    private String image_url;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

}