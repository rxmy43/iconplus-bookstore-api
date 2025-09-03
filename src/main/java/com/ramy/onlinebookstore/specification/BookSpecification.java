package com.ramy.onlinebookstore.specification;

import com.ramy.onlinebookstore.entity.Book;

import java.util.Map;
import java.util.Set;

public class BookSpecification extends EntitySpecification<Book>{
    public BookSpecification(Map<String, Object> filters, Boolean deleted) {
        super(filters, deleted);
    }

    @Override
    protected Set<String> getAllowedFieldOperators() {
        return Set.of(
                "title__like",
                "author__like",
                "category.id__eq"
        );
    }
}
