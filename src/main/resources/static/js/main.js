document.addEventListener('DOMContentLoaded', () => {
    initializeUserMenu();
    initializeCarousels();
});

// User Menu Handling
function initializeUserMenu() {
    const profileButton = document.getElementById('profileButton');
    const userMenu = document.getElementById('userMenu');

    if (!profileButton || !userMenu) return;

    profileButton.addEventListener('click', (e) => {
        e.stopPropagation();
        userMenu.classList.toggle('active');
    });

    document.addEventListener('click', (e) => {
        if (!profileButton.contains(e.target) && !userMenu.contains(e.target)) {
            userMenu.classList.remove('active');
        }
    });

    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape' && userMenu.classList.contains('active')) {
            userMenu.classList.remove('active');
        }
    });
}

// Carousel Functionality
function initializeCarousels() {
    document.querySelectorAll('.carousel-container').forEach(carousel => {
        const prevButton = carousel.querySelector('.carousel-button.prev');
        const nextButton = carousel.querySelector('.carousel-button.next');
        const items = carousel.querySelector('.carousel-items');
        if (!items) return;

        let currentIndex = 0;
        const itemWidth = items.children[0]?.offsetWidth || 300;
        const gap = parseInt(getComputedStyle(items).gap) || 24;

        const updateCarousel = () => {
            const offset = -currentIndex * (itemWidth + gap);
            items.style.transform = `translateX(${offset}px)`;
        };

        prevButton?.addEventListener('click', () => {
            if (currentIndex > 0) {
                currentIndex--;
                updateCarousel();
            }
        });

        nextButton?.addEventListener('click', () => {
            const visibleItems = Math.floor(carousel.offsetWidth / (itemWidth + gap));
            if (currentIndex < items.children.length - visibleItems) {
                currentIndex++;
                updateCarousel();
            }
        });
    });
}

// Resize Handler
window.addEventListener('resize', () => {
    initializeCarousels();
});