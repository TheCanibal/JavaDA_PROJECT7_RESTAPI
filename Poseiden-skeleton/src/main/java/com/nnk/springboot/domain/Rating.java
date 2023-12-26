package com.nnk.springboot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rating")
@Getter
@Setter
@NoArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer Id;
    @Column(name = "moodys_Rating")
    @NotBlank(message = "moodysRating is mandatory")
    private String moodysRating;
    @Column(name = "sand_P_Rating")
    @NotBlank(message = "sandPRating is mandatory")
    private String sandPRating;
    @Column(name = "fitch_Rating")
    @NotBlank(message = "fitchRating is mandatory")
    private String fitchRating;
    @Column(name = "order_Number")
    @NotNull(message = "order number can't be null")
    @Min(1)
    @Digits(integer = 4, fraction = 0, message = "Maximum 4 digits")
    private Integer orderNumber;

    public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }

}
