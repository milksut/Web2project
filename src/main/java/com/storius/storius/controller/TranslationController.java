package com.storius.storius.controller;

import com.storius.storius.entities.Translation;
import com.storius.storius.services.TranslationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
public class TranslationController {

    @Autowired
    private TranslationService translationService;

    @PostMapping("/translation/upload")
    public String uploadTranslation(@RequestParam Long bookId,
                                    @RequestParam String language,
                                    @RequestParam String translatorName,
                                    @RequestParam String textContent,
                                    @RequestParam(required = false) MultipartFile audioFile,
                                    HttpSession session) {
        // Save audio file if present
        String audioUrl = null;
        if (audioFile != null && !audioFile.isEmpty()) {
            audioUrl = translationService.saveAudioFile(audioFile);
        }

        Translation translation = new Translation();
        translation.setLanguage(language);
        translation.setTranslatorName(translatorName);
        translation.setTextContent(textContent);
        translation.setTranslatedAudioUrl(audioUrl);

        translationService.addTranslation(bookId, translation);

        return "redirect:/book/" + bookId;
    }

    @PostMapping("/api/translation/upvote")
    @ResponseBody
    public Map<String, Object> upvoteTranslation(@RequestBody Map<String, Long> payload, HttpSession session) {
        Long translationId = payload.get("id");
        Long userId = (Long) session.getAttribute("user_id");
        Map<String, Object> response = new HashMap<>();
        int upvotes = translationService.upvote(translationId, userId);
        int downvotes = translationService.getDownvotes(translationId); // Add this method if needed
        response.put("success", true);
        response.put("upvotes", upvotes);
        response.put("downvotes", downvotes);
        return response;
    }

    @PostMapping("/api/translation/downvote")
    @ResponseBody
    public Map<String, Object> downvoteTranslation(@RequestBody Map<String, Long> payload, HttpSession session) {
        Long translationId = payload.get("id");
        Long userId = (Long) session.getAttribute("user_id");
        Map<String, Object> response = new HashMap<>();
        int downvotes = translationService.downvote(translationId, userId);
        int upvotes = translationService.getUpvotes(translationId); // Add this method if needed
        response.put("success", true);
        response.put("upvotes", upvotes);
        response.put("downvotes", downvotes);
        return response;
    }
}