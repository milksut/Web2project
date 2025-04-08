document.addEventListener('DOMContentLoaded', () => {
    initializeAudioPlayer();
    initializeTranslations();
    initializeComments();
    initializeUserMenu();
});

function initializeAudioPlayer() {
    const playPauseButton = document.getElementById('playPause');
    const rewindButton = document.getElementById('rewind');
    const forwardButton = document.getElementById('forward');
    const prevChapterButton = document.getElementById('prevChapter');
    const nextChapterButton = document.getElementById('nextChapter');
    const progressBar = document.querySelector('.progress-bar');
    const progress = document.querySelector('.progress');
    const currentTime = document.querySelector('.time.current');
    const totalTime = document.querySelector('.time.total');
    const speedButton = document.querySelector('.speed-button');
    const volumeSlider = document.querySelector('.volume-slider');
    const volumeIcon = document.querySelector('.volume-control i');

    let isPlaying = false;
    let currentSpeed = 1;
    const speeds = [0.5, 0.75, 1, 1.25, 1.5, 2];
    let currentSpeedIndex = 2;

    playPauseButton.addEventListener('click', () => {
        isPlaying = !isPlaying;
        playPauseButton.innerHTML = isPlaying ? 
            '<i class="fas fa-pause"></i>' : 
            '<i class="fas fa-play"></i>';
    });

    rewindButton.addEventListener('click', () => {
        updateProgress(Math.max(0, (progress.style.width.replace('%', '') - 5)));
    });

    forwardButton.addEventListener('click', () => {
        updateProgress(Math.min(100, (Number(progress.style.width.replace('%', '')) + 5)));
    });

    progressBar.addEventListener('click', (e) => {
        const rect = progressBar.getBoundingClientRect();
        const percent = Math.min(Math.max(0, e.x - rect.x), rect.width) / rect.width;
        updateProgress(percent * 100);
    });

    speedButton.addEventListener('click', () => {
        currentSpeedIndex = (currentSpeedIndex + 1) % speeds.length;
        currentSpeed = speeds[currentSpeedIndex];
        speedButton.textContent = `${currentSpeed}x`;
    });

    volumeSlider.addEventListener('input', (e) => {
        const volume = e.target.value;
        updateVolumeIcon(volume);
    });

    function updateProgress(percent) {
        progress.style.width = `${percent}%`;
        const totalSeconds = 31500;
        const currentSeconds = totalSeconds * (percent / 100);
        currentTime.textContent = formatTime(currentSeconds);
    }

    function updateVolumeIcon(volume) {
        const volumeClass = volume > 50 ? 'fa-volume-up' : 
                           volume > 0 ? 'fa-volume-down' : 
                           'fa-volume-mute';
        volumeIcon.className = `fas ${volumeClass}`;
    }

    function formatTime(seconds) {
        const h = Math.floor(seconds / 3600);
        const m = Math.floor((seconds % 3600) / 60);
        const s = Math.floor(seconds % 60);
        return h > 0 ? 
            `${h}:${String(m).padStart(2, '0')}:${String(s).padStart(2, '0')}` : 
            `${m}:${String(s).padStart(2, '0')}`;
    }
}

function initializeTranslations() {
    const languageButtons = document.querySelectorAll('.language-selector button:not(.add-language)');
    const addTranslationButton = document.querySelector('.add-translation-button');
    const voteButtons = document.querySelectorAll('.vote-buttons button');

    languageButtons.forEach(button => {
        button.addEventListener('click', () => {
            languageButtons.forEach(btn => btn.classList.remove('active'));
            button.classList.add('active');
        });
    });

    addTranslationButton.addEventListener('click', () => {
        showTranslationModal();
    });

    voteButtons.forEach(button => {
        button.addEventListener('click', () => {
            const isUpvote = button.classList.contains('vote-up');
            const siblingButton = isUpvote ? 
                button.nextElementSibling : 
                button.previousElementSibling;
            
            if (button.classList.contains('active')) {
                button.classList.remove('active');
                updateVoteCount(button, -1);
            } else {
                if (siblingButton.classList.contains('active')) {
                    siblingButton.classList.remove('active');
                    updateVoteCount(siblingButton, -1);
                }
                button.classList.add('active');
                updateVoteCount(button, 1);
            }
        });
    });

    function showTranslationModal() {
        console.log('Show translation modal');
    }

    function updateVoteCount(button, change) {
        const countSpan = button.querySelector('span');
        const currentCount = parseInt(countSpan.textContent);
        countSpan.textContent = currentCount + change;
    }
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