package com.storius.storius.repos;

import com.storius.storius.entities.Translation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TranslationRepository extends JpaRepository<Translation,Long>
{
    List<Translation> findByBook_Id(Long bookId);

}
