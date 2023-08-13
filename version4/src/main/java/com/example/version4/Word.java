package com.example.version4;

public class Word {
    private String wordTarget;
    private String wordExplain;
    private String spelling;
    private String example;
    private String wordForm;

    public Word(String wordTarget, String wordExplain) {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
    }
    public Word(String wordTarget, String wordExplain, String spelling) {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
        this.spelling = spelling;
    }
    public Word(String wordTarget, String wordExplain, String wordForm, String spelling, String example) {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
        this.spelling = spelling;
        this.example = example;
        this.wordForm = wordForm;
    }

    public String getWordTarget() {
        return wordTarget;
    }

    public void setWordTarget(String wordTarget) {
        this.wordTarget = wordTarget;
    }

    public String getWordExplain() {
        return wordExplain;
    }

    public void setWordExplain(String wordExplain) {
        this.wordExplain = wordExplain;
    }

    public String getSpelling() {
        return spelling;
    }

    public void setSpelling(String spelling) {
        this.spelling = spelling;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getWordForm() {
        return wordForm;
    }

    public void setWordForm(String wordForm) {
        this.wordForm = wordForm;
    }
    public void update(Word word) {
        this.wordTarget = word.getWordTarget();
        this.wordExplain = word.getWordExplain();
        this.wordForm = word.getWordForm();
        this.spelling = word.getSpelling();
        this.example = word.getExample();
    }
}
