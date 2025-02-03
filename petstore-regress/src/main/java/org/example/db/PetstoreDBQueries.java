package org.example.db;

import io.qameta.allure.Step;
import org.example.db.model.CategoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public class PetstoreDBQueries {
    @Autowired
    @Qualifier("petstoreJdbcTemplate")
    private JdbcTemplate petstoreJdbcTemplate;

    @Step("Добавление записи в таблицу category")
    public void insertCategory(CategoryModel categoryModel) {
        String sql = """
                INSERT INTO public.category
                (id, name)
                VALUES(%d,'%s');
                """.formatted(categoryModel.id(), categoryModel.name());
        petstoreJdbcTemplate.update(sql);
    }

    @Step("Удаление записи из таблицы category по id")
    public void deleteCategoryById(int id) {
        String sql = """
                DELETE FROM public.category
                WHERE id = %d;
                """.formatted(id);
        petstoreJdbcTemplate.update(sql);
    }
}
