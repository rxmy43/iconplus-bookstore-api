package com.ramy.onlinebookstore.specification;

import com.ramy.onlinebookstore.entity.Category;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CategorySpecification extends EntitySpecification<Category>{
    public CategorySpecification(Map<String, Object> filters, Boolean deleted) {
        super(filters, deleted);
    }


    @Override
    protected Set<String> getAllowedFieldOperators() {
        return Set.of(
                "name__like"
        );
    }
}
