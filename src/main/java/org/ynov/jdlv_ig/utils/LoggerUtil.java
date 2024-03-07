package org.ynov.jdlv_ig.utils;

import java.util.ResourceBundle;

/**
 * Classe de Logger pour l'application.
 */
public class LoggerUtil implements System.Logger {
    /**
     * Getter du nom du logger
     * @return
     */
    @Override
    public String getName() {
        return null;
    }
    /**
     * Vérififcation si le Level choisi est loggable.
     * @param level the log message level.
     * @return
     */
    @Override
    public boolean isLoggable(Level level) {
        return false;
    }

    /**
     * Méthode pour créer le log avec un niveau et un message.
     * @param level the log message level.
     * @param msg the string message (or a key in the message catalog, if
     * this logger is a {@link
     * LoggerFinder#getLocalizedLogger(java.lang.String,
     * java.util.ResourceBundle, java.lang.Module) localized logger});
     * can be {@code null}.
     *
     */
    @Override
    public void log(Level level, String msg) {
        System.Logger.super.log(level, msg);
    }

    /**
     * Méthode pour créer le log avec un niveau, un bundle de ressources, un message et un Throwable.
     * @param level the log message level.
     * @param bundle a resource bundle to localize {@code msg}; can be
     * {@code null}.
     * @param msg the string message (or a key in the message catalog,
     *            if {@code bundle} is not {@code null}); can be {@code null}.
     * @param thrown a {@code Throwable} associated with the log message;
     *        can be {@code null}.
     *
     */
    @Override
    public void log(Level level, ResourceBundle bundle, String msg, Throwable thrown) {

    }

    /**
     * Méthode pour créer un log avec un niveau, un bundle de ressources,
     * un String et une liste optionnelle de paramètres.
     * @param level the log message level.
     * @param bundle a resource bundle to localize {@code format}; can be
     * {@code null}.
     * @param format the string message format in {@link
     * java.text.MessageFormat} format, (or a key in the message
     * catalog if {@code bundle} is not {@code null}); can be {@code null}.
     * @param params an optional list of parameters to the message (may be
     * none).
     *
     */
    @Override
    public void log(Level level, ResourceBundle bundle, String format, Object... params) {

    }
}
