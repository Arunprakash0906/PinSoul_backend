package com.example.PinSoul.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Entity
@Data
@NoArgsConstructor
public class Sign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer signId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    public Sign(Integer signId, String username, String password, Category category) {
        this.signId = signId;
        this.username = username;
        this.password = password;
        this.category = category;
    }
}
