/*
   Copyright 2009-2021 PrimeTek.

   Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.dandandog.blog.view;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component("guestPreferences")
public class GuestPreferences implements Serializable {

    private String theme = "blue";

    private String menuMode = "layout-menu-static";

    private String menuColor = "light";

    private boolean orientationRTL;

    private String inputStyle = "outlined";

    private List<MenuTheme> menuThemes;

    @PostConstruct
    public void init() {
        menuThemes = new ArrayList<>();
        menuThemes.add(new MenuTheme("Amber", "amber", "#f66b0b", "#efd417"));
        menuThemes.add(new MenuTheme("Blue", "blue", "#2872B4", "#26BED0"));
        menuThemes.add(new MenuTheme("Blue-Grey", "bluegrey", "#16222A", "#3A6073"));
        menuThemes.add(new MenuTheme("Cyan", "cyan", "#12AABD", "#C4C988"));
        menuThemes.add(new MenuTheme("Dark-Blue", "darkblue", "#4b6cb7", "#182848"));
        menuThemes.add(new MenuTheme("Deep-Orange", "deeporange", "#FF2525", "#FFA43B"));
        menuThemes.add(new MenuTheme("Deep-Purple", "deeppurple", "#5023A0", "#7A318D"));
        menuThemes.add(new MenuTheme("Green", "green", "#07b750", "#c7d41b"));
        menuThemes.add(new MenuTheme("Grey", "grey", "#333333", "#5A5D60"));
        menuThemes.add(new MenuTheme("Indigo", "indigo", "#1e469a", "#49a7c1"));
        menuThemes.add(new MenuTheme("Lime", "lime", "#53C018", "#C6D309"));
        menuThemes.add(new MenuTheme("Mojito", "mojito", "#1D976C", "#93F9B9"));
        menuThemes.add(new MenuTheme("Pink", "pink", "#e02365", "#db3c06"));
        menuThemes.add(new MenuTheme("Purple", "purple", "#B721FF", "#21D4FD"));
        menuThemes.add(new MenuTheme("Yellow", "yellow", "#F39C05", "#F3C704"));
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getMenuMode() {
        return menuMode;
    }

    public void setMenuMode(String menuMode) {
        this.menuMode = menuMode;
    }

    public String getMenuColor() {
        return menuColor;
    }

    public void setMenuColor(String menuColor) {
        this.menuColor = menuColor;
    }

    public boolean isOrientationRTL() {
        return orientationRTL;
    }

    public void setOrientationRTL(boolean orientationRTL) {
        this.orientationRTL = orientationRTL;
    }

    public String getInputStyleClass() {
        return this.inputStyle.equals("filled") ? "ui-input-filled" : "";
    }

    public String getInputStyle() {
        return inputStyle;
    }

    public void setInputStyle(String inputStyle) {
        this.inputStyle = inputStyle;
    }

    public List<MenuTheme> getMenuThemes() {
        return menuThemes;
    }

    public class MenuTheme {

        private String name;

        private String file;

        private String color1;

        private String color2;

        public MenuTheme() {
        }

        public MenuTheme(String name, String file, String color1, String color2) {
            this.name = name;
            this.file = file;
            this.color1 = color1;
            this.color2 = color2;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public String getColor1() {
            return color1;
        }

        public void setColor1(String color1) {
            this.color1 = color1;
        }

        public String getColor2() {
            return color2;
        }

        public void setColor2(String color2) {
            this.color2 = color2;
        }
    }
}
