package br.com.pointel.wiz_jarch.flow;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Manages the text history (undo/redo states) for multiple files.
 * Provides functionality to track text changes, limit history size, 
 * and perform undo and redo operations directly on the filesystem.
 *
 * @author emuvi
 */
public class TextHistory {

    private final Integer size;
    private final Map<File, History> notesHistory = new HashMap<>();

    /**
     * Creates a new TextHistory with the specified maximum number of states to remember per file.
     * 
     * @param size The maximum number of history states per file.
     */
    public TextHistory(Integer size) {
        this.size = size;
    }

    /**
     * Gets the maximum number of history states allowed per file.
     * 
     * @return The maximum history size.
     */
    public int getMaxSize() {
        return size;
    }

    /**
     * Pushes a new source state to the file's history.
     * 
     * @param file   The target file.
     * @param source The source text to push.
     */
    public void push(File file, String source) {
        getHistory(file).push(source);
    }

    /**
     * Undoes the last change for the given file, rewriting the file with its previous state.
     * 
     * @param file The file to undo.
     * @throws Exception If there is no previous state to undo.
     */
    public void undo(File file) throws Exception {
        getHistory(file).undo();
    }

    /**
     * Redoes the next change for the given file, rewriting the file with its next state.
     * 
     * @param file The file to redo.
     * @throws Exception If there is no next state to redo.
     */
    public void redo(File file) throws Exception {
        getHistory(file).redo();
    }

    /**
     * Reverts the file all the way back to its first recorded state.
     * 
     * @param file The target file.
     * @throws Exception If there is no previous state to undo.
     */
    public void undoAll(File file) throws Exception {
        getHistory(file).undoAll();
    }

    /**
     * Advances the file all the way forward to its latest recorded state.
     * 
     * @param file The target file.
     * @throws Exception If there is no forward state to redo.
     */
    public void redoAll(File file) throws Exception {
        getHistory(file).redoAll();
    }
    
    /**
     * Checks if an undo operation is available for the given file.
     * 
     * @param file The target file.
     * @return true if undo is possible, false otherwise.
     */
    public boolean canUndo(File file) {
        return notesHistory.containsKey(file) && notesHistory.get(file).canUndo();
    }

    /**
     * Checks if a redo operation is available for the given file.
     * 
     * @param file The target file.
     * @return true if redo is possible, false otherwise.
     */
    public boolean canRedo(File file) {
        return notesHistory.containsKey(file) && notesHistory.get(file).canRedo();
    }

    /**
     * Returns the current source state tracked in history for the given file.
     * 
     * @param file The target file.
     * @return The current source state, or null if not tracking.
     */
    public String getCurrentSource(File file) {
        return notesHistory.containsKey(file) ? notesHistory.get(file).getCurrentSource() : null;
    }

    /**
     * Returns the source state that would be applied upon undo.
     * 
     * @param file The target file.
     * @return The undo source state, or null if cannot undo.
     */
    public String peekUndo(File file) {
        return notesHistory.containsKey(file) ? notesHistory.get(file).peekUndo() : null;
    }

    /**
     * Returns the source state that would be applied upon redo.
     * 
     * @param file The target file.
     * @return The redo source state, or null if cannot redo.
     */
    public String peekRedo(File file) {
        return notesHistory.containsKey(file) ? notesHistory.get(file).peekRedo() : null;
    }

    /**
     * Gets the number of states currently stored for the given file.
     * 
     * @param file The target file.
     * @return The number of history states.
     */
    public int getHistorySize(File file) {
        return notesHistory.containsKey(file) ? notesHistory.get(file).size() : 0;
    }

    /**
     * Gets the current active history index for the file.
     * 
     * @param file The target file.
     * @return The history index, or -1 if not tracking.
     */
    public int getHistoryIndex(File file) {
        return notesHistory.containsKey(file) ? notesHistory.get(file).getIndex() : -1;
    }

    /**
     * Gets the original source text (the very first recorded state) for the file.
     * 
     * @param file The target file.
     * @return The original source text, or null if not tracking.
     */
    public String getOriginalSource(File file) {
        return notesHistory.containsKey(file) ? notesHistory.get(file).getOriginalSource() : null;
    }

    /**
     * Returns a copy of all the source states currently tracked for the given file.
     * 
     * @param file The target file.
     * @return A list of source states.
     */
    public List<String> getSources(File file) {
        return notesHistory.containsKey(file) ? notesHistory.get(file).getSources() : new ArrayList<>();
    }

    /**
     * Returns a set of all files currently tracked in the history.
     * 
     * @return A set of tracked files.
     */
    public Set<File> getTrackedFiles() {
        return notesHistory.keySet();
    }

    /**
     * Checks if the history is tracking the specified file.
     * 
     * @param file The target file.
     * @return true if the file is tracked, false otherwise.
     */
    public boolean isTracking(File file) {
        return notesHistory.containsKey(file);
    }

    /**
     * Checks if the file is currently at its original recorded state.
     * 
     * @param file The target file.
     * @return true if it is at the original state, false otherwise.
     */
    public boolean isAtOriginalState(File file) {
        return notesHistory.containsKey(file) && notesHistory.get(file).getIndex() == 0;
    }

    /**
     * Checks if the file is currently at its latest recorded state.
     * 
     * @param file The target file.
     * @return true if it is at the latest state, false otherwise.
     */
    public boolean isAtLatestState(File file) {
        if (!notesHistory.containsKey(file)) return false;
        History h = notesHistory.get(file);
        return h.size() > 0 && h.getIndex() == h.size() - 1;
    }

