package com.example.dictionary.utils;

import javafx.util.Callback;
import org.controlsfx.control.textfield.AutoCompletionBinding;

import java.util.*;

public abstract class SuggestionProvider<T> implements Callback<AutoCompletionBinding.ISuggestionRequest, Collection<T>> {

    private final List<T> possibleSuggestions = new ArrayList<T>();
    private final Object possibleSuggestionsLock = new Object();


    /**
     * Add the given new possible suggestions to this  SuggestionProvider
     * @param newPossible
     */
    public void addPossibleSuggestions(T... newPossible){     
        addPossibleSuggestions(Arrays.asList(newPossible));
    }

    /**
     * Add the given new possible suggestions to this  SuggestionProvider
     * @param newPossible
     */
    public void addPossibleSuggestions(Collection<T> newPossible){
        synchronized (possibleSuggestionsLock) {
            possibleSuggestions.addAll(newPossible);
        }
    }

    /**
     * Remove all current possible suggestions
     */
    public void clearSuggestions(){
        synchronized (possibleSuggestionsLock) {
            possibleSuggestions.clear();
        }
    }

    @Override
    public final Collection<T> call(final AutoCompletionBinding.ISuggestionRequest request) {
        List<T> suggestions = new ArrayList<>();
        if(!request.getUserText().isEmpty()){
            synchronized (possibleSuggestionsLock) {
                for (T possibleSuggestion : possibleSuggestions) {
                    if(isMatch(possibleSuggestion, request)){
                        suggestions.add(possibleSuggestion);
                    }
                }
            }
            Collections.sort(suggestions, getComparator());
        }
        return suggestions;
    }

    /**
     * Get the comparator to order the suggestions
     * @return
     */
    protected abstract Comparator<T> getComparator();

    /**
     * Check the given possible suggestion is a match (is a valid suggestion)
     * @param suggestion
     * @param request
     * @return
     */
    protected abstract boolean isMatch(T suggestion, AutoCompletionBinding.ISuggestionRequest request);


    /***************************************************************************
     *                                                                         *
     * Static methods                                                          *
     *                                                                         *
     **************************************************************************/


    /**
     * Create a default suggestion provider based on the toString() method of the generic objects
     * @param possibleSuggestions All possible suggestions
     * @return
     */
    public static <T> SuggestionProvider<T> create(Collection<T> possibleSuggestions){
        return create(null, possibleSuggestions);
    }

    /**
     * Create a default suggestion provider based on the toString() method of the generic objects
     * using the provided stringConverter
     * 
     * @param stringConverter A stringConverter which converts generic T into a string
     * @param possibleSuggestions All possible suggestions
     * @return
     */
    public static <T> SuggestionProvider<T> create(Callback<T, String> stringConverter, Collection<T> possibleSuggestions){
        SuggestionProviderString<T> suggestionProvider = new SuggestionProviderString<>(stringConverter);
        suggestionProvider.addPossibleSuggestions(possibleSuggestions);
        return suggestionProvider;
    }



    /***************************************************************************
     *                                                                         *
     * Default implementations                                                 *
     *                                                                         *
     **************************************************************************/


    /**
     * This is a simple string based suggestion provider.
     * All generic suggestions T are turned into strings for processing.
     * 
     */
    private static class SuggestionProviderString<T> extends SuggestionProvider<T> {

        private Callback<T, String> stringConverter;

        private final Comparator<T> stringComparator = new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                String o1str = stringConverter.call(o1);
                String o2str = stringConverter.call(o2);
                return o1str.compareTo(o2str);
            }
        };

        /**
         * Create a new SuggestionProviderString
         * @param stringConverter
         */
        public SuggestionProviderString(Callback<T, String> stringConverter){
            this.stringConverter = stringConverter;

            // In case no stringConverter was provided, use the default strategy
            if(this.stringConverter == null){
                this.stringConverter = new Callback<T, String>() {
                    @Override
                    public String call(T obj) {
                        return obj != null ? obj.toString() : ""; //$NON-NLS-1$
                    }
                };
            }
        }

        /**{@inheritDoc}*/
        @Override
        protected Comparator<T> getComparator() {
            return stringComparator;
        }

        /**{@inheritDoc}*/
        @Override
        protected boolean isMatch(T suggestion, AutoCompletionBinding.ISuggestionRequest request) {
            String userTextLower = request.getUserText().toLowerCase();
            String suggestionStr = suggestion.toString().toLowerCase();
            return suggestionStr.contains(userTextLower) 
                    && !suggestionStr.equals(userTextLower);
        }
    }
}