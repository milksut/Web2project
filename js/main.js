document.addEventListener('DOMContentLoaded', () => {
    initializeUserMenu();
    initializeCarousel();
    loadDynamicContent();
});

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

function initializeCarousel() {
    const carousel = document.querySelector('.carousel-items');
    const prevButton = document.querySelector('.carousel-button.prev');
    const nextButton = document.querySelector('.carousel-button.next');
    let currentIndex = 0;

    const featuredBooks = [
        {
            title: "Fahrenheit 451",
            author: "Ray Bradbury",
            cover: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTmwZyttNI6lvOvr4R5S09LN7FF21P1tXaqyQ&s",
            rating: 4.5
        },
    ];

    featuredBooks.forEach(book => {
        const bookElement = createBookElement(book);
        carousel.appendChild(bookElement);
    });

    prevButton.addEventListener('click', () => {
        if (currentIndex > 0) {
            currentIndex--;
            updateCarousel();
        }
    });

    nextButton.addEventListener('click', () => {
        if (currentIndex < featuredBooks.length - 1) {
            currentIndex++;
            updateCarousel();
        }
    });

    function updateCarousel() {
        const offset = -currentIndex * (300 + 24);
        carousel.style.transform = `translateX(${offset}px)`;
    }
}

function loadDynamicContent() {
    const progressContainer = document.querySelector('.book-progress-container');
    const inProgressBooks = [
        {
            title: "Fahrenheit 451",
            author: "Ray Bradbury",
            cover: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTmwZyttNI6lvOvr4R5S09LN7FF21P1tXaqyQ&s",
            rating: 4.5
        },
    ];

    inProgressBooks.forEach(book => {
        progressContainer.appendChild(createProgressCard(book));
    });

    const categoriesGrid = document.querySelector('.categories-grid');
    const categories = [
        { name: "Fiction", icon: "book-open" },
        { name: "Non-Fiction", icon: "graduation-cap" },
        { name: "Science", icon: "flask" },
        { name: "History", icon: "landmark" },
        { name: "Technology", icon: "microchip" },
        { name: "Arts", icon: "palette" }
    ];

    categories.forEach(category => {
        categoriesGrid.appendChild(createCategoryCard(category));
    });

    const trendingContainer = document.querySelector('.trending-container');
    const trendingBooks = [
        {
            title: "Digital Revolution",
            author: "Tech Master",
            rating: 4.7,
            cover: "https://picsum.photos/200/300"
        },
        {
            title: "Nature's Secrets",
            author: "Dr. Green",
            rating: 4.9,
            cover: "https://picsum.photos/200/300"
        },
        {
            title: "Modern Philosophy",
            author: "Think Deep",
            rating: 4.6,
            cover: "https://picsum.photos/200/300"
        }
    ];

    trendingBooks.forEach(book => {
        trendingContainer.appendChild(createBookCard(book));
    });
}

function createBookElement(book) {
    const div = document.createElement('div');
    div.className = 'featured-book';
    div.style.minWidth = '300px';
    div.innerHTML = `
        <div class="book-card" style="position: relative; border-radius: 12px; overflow: hidden;">
            <img src="${book.cover}" alt="${book.title}" style="width: 100%; height: 400px; object-fit: cover;">
            <div class="book-info" style="position: absolute; bottom: 0; left: 0; right: 0; padding: 1rem; background: linear-gradient(transparent, rgba(0,0,0,0.8));">
                <h3 style="color: white; margin: 0;">${book.title}</h3>
                <p style="color: rgba(255,255,255,0.8); margin: 0.5rem 0;">by ${book.author}</p>
                <div class="rating" style="color: var(--accent);">
                    ${"★".repeat(Math.floor(book.rating))}${book.rating % 1 >= 0.5 ? "½" : ""}
                    <span style="color: white;">${book.rating}</span>
                </div>
            </div>
        </div>
    `;
    return div;
}

function createProgressCard(book) {
    const div = document.createElement('div');
    div.className = 'progress-card';
    div.innerHTML = `
        <div class="book-card" style="background: var(--white); border-radius: var(--radius-md); overflow: hidden; box-shadow: var(--shadow-md);">
            <div style="display: flex; gap: 1rem; padding: 1rem;">
                <img src="${book.cover}" alt="${book.title}" style="width: 80px; height: 120px; object-fit: cover; border-radius: var(--radius-sm);">
                <div style="flex: 1;">
                    <h3 style="margin: 0; font-size: 1rem;">${book.title}</h3>
                    <p style="color: var(--text-light); margin: 0.5rem 0;">by ${book.author}</p>
                    <div class="progress" style="background: var(--background); height: 4px; border-radius: 2px; margin-top: 1rem;">
                        <div style="width: ${book.progress}%; height: 100%; background: var(--primary); border-radius: 2px;"></div>
                    </div>
                    <p style="color: var(--text-light); font-size: 0.875rem; margin: 0.5rem 0;">${book.progress}% completed</p>
                </div>
            </div>
        </div>
    `;
    return div;
}

function createCategoryCard(category) {
    const div = document.createElement('div');
    div.className = 'category-card';
    div.innerHTML = `
        <div style="background: var(--white); border-radius: var(--radius-md); padding: 1.5rem; text-align: center; box-shadow: var(--shadow-md); cursor: pointer; transition: transform 0.2s ease;">
            <i class="fas fa-${category.icon}" style="font-size: 2rem; color: var(--primary); margin-bottom: 1rem;"></i>
            <h3 style="margin: 0; font-size: 1rem;">${category.name}</h3>
        </div>
    `;
    div.querySelector('.category-card').addEventListener('mouseenter', (e) => {
        e.currentTarget.style.transform = 'translateY(-5px)';
    });
    div.querySelector('.category-card').addEventListener('mouseleave', (e) => {
        e.currentTarget.style.transform = 'translateY(0)';
    });
    return div;
}

function createBookCard(book) {
    const div = document.createElement('div');
    div.className = 'book-card';
    div.innerHTML = `
        <div style="background: var(--white); border-radius: var(--radius-md); overflow: hidden; box-shadow: var(--shadow-md);">
            <img src="${book.cover}" alt="${book.title}" style="width: 100%; height: 250px; object-fit: cover;">
            <div style="padding: 1rem;">
                <h3 style="margin: 0; font-size: 1rem;">${book.title}</h3>
                <p style="color: var(--text-light); margin: 0.5rem 0;">by ${book.author}</p>
                <div class="rating" style="color: var(--accent);">
                    ${"★".repeat(Math.floor(book.rating))}${book.rating % 1 >= 0.5 ? "½" : ""}
                    <span style="color: var(--text);">${book.rating}</span>
                </div>
            </div>
        </div>
    `;
    return div;
} 