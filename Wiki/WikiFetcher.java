/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Wiki;

/**
 *
 * @author BintangDiLangit
 */
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class WikiFetcher {

    // menyiapkan batas waktu saat mengambil source pada alamat yang dituju
    private long lastRequestTime = -1;
    private long minInterval = 1000;

    /*
     Fetches and parses a URL string, returning a list of paragraph elements.
     Pada method fetchWikipedia berfungsi untuk mengambil data(source) dari sebuah
     web yang dituju, dimana method ini berparameter Url (alamat yang di inignkan)
     */
    public Elements fetchWikipedia(String url) throws IOException {
        /*
            Method sleepIfNeeded berfungsi untuk menghitung waktu proses mengambil source
            pada method fetchWikipedia, apabila waktu telah melebihi batas yang sudah ditentukan
            maka akan muncul pesan error :
            System.err.println("Warning: sleep interrupted in fetchWikipedia.");
         */
        sleepIfNeeded();

        // Menyiapkan koneksi ke alamat yang dituju
        Connection conn = Jsoup.connect(url);
        // Mengambil source
        Document doc = conn.get();

        // Menyeleksi source yang sudah di downlad/ambil berdasarkan id "mw-content-text"
        Element content = doc.getElementById("mw-content-text");

        // menyeleksi lebih dalam/detail dengan memilih tag <p>
        Elements paras = content.select("p");
        // mengembalikan nilai yan sudah di seleksi tadi
        return paras;
    }

    /*
     Reads the contents of a Wikipedia page from src/resources.
     Pada method readWikipedia ini berfungsi untuk mengambil source dari sebuah file
     yang sebelumnya sudah dibuat yang dimana file tersebut berisi source html
     web yang diingikan, method ini berparamater url, jadi method ini dapat mengambil dan menampilkan sebuah source
     dari file html yang sudah disiapkan.
     */
    public Elements readWikipedia(String url) throws IOException {
        URL realURL = new URL(url);

        // membuat seperator slash "/"
        String slash = File.separator;
        /*
            menyiapkan nama alamat file yang dimana akan menjadi
            "resources/en.wikipedia.org/wiki/Java_(programming_language)"
         */
        String filename = "resources" + slash + realURL.getHost() + realURL.getPath();
        System.out.println(filename);
        // membaca file yang telah dibuat tadi
        InputStream stream = WikiFetcher.class.getClassLoader().getResourceAsStream(filename);
        // mengambil source dengan aturan encoding UTF-8 dari file
        Document doc = Jsoup.parse(stream, "UTF-8", filename);

        // Menyeleksi source yang sudah di downlad/ambil berdasarkan id "mw-content-text"
        Element content = doc.getElementById("mw-content-text");
        // menyeleksi lebih dalam/detail dengan memilih tag <p>
        Elements paras = content.select("p");
        // mengembalikan nilai paras
        return paras;
    }

    /*
     Rate limits by waiting at least the minimum interval between requests.
     Menghitung batas waktu permintaan saat mengambil source/data dari web
     */
    private void sleepIfNeeded() {
        if (lastRequestTime != -1) {
            long currentTime = System.currentTimeMillis();
            long nextRequestTime = lastRequestTime + minInterval;
            if (currentTime < nextRequestTime) {
                try {
                    //System.out.println("Sleeping until " + nextRequestTime);
                    Thread.sleep(nextRequestTime - currentTime);
                } catch (InterruptedException e) {
                    System.err.println("Warning: sleep interrupted in fetchWikipedia.");
                }
            }
        }
        lastRequestTime = System.currentTimeMillis();
    }

    public static void main(String[] args) throws IOException {
        WikiFetcher wf = new WikiFetcher();
        // menyiapkan url
        String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";

        // menggunakan method readWikiedia atau fetchWikipedia
        Elements paragraphs = wf.readWikipedia(url);
        // Elements paragraphs = wf.fetchWikipedia(url);

        // menampilkan source/data yang sudah di ambil dari web
        for (Element paragraph : paragraphs) {
            System.out.println(paragraph);
        }
    }
}
