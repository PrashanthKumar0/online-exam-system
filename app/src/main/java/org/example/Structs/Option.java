package org.example.Structs;


public class Option {
    private String option;
    private EOptionType option_type;

    public Option(String option, EOptionType option_type){
        this.option = option;
        this.option_type = option_type;
    }

    public String getOption() {
        return option;
    }
    public EOptionType getOptionType() {
        return option_type;
    }
    public void setOption(String option) {
        this.option = option;
    }
    public void setOptionType(EOptionType option_type) {
        this.option_type = option_type;
    }
}
