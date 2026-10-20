package com.seatify.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(
        name = "seats",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_seat_venue_number",
                columnNames = {"venue_id", "seat_number"}
        )
)
@Getter
@Setter
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    @Column(name = "seat_number", nullable = false, length = 10)
    private String seatNumber;

    @Column(name = "row_name", nullable = false, length = 10)
    private String rowName;

    @Enumerated(EnumType.STRING)
    @Column(name = "seat_type", nullable = false)
    private EventSeat seatType = EventSeat.REGULAR;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    public enum EventSeat {
        REGULAR,
        PREMIUM,
        VIP
    }
}