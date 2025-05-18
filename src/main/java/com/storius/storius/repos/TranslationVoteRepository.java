package com.storius.storius.repos;

import com.storius.storius.entities.Translation;
import com.storius.storius.entities.TranslationVote;
import com.storius.storius.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TranslationVoteRepository extends JpaRepository<TranslationVote, Long> {
    Optional<TranslationVote> findByUserAndTranslation(User user, Translation translation);
}