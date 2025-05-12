package com.storius.storius.services;

import com.storius.storius.entities.Book;
import com.storius.storius.entities.Translation;
import com.storius.storius.repos.BookRepository;
import com.storius.storius.repos.TranslationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TranslationService {

    private final TranslationRepository translationRepository;
    private final BookRepository bookRepository;

    public TranslationService(TranslationRepository translationRepository,
                              BookRepository bookRepository) {
        this.translationRepository = translationRepository;
        this.bookRepository = bookRepository;
    }

    public List<Translation> getTranslationsBybook(Long bookId) {
        return translationRepository.findByBook_Id(bookId);
    }

    public Translation addTranslation(Long bookId, Translation translation) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("book not found"));
        translation.setBook(book);
        return translationRepository.save(translation);
    }

    public void upvote(Long translationId) {
        Translation translation = translationRepository.findById(translationId)
                .orElseThrow(() -> new RuntimeException("Translation not found"));
        translation.setUpvotes(translation.getUpvotes() + 1);
        translationRepository.save(translation);
    }

    public void downvote(Long translationId) {
        Translation translation = translationRepository.findById(translationId)
                .orElseThrow(() -> new RuntimeException("Translation not found"));
        translation.setDownvotes(translation.getDownvotes() + 1);
        translationRepository.save(translation);
    }
}
