package net.ducquoc.tr.engine.plugin;

import net.ducquoc.tr.engine.download.LftpWgetDownloader;

public class VideoAnimationDownloader {

    private String webLink;

    public VideoAnimationDownloader() {
        this.webLink = WebHelper.MAGIC_LINK4;
    }

    public VideoAnimationDownloader(String webLink) {
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
