package com.codedifferently.tankofamerica.domain.menu;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.jline.reader.LineReader;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class Startup {
    @Lazy
    private LineReader lineReader;

    @ShellMethod("Start program")
    public String startup() {
        System.out.println("Welcome to \nTank of America\n\n" +
                "    .--._____,\n" +
                " .-='=='==-, \"\n" +
                "(O_o_o_o_o_O)    \n\n");

        return null;
    }

    @ShellMethod("Greets the user and prompts for sign up/sign in.")
    public String welcomeScreen(@ShellOption({"-C","--signon"}) String userchoice){
        return String.format("you have selected %s.", userchoice);

    }

}
