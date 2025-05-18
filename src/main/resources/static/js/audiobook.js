document.addEventListener('DOMContentLoaded', () => {
    initializeAudioPlayer();
    initializeTranslations();
    initializeComments();
    initializeUserMenu();
});

 function syncTextarea() {
        const textarea = document.querySelector('.comment-area');
        const hiddenInput = document.querySelector('#contentHidden');
        hiddenInput.value = textarea.value;
    }

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
    // Comment voting
    document.querySelectorAll('.comment-vote').forEach(btn => {
        btn.addEventListener('click', function () {
            const commentId = this.getAttribute('data-id');
            const isUpvote = this.classList.contains('upvote-btn');
            vote('comment', commentId, isUpvote, this);
        });
    });

    // Translation voting
    document.querySelectorAll('.translation-vote').forEach(btn => {
        btn.addEventListener('click', function () {
            const translationId = this.getAttribute('data-id');
            const isUpvote = this.classList.contains('upvote-btn');
            vote('translation', translationId, isUpvote, this);
        });
    });

    function vote(type, id, isUpvote, btn) {
        fetch(`/api/${type}/${isUpvote ? 'upvote' : 'downvote'}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ id: id })
        })
        .then(res => res.json())
        .then(data =>
        {
            if (type === 'translation')
            {
                document.getElementById(`translation-upvotes-${id}`).textContent = data.upvotes + ' upvotes';
                document.getElementById(`translation-downvotes-${id}`).textContent = data.downvotes + ' downvotes';
                // Highlight the voted button
                document.querySelectorAll(`.translation-vote[data-id="${id}"]`).forEach(b => b.classList.remove('active'));
                btn.classList.add('active');
            }
            else if (type === 'comment')
            {
                document.getElementById(`comment-likes-${id}`).textContent = data.likes + ' likes';
                document.querySelectorAll(`.comment-vote[data-id="${id}"]`).forEach(b => b.classList.remove('active'));
                btn.classList.add('active');
            }
            if (!data.success && data.message) {
                alert(data.message);
            }
        })
        .catch(() => {
            alert('Vote failed');
        });
    }
}