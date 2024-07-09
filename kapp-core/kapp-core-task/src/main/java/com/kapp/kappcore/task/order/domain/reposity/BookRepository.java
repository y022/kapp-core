package com.kapp.kappcore.task.order.domain.reposity;

import com.kapp.kappcore.model.entity.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author:Heping
 * Date: 2024/7/9 17:53
 */
public interface BookRepository extends JpaRepository<Book,String> {

}
