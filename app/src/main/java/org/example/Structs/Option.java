package org.example.Structs;


public class Option {
    private String option;
    private EOptionType option_type;
    private String id;

    public Option(String option, EOptionType option_type) {
        this.option = option;
        this.option_type = option_type;
    }

    public String getOption() {
        return option;
    }

    public EOptionType getOptionType() {
        return option_type;
    }

    public boolean isCorrect() {
        return option_type == EOptionType.CORRECT;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setOptionType(EOptionType option_type) {
        this.option_type = option_type;
    }
}
