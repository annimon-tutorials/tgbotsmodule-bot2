package com.example.secondbot.roles;

import com.annimon.tgbotsmodule.commands.authority.Authority;
import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

/**
 * Custom user authority.
 * Here we define only two roles: IGNORED and REGULAR.
 */
public class IgnorableAuthority implements Authority<UserRole> {

    private final Set<Long> ignoredUsers = ConcurrentHashMap.newKeySet();

    @Override
    public boolean hasRights(Update update, User user, EnumSet<UserRole> userRoles) {
        final Long userId = user.getId();
        final boolean userIgnored = ignoredUsers.contains(userId);
        if (userRoles.contains(UserRole.IGNORED) && userIgnored) {
            return true;
        }
        if (userRoles.contains(UserRole.REGULAR) && !userIgnored) {
            return true;
        }
        return false;
    }

    public UserRole getRole(User user) {
        final var ignored = ignoredUsers.contains(user.getId());
        return ignored ? UserRole.IGNORED : UserRole.REGULAR;
    }

    public void ignore(User user) {
        ignoredUsers.add(user.getId());
    }

    public void unignore(User user) {
        ignoredUsers.remove(user.getId());
    }
}
