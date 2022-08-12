package net.ducquoc.tr.engine;

public class WebHelper {

    public static String DEFAULT_LINK = "http://letmegooglethat.com?q=song+hung+ky+hiep"; //"https://ducquoc.github.io/elsa/images/pic08.jpg";
    public static String MAGIC_LINK1 = "https://ducquoc.github.io/elsa/images/pic03.jpg";
    public static String MAGIC_LINK2 = "https://ducquoc.github.io/elsa/images/pic04-2.jpg";

    private String webLink;

//    public static void main(String[] args) {
//
//        System.out.print(MAGIC_LINK1 + " " + MAGIC_LINK2);
//    }

    public WebHelper() {
        this.webLink = DEFAULT_LINK;
    }

    public WebHelper(String webLink) {
        this.webLink = webLink;
    }

    public void downAnimation() {
        System.out.println(webLink);
    }

    public static void downAnimation(String webLink) {
        System.out.println(webLink);
    }

}
