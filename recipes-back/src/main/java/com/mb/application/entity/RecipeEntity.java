/*
package com.mb.application.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Data
@Entity
@Table(name = "recipe", schema = "main", catalog = "")
public class RecipeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = true)
    private Integer id;
    @Basic
    @Column(name = "name", nullable = true, length = 200)
    private String name;
    @Basic
    @Column(name = "url", nullable = true, length = 200)
    private String url;
    @Basic
    @Column(name = "category", nullable = true, length = 200)
    private String category;

    @Basic
    @Column(name = "image", nullable = true)
    private String image;

    @Basic
    @Column(name = "description", nullable = true)
    private String description;

    @OneToMany(mappedBy="recipe")
    private List<IngredientEntity> ingredients;

    @OneToMany(mappedBy="recipe")
    private List<InstructionEntity> instructions;



    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        RecipeEntity that = (RecipeEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null)
            return false;
        if (url != null ? !url.equals(that.url) : that.url != null)
            return false;
        if (category != null ? !category.equals(that.category) : that.category != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }
}
*/
