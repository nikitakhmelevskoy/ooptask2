package models;

import org.jetbrains.annotations.NotNull;

public record Step(@NotNull Point start, @NotNull Point end) {
}