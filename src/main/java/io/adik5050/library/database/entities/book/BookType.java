package io.adik5050.library.database.entities.book;

public enum BookType {
    ROMANCE("Romance"),
    DYSTOPIAN("Dystopian"),
    FICTION("Fiction"),
    CLASSIC("Classic"),
    MAGICAL_REALISM("Magical Realism"),
    FANTASY("Fantasy"),
    SATIRE("Satire"),
    EPIC_POETRY("Epic Poetry"),
    PSYCHOLOGICAL_FICTION("Psychological Fiction"),
    HISTORICAL_FICTION("Historical Fiction"),
    PHILOSOPHICAL_FICTION("Philosophical Fiction"),
    ADVENTURE("Adventure"),
    GOTHIC_FICTION("Gothic Fiction"),
    HORROR("Horror"),
    MYSTERY("Mystery"),
    POST_APOCALYPTIC("Post-Apocalyptic"),
    FABLE("Fable"),
    THRILLER("Thriller"),
    MEMOIR("Memoir"),
    NON_FICTION("Non-Fiction"),
    PSYCHOLOGY("Psychology"),
    SELF_HELP("Self-Help"),
    FINANCE("Finance"),
    SCIENCE("Science"),
    WESTERN("Western");

    private final String displayName;

    BookType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
