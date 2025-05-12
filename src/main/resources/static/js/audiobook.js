document.addEventListener('DOMContentLoaded', () => {
    initializeAudioPlayer();
    initializeTranslations();
    initializeComments();
    initializeUserMenu();
});

function initializeAudioPlayer() {
    const audioPlayer = document.getElementById("audioPlayer");
    const playPauseButton = document.getElementById("playPause");
    const rewindButton = document.getElementById("rewind");
    const forwardButton = document.getElementById("forward");
    const prevChapterButton = document.getElementById('prevChapter');
    const nextChapterButton = document.getElementById('nextChapter');
    const progressBar = document.querySelector('.progress-bar');
    const progress = document.querySelector('.progress');
    const currentTimeElement = document.querySelector('.time.current'); // Fix here
    const totalTimeElement = document.querySelector('.time.total'); // Fix here
    const volumeSlider = document.querySelector('.volume-slider');
    const volumeIcon = document.querySelector('.volume-control i');

    let isPlaying = false;

    // Check if the audioPlayer exists
    if (!audioPlayer) {
        console.error("Audio player element not found!");
        return;
    }

    playPauseButton.addEventListener('click', () => {
        if (audioPlayer.paused) {
            audioPlayer.play();
            playPauseButton.innerHTML = '<i class="fas fa-pause"></i>';
        } else {
            audioPlayer.pause();
            playPauseButton.innerHTML = '<i class="fas fa-play"></i>';
        }
    });

    rewindButton.addEventListener('click', () => {
        audioPlayer.currentTime -= 10; // Rewind 10 seconds
    });

    forwardButton.addEventListener('click', () => {
        audioPlayer.currentTime += 10; // Forward 10 seconds
    });

    progressBar.addEventListener('click', (e) => {
        const rect = progressBar.getBoundingClientRect();
        const percent = (e.clientX - rect.left) / rect.width;
        audioPlayer.currentTime = percent * audioPlayer.duration;
        progress.style.width = `${percent * 100}%`;
    });

    volumeSlider.addEventListener('input', (e) => {
        const volume = e.target.value / 100;
        audioPlayer.volume = volume;
        updateVolumeIcon(volume);
    });

    audioPlayer.addEventListener("timeupdate", () => {
        const currentTime = audioPlayer.currentTime;
        const duration = audioPlayer.duration;

        // Update current time and total time
        currentTimeElement.textContent = formatTime(currentTime);
        totalTimeElement.textContent = formatTime(duration);

        // Update progress bar
        const progressPercent = (currentTime / duration) * 100;
        progress.style.width = `${progressPercent}%`;
    });

    function updateVolumeIcon(volume) {
        const volumeClass = volume > 0.5 ? 'fa-volume-up' :
                            volume > 0 ? 'fa-volume-down' :
                            'fa-volume-mute';
        volumeIcon.className = `fas ${volumeClass}`;
    }

    function formatTime(seconds) {
        const minutes = Math.floor(seconds / 60);
        const secs = Math.floor(seconds % 60);
        return `${minutes}:${secs < 10 ? "0" : ""}${secs}`;
    }
}

function initializeTranslations() {
    const translationCards = document.querySelectorAll('.translation-card');
    const bookText = document.getElementById('bookText');
    const audioPlayer = document.getElementById('audioPlayer');

    translationCards.forEach(card => {
        card.addEventListener('click', () => {
            // Get translation data from the clicked card
            const textContent = card.getAttribute('data-text');
            const audioUrl = card.getAttribute('data-audio');

            // Update the displayed book text
            bookText.textContent = textContent;

            // Update the main audio player
            audioPlayer.src = audioUrl;
            audioPlayer.load();

            // Highlight the selected card
            translationCards.forEach(c => c.classList.remove('active'));
            card.classList.add('active');
        });
    });
}

function initializeComments() {
    const commentsContainer = document.querySelector('.comments-container');
    const commentForm = document.querySelector('.add-comment');
    const commentInput = commentForm.querySelector('textarea');
    const submitButton = commentForm.querySelector('.submit-comment');

    loadComments();

    submitButton.addEventListener('click', () => {
        const commentText = commentInput.value.trim();
        if (commentText) {
            addComment({
                user: 'Selcuk Oz',
                avatar: 'https://ui-avatars.com/api/?name=Selcuk+Oz&background=4A90E2&color=fff',
                text: commentText,
                timestamp: new Date()
            });
            commentInput.value = '';
        }
    });

    function loadComments() {
        const comments = [
            {
                user: 'Maria Garcia',
                avatar: 'https://ui-avatars.com/api/?name=Maria+Garcia&background=FF6B6B&color=fff',
                text: 'This translation really helped me understand the nuances of the story better.',
                timestamp: new Date(Date.now() - 3600000)
            },
        ];

        comments.forEach(addComment);
    }

    function addComment(comment) {
        const commentElement = document.createElement('div');
        commentElement.className = 'comment';
        commentElement.innerHTML = `
            <div style="display: flex; gap: 1rem; margin-bottom: 1.5rem;">
                <img src="${comment.avatar}" alt="${comment.user}" 
                     style="width: 40px; height: 40px; border-radius: 50%;">
                <div style="flex: 1;">
                    <div style="display: flex; justify-content: space-between; margin-bottom: 0.5rem;">
                        <strong>${comment.user}</strong>
                        <span style="color: var(--text-light); font-size: 0.875rem;">
                            ${formatTimestamp(comment.timestamp)}
                        </span>
                    </div>
                    <p style="color: var(--text); margin: 0;">${comment.text}</p>
                </div>
            </div>
        `;
        commentsContainer.insertBefore(commentElement, commentsContainer.firstChild);
    }

    function formatTimestamp(date) {
        const now = new Date();
        const diff = now - date;
        const minutes = Math.floor(diff / 60000);
        const hours = Math.floor(minutes / 60);
        const days = Math.floor(hours / 24);

        if (days > 0) return `${days}d ago`;
        if (hours > 0) return `${hours}h ago`;
        if (minutes > 0) return `${minutes}m ago`;
        return 'Just now';
    }
}

function initializeUserMenu() {
    const userProfile = document.querySelector('.user-profile');
    const userMenu = document.querySelector('.user-menu');

    userProfile.addEventListener('click', () => {
        userMenu.style.display = userMenu.style.display === 'none' ? 'block' : 'none';
    });

    document.addEventListener('click', (e) => {
        if (!userProfile.contains(e.target) && !userMenu.contains(e.target)) {
            userMenu.style.display = 'none';
        }
    });
}