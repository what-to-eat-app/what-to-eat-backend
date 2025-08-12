package com.mb.application.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Getter
@Entity
@Builder
@Table(name = "ingredients")
public class IngredientEntity {
    @Id
    @Column(name = "id")
    private Long id;

    @Setter
    @Column(name = "name")
    private String name;

    @Setter
    @Column(name = "measure")
    private String measure;

    @Setter
    @Column(name = "unit")
    private String unit;

    @Setter
    @Column(name = "recipe_id")
    private Long recipeId;
}