    /**
     * Checks if the given text differs from the currently tracked history state for the file.
     * 
     * @param file The target file.
     * @param currentText The text to compare.
     * @return true if the text is different or history is empty, false if they match exactly.
     */
    public boolean isModified(File file, String currentText) {
        String lastTracked = getCurrentSource(file);
        if (lastTracked == null) {
            return currentText != null && !currentText.isEmpty();
        }
        return !lastTracked.equals(currentText);
    }

    /**
     * Reads the current contents of the physical file and pushes it as a new history state.
     * 
     * @param file The target file.
     * @throws Exception If the file cannot be read.
     */
    public void pushFileContent(File file) throws Exception {
        String content = Files.readString(file.toPath(), StandardCharsets.UTF_8);
        push(file, content);
    }

    /**
     * Clears the history for a specific file.
     * 
     * @param file The file to clear.
     */
    public void clear(File file) {
        notesHistory.remove(file);
    }

    /**
     * Clears all history for all files.
     */
    public void clean() {
        notesHistory.clear();
    }

    /**
     * Retrieves the history tracker for a specific file, creating one if it doesn't exist.
     * 
     * @param file The file.
     * @return The history tracker for the file.
     */
    private History getHistory(File file) {
        var history = notesHistory.get(file);
        if (history == null) {
            history = new History(file);
            notesHistory.put(file, history);
        }
        return history;
    }

    /**
     * Represents the undo/redo state history for a single file.
     */
    private class History {

        private final File file;
        private final List<String> sources;
        private Integer index;

        /**
         * Initializes a new history tracker for a file.
         * 
         * @param file The file to track.
         */
        public History(File file) {
            this.file = file;
            this.sources = new ArrayList<>();
            this.index = -1;
        }

        /**
         * Pushes a new source string into the history, adjusting the index and 
         * clearing forward states if necessary. Limits the size to the configured max.
         * 
         * @param source The new text source.
         */
        public void push(String source) {
            assert source != null;
            if (index > -1 && source.equals(sources.get(index))) {
                return;
            }
            if (index + 1 < sources.size() && source.equals(sources.get(index + 1))) {
                index++;
                return;
            }
            index++;
            
            // Remove any forward history (redo states) before adding a new state
            while (sources.size() > index) {
                sources.remove(sources.size() - 1);
            }
            
            sources.add(source);
            
            // Ensure the history size doesn't exceed the limit
            while (sources.size() > size) {
                sources.remove(0);
                index--;
            }
        }

        /**
         * Checks whether an undo operation can be performed.
         * 
         * @return true if there is a previous state.
         */
        public boolean canUndo() {
            return index > 0;
        }

        /**
         * Checks whether a redo operation can be performed.
         * 
         * @return true if there is a forward state.
         */
        public boolean canRedo() {
            return index >= 0 && index < sources.size() - 1;
        }

        /**
         * Reverts to the previous state in history and writes it to the file.
         * 
         * @throws Exception If undo is not possible.
         */
        public void undo() throws Exception {
            if (!canUndo()) {
                throw new Exception("No source in the history to undo.");
            }
            index--;
            rewrite();
        }

        /**
         * Advances to the next state in history and writes it to the file.
         * 
         * @throws Exception If redo is not possible.
         */
        public void redo() throws Exception {
            if (!canRedo()) {
                throw new Exception("No source in the history to redo.");
            }
            index++;
            rewrite();
        }

        /**
         * Undoes all changes, jumping to the first recorded state.
         * 
         * @throws Exception If undo is not possible.
         */
        public void undoAll() throws Exception {
            if (!canUndo()) {
                throw new Exception("No source in the history to undo.");
            }
            index = 0;
            rewrite();
        }

        /**
         * Redoes all changes, jumping to the latest recorded state.
         * 
         * @throws Exception If redo is not possible.
         */
        public void redoAll() throws Exception {
            if (!canRedo()) {
                throw new Exception("No source in the history to redo.");
            }
            index = sources.size() - 1;
            rewrite();
        }
        
        /**
         * Gets the source text at the current index.
         * 
         * @return The current source text, or null if empty.
         */
        public String getCurrentSource() {
            if (index >= 0 && index < sources.size()) {
                return sources.get(index);
            }
            return null;
        }

        /**
         * Gets the current index in the history tracker.
         * 
         * @return The current index.
         */
        public int getIndex() {
            return index;
        }

        /**
         * Gets the original source state.
         * 
         * @return The very first state.
         */
        public String getOriginalSource() {
            return sources.isEmpty() ? null : sources.get(0);
        }

        /**
         * Gets the source text that would be applied upon undo.
         * 
         * @return The previous source text, or null if cannot undo.
         */
        public String peekUndo() {
            if (canUndo()) {
                return sources.get(index - 1);
            }
            return null;
        }

        /**
         * Gets the source text that would be applied upon redo.
         * 
         * @return The next source text, or null if cannot redo.
         */
        public String peekRedo() {
            if (canRedo()) {
                return sources.get(index + 1);
            }
            return null;
        }

        /**
         * Gets the number of tracked states.
         * 
         * @return The state count.
         */
        public int size() {
            return sources.size();
        }

        /**
         * Gets all the recorded source states.
         * 
         * @return A list of tracked states.
         */
        public List<String> getSources() {
            return new ArrayList<>(sources);
        }

        /**
         * Rewrites the physical file with the text from the current index.
         * 
         * @throws Exception If a file system error occurs.
         */
        private void rewrite() throws Exception {
            var source = sources.get(index);
            Files.writeString(file.toPath(), source, StandardCharsets.UTF_8);
        }

    }

}
