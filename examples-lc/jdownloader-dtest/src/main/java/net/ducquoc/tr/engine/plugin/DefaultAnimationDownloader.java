package net.ducquoc.tr.engine.plugin;

import net.ducquoc.tr.engine.download.LftpWgetDownloader;

public class DefaultAnimationDownloader {

    private String webLink;

    public DefaultAnimationDownloader() {
        this.webLink = WebHelper.DEFAULT_LINK;
    }

    public DefaultAnimationDownloader(String webLink) {
        this.webLink = webLink;
    }

    public void downAnimation() {
        downAnimation(this.webLink);
    }

    public static void downAnimation(String webLink) {
        System.out.println("Downloading... " + webLink);
        LftpWgetDownloader.downAnimation(webLink);
        System.out.println("Complete Download " + webLink);
    }

}
