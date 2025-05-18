package com.storius.storius.services;

import com.storius.storius.entities.*;
import com.storius.storius.repos.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TranslationService {

    private final TranslationRepository translationRepository;
    private final TranslationVoteRepository translationVoteRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public TranslationService(TranslationRepository translationRepository, TranslationVoteRepository translationVoteRepository,
                              BookRepository bookRepository, UserRepository userRepository) {
        this.translationRepository = translationRepository;
        this.translationVoteRepository = translationVoteRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
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

    public int upvote(Long translationId, Long userId) {
        Translation translation = translationRepository.findById(translationId)
                .orElseThrow(() -> new RuntimeException("Translation not found"));

        User user = userRepository.findById(userId).get();

        Optional<TranslationVote> optionalTranslationVote = translationVoteRepository.findByUserAndTranslation(user, translation);

        if(optionalTranslationVote.isPresent())
        {
            TranslationVote translationVote = optionalTranslationVote.get();

            if (translationVote.getVoteType() <= -1)
            {
                translationVote.setVoteType(1);
                translation.setUpvotes(translation.getUpvotes() + 1);
                translation.setDownvotes(translation.getDownvotes() - 1);
                translationRepository.save(translation);
            }
            return translation.getUpvotes();
        }

        TranslationVote vote = new TranslationVote();
        vote.setUser(user);
        vote.setTranslation(translation);
        vote.setVoteType(1);
        translationVoteRepository.save(vote);

        translation.setUpvotes(translation.getUpvotes() + 1);
        translationRepository.save(translation);
        return translation.getUpvotes();
    }

    public int downvote(Long translationId, Long userId) {
        Translation translation = translationRepository.findById(translationId)
                .orElseThrow(() -> new RuntimeException("Translation not found"));

        User user = userRepository.findById(userId).get();

        Optional<TranslationVote> optionalTranslationVote = translationVoteRepository.findByUserAndTranslation(user, translation);

        if(optionalTranslationVote.isPresent())
        {
            TranslationVote translationVote = optionalTranslationVote.get();

            if (translationVote.getVoteType() >= 1)
            {
                translationVote.setVoteType(-1);
                translation.setUpvotes(translation.getUpvotes() - 1);
                translation.setDownvotes(translation.getDownvotes() + 1);
                translationRepository.save(translation);
            }
            return translation.getDownvotes();
        }

        TranslationVote vote = new TranslationVote();
        vote.setUser(user);
        vote.setTranslation(translation);
        vote.setVoteType(-1);
        translationVoteRepository.save(vote);

        translation.setDownvotes(translation.getDownvotes() + 1);
        translationRepository.save(translation);
        return translation.getDownvotes();
    }

    public int getUpvotes(Long translationId)
    {
        Translation translation = translationRepository.findById(translationId)
                .orElseThrow(() -> new RuntimeException("Translation not found"));
        return translation.getUpvotes();
    }

    public int getDownvotes(Long translationId)
    {
        Translation translation = translationRepository.findById(translationId)
                .orElseThrow(() -> new RuntimeException("Translation not found"));

        return translation.getDownvotes();
    }

    public String saveAudioFile(MultipartFile audioFile) {
        if (audioFile == null || audioFile.isEmpty()) return null;
        String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/audio/";
        String fileName = System.currentTimeMillis() + "_" + audioFile.getOriginalFilename();
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs(); // Create the directory if it doesn't exist
        }
        File dest = new File(uploadDir + fileName);
        try {
            audioFile.transferTo(dest);
            return "/audio/" + fileName; // URL to access the file
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
