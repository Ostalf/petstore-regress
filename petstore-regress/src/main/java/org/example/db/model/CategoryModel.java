package org.example.db.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class CategoryModel {
    private int id;
    private String name;
}
